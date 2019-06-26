package web;

import model.Vote;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.List;

import static model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;
import static web.RestaurantTestData.*;
import static util.TestUtil.readListFromJsonMvcResult;
import static web.UserTestData.*;

public class VoteTestData {

    public static final int VOTE_ID = START_SEQ + 18;
    public static final int VOTE_ID_2 = VOTE_ID + 1;

    public static final Vote VOTE_1 = new Vote(VOTE_ID, LocalDate.of(2019, 6, 5), RESTAURANT_1, USER);
    public static final Vote VOTE_2 = new Vote(VOTE_ID_2, LocalDate.now(), RESTAURANT_1, USER);

    public static final Vote CREATED_VOTE = new Vote(LocalDate.now(), RESTAURANT_1, USER_2);

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Iterable<Vote> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), expected);
    }
}
