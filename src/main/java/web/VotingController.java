package web;

import lombok.extern.slf4j.Slf4j;
import model.Vote;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import repository.VotingRepository;
import service.VotingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = VotingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VotingController {

    public static final String REST_URL = "/votes";

    private final VotingService service;
    private final VotingRepository repository;

    public VotingController(VotingService service, VotingRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PutMapping("/{voteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @PathVariable("voteId") int voteId,
                       @RequestParam int restaurantId) {
        log.info("update vote {} for restaurant {}", voteId, restaurantId);
        service.update(authUser.id(), restaurantId, voteId);
    }

    @PostMapping
    public Vote create(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        log.info("create vote for restaurant {}", restaurantId);
        return service.create(authUser.id(), restaurantId);
    }

    @GetMapping
    public List<Vote> getAllByUser(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all votes for user {}", authUser);
        return repository.getAllByUserId(authUser.id());
    }
}
