package com.example.filtro_meta.api.dto.request;

import com.example.filtro_meta.utils.enums.State;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionReq {
    @NotBlank(message = "The question is required.")
    private String text;

    @NotBlank(message = "The question type is required.")
    @Size(max = 50, message = "The type of question can't be longer than 50 characters.")
    private String type;

    @NotNull(message = "The status of the user is required.")
    private State active;

    @NotNull(message = "The survey id is required.")
    private Long survey_id;
}
