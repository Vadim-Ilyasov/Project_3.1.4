package ru.kata.spring.boot_security.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;



@Repository
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, Long> {

}
