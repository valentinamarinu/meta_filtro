package com.example.filtro_meta.infrastructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.filtro_meta.api.dto.request.QuestionReq;
import com.example.filtro_meta.api.dto.response.OptionQuestionBasicResp;
import com.example.filtro_meta.api.dto.response.QuestionResp;
import com.example.filtro_meta.api.dto.response.SurveyToQuestionResp;
import com.example.filtro_meta.api.dto.response.UserBasicResp;
import com.example.filtro_meta.domain.entities.OptionQuestion;
import com.example.filtro_meta.domain.entities.Question;
import com.example.filtro_meta.domain.entities.Survey;
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

    @Override
    public Page<QuestionResp> getAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public QuestionResp get(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public QuestionResp create(QuestionReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public QuestionResp update(QuestionReq request, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
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
