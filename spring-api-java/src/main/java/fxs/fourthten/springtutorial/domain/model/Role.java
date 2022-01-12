package fxs.fourthten.springtutorial.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 100)
    private String label;
    @Column(columnDefinition = "text")
    private String description;
    private Boolean enabled;
    @Column(length = 100)
    private String created;
    @Column(length = 100)
    private String updated;
}