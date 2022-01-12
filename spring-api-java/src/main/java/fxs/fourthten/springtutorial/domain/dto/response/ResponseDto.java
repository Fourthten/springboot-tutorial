package fxs.fourthten.springtutorial.domain.dto.response;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ResponseDto<Any> {
    private Integer code;
    private String info;
    private Any data;
}