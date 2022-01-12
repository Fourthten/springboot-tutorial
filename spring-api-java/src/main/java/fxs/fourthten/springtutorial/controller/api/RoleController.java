package fxs.fourthten.springtutorial.controller.api;

import fxs.fourthten.springtutorial.config.security.AuthorizationUtil;
import fxs.fourthten.springtutorial.config.security.CustomAuthorizationFilter;
import fxs.fourthten.springtutorial.config.utility.ConstantUtil;
import fxs.fourthten.springtutorial.domain.dto.request.role.RoleFormDto;
import fxs.fourthten.springtutorial.domain.dto.request.role.RoleIdDto;
import fxs.fourthten.springtutorial.domain.dto.response.ResponseDto;
import fxs.fourthten.springtutorial.domain.dto.response.role.RoleDto;
import fxs.fourthten.springtutorial.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController @RequestMapping("/api/role") @RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final AuthorizationUtil authorizationUtil;

    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody RoleFormDto roleFormDto, HttpServletRequest request) {
        String token = authorizationUtil.getToken(request);
        RoleDto roleDto = roleService.create(roleFormDto, token);
        ResponseDto<RoleDto> responseDto = new ResponseDto(CREATED.value(), ConstantUtil.SUCCESS, roleDto);
        return new ResponseEntity<>(responseDto, CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity update(@Valid @RequestBody RoleFormDto roleFormDto, HttpServletRequest request) {
        String token = authorizationUtil.getToken(request);
        RoleDto roleDto = roleService.update(roleFormDto, token);
        ResponseDto<RoleDto> responseDto = new ResponseDto(CREATED.value(), ConstantUtil.SUCCESS, roleDto);
        return new ResponseEntity<>(responseDto, CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity view(@Valid RoleIdDto roleIdDto, HttpServletRequest request) {
        String token = authorizationUtil.getToken(request);
        RoleDto roleDto = roleService.view(roleIdDto, token);
        ResponseDto<RoleDto> responseDto = new ResponseDto(OK.value(), ConstantUtil.SUCCESS, roleDto);
        return new ResponseEntity<>(responseDto, OK);
    }
}
