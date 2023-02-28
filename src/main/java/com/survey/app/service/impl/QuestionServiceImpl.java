package com.survey.app.service.impl;

import com.survey.app.domain.Question;
import com.survey.app.repository.QuestionRepository;
import com.survey.app.service.QuestionService;
import com.survey.app.service.dto.QuestionCreateRequest;
import com.survey.app.service.dto.QuestionResponse;
import com.survey.app.service.dto.QuestionUpdateDto;
import com.survey.app.service.dto.QuestionUpdateRequest;
import com.survey.app.service.mapper.QuestionMapper;
import com.survey.app.service.validator.QuestionValidator;
import com.survey.app.service.validator.SubjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

@Component
public class QuestionServiceImpl implements QuestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private final SubjectValidator subjectValidator;
    private final QuestionMapper questionMapper;
    private final QuestionValidator questionValidator;
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(final SubjectValidator subjectValidator, final QuestionMapper questionMapper,
                               final QuestionValidator questionValidator, final QuestionRepository questionRepository) {
        this.subjectValidator = subjectValidator;
        this.questionMapper= questionMapper;
        this.questionValidator = questionValidator;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionResponse createSubjectQuestions(final QuestionCreateRequest questionCreate) {
        LOGGER.debug("Add new createdQuestions for subject {}", questionCreate.getSubjectId());
        if (CollectionUtils.isEmpty(questionCreate.getSubjectQuestions())) {
            LOGGER.debug("No subjects to add");
            return questionMapper.toQuestionCreateUpdateResponse(questionCreate.getSubjectId(), emptyList());
        }

        subjectValidator.verifySubjectsExist(Set.of(questionCreate.getSubjectId()));
        List<Question> questions = questionMapper
            .questionDtosToQuestions(questionCreate.getSubjectId(), questionCreate.getSubjectQuestions());
        List<Question> createdQuestions = questionRepository.saveAll(questions);
        LOGGER.debug("Created createdQuestions {} for subject: {}", createdQuestions.size(),
            questionCreate.getSubjectId());
        return questionMapper.toQuestionCreateUpdateResponse(questionCreate.getSubjectId(), createdQuestions);
    }

    @Override
    public QuestionResponse updateSubjectQuestions(final QuestionUpdateRequest questionUpdate) {
        LOGGER.debug("Update an existing questions for subject {}", questionUpdate.getSubjectId());
        if (CollectionUtils.isEmpty(questionUpdate.getSubjectQuestions())) {
            LOGGER.debug("No subjects to update");
            return questionMapper.toQuestionCreateUpdateResponse(questionUpdate.getSubjectId(), emptyList());
        }
        subjectValidator.verifySubjectsExist(Set.of(questionUpdate.getSubjectId()));
        Set<Long> existingQuestionIds = questionMapper
            .questionUpdateDtoListToQuestionIds(questionUpdate.getSubjectQuestions());
        questionValidator.verifyQuestionsExist(existingQuestionIds);
        Map<Long, List<QuestionUpdateDto>> questionsUpdateAgr = getQuestionUpdateArg(questionUpdate);
        Set<Question> existingQuestions = questionRepository.findExistingQuestionsByIds(existingQuestionIds);
        updateExistingQuestions(questionsUpdateAgr, existingQuestions);
        List<Question> updatedQuestions = questionRepository.saveAll(existingQuestions);
        LOGGER.debug("Updated questions {} for subject: {}", updatedQuestions.size(),
            questionUpdate.getSubjectId());
        return questionMapper.toQuestionCreateUpdateResponse(questionUpdate.getSubjectId(), updatedQuestions);
    }

    private Map<Long, List<QuestionUpdateDto>> getQuestionUpdateArg(final QuestionUpdateRequest questionsUpdateDto) {
        return CollectionUtils.isEmpty(questionsUpdateDto.getSubjectQuestions()) ? new HashMap<>()
            : questionsUpdateDto.getSubjectQuestions().stream().collect(groupingBy(QuestionUpdateDto::getQuestionId));
    }

    private void updateExistingQuestions(final Map<Long, List<QuestionUpdateDto>> questionsUpdateAgr,
                                         final Set<Question> existingQuestions) {
        for (Question question : existingQuestions) {
            QuestionUpdateDto questionDto = questionsUpdateAgr.get(question.getId()).get(0);
            question.setLabel(questionDto.getQuestionLabel());
            question.setEnabled(questionDto.getEnabled());
        }
    }
}
