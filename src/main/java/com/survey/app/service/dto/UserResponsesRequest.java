package com.survey.app.service.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserResponsesRequest {
    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long userId;

    @NotNull
    private List<@Valid UserResponseDto> userResponses;
}
