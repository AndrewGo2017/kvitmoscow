package ru.sber.kvitmoscow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sber.kvitmoscow.Authorization;
import ru.sber.kvitmoscow.model.User;
import ru.sber.kvitmoscow.repository.RoleRepository;
import ru.sber.kvitmoscow.repository.UserRepository;
import ru.sber.kvitmoscow.to.UserTo;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User save(UserTo entity) {
        User user = new User(
                entity.getId(),
                roleRepository.getOne(entity.getRole()),
                entity.getName(),
                entity.getPassword(),
                entity.getEmail()
        );

        return userRepository.save(user);
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return userRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findUserByName(name).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getUserByName(s);
        if (user == null)
            throw new UsernameNotFoundException("Пользователь (id)" + s + " не найден");

        return new Authorization(user);
    }
}
