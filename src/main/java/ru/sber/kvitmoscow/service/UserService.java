package ru.sber.kvitmoscow.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.sber.kvitmoscow.model.User;
import ru.sber.kvitmoscow.to.UserTo;

public interface UserService extends BaseToService<User, UserTo>, UserDetailsService {
    User getUserByName(String name);
}
