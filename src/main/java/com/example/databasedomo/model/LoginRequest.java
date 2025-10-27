package com.example.databasedomo.model;

public class LoginRequest {
    private Long userId;
    private String contactNo;

    // ✅ Default constructor (needed for JSON deserialization)
    public LoginRequest() {
    }

    // ✅ Constructor with parameters
    public LoginRequest(Long userId, String contactNo) {
        this.userId = userId;
        this.contactNo = contactNo;
    }

    // ✅ Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
