package id.asalife.api.controllers;

import id.asalife.api.domains.models.*;
import id.asalife.api.services.RefreshTokenService;
import id.asalife.api.services.TokenService;
import id.asalife.api.services.UserService;
import id.asalife.api.domains.models.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController extends HandlerController {
    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register/user")
    public ResponseEntity<TokenResponse> registerUser(@Valid @RequestBody UserRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register/user").toUriString());
        try {
            TokenResponse tokenResponse = userService.registerUser(user);
            return ResponseEntity.created(uri).body(tokenResponse);
        } catch (Exception e) {
            if (e.getMessage().equals("NRP_UNAVAILABLE")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NRP already exist", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<SimpleResponse> registerAdmin(@Valid @RequestBody UserRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register/admin").toUriString());
        try {
            userService.registerAdmin(user);
            return ResponseEntity.created(uri).body(new SimpleResponse("ok", 201));
        } catch (Exception e) {
            if (e.getMessage().equals("NRP_UNAVAILABLE")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NRP already exist", e.getCause());
            } else if (e.getMessage().equals("ROLE_NOT_FOUND")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/nrp-availability")
    public ResponseEntity<Boolean> checkNrpAvailability(@RequestBody NrpAvailability availability) {
        boolean getIsAvailable = userService.getIsNrpAvailable(availability.getNrp());
        return ResponseEntity.ok(getIsAvailable);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest signInRequest) {
        try {
            TokenResponse result = tokenService.signIn(signInRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            if (e.getMessage().equals("invalid password")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid password", e.getCause());
            }
            else if (e.getMessage().equals("User not found")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenResponse> getNewToken(@Valid @RequestBody NewTokenRequest request) {
        try {
            TokenResponse tokenResponse = tokenService.getNewToken(request);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            if (e.getMessage().equals("Token Not Found")) {
                return ResponseEntity.notFound().build();
            } else return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/signout")
    public ResponseEntity<ResponseTemplate> signOut(@Valid @RequestBody SignOutRequest signOutRequest) {
        try {
            ResponseTemplate result = refreshTokenService.signOut(signOutRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            if (e.getMessage().equals("Token Not Found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }
}
