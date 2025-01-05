package org.ged.auth.api.v1;


import org.ged.auth.dto.UserRecord;
import org.ged.auth.service.KeycloakUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class KeycloakUserController {


    private final KeycloakUserService keycloakUserService;

    public KeycloakUserController(KeycloakUserService keycloakUserService) {
        this.keycloakUserService = keycloakUserService;
    }


    @PostMapping("/login")
    public void register(@RequestBody UserRecord userRecord) {
        System.out.println(userRecord);
//        keycloakUserService.login(userRegistrationRecord);
    }



}
