package hu.morabarna.csaladfa.dtos.personDtos.out;
//Purpose: provide an abstraction for reference carrying between dtos and methods
public record PersonDTOIdNameSex(Long id, String name, boolean sex) {}
