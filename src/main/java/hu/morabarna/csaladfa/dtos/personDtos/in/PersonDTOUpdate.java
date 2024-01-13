package hu.morabarna.csaladfa.dtos.personDtos.in;

import java.time.LocalDate;
import java.util.List;

import org.springframework.lang.NonNull;

import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdName;
//Purpose: carry incoming data to be processed before saving to database with the id provided updating a person's record and or the relations it has with it's parents and or children
public record PersonDTOUpdate(@NonNull Long id, @NonNull String name, boolean sex, @NonNull LocalDate birthDate, @NonNull String birthLocation, Long motherId, String motherName, Long fatherId, String fatherName, LocalDate deathDate, String deathLocation, List<PersonDTOIdName> children) {}
