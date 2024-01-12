package hu.morabarna.csaladfa.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic;
import hu.morabarna.csaladfa.repositories.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class PersonService {

    @PersistenceContext
    EntityManager entityManager;

    PersonRepository personRepository;


    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    // public Optional<PersonDTOBasic> getPerson(Long id){
    //     // return personRepository.findPersonDTOBasicById(id, entityManager);
    // }


}
