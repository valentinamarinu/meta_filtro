package com.example.filtro_meta.api.dto.response;

import java.time.LocalDateTime;
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
public class SurveyToUserResp {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime creation_date;
    private State active;
    private List<QuestionBasic> questions;
}
