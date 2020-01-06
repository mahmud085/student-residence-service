package org.authentication.dataaccess.respositories.implementations;

import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.helpers.Configuration;
import org.authentication.dataaccess.respositories.interfaces.AuthenticationRepository;
import org.dataccess.Storage;

import java.util.List;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {
	@Override
	public Authentication add(Authentication obj) {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));
		Authentication authentication = new Authentication();
		authentication.setUserId(obj.getUserId());
		authentication.setAccessToken(obj.getAccessToken());
		authentication.setExpiryTime(obj.getExpiryTime());
		authentication.setGeneratedTime(obj.getGeneratedTime());
		storage.insert(authentication);

		return obj;
	}

	@Override
	public List<Authentication> getAll() {
		return null;
	}
}
