package hu.morabarna.csaladfa.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Table(name = "persons")
public class Person {
    //Purpose: main entity 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    public Long id;

    public String name;
    // true=female, false=male
    public boolean sex;

    public LocalDate birthDate;
    public String birthLocation;

    
    public LocalDate deathDate;
    public String deathLocation;
    
    
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("child")
    private Set<ParentsChildren> parents;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("parent")
    private Set<ParentsChildren> children;

    public void setChildren(Set<ParentsChildren> children) {
        if (this.children == null) {
            this.children = children;
        } else {
            // this.children.clear();
            if (children != null) {
                for (ParentsChildren child : children) {
                    addChild(child);
                }
            }
        }
    }

    public void addChild(ParentsChildren child) {
        if (this.children == null) {
            this.children = new HashSet<>();
        }
    
        // Check if the child is not already in the set
        if (!this.children.contains(child)) {
            this.children.add(child);
            
            // Check if the parent is not already set for the child
            if (child.getParent() != this) {
                child.setParent(this);
            }
        }
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return id != null ? id.equals(person.id) : person.id == null;
    }
}