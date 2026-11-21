package com.example.demo_webapp.repository;

import com.example.demo_webapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
