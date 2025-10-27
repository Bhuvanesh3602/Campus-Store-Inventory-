package com.example.databasedomo.repository;

import com.example.databasedomo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndContactNo(Long userId, String contactNo);
}
