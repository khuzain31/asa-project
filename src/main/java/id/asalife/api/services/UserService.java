package id.asalife.api.services;

import id.asalife.api.domains.entities.Role;
import id.asalife.api.domains.entities.User;
import id.asalife.api.domains.models.TokenResponse;
import id.asalife.api.domains.models.UserRegister;

import java.util.List;

public interface UserService {
    TokenResponse registerUser(UserRegister userRegister) throws Exception;

    void registerUserIfNotExists(UserRegister userRegister);

    void registerAdminIfNotExists(UserRegister userRegister);

    void registerAdmin(UserRegister userRegister) throws Exception;

    Role saveRole(Role role);

    Boolean checkRoleForUserExist(UserRegister userRegister);

    Boolean checkRoleForAdminExist(UserRegister userRegister);

    Role saveRoleIfNotExists(Role role);

    User getUser(String nrp);

    List<User> getUsers();

    boolean getIsNrpAvailable(String nrp);
}
