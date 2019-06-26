package web;

import model.Lunch;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static model.AbstractBaseEntity.START_SEQ;
import static util.TestUtil.*;

public class LunchTestData {

    public static final int LUNCH_ID = START_SEQ + 6;

    public static final Lunch LUNCH_1 = new Lunch(LUNCH_ID, "Маргарита", 10);
    public static final Lunch LUNCH_2 = new Lunch(LUNCH_ID + 1, "Карбонара", 11);
    public static final Lunch LUNCH_3 = new Lunch(LUNCH_ID + 2, "Барбекю", 12);
    public static final Lunch LUNCH_4 = new Lunch(LUNCH_ID + 3, "Грибная", 13);

    public static final Lunch UPDATED_LUNCH = new Lunch("обновленный ланч", 15);
    public static final Lunch CREATED_LUNCH = new Lunch("новый ланч", 10);

    static {
        UPDATED_LUNCH.setId(LUNCH_ID);
    }

    public static final List<Lunch> LUNCHES_FOR_RESTAURANT_1 = List.of(LUNCH_1, LUNCH_2, LUNCH_3, LUNCH_4);

    public static ResultMatcher contentJson(Iterable<Lunch> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Lunch.class), expected);
    }
}
