package com.example.filtro_meta.infrastructure.abstract_services;

import com.example.filtro_meta.api.dto.request.SurveyReq;
import com.example.filtro_meta.api.dto.response.SurveyResp;

public interface ISurveyService extends CrudService<SurveyReq, SurveyResp, Long> {
    
}
