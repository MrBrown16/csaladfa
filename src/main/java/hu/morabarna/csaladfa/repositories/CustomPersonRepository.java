package hu.morabarna.csaladfa.repositories;

import java.util.Optional;

import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic;

public interface CustomPersonRepository {


    Optional<PersonDTOBasic> findPersonDTOBasicById(Long id);
}
