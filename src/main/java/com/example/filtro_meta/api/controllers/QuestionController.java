package com.example.filtro_meta.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.filtro_meta.api.dto.request.QuestionReq;
import com.example.filtro_meta.api.dto.response.QuestionResp;
import com.example.filtro_meta.infrastructure.abstract_services.IQuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/questions")
@AllArgsConstructor
public class QuestionController {
    @Autowired
    private final IQuestionService service;
    
    @Operation(summary = "Lista todos los usuarios con paginación", description = "Debes enviar la página y el tamaño de la página para recibir todas los usuarios correspondientes")
    @GetMapping
    public ResponseEntity<Page<QuestionResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(this.service.getAll(page - 1, size));
    }

    @ApiResponse(responseCode = "400", description = "Cuando el id no es válido", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(summary = "Lista un usuario por id", description = "Debes enviar el id del usuario que deseas buscar")
    @GetMapping(path = "/{id}")
    public ResponseEntity<QuestionResp> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.get(id));
    }

    @Operation(summary = "Crea un usuario", description = "Crea un usuario")
    @PostMapping
    public ResponseEntity<QuestionResp> create(@Validated @RequestBody QuestionReq request){
        return ResponseEntity.ok(this.service.create(request));
    }

    @Operation(summary = "Actualiza un usuario", description = "Actualiza un usuario")
    @PutMapping(path = "/{id}")
    public ResponseEntity<QuestionResp> update(
            @PathVariable Long id,
            @Validated @RequestBody QuestionReq request) {

        return ResponseEntity.ok(this.service.update(request, id));
    }

    @Operation(summary = "Elimina una vacante por Id", description = "Elimina una vacante por Id")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Long id) {

        this.service.delete(id);

        Map<String, String> response = new HashMap<>();

        response.put("message", "Se eliminó la vacante correctamente");

        return ResponseEntity.ok(response);
    }
}
