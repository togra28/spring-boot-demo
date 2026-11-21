package com.example.demo_webapp.controller;

import com.example.demo_webapp.model.Person;
import com.example.demo_webapp.repository.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/people")
public class PersonRestController {

    private final PersonRepository personRepository;

    public PersonRestController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable Long id) {
        return findPerson(id);
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        Person newPerson = new Person();
        newPerson.setName(person.getName());
        newPerson.setEmail(person.getEmail());

        Person savedPerson = personRepository.save(newPerson);
        return ResponseEntity.created(URI.create("/api/people/" + savedPerson.getId()))
                .body(savedPerson);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable Long id, @Valid @RequestBody Person person) {
        Person existingPerson = findPerson(id);
        existingPerson.setName(person.getName());
        existingPerson.setEmail(person.getEmail());
        return personRepository.save(existingPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personRepository.delete(findPerson(id));
        return ResponseEntity.noContent().build();
    }

    private Person findPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Person not found"));
    }
}
