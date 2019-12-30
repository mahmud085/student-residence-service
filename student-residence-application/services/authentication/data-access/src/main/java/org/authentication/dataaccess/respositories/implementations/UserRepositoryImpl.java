package org.authentication.dataaccess.respositories.implementations;

import org.authentication.common.Messages;
import org.authentication.common.exceptions.PaginationRangeOutOfBoundException;
import org.authentication.common.helpers.PaginationHelper;
import org.authentication.dataaccess.data.enums.AppointmentStatus;
import org.authentication.dataaccess.data.enums.UserType;
import org.authentication.dataaccess.data.models.Appointment;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.helpers.Configuration;
import org.authentication.dataaccess.respositories.interfaces.AppointmentRepository;
import org.authentication.dataaccess.respositories.interfaces.UserRepository;
import org.authentication.dataaccess.store.DataStore;
import org.daaaccess.Storage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {

	private static int id = 0;



	@Override
	public User add(User obj) {
		
		System.out.println("User add called");
		Properties p = new Properties();
		p.put("jdbc.url", "jdbc:mysql://localhost:3306/projectrestapi");
		p.put("jdbc.user", "root");
		p.put("jdbc.password", "");
		p.put("jdbc.driver", "com.mysql.cj.jdbc.Driver");
		p.put("persistent.unitname", "AuthenticationStorage");

		Storage storage = Storage.instance().build(Configuration.loadProperties("tuntuni.properties"));

		System.out.println("Now going to add user");
		User u = new User();
		u.setId(obj.getId());
		u.setPassword(obj.getPassword());
		u.setUserId(obj.getUserId());
		u.setUserName(obj.getUserName());
		u.setUserType(obj.getUserType());

		storage.execute(obj);

		return obj;
	}

	@Override
	public List<User> getAll() {
		Storage storage = Storage.instance().build(Configuration.loadProperties("tuntuni.properties"));
		return (List<User>)storage.execute("user.getAll", User.class);

	}

	private synchronized int getNewId() {
		return ++id;
	}

	@Override
	public User getUserById(String userId) {
		Storage storage = Storage.instance().build(Configuration.loadProperties("tuntuni.properties"));
		//		for (User user : DataStore.user) {
		//			if (user.getUserId().equals(userId)) {
		//				return user;
		//			}
		//
		//		}
		//		return null;
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("userId", userId);
		List<User> userlist=(List<User>)storage.execute("user.findByUserId", hm);
		if(userlist.size()==0)
			return null;

		return userlist.get(0);

	}

	@Override
	public User getUserIdByAccessToken(String accessToken) {
		for (Authentication authentication : DataStore.authentications) {
			if (authentication.getAccessToken().equals(accessToken)) {
				return getUserById(authentication.getUserId());
			}

		}
		return null;
	}

	@Override
	public User getUserByIdPass(String userId, String password) {
		
		
		Properties p = new Properties();
		p.put("jdbc.url", "jdbc:mysql://localhost:3306/projectrestapi");
		p.put("jdbc.user", "root");
		p.put("jdbc.password", "");
		p.put("jdbc.driver", "com.mysql.cj.jdbc.Driver");
		p.put("persistent.unitname", "AuthenticationStorage");
		
		
		System.out.println("Get User by ID Pass called");

		//		for (User user : DataStore.user) {
		//			if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
		//				/*				for(Authentication authentication : DataStore.authentications){
		//					if(authentication.getUserId().equals(userId)) {
		//						return authentication;
		//					}
		//
		//				}*/
		//				return user;
		//			}
		//
		//		}
		//		return null;

		User user= new User();
		user.setId(getNewId());
		user.setPassword("Nopassword01");
		user.setUserId("Tuntuni");
		user.setUserName("Tuntuni");
		user.setUserType(UserType.Admin);
		
		
		add(user);
		System.out.println("Added User");
		Storage storage = Storage.instance().build(Configuration.loadProperties("tuntuni.properties"));
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("userId", userId);
		hm.put("password", password);

		List<User> userlist=(List<User>)storage.execute("user.findByUserId", hm);
		if(userlist.size()==0)
			return null;

		return userlist.get(0);
	}
}
