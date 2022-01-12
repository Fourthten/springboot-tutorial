package fxs.fourthten.springtutorial.domain.dto.request.role;

import lombok.*;

import javax.validation.constraints.*;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RoleFormDto {
    private UUID id = UUID.fromString("0000000-0000-0000-0000-000000000000");
    @NotEmpty @Size(max = 50, message = "Name has a maximum of 50 characters")
    private String name;
    @NotEmpty @Size(max = 100, message = "Username has a maximum of 100 characters")
    private String label;
    private String description;
    private Boolean enabled;
}
