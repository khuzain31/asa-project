package id.asalife.api.utils.mappers;

import id.asalife.api.domains.entities.User;
import id.asalife.api.domains.models.SimpleUser;
import id.asalife.api.domains.models.UserRegister;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public final class UserMapper {
    public static User userRegisterToUser(UserRegister userRegister) {
        User user = new User();
        user.setName(userRegister.getName());
        user.setNrp(userRegister.getNrp());
        user.setPassword(userRegister.getPassword());
        user.setDepartment(userRegister.getDepartment());
        return user;
    }

    public static User principalToUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }

    public static SimpleUser principalToSimpleUser(Principal principal) {
        User user = principalToUser(principal);
        return new SimpleUser(user.getName(), user.getNrp());
    }
}
