package id.asalife.api.domains.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class NewTokenRequest {
    @NotEmpty(message = "Refresh Token is mandatory")
    private String refreshToken;
}
