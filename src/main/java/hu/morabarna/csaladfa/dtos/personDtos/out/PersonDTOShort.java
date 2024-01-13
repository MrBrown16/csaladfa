package hu.morabarna.csaladfa.dtos.personDtos.out;

import java.time.LocalDate;
//Purpose: to query a Person entity without its children to later be used in a PersonDTOBasic where it is united with its children only needed because couldn't query the Person and receive a personDTOBasic (properties + a list of children)
public record PersonDTOShort(Long id, String name, boolean sex, LocalDate birthDate, String birthLocation, Long motherId, String motherName, Long fatherId, String fatherName, LocalDate deathDate, String deathLocation) {}
