package fxs.fourthten.springtutorial.service;

import fxs.fourthten.springtutorial.domain.dto.request.role.RoleFormDto;
import fxs.fourthten.springtutorial.domain.dto.request.user.UserFormDto;

public interface CoreService {
    Boolean addRole(RoleFormDto roleFormDto);
    Boolean addUser(UserFormDto userFormDto);
}
