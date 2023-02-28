package com.survey.app.web;

import com.survey.app.service.QuestionService;
import com.survey.app.service.dto.QuestionCreateRequest;
import com.survey.app.service.dto.QuestionResponse;
import com.survey.app.service.dto.QuestionUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    public QuestionController(final QuestionService questionService) {
        this.questionService = questionService;

    }

    /**
     * Add the new questions provides in request
     *
     * @param questionsCreate contains subjectId and question answers user entered
     * @return the ResponseEntity with status 200 (OK) and list of the saved QuestionDto
     */
    @PostMapping
    public QuestionResponse addQuestionsToSubject(@Valid @RequestBody final QuestionCreateRequest questionsCreate) {
        LOGGER.debug("Create a new questions for subject id : {}", questionsCreate.getSubjectId());
        return questionService.createSubjectQuestions(questionsCreate);
    }

    /**
     * Updates the existing questions provides in request
     *
     * @param questionUpdate contains subjectId and question answers updates user entered
     * @return the ResponseEntity with status 200 (OK) and list of the saved QuestionDto
     */
    @PutMapping
    public QuestionResponse updateQuestionsToSubject(@Valid @RequestBody final QuestionUpdateRequest questionUpdate) {
        LOGGER.debug("Update an existing questions for subject id : {}", questionUpdate.getSubjectId());
        return questionService.updateSubjectQuestions(questionUpdate);
    }
}
