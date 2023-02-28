package com.survey.app.web;

import com.survey.app.service.UserResponseService;
import com.survey.app.service.dto.UserResponsesRequest;
import com.survey.app.service.dto.UserResponsesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/response")
public class UserResponseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResponseController.class);
    private final UserResponseService userResponseService;

    public UserResponseController(final UserResponseService userResponseService) {
        this.userResponseService = userResponseService;
    }

    /**
     * Saves user responses for the provided questions for specific customerId
     *
     * @param responseCreate questions customer id and question answers user entered
     * @return the ResponseEntity with status 200 (OK) and list of the saved UserResponseDto
     */
    @PostMapping
    public UserResponsesResponse saveUserResponses(@Valid @RequestBody final UserResponsesRequest responseCreate) {
        LOGGER.debug("Save the a new user responses for user {}", responseCreate.getUserId());
        return userResponseService.createUserResponses(responseCreate);
    }

    /**
     * Updates user responses for the provided questions for specific customerId
     *
     * @param responseUpdate questions customer id and question answers updates user entered
     * @return the ResponseEntity with status 200 (OK) and list of the saved UserResponseDto
     */
    @PutMapping
    public UserResponsesResponse updateUserResponses(@Valid @RequestBody final UserResponsesRequest responseUpdate) {
        LOGGER.debug("Update existing user responses for user {}", responseUpdate.getUserId());
        return userResponseService.updateUserResponses(responseUpdate);
    }
}
