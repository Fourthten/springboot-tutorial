package fxs.fourthten.springtutorial.domain.dto.request.role;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RoleIdDto {
    @NotEmpty
    private UUID id;
}
