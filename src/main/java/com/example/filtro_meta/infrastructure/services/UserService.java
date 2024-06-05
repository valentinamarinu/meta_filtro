package com.example.filtro_meta.infrastructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.filtro_meta.api.dto.request.UserReq;
import com.example.filtro_meta.api.dto.response.OptionQuestionBasicResp;
import com.example.filtro_meta.api.dto.response.QuestionBasicResp;
import com.example.filtro_meta.api.dto.response.SurveyToUserResp;
import com.example.filtro_meta.api.dto.response.UserResp;
import com.example.filtro_meta.domain.entities.OptionQuestion;
import com.example.filtro_meta.domain.entities.Question;
import com.example.filtro_meta.domain.entities.Survey;
import com.example.filtro_meta.domain.entities.User;
import com.example.filtro_meta.domain.repositories.UserRepository;
import com.example.filtro_meta.infrastructure.abstract_services.IUserService;
import com.example.filtro_meta.utils.exceptions.BadRequestException;
import com.example.filtro_meta.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private final UserRepository repository;
    
    @Override
    public Page<UserResp> getAll(int page, int size) {
        if (page < 0)
            page = 0;

        PageRequest pagination = PageRequest.of(page, size);

        return this.repository.findAll(pagination)
                .map(user -> this.entityToResponse(user)); 
    }

    @Override
    public UserResp get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public UserResp create(UserReq request) {
        User user = this.requestToEntity(request);
        
        user.setSurveys(new ArrayList<>());

        return this.entityToResponse(this.repository.save(user));
    }

    @Override
    public UserResp update(UserReq request, Long id) {
        User user = this.find(id);

        User userUpdate = this.requestToEntity(request);

        userUpdate.setId(id);
        userUpdate.setSurveys(user.getSurveys());
    
        return this.entityToResponse(this.repository.save(userUpdate));
    }

    @Override
    public void delete(Long id) {
        
    }
    
    private UserResp entityToResponse(User entity) {
        List<SurveyToUserResp> surveys = 
            entity.getSurveys()
            .stream()
            .map(this::surveyEntityToResponse)
            .collect(Collectors.toList());         
        
        return UserResp.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .active(entity.getActive())
                .surveys(surveys)
                .build();
    }

    private SurveyToUserResp surveyEntityToResponse(Survey entity) {
        List<QuestionBasicResp> questions = 
            entity.getQuestions()
            .stream()
            .map(this::questionEntityToResponse)
            .collect(Collectors.toList());  

        return SurveyToUserResp.builder()
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

    private User requestToEntity(UserReq request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .active(request.getActive())
                .build();
    }

    private User find(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("User")));
    }
}
