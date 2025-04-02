package com.self.recap.rest.controller;

import com.self.recap.rest.dto.PersonDTO;
import com.self.recap.rest.exception.PersonNotCreatedException;
import com.self.recap.rest.model.Person;
import com.self.recap.rest.service.PeopleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper mapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper mapper) {
        this.peopleService = peopleService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PersonDTO> getPeople() {
        return peopleService.findAll().stream().map(this::convertToDTO).toList();
    }

    @GetMapping("/{id}")
    public PersonDTO getById(@PathVariable("id") int id) {
        return convertToDTO(peopleService.find(id)); // convert to JSON by Jackson lib
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

    private Person convertToPerson(PersonDTO personDTO) {
        return mapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToDTO(Person person) {
        return mapper.map(person, PersonDTO.class);
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
