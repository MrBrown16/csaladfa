package hu.morabarna.csaladfa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOChildren;
import hu.morabarna.csaladfa.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // @Query("SELECT new hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic("
    // +
    // "p.id, p.name, p.sex, p.birthDate, p.birthLocation, " +
    // "mother.id, mother.name, father.id, father.name, " +
    // "p.deathDate, p.deathLocation, " +
    // "COLLECT(new hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOChildren(
    // children.id, children.name))) " +
    // "FROM Person p " +
    // "LEFT JOIN p.parents mother WITH mother.sex = true " +
    // "LEFT JOIN p.parents father WITH father.sex = false " +
    // "LEFT JOIN p.children children " +
    // "WHERE p.id=:id")
    // Optional<PersonDTOBasic> findPersonDTOBasicById(@Param(value = "id") Long
    // id);

    // @Query("SELECT new hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic("
    // +
    // "p.id, p.name, p.sex, p.birthDate, p.birthLocation, " +
    // "mother.id, mother.name, father.id, father.name, " +
    // "p.deathDate, p.deathLocation, " +
    // "CAST(COLLECT(new
    // hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOChildren(children.id AS
    // id, children.name AS name)) AS children" +
    // ")) " +
    // "FROM Person p " +
    // "LEFT JOIN p.parents mother WITH mother.sex = true " +
    // "LEFT JOIN p.parents father WITH father.sex = false " +
    // "LEFT JOIN p.children children " +
    // "WHERE p.id=:id")
    // Optional<PersonDTOBasic> findPersonDTOBasicById(@Param(value = "id") Long
    // id);



// default List<PersonDTOBasic> findPersonDTOBasicById(Long id, EntityManager entityManager) {
//     CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//     CriteriaQuery<PersonDTOBasic> query = builder.createQuery(PersonDTOBasic.class);

//     Root<Person> personRoot = query.from(Person.class);
//     Join<Person, Person> motherJoin = personRoot.join("parents", JoinType.LEFT);
//     Join<Person, Person> fatherJoin = personRoot.join("parents", JoinType.LEFT);
//     Join<Person, Person> childrenJoin = personRoot.join("children", JoinType.LEFT);

//     query.select(builder.construct(
//             PersonDTOBasic.class,
//             personRoot.get("id"),
//             personRoot.get("name"),
//             personRoot.get("sex"),
//             personRoot.get("birthDate"),
//             personRoot.get("birthLocation"),
//             motherJoin.get("id"),
//             motherJoin.get("name"),
//             fatherJoin.get("id"),
//             fatherJoin.get("name"),
//             personRoot.get("deathDate"),
//             personRoot.get("deathLocation"),
//             builder.function("COLLECT", PersonDTOChildren.class, 
//                 childrenJoin.get("id"), childrenJoin.get("name"))
//     ));

//     Predicate idPredicate = builder.equal(personRoot.get("id"), id);
//     query.where(idPredicate);

//     query.groupBy(personRoot.get("id"));

//     return entityManager.createQuery(query).getResultList();
// }

}
