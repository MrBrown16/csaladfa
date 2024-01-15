package hu.morabarna.csaladfa.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.morabarna.csaladfa.dtos.personDtos.in.PersonDTOCreate;
import hu.morabarna.csaladfa.dtos.personDtos.in.PersonDTOUpdate;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOShortest;
import hu.morabarna.csaladfa.services.PersonService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/person")
public class PersonController {
    
    PersonService personService;
    

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public Optional<PersonDTOBasic> getPersonById(@PathVariable Long id) {
        if (id>0) {
            return personService.getPerson(id);
        }
        return Optional.empty();
    }

    @GetMapping("/reference/{name}")
    public List<PersonDTOShortest> getPersonByNameLike(@PathVariable String name) {
        if (name !=null && name.length()<3) {
            return Collections.emptyList();
        }
        return personService.findByNameLike(name);
    }

    @PostMapping("/create")
    public boolean addPerson(@RequestBody PersonDTOUpdate person) {
        
        return personService.saveNewPerson(person);
    }
    @PutMapping("/update/{id}")
    public boolean updatePerson(@PathVariable Long id, @RequestBody PersonDTOUpdate person) {
        
        return personService.updatePerson(id,person);
    }
    @GetMapping("/create")
    public Optional<PersonDTOCreate> getNewPersonDTOCreate() {
        
        return Optional.of( new PersonDTOCreate("Miklós", false, (LocalDate) dateParser("1970-11-03"), "Miskolc", 66L, "Márta", 1L, "Sanyi", null, null, Collections.emptyList()));
    }
    @GetMapping("/update")
    public Optional<PersonDTOUpdate> getPersonDTOUpdate() {
        
        return Optional.of( new PersonDTOUpdate(7L, "Miklós", false, (LocalDate) dateParser("1970-11-03"), "Miskolc", 66L, "Márta", 1L, "Sanyi", null, null, Collections.emptyList()));
    }
    
    public LocalDate dateParser(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(dateString, formatter);
    }

    @DeleteMapping("/del/{id}")
    public boolean delById(@PathVariable Long id){
        return personService.delById(id);
    }
}
