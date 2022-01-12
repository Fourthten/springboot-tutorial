package fxs.fourthten.springtutorial.domain.dto.response.role;

import lombok.*;

import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RoleDto {
    private UUID id;
    private String name;
    private String label;
    private String description;
    private Boolean enabled;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
}
