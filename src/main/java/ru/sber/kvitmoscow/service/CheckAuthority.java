package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.Authorization;

import javax.security.auth.message.AuthException;

public class CheckAuthority {
    public static void check(int userId) throws AuthException {
        if (userId != Authorization.id()){
            throw new AuthException("Неверный запрос!");
        }
    }
}
