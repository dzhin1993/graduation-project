package web;

import model.Restaurant;
import org.springframework.test.web.servlet.ResultMatcher;
import util.TestUtil;

import java.util.List;

import static model.AbstractBaseEntity.START_SEQ;
import static util.TestUtil.*;

public class RestaurantTestData {

    public static final int RESTAURANT_ID = START_SEQ + 3;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID, "Dominos Pizza");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID + 1, "Pizza Tempo");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID + 2, "Super Pizza");

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);

    public static final Restaurant UPDATED_RESTAURANT = new Restaurant("обновленный ресторан");
    public static final Restaurant CREATED_RESTAURANT = new Restaurant("новый ресторан");

    static {
        UPDATED_RESTAURANT.setId(RESTAURANT_ID);
    }

    public static ResultMatcher contentJson(Iterable<Restaurant> expected) {
        return result -> TestUtil.assertMatch(readListFromJsonMvcResult(result, Restaurant.class), expected);
    }
}
