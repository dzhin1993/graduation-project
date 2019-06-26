package web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import service.MenuService;
import to.MenuTo;
import web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static web.MenuTestData.*;
import static web.RestaurantTestData.RESTAURANT_ID;
import static util.TestUtil.readFromJson;
import static util.TestUtil.userHttpBasic;
import static web.UserTestData.ADMIN;
import static web.UserTestData.USER;

class MenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuController.REST_URL + '/';

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private MenuService service;

    @BeforeEach
    void clearCache() {
        cacheManager.getCache("menus").clear();
    }

    @Test
    void testGetCurrent() throws Exception {
        mockMvc.perform(get(REST_URL + "by-date"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(CURRENT_MENUS));
    }

    @Test
    void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "by-date")
                .param("date", "2019-06-05"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DATE_MENUS));
    }

    @Test
    void testGetByRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + "by-restaurant")
                .param("restaurantId", String.valueOf(RESTAURANT_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1_MENUS));
    }

    @Test
    void testCreate() throws Exception {
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(CREATED_SHORT_MENU))
                .with(userHttpBasic(ADMIN)));

        MenuTo returned = readFromJson(action, MenuTo.class);

        assertMatch(service.getByRestaurant(RESTAURANT_ID + 2), returned);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID))
                .param("date", "2019-06-05")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertMatch(service.getByRestaurant(RESTAURANT_ID), MENU_1);
    }

    @Test
    void testDeleteForbidden() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID))
                .param("date", "2019-06-05")
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }
}