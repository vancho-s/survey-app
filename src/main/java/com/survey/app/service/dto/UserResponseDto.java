package com.survey.app.service.dto;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserResponseDto {

    private Long userResponseId;
    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long questionId;

    private String userResponseOption;

    @NotNull
    @Size(max = 50)
    private String userResponse;
}
