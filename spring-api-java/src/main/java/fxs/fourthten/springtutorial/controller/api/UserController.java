package fxs.fourthten.springtutorial.controller.api;

import fxs.fourthten.springtutorial.config.security.AuthorizationUtil;
import fxs.fourthten.springtutorial.config.utility.ConstantUtil;
import fxs.fourthten.springtutorial.domain.dto.request.user.UserFormDto;
import fxs.fourthten.springtutorial.domain.dto.request.user.UserIdDto;
import fxs.fourthten.springtutorial.domain.dto.response.ResponseDto;
import fxs.fourthten.springtutorial.domain.dto.response.user.UserDto;
import fxs.fourthten.springtutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

@RestController @RequestMapping("/api/user") @RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthorizationUtil authorizationUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody UserFormDto userFormDto, HttpServletRequest request) {
        String token = authorizationUtil.getToken(request);
        UserDto userDto = userService.create(userFormDto, token);
        ResponseDto<UserDto> responseDto = new ResponseDto(CREATED.value(), ConstantUtil.SUCCESS, userDto);
        return new ResponseEntity<>(responseDto, CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity update(@Valid @RequestBody UserFormDto userFormDto, HttpServletRequest request) {
        String token = authorizationUtil.getToken(request);
        UserDto userDto = userService.update(userFormDto, token);
        ResponseDto<UserDto> responseDto = new ResponseDto(CREATED.value(), ConstantUtil.SUCCESS, userDto);
        return new ResponseEntity<>(responseDto, CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity view(@Valid UserIdDto userIdDto, HttpServletRequest request) {
        String token = authorizationUtil.getToken(request);
        UserDto userDto = userService.view(userIdDto, token);
        ResponseDto<UserDto> responseDto = new ResponseDto(OK.value(), ConstantUtil.SUCCESS, userDto);
        return new ResponseEntity<>(responseDto, OK);
    }

    @GetMapping
    public UserDetails getUser(Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Map<String, Object> attributes = token.getTokenAttributes();
        return userDetailsService.loadUserByUsername(attributes.get("username").toString());
    }
}
