package com.example.filtro_meta.infrastructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.filtro_meta.api.dto.request.QuestionReq;
import com.example.filtro_meta.api.dto.response.OptionQuestionBasicResp;
import com.example.filtro_meta.api.dto.response.QuestionResp;
import com.example.filtro_meta.api.dto.response.SurveyToQuestionResp;
import com.example.filtro_meta.api.dto.response.UserBasicResp;
import com.example.filtro_meta.domain.entities.OptionQuestion;
import com.example.filtro_meta.domain.entities.Question;
import com.example.filtro_meta.domain.entities.Survey;
import com.example.filtro_meta.domain.repositories.OptionQuestionRepository;
import com.example.filtro_meta.domain.repositories.QuestionRepository;
import com.example.filtro_meta.domain.repositories.SurveyRepository;
import com.example.filtro_meta.infrastructure.abstract_services.IQuestionService;
import com.example.filtro_meta.utils.exceptions.BadRequestException;
import com.example.filtro_meta.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {
    @Autowired
    private final QuestionRepository repository;

    @Autowired
    private final SurveyRepository surveyRepository;

    @Autowired
    private final OptionQuestionRepository optionQuestionRepository;

    @Override
    public Page<QuestionResp> getAll(int page, int size) {
         if (page < 0)
            page = 0;

        PageRequest pagination = PageRequest.of(page, size);

        return this.repository.findAll(pagination)
                .map(question -> this.entityToResponse(question)); 
    }

    @Override
    public QuestionResp get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public QuestionResp create(QuestionReq request) {
        Question question = this.requestToEntity(request);
        
        boolean IsEmpty = true;

        if(question.getOptionQuestions() == null) {
            IsEmpty = true;
        }else if(question.getOptionQuestions().isEmpty()) {
            IsEmpty = true;
        } else {
            IsEmpty = false;
        }
        
        
        
        
        question.setOptionQuestions(new ArrayList<>());
        question.setSurvey(findSurvey(request.getSurvey_id()));

        return this.entityToResponse(this.repository.save(question));
    }

    @Override
    public QuestionResp update(QuestionReq request, Long id) {
        Question question = this.find(id);

        Question questionUpdate = this.requestToEntity(request);

        questionUpdate.setId(id);
        questionUpdate.setOptionQuestions(question.getOptionQuestions());
    
        return this.entityToResponse(this.repository.save(questionUpdate));
    }

    @Override
    public void delete(Long id) {
        Question question = this.find(id);

        this.repository.delete(question);
    }

    private QuestionResp entityToResponse(Question entity) {
        UserBasicResp user = new UserBasicResp();

        if(entity.getSurvey().getUser() != null) {
            BeanUtils.copyProperties(entity.getSurvey().getUser(), user);
        }
        
        SurveyToQuestionResp survey = new SurveyToQuestionResp();

        if (entity.getSurvey() != null) {
            BeanUtils.copyProperties(entity.getSurvey(), survey);
        }

        List<OptionQuestionBasicResp> optionQuestions = 
            entity.getOptionQuestions()
            .stream()
            .map(this::OptionQuestionEntityToResponse)
            .collect(Collectors.toList());

        return QuestionResp.builder()
                .id(entity.getId())
                .text(entity.getText())
                .type(entity.getType())
                .optionQuestions(optionQuestions)
                .survey(survey)
                .build();
    }
    
    private OptionQuestionBasicResp OptionQuestionEntityToResponse(OptionQuestion entity) {
        return OptionQuestionBasicResp.builder()
                .id(entity.getId())
                .text(entity.getText())
                .active(entity.getActive())
                .build();
    }

    private Question requestToEntity(QuestionReq request) {
        return Question.builder()
                .text(request.getText())
                .type(request.getType())
                .active(request.getActive())
                .survey(findSurvey(request.getSurvey_id()))
                .build();
    }
    
    private Question find(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("User")));
    }

    private Survey findSurvey(Long id) {
        return this.surveyRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Survey")));
    }
}
