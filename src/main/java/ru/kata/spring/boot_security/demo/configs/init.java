package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class init {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public init(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void initUsers() {
        //Создали роль юзер
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        roleRepository.save(userRole);


        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        roleRepository.save(adminRole);

        List<Role> userRoles = Arrays.asList(userRole);
        List<Role> adminRoles = Arrays.asList(adminRole, userRole);

        User admin = new User();
        admin.setUsername("Admin");
        admin.setName("Admin");
        admin.setSurname("Adminov");
        admin.setAge(67);
        admin.setEmail("admin@mail.ru");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        userRepository.save(admin);

        User user = new User();
        user.setUsername("User");
        user.setName("User");
        user.setSurname("Userov");
        user.setAge(82);
        user.setEmail("user@mail.ru");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(userRoles);
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("User2");
        user2.setName("User2");
        user2.setSurname("Pitson");
        user2.setAge(45);
        user2.setEmail("user2@mail.ru");
        user2.setPassword(passwordEncoder.encode("user2"));
        user2.setRoles(userRoles);
        userRepository.save(user2);

        User user3 = new User();
        user3.setUsername("User3");
        user3.setName("User3");
        user3.setSurname("Userson");
        user3.setAge(23);
        user3.setEmail("user3@mail.ru");
        user3.setPassword(passwordEncoder.encode("user3"));
        user3.setRoles(userRoles);
        userRepository.save(user3);
    }
}
