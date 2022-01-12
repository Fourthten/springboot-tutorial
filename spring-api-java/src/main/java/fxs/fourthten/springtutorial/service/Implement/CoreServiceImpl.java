package fxs.fourthten.springtutorial.service.Implement;

import fxs.fourthten.springtutorial.domain.dto.request.role.*;
import fxs.fourthten.springtutorial.domain.dto.request.user.*;
import fxs.fourthten.springtutorial.domain.model.Role;
import fxs.fourthten.springtutorial.domain.model.User;
import fxs.fourthten.springtutorial.repository.RoleRepo;
import fxs.fourthten.springtutorial.repository.UserRepo;
import fxs.fourthten.springtutorial.service.CoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service @RequiredArgsConstructor @Slf4j
public class CoreServiceImpl implements CoreService {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Boolean addRole(RoleFormDto roleFormDto) {
        Role role = roleRepo.findOneByName(roleFormDto.getName());
        if (role == null) {
            Role _role = new Role();
            _role.setName(roleFormDto.getName());
            _role.setLabel(roleFormDto.getLabel());
            _role.setDescription(roleFormDto.getDescription());
            _role.setEnabled(true);
            _role.setCreated("superadmin::" + new Date().getTime());
            roleRepo.save(_role);
        } else {
            if (role.getName() == null) role.setName(roleFormDto.getName());
            if (role.getLabel() == null) role.setLabel(roleFormDto.getLabel());
            if (role.getDescription() == null) role.setDescription(roleFormDto.getDescription());
            if (role.getName() == null || role.getLabel() == null || role.getDescription() == null) {
                role.setUpdated("superadmin::" + new Date().getTime());
                roleRepo.save(role);
            }
        }
        return true;
    }

    @Override
    public Boolean addUser(UserFormDto userFormDto) {
        User user = userRepo.findOneByUsername(userFormDto.getUsername());
        if (user == null) {
            User _user = new User();
            _user.setUsername(userFormDto.getUsername());
            _user.setName(userFormDto.getName());
            _user.setEmail(userFormDto.getEmail());
            _user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
            _user.setEnabled(true);
            _user.setCreated("superadmin::" + new Date().getTime());
            for (String roleName : userFormDto.getRoles()) {
                Role role = roleRepo.findOneByName(roleName);
                _user.getRoles().add(role);
            }
            userRepo.save(_user);
        } else {
            if (user.getUsername() == null) user.setUsername(userFormDto.getUsername());
            if (user.getName() == null) user.setName(userFormDto.getName());
            if (user.getEmail() == null) user.setEmail(userFormDto.getEmail());
            if (user.getPassword() == null) user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
            if (user.getRoles().size() < 1) {
                for (String roleName : userFormDto.getRoles()) {
                    Role role = roleRepo.findOneByName(roleName);
                    user.getRoles().add(role);
                }
            }
            if (user.getUsername() == null || user.getName() == null || user.getEmail() == null || user.getPassword() == null ||
                    user.getRoles().size() < 1) {
                user.setUpdated("superadmin::" + new Date().getTime());
                userRepo.save(user);
            }
        }
        return true;
    }
}
