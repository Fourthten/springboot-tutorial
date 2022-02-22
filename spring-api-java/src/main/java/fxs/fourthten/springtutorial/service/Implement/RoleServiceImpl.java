package fxs.fourthten.springtutorial.service.Implement;

import fxs.fourthten.springtutorial.config.exception.CustomException;
import fxs.fourthten.springtutorial.config.security.AuthorizationUtil;
import fxs.fourthten.springtutorial.config.utility.*;
import fxs.fourthten.springtutorial.domain.dto.request.role.*;
import fxs.fourthten.springtutorial.domain.dto.response.role.*;
import fxs.fourthten.springtutorial.domain.model.Role;
import fxs.fourthten.springtutorial.domain.model.User;
import fxs.fourthten.springtutorial.repository.RoleRepo;
import fxs.fourthten.springtutorial.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Service @RequiredArgsConstructor @Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    private final AuthorizationUtil authorizationUtil;

    @Override @Transactional
    public RoleDto create(RoleFormDto roleFormDto, String token) {
        Role role = new Role();

        User user = authorizationUtil.getUserFromToken(token);
        if (user == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": User", NOT_FOUND);
        Role _role = roleRepo.findOneByName(roleFormDto.getName());
        if (_role != null) throw new CustomException(ConstantUtil.EXISTING_DATA + ": Role", BAD_REQUEST);

        role.setName(roleFormDto.getName());
        role.setLabel(roleFormDto.getLabel());
        role.setDescription(roleFormDto.getDescription());
        role.setEnabled(true);
        role.setCreated(user.getUsername() + "::" + new Date().getTime());
        Role roleSave = roleRepo.saveAndFlush(role);
        if (roleSave == null) throw new CustomException(ConstantUtil.MODIFY_DATA_FAILED + ": Role", UNPROCESSABLE_ENTITY);

        List<String> createdDate = DateTimeUtil.entityDate(roleSave.getCreated());
        RoleDto roleDto = new RoleDto(roleSave.getId(), roleSave.getName(), roleSave.getLabel(), roleSave.getDescription(),
                roleSave.getEnabled(), createdDate.get(0), createdDate.get(1), "", "");
        return roleDto;
    }

    @Override @Transactional
    public RoleDto update(RoleFormDto roleFormDto, String token) {
        User user = authorizationUtil.getUserFromToken(token);
        if (user == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": User", NOT_FOUND);
        Role role = roleRepo.findOneById(roleFormDto.getId());
        if (role == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": Role", NOT_FOUND);
        Role _role = roleRepo.findOneByIdNotAndName(roleFormDto.getId(), roleFormDto.getName());
        if (_role != null) throw new CustomException(ConstantUtil.EXISTING_DATA + ": Role", BAD_REQUEST);

        role.setName(roleFormDto.getName());
        role.setLabel(roleFormDto.getLabel());
        if (roleFormDto.getDescription() != null) role.setDescription(roleFormDto.getDescription());
        if (roleFormDto.getEnabled() != null) role.setEnabled(roleFormDto.getEnabled());
        role.setUpdated(user.getUsername() + "::" + new Date().getTime());
        Role roleSave = roleRepo.saveAndFlush(role);
        if (roleSave == null) throw new CustomException(ConstantUtil.MODIFY_DATA_FAILED + ": Role", UNPROCESSABLE_ENTITY);

        List<String> createdDate = DateTimeUtil.entityDate(roleSave.getCreated());
        List<String> updatedDate = DateTimeUtil.entityDate(roleSave.getUpdated());
        RoleDto roleDto = new RoleDto(roleSave.getId(), roleSave.getName(), roleSave.getLabel(), roleSave.getDescription(),
                roleSave.getEnabled(), createdDate.get(0), createdDate.get(1), updatedDate.get(0), updatedDate.get(1));
        return roleDto;
    }

    @Override
    public RoleDto view(RoleIdDto roleIdDto, String token) {
        User user = authorizationUtil.getUserFromToken(token);
        if (user == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": User", NOT_FOUND);
        Role role = roleRepo.findOneById(roleIdDto.getId());
        if (role == null) throw new CustomException(ConstantUtil.DATA_NOT_FOUND + ": Role", NOT_FOUND);

//        ModelMapper modelMapper = new ModelMapper();
//        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        List<String> createdDate = DateTimeUtil.entityDate(role.getCreated());
        List<String> updatedDate = DateTimeUtil.entityDate(role.getUpdated());
        RoleDto roleDto = new RoleDto(role.getId(), role.getName(), role.getLabel(), role.getDescription(),
                role.getEnabled(), createdDate.get(0), createdDate.get(1), updatedDate.get(0), updatedDate.get(1));
        return roleDto;
    }
}
