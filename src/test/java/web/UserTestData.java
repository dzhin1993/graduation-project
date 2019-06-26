package web;

import model.Role;
import model.User;

import java.time.LocalDate;
import java.util.EnumSet;

import static model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final int USER_ID = START_SEQ;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", LocalDate.now(), EnumSet.of(Role.ROLE_USER));
    public static final User USER_2 = new User(USER_ID + 1, "User2", "user2@yandex.ru", "password2", LocalDate.now(), EnumSet.of(Role.ROLE_USER));
    public static final User ADMIN = new User(USER_ID + 2, "Admin", "admin@gmail.com", "admin", LocalDate.now(), EnumSet.of(Role.ROLE_ADMIN));
}
