package org.authentication.service.models;

import org.authentication.common.Messages;
import org.authentication.common.validation.annotations.HasValue;

public class NewUser {
    @HasValue(message = Messages.REQUIRED_USER_ID)
    private String userId;
    private String firstname;
    private String lastname;
    @HasValue(message = Messages.REQUIRED_USER_PASSWORD)
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

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
