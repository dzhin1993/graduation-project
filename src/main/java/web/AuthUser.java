package web;

import model.User;
import org.springframework.lang.NonNull;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    @NonNull
    private final User user;

    public AuthUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.getId();
    }
}