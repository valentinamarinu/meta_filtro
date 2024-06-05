package com.example.filtro_meta.infrastructure.abstract_services;

import com.example.filtro_meta.api.dto.request.QuestionReq;
import com.example.filtro_meta.api.dto.response.QuestionResp;

public interface IQuestionService extends CrudService<QuestionReq, QuestionResp, Long> {
    
}
