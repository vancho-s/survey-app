package com.survey.app.service;

import com.survey.app.exception.QuestionNotFoundException;
import com.survey.app.exception.SubjectNotFoundException;
import com.survey.app.service.dto.*;

public interface QuestionService {

    /**
     * Adds a new question for existing subject
     *
     * @param questionCreateRequest user request which contains subject id and a list of questions
     * @return list of created questions for survey
     * @throws SubjectNotFoundException if no subject was found
     */
    QuestionResponse createSubjectQuestions(final QuestionCreateRequest questionCreateRequest);

    /**
     * Updates existing question for existing subject
     *
     * @param questionUpdateRequest user request which contains subject id and a list of questions to update
     * @return list of updated questions for survey
     * @throws SubjectNotFoundException  if no subject was found
     * @throws QuestionNotFoundException if no question was found
     */
    QuestionResponse updateSubjectQuestions(final QuestionUpdateRequest questionUpdateRequest);

}
