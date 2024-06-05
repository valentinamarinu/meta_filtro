package com.example.filtro_meta.infrastructure.abstract_services;

import com.example.filtro_meta.api.dto.request.UserReq;
import com.example.filtro_meta.api.dto.response.UserResp;

public interface IUserService extends CrudService<UserReq, UserResp, Long> {
    
}
