package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с таким именем не найден");
        }
        return userRepository.findByUsername(username).get();
    }

    @Override
    public User findUserById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с таким ID не найден");
        }
        return userRepository.findById(id).get();
    }


    @Override
    @Transactional
    public void updateUser(User updateUser, Long id) {
        User user_from_DB = userRepository.findById(id).get(); //Нашли в БД, кого хотим редактировать
        user_from_DB.setUsername(updateUser.getUsername());
        user_from_DB.setName(updateUser.getName()); //сохраняет
        user_from_DB.setAge(updateUser.getAge()); //сохраняет
        user_from_DB.setEmail(updateUser.getEmail()); //сохраняет
        user_from_DB.setRoles(updateUser.getRoles());

        if (user_from_DB.getPassword().equals(updateUser.getPassword())) {
            userRepository.save(user_from_DB);
        } else {
            user_from_DB.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            userRepository.save(user_from_DB);
        }

        userRepository.save(user_from_DB);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);

    }


}
