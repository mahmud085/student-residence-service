package org.authentication.web;

import com.google.inject.AbstractModule;
import org.authentication.dataaccess.respositories.implementations.AuthenticationRepositoryImpl;
import org.authentication.dataaccess.respositories.implementations.UserRepositoryImpl;
import org.authentication.dataaccess.respositories.interfaces.AuthenticationRepository;
import org.authentication.dataaccess.respositories.interfaces.UserRepository;
import org.authentication.service.helpers.KeyGenerator;
import org.authentication.service.helpers.SimpleKeyGenerator;
import org.authentication.service.services.implementations.AuthenticationServiceImpl;
import org.authentication.service.services.implementations.UserServiceImpl;
import org.authentication.service.services.interfaces.AuthenticationService;
import org.authentication.service.services.interfaces.UserService;


public class IoCModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserService.class)
            .to(UserServiceImpl.class);
        bind(AuthenticationService.class)
            .to(AuthenticationServiceImpl.class);
        bind(KeyGenerator.class)
                .to(SimpleKeyGenerator.class);
        bind(UserRepository.class)
                .to(UserRepositoryImpl.class);

        bind(AuthenticationRepository.class)
                .to(AuthenticationRepositoryImpl.class);
    }
}
