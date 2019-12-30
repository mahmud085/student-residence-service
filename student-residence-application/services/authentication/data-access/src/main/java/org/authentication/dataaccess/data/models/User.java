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

@XmlRootElement(name = "user")
@javax.persistence.Entity(name = "user")
@Table(name = "user")
@NamedQueries({
	
	@NamedQuery(name = "user.findByUserIdPassword" ,query = "SELECT u FROM user u where u.userId=:userId AND u.password=:password")
	,@NamedQuery(name = "user.getAllUser" ,query = "SELECT u FROM user u")
})

public class User extends Entity implements Cloneable {
	
	@Column(name = "userId")
	private String userId;
	@Column(name="userName")
	private String userName;
	@Column(name="password")
	private String password;

	private UserType userType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public User clone() {
		try {
			return (User) super.clone();
		} catch (CloneNotSupportedException ex) {
			return null;
		}
	}
}
