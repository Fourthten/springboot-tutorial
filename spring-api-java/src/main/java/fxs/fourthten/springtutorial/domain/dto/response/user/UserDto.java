package fxs.fourthten.springtutorial.domain.dto.response.user;

import fxs.fourthten.springtutorial.domain.model.Role;
import lombok.*;

import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDto {
    private UUID id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String picture;
    private Boolean enabled;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
    private Collection<Role> roles = new ArrayList<>();
}
