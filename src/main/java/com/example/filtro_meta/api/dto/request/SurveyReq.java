package com.example.filtro_meta.api.dto.request;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.filtro_meta.utils.enums.State;

import jakarta.validation.constraints.FutureOrPresent;
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
public class SurveyReq {
    @NotBlank(message = "The title of the survey is required.")
    @Size(max = 255, message = "The type of question can't be longer than 255 characters.")
    private String title;
    
    private String description;

    @NotNull(message = "Submission date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent(message = "It is not possible to enter a date later than the current date.")
    private LocalDateTime creation_date;

    @NotNull(message = "The status of the user is required.")
    private State active;

    @NotNull(message = "The creator id is required.")
    private Long creator_id;
}
