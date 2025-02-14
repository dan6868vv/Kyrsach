package com.knf.dev.demo.repository;

import com.knf.dev.demo.entity.Spec;
import com.knf.dev.demo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {

    Spec findByEmail(String email);
}