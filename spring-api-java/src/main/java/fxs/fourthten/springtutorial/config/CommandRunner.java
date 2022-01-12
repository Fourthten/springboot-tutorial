package fxs.fourthten.springtutorial.config;

import fxs.fourthten.springtutorial.config.utility.ConstantUtil;
import fxs.fourthten.springtutorial.domain.dto.request.role.RoleFormDto;
import fxs.fourthten.springtutorial.domain.dto.request.user.UserFormDto;
import fxs.fourthten.springtutorial.service.CoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component @Slf4j
public class CommandRunner implements CommandLineRunner {

    @Autowired
    CoreService coreService;

    private final List<String> usernames = List.of( "superadmin", "admin", "setia" );
    private final List<String> names = List.of( "Super Admin", "Admin", "Setia" );
    private final List<String> emails = List.of( "superadmin@mail.com", "admin@mail.com", "setia@mail.com" );
    private final List<String> roles = List.of( ConstantUtil.SUPERADMIN, ConstantUtil.ADMIN, ConstantUtil.USER );
    private final List<String> roleLabels = List.of( "Super Admin", "Admin", "User" );
    private final List<String> roleDescs = List.of( "This is Super Admin", "This is Admin", "This is User" );
    private final String pasword = "1234";

    @Override
    public void run(String... args) {
        addRoles();
        addUsers();
    }

    private void addRoles() {
        for(int i = 0; i < usernames.size(); i++) {
            RoleFormDto roleFormDto = new RoleFormDto();
            roleFormDto.setName(roles.get(i));
            roleFormDto.setLabel(roleLabels.get(i));
            roleFormDto.setDescription(roleDescs.get(i));
            coreService.addRole(roleFormDto);
        }
    }

    private void addUsers() {
        for(int i = 0; i < usernames.size(); i++) {
            UserFormDto userFormDto = new UserFormDto();
            userFormDto.setUsername(usernames.get(i));
            userFormDto.setName(names.get(i));
            userFormDto.setEmail(emails.get(i));
            userFormDto.setPassword(pasword);
            userFormDto.setRoles(Set.of(roles.get(i)));
            coreService.addUser(userFormDto);
        }
    }
}
