package com.example.filtro_meta.api.dto.request;

import com.example.filtro_meta.utils.enums.State;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class OptionQuestionReq {
    @NotBlank(message = "The option of the question is required.")
    private String text;

    @NotNull(message = "The status of the user is required.")
    private State active;

    @NotNull(message = "The question id is required.")
    private Long question_id;
}
