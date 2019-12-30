package org.authentication.dataaccess.data.models;

import org.authentication.dataaccess.data.enums.UserType;

import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

//import org.appointment.service.adapters.AppointmentPriorityAdapter;
//import org.appointment.service.adapters.AppointmentStatusAdapter;
//import org.appointment.service.adapters.AppointmentTypeAdapter;

@XmlRootElement(name = "authentication")
@javax.persistence.Entity(name ="authentication")
@Table(name = "authentication")
@NamedQueries({
	@NamedQuery(name = "authentication.getByUserId" , query = "SELECT a FROM authentication a WHERE a.userId=:userId")
})
public class Authentication extends Entity implements Cloneable {


	@Column(name = "userId")
	private String userId;
	@Column(name = "generatedTime")
	private String generatedTime;
	@Column(name = "expiryTime")
	private String expiryTime;
	@Column(name = "accessToken")
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
