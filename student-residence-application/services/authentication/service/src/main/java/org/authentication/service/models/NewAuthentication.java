package org.authentication.service.models;

import java.time.LocalDate;

public class NewAuthentication {
    private String userId;
    private LocalDate generatedTime;
    private LocalDate expiryTime;
    private String accessToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(LocalDate generatedTime) {
        this.generatedTime = generatedTime;
    }

    public LocalDate getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDate expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
