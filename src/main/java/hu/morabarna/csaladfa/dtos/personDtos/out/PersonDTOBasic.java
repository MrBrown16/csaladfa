package hu.morabarna.csaladfa.dtos.personDtos.out;

import java.util.Date;
import java.util.List;

public record PersonDTOBasic(Long id, String name, boolean sex, Date birthDate, String birthLocation, Long motherId, String motherName, Long fatherId, String fatherName, Date deathDate, String deathLocation, List<PersonDTOChildren> children) {}
