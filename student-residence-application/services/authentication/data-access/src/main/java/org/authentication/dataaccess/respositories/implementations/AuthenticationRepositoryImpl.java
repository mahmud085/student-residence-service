package org.authentication.dataaccess.respositories.implementations;

import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.respositories.interfaces.AuthenticationRepository;
import org.authentication.dataaccess.respositories.interfaces.UserRepository;
import org.authentication.dataaccess.store.DataStore;
import org.daaaccess.Storage;

import java.util.List;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {
	private static int id = 0;


	@Override
	public Authentication add(Authentication obj) {
		obj.setId(getNewId());
		Storage storage = Storage.instance().build(org.authentication.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));
		Authentication authentication = new Authentication();
		authentication.setUserId(obj.getUserId());
		authentication.setAccessToken(obj.getAccessToken());
		authentication.setExpiryTime(obj.getExpiryTime());
		authentication.setGeneratedTime(obj.getGeneratedTime());
		storage.insert(authentication);

		//DataStore.authentications.add(authentication);

		return obj;
	}

	@Override
	public List<Authentication> getAll() {
		return null;
	}


	private synchronized int getNewId() {
		return ++id;
	}




	@Override
	public Authentication getAuthenticationByUserId(String userId) {
		for (Authentication authentication : DataStore.authentications) {
			if (authentication.getUserId().equals(userId)) {
				return authentication;
			}

		}
		return null;
	}
}
