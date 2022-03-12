package id.asalife.api.services;

import id.asalife.api.domains.models.NewTokenRequest;
import id.asalife.api.domains.models.SignInRequest;
import id.asalife.api.domains.models.TokenResponse;

import java.util.List;

public interface TokenService {
    Boolean verifyUser(String nrp);

    Boolean authUser(String nrp, String password);

    List<String> roles(String nrp) throws Exception;

    String refreshToken(String nrp);

    TokenResponse signIn(SignInRequest signInRequest) throws Exception;

    TokenResponse getNewToken(NewTokenRequest request) throws Exception;
}
