package com.example.filtro_meta.infrastructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.filtro_meta.api.dto.request.SurveyReq;
import com.example.filtro_meta.api.dto.response.OptionQuestionBasicResp;
import com.example.filtro_meta.api.dto.response.QuestionBasicResp;
import com.example.filtro_meta.api.dto.response.SurveyResp;
import com.example.filtro_meta.domain.entities.OptionQuestion;
import com.example.filtro_meta.domain.entities.Question;
import com.example.filtro_meta.domain.entities.Survey;
import com.example.filtro_meta.domain.entities.User;
import com.example.filtro_meta.domain.repositories.SurveyRepository;
import com.example.filtro_meta.domain.repositories.UserRepository;
import com.example.filtro_meta.infrastructure.abstract_services.ISurveyService;
import com.example.filtro_meta.utils.exceptions.BadRequestException;
import com.example.filtro_meta.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SurveyService implements ISurveyService {
    @Autowired
    private final SurveyRepository repository;

    @Autowired
    private final UserRepository UserRepository;
    
    @Override
    public Page<SurveyResp> getAll(int page, int size) {
        if (page < 0)
            page = 0;

        PageRequest pagination = PageRequest.of(page, size);

        return this.repository.findAll(pagination)
                .map(user -> this.entityToResponse(user)); 
    }

    @Override
    public SurveyResp get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public SurveyResp create(SurveyReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public SurveyResp update(SurveyReq request, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private SurveyResp entityToResponse(Survey entity) {
        List<QuestionBasicResp> questions = 
            entity.getQuestions()
            .stream()
            .map(this::questionEntityToResponse)
            .collect(Collectors.toList());  

        return SurveyResp.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .active(entity.getActive())
                .questions(questions)
                .build();
    }

    private QuestionBasicResp questionEntityToResponse(Question entity) {
        List<OptionQuestionBasicResp> optionQuestions = 
            entity.getOptionQuestions()
            .stream()
            .map(this::OptionQuestionEntityToResponse)
            .collect(Collectors.toList());  

        return QuestionBasicResp.builder()
                .id(entity.getId())
                .text(entity.getText())
                .type(entity.getType())
                .active(entity.getActive())
                .optionQuestions(optionQuestions)
                .build();
    }

    private OptionQuestionBasicResp OptionQuestionEntityToResponse(OptionQuestion entity) {
        return OptionQuestionBasicResp.builder()
                .id(entity.getId())
                .text(entity.getText())
                .active(entity.getActive())
                .build();
    }

    private Survey requestToEntity(SurveyReq request) {
        return Survey.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creation_date(request.getCreation_date())
                .active(request.getActive())
                .user(findUser(request.getCreator_id()))
                .build();
    }

    private Survey find(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Survey")));
    }

    private User findUser(Long id) {
        return this.UserRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("User")));
    }
}
