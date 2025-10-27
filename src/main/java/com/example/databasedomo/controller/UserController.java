package com.example.databasedomo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.databasedomo.model.User;
import com.example.databasedomo.model.LoginRequest;
import com.example.databasedomo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Objects; // added for safe comparison

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // allow frontend connections (optional)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ 1. Add a new user (with all details)
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok("✅ User added successfully!");
    }

    // ✅ 2. Get all users
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ 3. Get one user by user_id
    // ...existing code...
    // ✅ 3. Get one user by user_id
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ User not found with ID: " + userId);
        }
    }
    // ...existing code...

    // ✅ 4. Login using user_id and contact_no (fixed)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isPresent() && Objects.equals(userOpt.get().getContactNo(), request.getContactNo())) {
            return ResponseEntity.ok("✅ Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("❌ Invalid user ID or contact number.");
        }
    }

    // ✅ 5. Update user details by user_id
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUserById(@PathVariable Long userId, @RequestBody User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(userId);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Update all allowed fields
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setContactNo(updatedUser.getContactNo());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setJoinDate(updatedUser.getJoinDate());

            userRepository.save(existingUser);
            return ResponseEntity.ok("✅ User updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ User not found with ID: " + userId);
        }
    }

    // ✅ 6. Delete user by user_id
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return ResponseEntity.ok("🗑️ User deleted successfully (ID: " + userId + ")");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ User not found with ID: " + userId);
        }
    }
}
