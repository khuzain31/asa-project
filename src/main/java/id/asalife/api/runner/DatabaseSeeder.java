package id.asalife.api.runner;

import id.asalife.api.domains.entities.Role;
import id.asalife.api.domains.models.UserRegister;
import id.asalife.api.domains.ERole;
import id.asalife.api.domains.ERoleRegister;
import id.asalife.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_ADMIN));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_USER));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_MEGAUSER));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_SUPERUSER));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_CUSTOMER));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_WORKER));

        userService.registerAdminIfNotExists(new UserRegister("loj", "001", "it","123", ERoleRegister.MEGAUSER));
        userService.registerAdminIfNotExists(new UserRegister("asa", "002", "manager","123", ERoleRegister.SUPERUSER));

        userService.registerUserIfNotExists(new UserRegister("dani", "111", "administration","123", ERoleRegister.CUSTOMER));
        userService.registerUserIfNotExists(new UserRegister("danis", "112", "hrd","123", ERoleRegister.WORKER));
    }
}
