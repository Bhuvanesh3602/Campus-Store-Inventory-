package com.example.databasedomo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // optional but recommended
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // matches your schema
    private Long userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "contact_no", length = 20)
    private String contactNo;

    @Column(length = 20)
    private String role; // Student, Staff, Admin

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    // ✅ Default constructor
    public User() {
    }

    // ✅ Constructor with all fields (optional)
    public User(String name, String email, String contactNo, String role, LocalDateTime joinDate) {
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.role = role;
        this.joinDate = joinDate;
    }

    // ✅ Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
