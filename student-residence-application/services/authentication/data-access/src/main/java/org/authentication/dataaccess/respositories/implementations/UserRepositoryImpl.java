package org.authentication.dataaccess.respositories.implementations;

import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.helpers.Configuration;
import org.authentication.dataaccess.respositories.interfaces.UserRepository;
import org.dataccess.Storage;

import java.util.HashMap;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
	@Override
	public User add(User obj) {

		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));
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

	@Override
	public User getUserById(String userId) {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("userId", userId);
		List<User> userlist=(List<User>)storage.execute("user.findByUserId", hm);
		if(userlist.size()==0)
			return null;

		return userlist.get(0);

	}

	@Override
	public User getUserByIdPass(String userId, String password) {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("userId", userId);
		hm.put("password", password);

		List<User> userlist=(List<User>)storage.execute("user.findByUserIdPassword",hm);
		if(userlist.size()==0)
			return null;

		return userlist.get(0);
	}
}
