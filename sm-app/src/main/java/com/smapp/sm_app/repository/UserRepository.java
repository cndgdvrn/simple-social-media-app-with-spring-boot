package com.smapp.sm_app.repository;

import com.smapp.sm_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    //Find all active users but not authenticated user
//    @Query("SELECT u FROM User u WHERE u.active = true AND u.id != ?1")
//    List<User> findAllActiveUsersButNotAuthenticated(Long id);

    @Query("SELECT u FROM User u WHERE u.active = true")
    List<User> findAllActiveUsers();

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
