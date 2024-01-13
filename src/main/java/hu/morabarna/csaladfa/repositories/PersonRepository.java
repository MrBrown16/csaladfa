package hu.morabarna.csaladfa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdName;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOShort;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOShortest;
import hu.morabarna.csaladfa.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

       

       @Query("SELECT new hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOShortest(" +
              "p.id, p.name, p.sex, p.birthDate, p.birthLocation, " +
              "p.deathDate, p.deathLocation) " +
              "FROM Person p " +
              "WHERE p.id = :personId")
       Optional<PersonDTOShortest> getPersonDetailsById(@Param("personId") Long personId);

       @Query("SELECT new hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOShortest(" +
        "p.id, p.name, p.sex, p.birthDate, p.birthLocation, " +
        "p.deathDate, p.deathLocation) " +
        "FROM Person p " +
        "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
       List<PersonDTOShortest> findByNameLike(@Param("name") String name);


       @Modifying
       @Query("DELETE FROM Person p WHERE p.id = :personId")
       void deletePersonAndRelations(@Param("personId") Long personId);

}
