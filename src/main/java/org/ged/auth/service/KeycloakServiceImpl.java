package org.ged.auth.service;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import org.ged.auth.dto.UserRegistrationRecord;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@RequestScoped
public class KeycloakServiceImpl implements KeycloakService {
    @Value("${keycloak.realm}")
    private String realm;

    Keycloak keycloak;

    public KeycloakServiceImpl(Keycloak keycloak) {
            this.keycloak = keycloak;
    }

    @Override
    public UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord) {

        UserRepresentation user = getUserRepresentation(userRegistrationRecord);

        UsersResource usersResource = getUsersResource();
        Response response = null;
        try{

            response = usersResource.create(user);
        }  catch (Exception e){
            throw e;
        }

        System.out.println("Status "+response.getStatus());
        if(Objects.equals(201,response.getStatus())){

            List<UserRepresentation> representationList = usersResource.searchByUsername(userRegistrationRecord.username(), true);
            if(!CollectionUtils.isEmpty(representationList)){
                UserRepresentation userRepresentation1 = representationList.stream().filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified())).findFirst().orElse(null);
                assert userRepresentation1 != null;
                assignRoles(userRepresentation1.getId(), userRegistrationRecord.username());

//                emailVerification(userRepresentation1.getId());
            }
            return  userRegistrationRecord;
        }

        return null;
    }

    private void assignRoles(String userId, String username) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Mapeamento das roles baseado no prefixo do username
        List<String> realmRoles = new ArrayList<>();
        Map<String, List<String>> clientRoles = new HashMap<>();

        if (username.startsWith("A")) {
            realmRoles.add("aluno");
            clientRoles.put("sistema", Collections.singletonList("aluno"));
        } else if (username.startsWith("P")) {
            realmRoles.add("professor");
            clientRoles.put("sistema", Collections.singletonList("professor"));
        } else if (username.startsWith("C")) {
            realmRoles.add("coordenador");
            clientRoles.put("sistema", Collections.singletonList("coordenador"));
        }

        // Adicionar Realm Roles
        for (String role : realmRoles) {
            realmResource.roles().get(role).toRepresentation();
            usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(
                    realmResource.roles().get(role).toRepresentation()
            ));
        }

        // Adicionar Client Roles
        for (Map.Entry<String, List<String>> clientEntry : clientRoles.entrySet()) {
            String clientId = clientEntry.getKey();
            List<String> roles = clientEntry.getValue();

            realmResource.clients().findByClientId(clientId).stream().findFirst().ifPresent(client -> {
                for (String role : roles) {
                    usersResource.get(userId).roles().clientLevel(client.getId()).add(Collections.singletonList(
                            realmResource.clients().get(client.getId()).roles().get(role).toRepresentation()
                    ));
                }
            });
        }
    }

    private static UserRepresentation getUserRepresentation(UserRegistrationRecord userRegistrationRecord) {
        UserRepresentation user=new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistrationRecord.username().toUpperCase());
        user.setEmail(userRegistrationRecord.email());
        user.setFirstName(userRegistrationRecord.firstName());
        user.setLastName(userRegistrationRecord.lastName());
        user.setEmailVerified(false);
        addUserRoles(userRegistrationRecord, user);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationRecord.password());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);
        return user;
    }

    private static void addUserRoles(UserRegistrationRecord userRegistrationRecord, UserRepresentation user) {
        List<String> roles = new ArrayList<>();
        Map<String, List<String>> clientRoles = new HashMap<>();

        if(userRegistrationRecord.username().startsWith("A")){
            roles.add("aluno");
            clientRoles.put("sistema", roles);
        }
        if(userRegistrationRecord.username().startsWith("P")){
            roles.add("professor");
            clientRoles.put("sistema", roles);
        }
        if(userRegistrationRecord.username().startsWith("C")){
            roles.add("coordenador");
            clientRoles.put("sistema", roles);
        }
        user.setRealmRoles(roles);
        user.setClientRoles(clientRoles);
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public void emailVerification(String userId){

        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

}
