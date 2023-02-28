package com.survey.app.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserResponsesResponse {
    private long userId;
    private List<UserResponseDto> responses;
}
