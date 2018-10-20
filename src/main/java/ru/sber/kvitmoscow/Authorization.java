package ru.sber.kvitmoscow;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.sber.kvitmoscow.model.User;

import java.util.Collections;

import static java.util.Objects.requireNonNull;

public class Authorization extends org.springframework.security.core.userdetails.User {
    private User user;

    public Authorization(User user) {
        super(user.getName(), user.getPassword(), true, true, true, true, Collections.singletonList(user));
        this.user = user;
    }

    public static Authorization safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof Authorization) ? (Authorization) principal : null;
    }

    public static Authorization get() {
        Authorization user = safeGet();
        requireNonNull(user, "Неавторизованный пользователь");
        return user;
    }

    public static int id() {
        return get().user.getId();
    }

    public static String role() {
        return get().user.getRole().getName();
    }
}
