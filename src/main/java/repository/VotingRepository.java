package repository;

import model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VotingRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> getByUserIdAndRegistered(int userId, LocalDate registered);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id=:userId ORDER BY v.registered DESC")
    List<Vote> getAllByUserId(@Param("userId") int userId);
}
