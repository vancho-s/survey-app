package com.survey.app.web;

import com.survey.app.service.SubjectService;
import com.survey.app.service.dto.SubjectCreateDto;
import com.survey.app.service.dto.SubjectCreateResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.survey.app.web.util.RestUtils.commaDelimitedListToLongList;

/**
 * Rest resource for subject entity
 */
@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);
    private final SubjectService subjectService;

    public SubjectController(final SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Returns existing subjects by ids with related questions
     *
     * @param subjectIds list of subject ids
     * @return the ResponseEntity with status 200 (OK) and list of existing subjects
     */
    @GetMapping("/{subjectIds}")
    public List<SubjectCreateResponseDto> findSubjectsByIds(@PathVariable("subjectIds") final String subjectIds) {
        LOGGER.debug("Get all the subjects and the questions");
        return subjectService.findSubjectsByIds(commaDelimitedListToLongList(subjectIds));
    }

    /**
     * Creates a new subjectCreateDto
     *
     * @param subjectCreateDto contains subject name user entered
     * @return the ResponseEntity with status 200 (OK) created subject
     */
    @PostMapping
    public SubjectCreateResponseDto createSubject(@Valid @RequestBody final SubjectCreateDto subjectCreateDto) {
        LOGGER.debug("Create a new subject: {}", subjectCreateDto.getSubjectLabel());
        return subjectService.createSubject(subjectCreateDto);
    }

}
