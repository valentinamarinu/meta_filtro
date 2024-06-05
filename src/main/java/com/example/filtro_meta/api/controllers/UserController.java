package com.example.filtro_meta.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.filtro_meta.api.dto.response.UserResp;
import com.example.filtro_meta.infrastructure.abstract_services.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {
    @Autowired
    private final IUserService service;

    @Operation(summary = "Lista todos los usuarios con paginación", description = "Debes enviar la página y el tamaño de la página para recibir todas los usuarios correspondientes")
    @GetMapping(path = "/class")
    public ResponseEntity<Page<UserResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(this.service.getAll(page - 1, size));
    }
}
