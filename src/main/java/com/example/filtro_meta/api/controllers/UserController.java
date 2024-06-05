package com.example.filtro_meta.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.filtro_meta.infrastructure.abstract_services.IUserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {
    @Autowired
    private final IUserService service;

}
