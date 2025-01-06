package org.ged.auth.client.response;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Token {

    @JsonProperty("realm")
    private String realm;

    @JsonProperty("public_key")
    private String publicKey;
}
