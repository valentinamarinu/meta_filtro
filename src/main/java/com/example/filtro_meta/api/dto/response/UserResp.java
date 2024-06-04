package com.example.filtro_meta.api.dto.response;

import java.util.List;

import com.example.filtro_meta.utils.enums.State;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResp {
    private Long id;
    private String name;
    private String email;
    private String password;
    private State active;
    private List<SurveyToUserResp> surveys;
}
