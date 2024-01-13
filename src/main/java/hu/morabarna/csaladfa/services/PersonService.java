package hu.morabarna.csaladfa.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import hu.morabarna.csaladfa.dtos.personDtos.in.PersonDTOCreate;
import hu.morabarna.csaladfa.dtos.personDtos.in.PersonDTOUpdate;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdName;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdNameSex;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOShort;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOShortest;
import hu.morabarna.csaladfa.entities.Person;
import hu.morabarna.csaladfa.entities.ParentsChildren;
import hu.morabarna.csaladfa.repositories.PersonRepository;
import hu.morabarna.csaladfa.repositories.ParentsChildrenRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @PersistenceContext
    EntityManager entityManager;

    PersonRepository personRepository;
    ParentsChildrenRepository parentsChildrenRepository;

    public PersonService(PersonRepository personRepository, ParentsChildrenRepository parentsChildrenRepository) {
        this.personRepository = personRepository;
        this.parentsChildrenRepository = parentsChildrenRepository;
    }

    @Transactional
    public Optional<PersonDTOBasic> getPerson(Long id) {
        Optional<PersonDTOShortest> person = personRepository.getPersonDetailsById(id);
        if (person.isPresent()) {
            PersonDTOShortest presentPerson = person.get();
            List<PersonDTOIdName> children = parentsChildrenRepository.getChildrenByParentId(id);
            List<PersonDTOIdNameSex> parents = parentsChildrenRepository.getParentsSexByChildId(id);
            PersonDTOIdNameSex mother=new PersonDTOIdNameSex(null, null, false);
            PersonDTOIdNameSex father=new PersonDTOIdNameSex(null, null, false);
            for (PersonDTOIdNameSex parent : parents) {
                if (parent.sex()) {
                    mother = parent;
                }else{
                    father = parent;
                }
            }
            return Optional.of(new PersonDTOBasic(id, presentPerson.name(), presentPerson.sex(),
            presentPerson.birthDate(), presentPerson.birthLocation(), mother.id(),
            mother.name(), father.id(), father.name(),
            presentPerson.deathDate(), presentPerson.deathLocation(), children));
        }
        return Optional.empty();
    }

    public List<PersonDTOShortest> findByNameLike(String name) {
        return personRepository.findByNameLike(name);
    }

    @Transactional
    public boolean saveNewPerson(PersonDTOCreate person) {
        if (person.name() != null && person.name().length() > 3 && person.birthDate() != null
                && person.birthLocation() != null) {
            Person newPerson = new Person();
            newPerson.setName(person.name());
            newPerson.setSex(person.sex());
            newPerson.setBirthDate(person.birthDate());
            newPerson.setBirthLocation(person.birthLocation());

            if (person.motherId() != null && person.motherId() > 0) {
                Person mother = entityManager.find(Person.class, person.motherId());
                if (mother != null) {
                    ParentsChildren parentsChildren = new ParentsChildren(mother, newPerson);
                    parentsChildrenRepository.save(parentsChildren);
                }else if (person.motherName() != null && person.motherName().length() > 3) {
                    // ... (create new Person entity for mother)
                    ParentsChildren parentsChildren = new ParentsChildren(createAndSavePartialEntityParent(person.motherName(), true), newPerson);
                    parentsChildrenRepository.save(parentsChildren);
                }else{
                    System.err.println("The given Person Entity (Mother) does not exist: ");
                }
            } 

            if (person.fatherId() != null && person.fatherId() > 0) {
                Person father = entityManager.find(Person.class, person.fatherId());
                if (father != null) {
                    ParentsChildren parentsChildren = new ParentsChildren(father, newPerson);
                    parentsChildrenRepository.save(parentsChildren);
                }else if (person.fatherName() != null && person.fatherName().length() > 3) {
                    // ... (create new Person entity for father)
                    ParentsChildren parentsChildren = new ParentsChildren(createAndSavePartialEntityParent(person.fatherName(), false), newPerson);
                    parentsChildrenRepository.save(parentsChildren);
                }else {
                    System.err.println("The given Person Entity (Father) does not exist: ");
                }
            } 
            newPerson.setDeathDate(person.deathDate());
            newPerson.setDeathLocation(person.deathLocation());

            if (person.children().size() > 0) {
                Set<ParentsChildren> children = new HashSet<>();
                for (PersonDTOIdName child : person.children()) {
                    if (child.id() != null && child.id() > 3) {
                        Person childEntity = entityManager.find(Person.class, child.id());
                        if(childEntity != null) {
                            ParentsChildren parentsChildren = new ParentsChildren(newPerson, childEntity);
                            children.add(parentsChildren);
                        } else if (child.name() != null && child.name().length() > 3) {
                            // ... (create new Person entity for child)
                            ParentsChildren parentsChildren = new ParentsChildren(newPerson, createAndSavePartialEntityChild(child.name()));
                            children.add(parentsChildren);
                        }else {
                            System.err.println("The given Person Entity (Child) does not exist: ");
                        }
                    }
                }
                newPerson.setChildren(children);
            }

            try {
                personRepository.save(newPerson);
            } catch (Exception e) {
                System.err.println(
                        "---------------------------Error in saving new Person Entity ------------------------------"
                                + e);
                return false;
            }
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updatePerson(Long id, PersonDTOUpdate person) {
        if (person.id() == id && id != null && personRepository.existsById(id)) {
            if (person.name() != null && person.name().length() > 3 && person.birthDate() != null
                    && person.birthLocation() != null) {
                Person newPerson = new Person();
                newPerson.setName(person.name());
                newPerson.setSex(person.sex());
                newPerson.setBirthDate(person.birthDate());
                newPerson.setBirthLocation(person.birthLocation());

                if (person.motherId() != null && person.motherId() > 0) {
                    Person mother = entityManager.find(Person.class, person.motherId());
                    if (mother != null) {
                        ParentsChildren parentsChildren = new ParentsChildren(mother, newPerson);
                        parentsChildrenRepository.save(parentsChildren);
                    }else if (person.motherName() != null && person.motherName().length() > 3) {
                        // ... (create new Person entity for mother)
                        ParentsChildren parentsChildren = new ParentsChildren(createAndSavePartialEntityParent(person.motherName(), true), newPerson);
                        parentsChildrenRepository.save(parentsChildren);
                    }else{
                        System.err.println("The given Person Entity (Mother) does not exist: ");
                    }
                } 
    
                if (person.fatherId() != null && person.fatherId() > 0) {
                    Person father = entityManager.find(Person.class, person.fatherId());
                    if (father != null) {
                        ParentsChildren parentsChildren = new ParentsChildren(father, newPerson);
                        parentsChildrenRepository.save(parentsChildren);
                    }else if (person.fatherName() != null && person.fatherName().length() > 3) {
                        // ... (create new Person entity for father)
                        ParentsChildren parentsChildren = new ParentsChildren(createAndSavePartialEntityParent(person.fatherName(), false), newPerson);
                        parentsChildrenRepository.save(parentsChildren);
                    }else {
                        System.err.println("The given Person Entity (Father) does not exist: ");
                    }
                } 

                newPerson.setDeathDate(person.deathDate());
                newPerson.setDeathLocation(person.deathLocation());

                if (person.children().size() > 0) {
                    Set<ParentsChildren> children = new HashSet<>();
                    for (PersonDTOIdName child : person.children()) {
                        if (child.id() != null && child.id() > 0 ) {
                            Person childEntity = entityManager.find(Person.class, child.id());
                            if(childEntity != null) {
                                ParentsChildren parentsChildren = new ParentsChildren(newPerson, childEntity);
                                children.add(parentsChildren);
                            }else if (child.name() != null&& child.name().length() > 3) {
                                // ... (create new Person entity for child)
                                ParentsChildren parentsChildren = new ParentsChildren(newPerson, createAndSavePartialEntityChild(child.name()));
                                children.add(parentsChildren);
                            }else {
                                System.err.println("The given Person Entity (Child) does not exist: ");
                            }
                        } 
                    }
                    newPerson.setChildren(children);
                }

                try {
                    personRepository.save(newPerson);
                } catch (Exception e) {
                    System.err.println(
                            "---------------------------Error in saving new Person Entity ------------------------------"
                                    + e);
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    @Transactional
    public boolean delById(Long id) {
        if (id != null && personRepository.existsById(id)) {
            // Also delete related ParentsChildren entries
            parentsChildrenRepository.deleteByParentIdOrChildId(id);
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public Person createAndSavePartialEntityParent(String name, boolean sex){
        Person newPerson = new Person();
        newPerson.setName(name);
        newPerson.setSex(sex);
        return personRepository.save(newPerson);
        
    }

    public Person createAndSavePartialEntityChild(String name){
        Person newPerson = new Person();
        newPerson.setName(name);
        return personRepository.save(newPerson);
        
    }
}


// package hu.morabarna.csaladfa.services;

// import java.util.HashSet;
// import java.util.List;
// import java.util.Optional;
// import java.util.Set;

// import org.springframework.stereotype.Service;

// import hu.morabarna.csaladfa.dtos.personDtos.in.PersonDTOCreate;
// import hu.morabarna.csaladfa.dtos.personDtos.in.PersonDTOUpdate;
// import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic;
// import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOChildren;
// import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOShort;
// import hu.morabarna.csaladfa.entities.Person;
// import hu.morabarna.csaladfa.entities.ParentsChildren;
// import hu.morabarna.csaladfa.repositories.PersonRepository;
// import hu.morabarna.csaladfa.repositories.ParentsChildrenRepository;
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.PersistenceContext;
// import jakarta.transaction.Transactional;


//     @Service
//     public class PersonService {

    
//     @PersistenceContext
//     EntityManager entityManager;
    
//     PersonRepository personRepository;

//     public PersonService(PersonRepository personRepository) {
//         this.personRepository = personRepository;
//     }


//     //Purpose:get a PersonDTOBasic from the person(s) entity/table by its id i needed to divide it to two parts because i need one optional
//     //dto with an included children list, but if its possible to do in  one querry it would be better.
//     @Transactional
//     public Optional<PersonDTOBasic> getPerson(Long id) {
//         Optional<PersonDTOShort> person = personRepository.getPersonDetailsById(id);
//         if (person.isPresent()) {
//             PersonDTOShort presentPerson = person.get();
//             List<PersonDTOChildren> children = personRepository.getChildrenByParentId(id);
//             return Optional.of(new PersonDTOBasic(id, presentPerson.name(), presentPerson.sex(),
//                     presentPerson.birthDate(), presentPerson.birthLocation(), presentPerson.motherId(),
//                     presentPerson.motherName(), presentPerson.fatherId(), presentPerson.fatherName(),
//                     presentPerson.deathDate(), presentPerson.deathLocation(), children));
//         }
//         return Optional.empty();
//     }
//     //Purpose: just a stop to the Controller
//     public List<PersonDTOShort> findByNameLike(String name) {
//         return personRepository.findByNameLike(name);
//     }
//     //Purpose: save a new person and its relations (parents and children) even if those do not jet exist meaning if there is valid data
//     // in the parents or children fields they will be created as a partial recoord(with nulls in the missing fields/columns)
//     @Transactional
//     public boolean saveNewPerson(PersonDTOCreate person) {
//         if (person.name() != null && person.name().length() > 3 && person.birthDate() != null
//                 && person.birthLocation() != null) {
//             Person newPerson = new Person();
//             newPerson.setName(person.name());
//             newPerson.setSex(person.sex());
//             newPerson.setBirthDate(person.birthDate());
//             newPerson.setBirthLocation(person.birthLocation());
//             if (person.motherId() != null || person.fatherId() != null) {
//                 Set<Person> parents = new HashSet<Person>();
//                 if (person.motherId() != null && person.motherId() > 0) {
//                     try {
//                         parents.add(entityManager.getReference(Person.class, person.motherId()));
//                     } catch (EntityNotFoundException e) {
//                         System.err.println("The given Person Entity (Mother) does not exists" + e);
//                     }
//                 } else if (person.motherName() != null && person.motherName().length() > 3) {
//                     var tmp = new Person();
//                     tmp.setName(person.motherName());
//                     tmp = personRepository.save(tmp);
//                     parents.add(tmp);
//                 }
//                 if (person.fatherId() != null && person.fatherId() > 0) {
//                     try {
//                         parents.add(entityManager.getReference(Person.class, person.fatherId()));
//                     } catch (EntityNotFoundException e) {
//                         System.err.println("The given Person Entity (Father) does not exists" + e);
//                     }
//                 } else if (person.fatherName() != null && person.fatherName().length() > 3) {
//                     var tmp = new Person();
//                     tmp.setName(person.fatherName());
//                     tmp = personRepository.save(tmp);
//                     parents.add(tmp);
//                 }
//                 newPerson.setParents(parents);
//             }
//             newPerson.setDeathDate(person.deathDate());
//             newPerson.setDeathLocation(person.deathLocation());
//             if (person.children().size() > 0) {
//                 Set<Person> children = new HashSet<Person>();
//                 for (PersonDTOChildren child : person.children()) {
//                     if (child.id() != null && child.name().length() > 3) {
//                         var tmp = new Person();
//                         tmp.setName(child.name());
//                         tmp = personRepository.save(tmp);
//                         children.add(tmp);
//                     } else if (child.id() != null) {
//                         children.add(entityManager.getReference(Person.class, child.id()));
//                     }
//                 }
//                 newPerson.setChildren(children);
//             }
//             try {
//                 personRepository.save(newPerson);
//             } catch (Exception e) {
//                 System.err.println(
//                         "---------------------------Error in saving new Person Entity ------------------------------"
//                                 + e);
//                 return false;
//             }
//             return true;
//         }
//         return false;
//     }

//     //Purpose same as the saveNewPerson except it updates the existing records creates new if needed or deletes old records 
//     @Transactional
//     public boolean updatePerson(Long id, PersonDTOUpdate person) {
//         if (person.id() == id && id != null && personRepository.existsById(id)) {
            
        
//             if (person.name() != null && person.name().length() > 3 && person.birthDate() != null
//                     && person.birthLocation() != null) {
//                 Person newPerson = new Person();
//                 newPerson.setName(person.name());
//                 newPerson.setSex(person.sex());
//                 newPerson.setBirthDate(person.birthDate());
//                 newPerson.setBirthLocation(person.birthLocation());
//                 if (person.motherId() != null || person.fatherId() != null) {
//                     Set<Person> parents = new HashSet<Person>();
//                     if (person.motherId() != null && person.motherId() > 0) {
//                         try {
//                             parents.add(entityManager.getReference(Person.class, person.motherId()));
//                         } catch (EntityNotFoundException e) {
//                             System.err.println("The given Person Entity (Mother) does not exists" + e);
//                         }
//                     } else if (person.motherName() != null && person.motherName().length() > 3) {
//                         var tmp = new Person();
//                         tmp.setName(person.motherName());
//                         tmp.setSex(true);
//                         tmp = personRepository.save(tmp);
//                         parents.add(tmp);
//                     }
//                     if (person.fatherId() != null && person.fatherId() > 0) {
//                         try {
//                             parents.add(entityManager.getReference(Person.class, person.fatherId()));
//                         } catch (EntityNotFoundException e) {
//                             System.err.println("The given Person Entity (Father) does not exists" + e);
//                         }
//                     } else if (person.fatherName() != null && person.fatherName().length() > 3) {
//                         var tmp = new Person();
//                         tmp.setName(person.fatherName());
//                         tmp.setSex(false);
//                         tmp = personRepository.save(tmp);
//                         parents.add(tmp);
//                     }
//                     newPerson.setParents(parents);
//                 }
//                 newPerson.setDeathDate(person.deathDate());
//                 newPerson.setDeathLocation(person.deathLocation());
//                 if (person.children().size() > 0) {
//                     Set<Person> children = new HashSet<Person>();
//                     for (PersonDTOChildren child : person.children()) {
//                         if (child.id() != null && child.name().length() > 3) {
//                             var tmp = new Person();
//                             tmp.setName(child.name());
//                             tmp = personRepository.save(tmp);
//                             children.add(tmp);
//                         } else if (child.id() != null) {
//                             children.add(entityManager.getReference(Person.class, child.id()));
//                         }
//                     }
//                     newPerson.setChildren(children);
//                 }
//                 try {
//                     personRepository.save(newPerson);
//                 } catch (Exception e) {
//                     System.err.println(
//                             "---------------------------Error in saving new Person Entity ------------------------------"
//                                     + e);
//                     return false;
//                 }
//                 return true;
//             }
//         }
//         return false;
//     }


//     //Purpose: provide an easy interface to delete a Person by it's id and any and all Related records too 
//     public boolean delById(Long id){
//         if (id != null && personRepository.existsById(id)) {
//             personRepository.deleteById(id);
            
//             return true;
//         }
//         return false;
//     }


// }
