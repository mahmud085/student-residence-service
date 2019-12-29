package org.authentication.dataaccess.data.models;

import org.authentication.dataaccess.data.enums.UserType;

import javax.xml.bind.annotation.XmlRootElement;

//import org.appointment.service.adapters.AppointmentPriorityAdapter;
//import org.appointment.service.adapters.AppointmentStatusAdapter;
//import org.appointment.service.adapters.AppointmentTypeAdapter;

@XmlRootElement(name = "authentication")
public class Authentication extends Entity implements Cloneable {

    private String userId;
    private String generatedTime;
    private String expiryTime;
    private String accessToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(String generatedTime) {
        this.generatedTime = generatedTime;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Authentication clone() {
        try {
            return (Authentication) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
