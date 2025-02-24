package com.self.recap.rest.service;

import com.self.recap.rest.model.Person;
import com.self.recap.rest.repository.PeopleRepository;
import com.self.recap.rest.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository repository;

    @Autowired
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person find(int id) throws PersonNotFoundException {
        return repository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

}

