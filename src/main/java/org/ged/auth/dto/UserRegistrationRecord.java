package org.ged.auth.dto;

import lombok.Builder;

@Builder
public record UserRegistrationRecord(String username, String email, String firstName, String lastName, String password) {
}
