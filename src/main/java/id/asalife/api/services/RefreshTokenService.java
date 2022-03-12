package id.asalife.api.services;

import id.asalife.api.domains.entities.RefreshToken;
import id.asalife.api.domains.models.ResponseTemplate;
import id.asalife.api.domains.models.SignOutRequest;

public interface RefreshTokenService {
    RefreshToken findByToken(String refreshToken);

    RefreshToken createRefreshToken(Long userId);

    Boolean verifyExpiration(RefreshToken token);

    void deleteByToken(String token);

    ResponseTemplate signOut(SignOutRequest signOutRequest) throws Exception;
}
