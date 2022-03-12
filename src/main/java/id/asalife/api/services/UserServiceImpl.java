package id.asalife.api.services;

import id.asalife.api.domains.ERole;
import id.asalife.api.domains.entities.Role;
import id.asalife.api.domains.entities.User;
import id.asalife.api.domains.models.SignInRequest;
import id.asalife.api.domains.models.TokenResponse;
import id.asalife.api.domains.models.UserRegister;
import id.asalife.api.utils.mappers.RoleAdminMapper;
import id.asalife.api.utils.mappers.RoleUserMapper;
import id.asalife.api.utils.mappers.UserMapper;
import id.asalife.api.domains.models.*;
import id.asalife.api.repositories.RoleRepository;
import id.asalife.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public User loadUserByUsername(String nrp) throws UsernameNotFoundException {
        User user = userRepo.findByNrp(nrp);
        if (user == null) {
            log.error("NRP not found {}", nrp);
            throw new UsernameNotFoundException(String.format("User not found %s", nrp));
        } else {
            log.info("NRP found {}", nrp);
        }
        return user;
    }

    @Override
    public TokenResponse registerUser(UserRegister userRegister) throws Exception {
        User user = UserMapper.userRegisterToUser(userRegister);
        if (!getIsNrpAvailable(user.getNrp())) throw new Exception("NRP_UNAVAILABLE");
        if (checkRoleForUserExist(userRegister)) throw new Exception("ROLE_NOT_FOUND");

        saveUser(user);
        addRoleToUser(user.getNrp(), RoleUserMapper.mapRole(userRegister.getRole()));
        addRoleToUser(user.getNrp(), ERole.ROLE_USER);
        return tokenService.signIn(new SignInRequest(userRegister.getNrp(), userRegister.getPassword()));
    }

    @Override
    public void registerUserIfNotExists(UserRegister userRegister) {
        if (getIsNrpAvailable(userRegister.getNrp())) {
            try {
                registerUser(userRegister);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerAdminIfNotExists(UserRegister userRegister) {
        if (getIsNrpAvailable(userRegister.getNrp())) {
            try {
                registerAdmin(userRegister);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerAdmin(UserRegister userRegister) throws Exception {
        User user = UserMapper.userRegisterToUser(userRegister);

        if (!getIsNrpAvailable(user.getNrp())) throw new Exception("NRP_UNAVAILABLE");

        if (checkRoleForAdminExist(userRegister)) throw new Exception("ROLE_NOT_FOUND");

        saveUser(user);
        addRoleToUser(user.getNrp(), RoleAdminMapper.mapRole(userRegister.getRole()));
        addRoleToUser(user.getNrp(), ERole.ROLE_ADMIN);
    }

    @Override
    public Boolean checkRoleForUserExist(UserRegister userRegister) {
        ERole role = RoleUserMapper.mapRole(userRegister.getRole());
        if (!ObjectUtils.isEmpty(role)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean checkRoleForAdminExist(UserRegister userRegister) {
        ERole role = RoleAdminMapper.mapRole(userRegister.getRole());
        if (!ObjectUtils.isEmpty(role)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public Role saveRoleIfNotExists(Role role) {
        if (roleRepo.findByName(role.getName()) == null) {
            return saveRole(role);
        }
        return null;
    }

    @Override
    public User getUser(String nrp) {
        log.info("Fetching user {}", nrp);
        return userRepo.findByNrp(nrp);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public boolean getIsNrpAvailable(String nrp) {
        return getUser(nrp) == null;
    }

    private void saveUser(User user) {
        log.info("Saving new user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    private void addRoleToUser(String nrp, ERole roleName) {
        log.info("Adding role {} to user {}", roleName, nrp);
        User user = userRepo.findByNrp(nrp);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }
}
