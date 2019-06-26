package web;

import org.springframework.test.web.servlet.ResultMatcher;
import to.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static web.LunchTestData.LUNCH_ID;
import static web.RestaurantTestData.RESTAURANT_ID;
import static util.TestUtil.readListFromJsonMvcResult;

public class MenuTestData {

    public static final RestaurantTo RESTAURANT_TO_1 = new RestaurantTo(RESTAURANT_ID, "Dominos Pizza");
    public static final RestaurantTo RESTAURANT_TO_2 = new RestaurantTo(RESTAURANT_ID + 1, "Pizza Tempo");

    public static final BaseTo RESTAURANT_SHORT_3 = new BaseTo(RESTAURANT_ID + 2);

    public static final LunchTo LUNCH_TO_1 = new LunchTo(LUNCH_ID, "Маргарита", 10);
    public static final LunchTo LUNCH_TO_2 = new LunchTo(LUNCH_ID + 1, "Карбонара", 11);
    public static final LunchTo LUNCH_TO_3 = new LunchTo(LUNCH_ID + 2, "Барбекю", 12);
    public static final LunchTo LUNCH_TO_4 = new LunchTo(LUNCH_ID + 3, "Грибная", 13);

    public static final BaseTo LUNCH_SHORT_3 = new BaseTo(LUNCH_ID + 2);
    public static final BaseTo LUNCH_SHORT_4 = new BaseTo(LUNCH_ID + 3);

    public static final MenuTo MENU_1 = new MenuTo(LocalDate.now(), RESTAURANT_TO_1, List.of(LUNCH_TO_3, LUNCH_TO_4));
    public static final MenuTo MENU_2 = new MenuTo(LocalDate.now(), RESTAURANT_TO_2, List.of(LUNCH_TO_3, LUNCH_TO_4));

    public static final MenuTo MENU_3 = new MenuTo(LocalDate.of(2019, 6, 5), RESTAURANT_TO_1, List.of(LUNCH_TO_1, LUNCH_TO_2));
    public static final MenuTo MENU_4 = new MenuTo(LocalDate.of(2019, 6, 5), RESTAURANT_TO_2, List.of(LUNCH_TO_1, LUNCH_TO_2));

    public static final MenuShortTo CREATED_SHORT_MENU = new MenuShortTo(RESTAURANT_SHORT_3, List.of(LUNCH_SHORT_3, LUNCH_SHORT_4));

    public static final List<MenuTo> CURRENT_MENUS = List.of(MENU_1, MENU_2);
    public static final List<MenuTo> DATE_MENUS = List.of(MENU_3, MENU_4);
    public static final List<MenuTo> RESTAURANT_1_MENUS = List.of(MENU_1, MENU_3);

    public static void assertMatch(Iterable<MenuTo> actual, MenuTo... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<MenuTo> actual, Iterable<MenuTo> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Iterable<MenuTo> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, MenuTo.class), expected);
    }

}
