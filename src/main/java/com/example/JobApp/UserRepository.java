package com.example.JobApp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    //for user registration
    Optional<User> findByEmail(String email);

    //for login and authentication
    boolean existsByEmail(String email);

    //for admin to get the users as per the role
    List<User> findByRole(Role role);

    //to get paged list
    Page<User> findByRole(Role role, Pageable pageable);

    //gives the registered users counts
    int countByRole(Role role);
}
