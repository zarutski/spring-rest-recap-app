package com.self.recap.rest.controller;

import com.self.recap.rest.dto.PersonDTO;
import com.self.recap.rest.exception.PersonNotCreatedException;
import com.self.recap.rest.model.Person;
import com.self.recap.rest.service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable("id") int id) {
        return peopleService.find(id); // convert to JSON by Jackson lib
    }

    // validation added for input requests [simple body validation]
    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = formErrorMessage(bindingResult);
            throw new PersonNotCreatedException(message);
        }
        Person persisted = peopleService.save(convertToPerson(personDTO));
        return new ResponseEntity<>(convertToDTO(persisted), HttpStatus.CREATED);
    }

    private Person convertToPerson(@Valid PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setAge(personDTO.getAge());
        person.setEmail(personDTO.getEmail());
        return person;
    }

    private PersonDTO convertToDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName());
        personDTO.setAge(person.getAge());
        personDTO.setEmail(person.getEmail());
        return personDTO;
    }

    private String formErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError error : fieldErrors) {
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMessage.toString();
    }
}
