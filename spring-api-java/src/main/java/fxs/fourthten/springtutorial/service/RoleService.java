package fxs.fourthten.springtutorial.service;

import fxs.fourthten.springtutorial.domain.dto.request.role.*;
import fxs.fourthten.springtutorial.domain.dto.response.role.*;

public interface RoleService {
    RoleDto create(RoleFormDto roleFormDto, String token);
    RoleDto update(RoleFormDto roleFormDto, String token);
    RoleDto view(RoleIdDto roleIdDto, String token);
}
