package web;

import model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import repository.VotingRepository;
import service.VotingService;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static web.RestaurantTestData.*;
import static util.TestUtil.readFromJson;
import static util.TestUtil.userHttpBasic;
import static web.UserTestData.*;
import static web.VoteTestData.*;

class VotingControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VotingController.REST_URL + '/';

    @Autowired
    private VotingRepository repository;

    @Test
    void testCreate() throws Exception {
        Vote created = new Vote(CREATED_VOTE);

        ResultActions action =  mockMvc.perform(post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_2)));

        Vote returned = readFromJson(action, Vote.class);
        created.setId(returned.getId());

        assertMatch(repository.getAllByUserId(USER_ID + 1), created);
    }

    @Test
    void testUpdateBefore() throws Exception {
        VotingService.setChangeVoteTime(LocalTime.MAX);

        Vote updated = new Vote(VOTE_2);
        updated.setRestaurant(RESTAURANT_2);

        mockMvc.perform(put(REST_URL + VOTE_ID_2)
                .param("restaurantId", String.valueOf(RESTAURANT_ID + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());

        assertMatch(repository.getAllByUserId(USER_ID), updated, VOTE_1);
    }

    @Test
    void testUpdateAfter() throws Exception {
        VotingService.setChangeVoteTime(LocalTime.MIN);

        mockMvc.perform(put(REST_URL + VOTE_ID)
                .param("restaurantId", String.valueOf(RESTAURANT_ID + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER)))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void testGetAllByUser() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(VOTE_2, VOTE_1)));
    }

    @Test
    void testCreateForbidden() throws Exception {
        mockMvc.perform(post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreateUnauth() throws Exception {
        mockMvc.perform(post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID)))
                .andExpect(status().isUnauthorized());
    }
}