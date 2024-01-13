package hu.morabarna.csaladfa.dtos.personDtos.in;

import java.time.LocalDate;
import java.util.List;

import org.springframework.lang.NonNull;

import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdName;
//Purpose: carry incoming data to be processed before saving to database
public record PersonDTOCreate(@NonNull String name, boolean sex, @NonNull LocalDate birthDate, @NonNull String birthLocation, Long motherId, String motherName, Long fatherId, String fatherName, LocalDate deathDate, String deathLocation, List<PersonDTOIdName> children) {}
