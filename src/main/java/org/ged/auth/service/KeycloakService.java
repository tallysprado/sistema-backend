package org.ged.auth.service;

import org.ged.auth.dto.UserRegistrationRecord;

public interface KeycloakService {

    UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord);
    void emailVerification(String userId);
    void deleteUser(String username);

}
