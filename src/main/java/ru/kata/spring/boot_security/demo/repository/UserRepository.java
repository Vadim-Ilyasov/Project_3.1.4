package ru.kata.spring.boot_security.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("Select u from User u left join fetch u.roles where u.username=:username")
//    User findByUsername(@Param("username")String username);

    Optional<User> findByUsername(String username);
}
