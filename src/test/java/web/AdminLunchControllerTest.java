package web;

import model.Lunch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import repository.LunchRepository;
import web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static web.LunchTestData.*;
import static util.TestUtil.*;
import static web.UserTestData.*;

class AdminLunchControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminLunchController.REST_URL + '/';

    @Autowired
    private LunchRepository repository;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(LUNCHES_FOR_RESTAURANT_1));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + LUNCH_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Lunch.class), LUNCH_1));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + LUNCH_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertMatch(repository.findAll(), LUNCH_2, LUNCH_3, LUNCH_4);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(put(REST_URL + LUNCH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(UPDATED_LUNCH))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertMatch(repository.findById(LUNCH_ID).orElse(null), UPDATED_LUNCH);
    }

    @Test
    void testUpdateNotValid() throws Exception {
        Lunch updated = new Lunch( UPDATED_LUNCH);
        updated.setName("");

        mockMvc.perform(put(REST_URL + LUNCH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testCreate() throws Exception {
        Lunch created = new Lunch(CREATED_LUNCH);

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)));

        Lunch returned = readFromJson(action, Lunch.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(repository.findAll(), LUNCH_1, LUNCH_2, LUNCH_3, LUNCH_4, created);
    }

    @Test
    void testCreateNotValid() throws Exception {
        Lunch created = new Lunch(CREATED_LUNCH);
        created.setName("");

        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL + LUNCH_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + LUNCH_ID))
                .andExpect(status().isUnauthorized());
    }
}