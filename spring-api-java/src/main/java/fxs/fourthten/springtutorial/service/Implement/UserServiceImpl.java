package fxs.fourthten.springtutorial.service.Implement;

import fxs.fourthten.springtutorial.config.exception.CustomException;
import fxs.fourthten.springtutorial.config.security.AuthorizationUtil;
import fxs.fourthten.springtutorial.config.utility.ConstantUtil;
import fxs.fourthten.springtutorial.config.utility.DateTimeUtil;
import fxs.fourthten.springtutorial.domain.dto.request.user.UserFormDto;
import fxs.fourthten.springtutorial.domain.dto.request.user.UserIdDto;
import fxs.fourthten.springtutorial.domain.dto.response.user.UserDto;
import fxs.fourthten.springtutorial.domain.model.Role;
import fxs.fourthten.springtutorial.domain.model.User;
import fxs.fourthten.springtutorial.repository.RoleRepo;
import fxs.fourthten.springtutorial.repository.UserRepo;
import fxs.fourthten.springtutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service @RequiredArgsConstructor @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    AuthorizationUtil authorizationUtil;

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findOneByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found with username: " + username);
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override @Transactional
    public UserDto create(UserFormDto userFormDto, String token) {
        User user = new User();

        User userToken = authorizationUtil.getUserFromToken(token);
        if (userToken == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": User", NOT_FOUND);
        User _user = userRepo.findOneByUsernameOrEmail(userFormDto.getUsername(), userFormDto.getEmail());
        if (_user != null) throw new CustomException(ConstantUtil.EXISTING_DATA + ": User", BAD_REQUEST);

        user.setUsername(userFormDto.getEmail());
        user.setName(userFormDto.getName());
        user.setEmail(userFormDto.getEmail());
        user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
        user.setPhone(userFormDto.getPhone());
        user.setEnabled(true);
        user.setCreated(userToken.getUsername() + "::" + new Date().getTime());
        for (String roleName : userFormDto.getRoles()) {
            Role role = roleRepo.findOneByName(roleName);
            user.getRoles().add(role);
        }
        User userSave = userRepo.saveAndFlush(user);
        if (userSave == null) throw new CustomException(ConstantUtil.MODIFY_DATA_FAILED + ": User", UNPROCESSABLE_ENTITY);

        List<String> createdDate = DateTimeUtil.entityDate(userSave.getCreated());
        UserDto userDto = new UserDto(userSave.getId(), userSave.getUsername(), userSave.getName(), userSave.getEmail(),
                userSave.getPhone(), userSave.getPicture(), userSave.getEnabled(), createdDate.get(0), createdDate.get(1),
                "", "", userSave.getRoles());
        return userDto;
    }

    @Override @Transactional
    public UserDto update(UserFormDto userFormDto, String token) {
        User userToken = authorizationUtil.getUserFromToken(token);
        if (userToken == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": User", NOT_FOUND);
        User user = userRepo.findOneById(userFormDto.getId());
        if (user == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": User", NOT_FOUND);
        User _user = userRepo.findOneByIdNotAndUsernameOrEmail(userFormDto.getId(), userFormDto.getUsername(), userFormDto.getEmail());
        if (_user != null) throw new CustomException(ConstantUtil.EXISTING_DATA + ": User", BAD_REQUEST);

        user.setUsername(userFormDto.getEmail());
        if(userFormDto.getName() != null) user.setName(userFormDto.getName());
        user.setEmail(userFormDto.getEmail());
        if(userFormDto.getPassword() != null) user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
        if(userFormDto.getPhone() != null) user.setPhone(userFormDto.getPhone());
        if(userFormDto.getEnabled() != null) user.setEnabled(userFormDto.getEnabled());
        user.setUpdated(userToken.getUsername() + "::" + new Date().getTime());
//        for (String roleName : userFormDto.getRoles()) {
//            Role role = roleRepo.findOneByName(roleName);
//            user.getRoles().add(role);
//        }
        User userSave = userRepo.saveAndFlush(user);
        if (userSave == null) throw new CustomException(ConstantUtil.MODIFY_DATA_FAILED + ": User", UNPROCESSABLE_ENTITY);

        List<String> createdDate = DateTimeUtil.entityDate(userSave.getCreated());
        List<String> updatedDate = DateTimeUtil.entityDate(userSave.getUpdated());
        UserDto userDto = new UserDto(userSave.getId(), userSave.getUsername(), userSave.getName(), userSave.getEmail(),
                userSave.getPhone(), userSave.getPicture(), userSave.getEnabled(), createdDate.get(0), createdDate.get(1),
                updatedDate.get(0), updatedDate.get(1), userSave.getRoles());
        return userDto;
    }

    @Override
    public UserDto view(UserIdDto userIdDto, String token) {
        User userToken = authorizationUtil.getUserFromToken(token);
        if (userToken == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": User", NOT_FOUND);
        User user = userRepo.findOneById(userIdDto.getId());
        if (user == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": User", NOT_FOUND);

        List<String> createdDate = DateTimeUtil.entityDate(user.getCreated());
        List<String> updatedDate = DateTimeUtil.entityDate(user.getUpdated());
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail(),
                user.getPhone(), user.getPicture(), user.getEnabled(), createdDate.get(0), createdDate.get(1),
                updatedDate.get(0), updatedDate.get(1), user.getRoles());
        return userDto;
    }
}
