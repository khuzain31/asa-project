package id.asalife.api.services;

import id.asalife.api.domains.entities.RefreshToken;
import id.asalife.api.domains.entities.Role;
import id.asalife.api.domains.entities.User;
import id.asalife.api.domains.models.NewTokenRequest;
import id.asalife.api.domains.models.SignInRequest;
import id.asalife.api.domains.models.TokenResponse;
import id.asalife.api.utils.converters.ModelConverter;
import id.asalife.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Boolean checkObject(Object object) {
        return ObjectUtils.isEmpty(object);
    }

    @Override
    public Boolean verifyUser(String nrp) {
        return !ObjectUtils.isEmpty(userRepo.findByNrp(nrp));
    }

    @Override
    public Boolean authUser(String nrp, String password) {
        Boolean user = verifyUser(nrp);

        try {
            if (user) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nrp, password));
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> roles(String nrp) throws Exception {
        Boolean userState = verifyUser(nrp);

        try {
            if (userState) {
                User user = userRepo.findByNrp(nrp);

                assert user != null;
                Collection<Role> roles = user.getRoles();
                return ModelConverter.toStringRoleList(Objects.requireNonNull(roles));
            } else return null;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public String refreshToken(String nrp) {
        Boolean userState = verifyUser(nrp);
        if (userState) {
            User user = userRepo.findByNrp(nrp);
            assert user != null;
            return refreshTokenService.createRefreshToken(user.getId()).getToken();
        } else return null;
    }

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) throws Exception {
        String nrp = signInRequest.getNrp();
        String password = signInRequest.getPassword();

        if (checkObject(nrp) || checkObject(password)) {
            throw new Exception("Bad Request");
        }

        Boolean userAvailable = verifyUser(nrp);

        if (!userAvailable) {
            throw new Exception("User not found");
        }

        if (!authUser(nrp, password)) {
            throw new Exception("invalid password");
        }

        List<String> roles = roles(nrp);
        String jwtToken = jwtService.generateToken(nrp);
        String refreshToken = refreshToken(nrp);

        return new TokenResponse(jwtToken, refreshToken, roles);
    }

    @Override
    public TokenResponse getNewToken(NewTokenRequest request) throws Exception {
        String refreshToken = request.getRefreshToken();

        if (checkObject(refreshToken)) {
            throw new Exception("Bad Request");
        }
        RefreshToken token = refreshTokenService.findByToken(refreshToken);

        if (checkObject(token)) {
            throw new Exception("Token Not Found");
        }

        Boolean notExpire = refreshTokenService.verifyExpiration(token);
        if (!notExpire) {
            throw new Exception("Token Not Found");
        }

        String newToken = jwtService.generateToken(token.getUser().getUsername());
        List<String> roles = roles(token.getUser().getNrp());
        return new TokenResponse(newToken, refreshToken, roles);
    }
}
