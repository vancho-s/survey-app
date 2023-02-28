package com.survey.app.web;

import com.survey.app.service.SurveyService;
import com.survey.app.service.dto.SurveyResponsesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.survey.app.web.util.RestUtils.commaDelimitedListToLongList;

@RestController
@RequestMapping("/api/v1/survey")
public class SurveyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);
    private final SurveyService surveyService;

    public SurveyController(final SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * Returns a list of surveys with user responses by subjectIds and userId
     *
     * @param userId    user id
     * @param subjectIds a comma separated ids of the subjects that the user may responded
     * @return the ResponseEntity with status 200 (OK) and list of surveys
     */
    @GetMapping("/{subjectIds}/user/{userId}")
    public SurveyResponsesDto getUserSurveysForUserIdAndSubjectIds(
        @PathVariable("userId") final long userId, @PathVariable("subjectIds") final String subjectIds) {
        LOGGER.debug("Get list of subjects for user {} with subject ids {}", userId, subjectIds);
        return surveyService.findAllSurveysByUserIdAndSubjectIds(userId, commaDelimitedListToLongList(subjectIds));
    }

    /**
     * Returns a list of all surveys with user responses by userId
     *
     * @param userId user id
     * @return the ResponseEntity with status 200 (OK) and list of surveys
     */
    @GetMapping("/all/user/{userId}")
    public SurveyResponsesDto getAllUserSurveys(@PathVariable("userId") long userId) {
        LOGGER.debug("Get list of all surveys for user {}", userId);
        return surveyService.findAllSurveysByUserId(userId);
    }
}
