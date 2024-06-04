package com.example.filtro_meta.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.filtro_meta.domain.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
