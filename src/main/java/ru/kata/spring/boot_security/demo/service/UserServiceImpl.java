package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }


    @Override
    @Transactional
    public void updateUser(User updateUser, Long id) {
        User user_from_DB = userRepository.findById(id).get();
        user_from_DB.setUsername(updateUser.getUsername());
        user_from_DB.setName(updateUser.getName());
        user_from_DB.setAge(updateUser.getAge());
        user_from_DB.setEmail(updateUser.getEmail());
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
