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

		Storage storage = Storage.instance().build(org.authentication.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));
		User u = new User();
		u.setId(obj.getId());
		u.setPassword(obj.getPassword());
		u.setUserEmail(obj.getUserEmail());
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
		Storage storage = Storage.instance().build(org.authentication.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));
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
		Storage storage = Storage.instance().build(org.authentication.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("accessToken", accessToken);
		System.out.println("Here is Access Token");
		String userId= storage.execute("authentication.getByUserId",hm).toString();
		userId= userId.replaceAll("\\p{P}","");
		System.out.println(userId);
		HashMap<String, String> hm1 = new HashMap<String, String>();
		hm1.put("userId", userId);
		System.out.println("Here is getUserById");
		List<User> userlist=(List<User>)storage.execute("user.findByUserId", hm1);
		if(userlist.size()==0)
			return null;

		return userlist.get(0);

	}

	@Override
	public User getUserByIdPass(String userId, String password) {
		Storage storage = Storage.instance().build(org.authentication.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("userId", userId);
		hm.put("password", password);

		List<User> userlist=(List<User>)storage.execute("user.findByUserIdPassword",hm);
		if(userlist.size()==0)
			return null;

		return userlist.get(0);
	}
}
