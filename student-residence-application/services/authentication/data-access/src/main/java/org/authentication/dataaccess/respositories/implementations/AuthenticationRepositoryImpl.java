package org.authentication.dataaccess.respositories.implementations;

import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.respositories.interfaces.AuthenticationRepository;
import org.authentication.dataaccess.respositories.interfaces.UserRepository;
import org.authentication.dataaccess.store.DataStore;

import java.util.List;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {
	private static int id = 0;





	@Override
	public Authentication add(Authentication obj) {
		obj.setId(getNewId());

		DataStore.authentications.add(obj);

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
