package id.asalife.api.controllers;

import id.asalife.api.domains.entities.User;
import id.asalife.api.domains.models.SimpleUser;
import id.asalife.api.services.UserService;
import id.asalife.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<SimpleUser> getUser(Principal principal) {
        return ResponseEntity.ok(UserMapper.principalToSimpleUser(principal));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

}
