package hu.morabarna.csaladfa.dtos.personDtos.out;

import java.time.LocalDate;
import java.util.List;


public record PersonDTOShortest(Long id, String name, boolean sex, LocalDate birthDate, String birthLocation, LocalDate deathDate, String deathLocation) {}

