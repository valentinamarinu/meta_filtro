package com.example.filtro_meta.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.filtro_meta.infrastructure.abstract_services.ISurveyService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class SurveyController {
    @Autowired
    private final ISurveyService service;
        
}
