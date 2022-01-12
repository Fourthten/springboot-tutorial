package fxs.fourthten.springtutorial.domain.dto.request.user;

import lombok.*;

import javax.validation.constraints.*;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserFormDto {
    private UUID id = UUID.fromString("0000000-0000-0000-0000-000000000000");
    @NotEmpty @Size(max = 50, message = "Username has a maximum of 50 characters")
    private String username;
    @Size(max = 255, message = "Name has a maximum of 255 characters")
    private String name;
    @NotEmpty @Size(max = 255, message = "Username has a maximum of 255 characters")
    private String email;
    @Size(max = 50, message = "Username has a maximum of 50 characters")
    private String password;
    @Size(max = 15, message = "Username has a maximum of 15 characters")
    private String phone;
    private Boolean enabled;
    private Set<String> roles;
}
