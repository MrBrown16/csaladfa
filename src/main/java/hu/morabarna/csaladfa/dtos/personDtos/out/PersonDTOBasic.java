package hu.morabarna.csaladfa.dtos.personDtos.out;

import java.time.LocalDate;
import java.util.List;
//Purpose: carry data from a method to the client(controller) one person with a list of its children and other properties
public record PersonDTOBasic(Long id, String name, boolean sex, LocalDate birthDate, String birthLocation, Long motherId, String motherName, Long fatherId, String fatherName, LocalDate deathDate, String deathLocation, List<PersonDTOIdName> children) {}
