package org.authentication.service.models;

import org.authentication.common.Messages;
import org.authentication.common.validation.annotations.HasValue;

public class RegisterUser {
    @HasValue(message = Messages.REQUIRED_USER_ID)
    private String userId;
    private String name;
    @HasValue(message = Messages.REQUIRED_USER_EMAIL)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String password;

    private UserRole role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
