package org.authentication.dataaccess.data.models;

import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement(name = "authentication")
@javax.persistence.Entity(name ="authentication")
@Table(name = "authentication")
@NamedQueries({
	@NamedQuery(name = "authentication.getByUserId" , query = "SELECT a.userId FROM authentication a WHERE a.accessToken=:accessToken")
})
public class Authentication extends Entity implements Cloneable {


	@Column(name = "userId")
	private String userId;
	@Column(name = "generatedTime")
	private LocalDateTime generatedTime;
	@Column(name = "expiryTime")
	private LocalDateTime expiryTime;
	@Column(name = "accessToken")
	private String accessToken;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getGeneratedTime() {
		return generatedTime;
	}

	public void setGeneratedTime(LocalDateTime generatedTime) {
		this.generatedTime = generatedTime;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
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
