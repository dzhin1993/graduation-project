package service;

import model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.RestaurantRepository;
import repository.UserRepository;
import repository.VotingRepository;
import util.exception.VotingException;
import util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

import static util.ValidationUtil.assureIdConsistent;
import static util.ValidationUtil.checkNotFoundWithId;

@Service
public class VotingService {

    public static LocalTime CHANGE_VOTE_TIME = LocalTime.of(11, 0);

    private final VotingRepository votingRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    public VotingService(UserRepository userRepository,
                         RestaurantRepository restaurantRepository,
                         VotingRepository votingRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.votingRepository = votingRepository;
    }

    @Transactional
    public Vote create(int userId, int restaurantId) {
        if (votingRepository.getByUserIdAndRegistered(userId, LocalDate.now()).isPresent()) {
            throw new VotingException("current user is voted this day");
        }
        Vote vote = new Vote();
        return save(userId, restaurantId, vote);
    }

    @Transactional
    public void update(int userId, int restaurantId, int voteId) {
        if (LocalTime.now().isAfter(CHANGE_VOTE_TIME)) {
            throw new VotingException("current time must be before 11:00 pm");
        }

        Vote vote = votingRepository.getByUserIdAndRegistered(userId, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("not found vote on current day for update"));

        assureIdConsistent(vote, voteId);
        save(userId, restaurantId, vote);
    }

    private Vote save(int userId, int restaurantId, Vote vote) {
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));
        return votingRepository.save(vote);
    }

    public static void setChangeVoteTime(LocalTime changeVoteTime) {
        CHANGE_VOTE_TIME = changeVoteTime;
    }
}
