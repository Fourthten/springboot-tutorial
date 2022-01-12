package fxs.fourthten.springtutorial.service;

import fxs.fourthten.springtutorial.domain.dto.request.user.UserFormDto;
import fxs.fourthten.springtutorial.domain.dto.request.user.UserIdDto;
import fxs.fourthten.springtutorial.domain.dto.response.user.UserDto;
import fxs.fourthten.springtutorial.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDto create(UserFormDto userFormDto, String token);
    UserDto update(UserFormDto userFormDto, String token);
    UserDto view(UserIdDto userIdDto, String token);
}
