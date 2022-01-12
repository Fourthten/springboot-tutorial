package fxs.fourthten.springtutorial.domain.dto.request.user;

import lombok.*;

import javax.validation.constraints.*;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserIdDto {
    @NotEmpty
    private UUID id;
}
