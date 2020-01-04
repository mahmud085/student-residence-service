package org.authentication.service.models;

import org.authentication.common.Messages;
import org.authentication.common.validation.annotations.HasValue;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "loginRequest")
public class LoginRequest {
    @HasValue(message = Messages.REQUIRED_USER_ID)
    private String userId;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
