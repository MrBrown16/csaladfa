package hu.morabarna.csaladfa.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic;
import hu.morabarna.csaladfa.services.PersonService;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/person")
public class PersonController {
    
    PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // @GetMapping("/{id}")
    // public Optional<PersonDTOBasic> getPersonById(@PathVariable Long id) {
    //     return personService.getPerson(id);
    // }
    
}
