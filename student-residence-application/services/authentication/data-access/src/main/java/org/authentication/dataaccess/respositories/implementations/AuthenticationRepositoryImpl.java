package org.authentication.dataaccess.respositories.implementations;

import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.helpers.Configuration;
import org.authentication.dataaccess.respositories.interfaces.AuthenticationRepository;
import org.dataccess.Storage;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public Authentication getByAccessToken(String accessToken) {
		Map<String, Object> paramList = new HashMap<>();
		paramList.put("accessToken", accessToken);

		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));

		try {
			return storage.executeForSingle("authentication.getByAccessToken", paramList);
		} catch (NoResultException ex) {
			return null;
		}
	}
}
