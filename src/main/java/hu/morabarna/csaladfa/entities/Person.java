package hu.morabarna.csaladfa.entities;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @RequiredArgsConstructor @EqualsAndHashCode
@Table(name = "persons")
public class Person {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    public Long id;

    public String name;
    //true=female, false=male
    public boolean sex;

    public Date birthDate;
    public String birthLocation;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("children")
    public Set<Person> parents;
    
    public Date deathDate;
    public String deathLocation;

    @ManyToMany(mappedBy = "parents", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("parents")
    public Set<Person> children;
}
