package com.example.filtro_meta.api.dto.request;

import com.example.filtro_meta.utils.enums.State;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "The status of the option of the question is required.")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "The state must be ACTIVE or INACTIVE.")
    private State active;

    @NotNull(message = "The question id is required.")
    private Long question_id;
}
