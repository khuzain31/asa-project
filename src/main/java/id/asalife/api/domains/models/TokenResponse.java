package id.asalife.api.domains.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private String tokenType = "Bearer ";
    private final String refreshToken;
    private final List<String> roles;
}
