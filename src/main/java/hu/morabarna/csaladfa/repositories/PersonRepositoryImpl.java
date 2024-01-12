// package hu.morabarna.csaladfa.repositories;

// import java.util.Optional;

// import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOBasic;
// import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOChildren;
// import hu.morabarna.csaladfa.entities.Person;
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.criteria.*;

// public class PersonRepositoryImpl implements CustomPersonRepository {

//     private final EntityManager entityManager;

//     public PersonRepositoryImpl(EntityManager entityManager) {
//         this.entityManager = entityManager;
//     }

//     @Override
//     public Optional<PersonDTOBasic> findPersonDTOBasicById(Long id) {
//         CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//         CriteriaQuery<PersonDTOBasic> query = cb.createQuery(PersonDTOBasic.class);
//         Root<Person> personRoot = query.from(Person.class);


//         // Create a subquery for collecting children
//         Subquery<PersonDTOChildren> childrenSubquery = query.subquery(PersonDTOChildren.class);
//         Root<Person> childrenRoot = childrenSubquery.from(Person.class);
//         Join<Person, Person> parentJoin = childrenRoot.join("parents", JoinType.INNER);
//         Predicate parentIdPredicate = cb.equal(parentJoin.get("id"), personRoot.get("id"));
//         childrenSubquery.select((Expression<PersonDTOChildren>) cb.construct(PersonDTOChildren.class, childrenRoot.get("id"), childrenRoot.get("name")))
//                 .where(parentIdPredicate);

//         Join<Person, Person> motherJoin = personRoot.join("parents", JoinType.LEFT);
//         Join<Person, Person> fatherJoin = personRoot.join("parents", JoinType.LEFT);
//         Join<Person, Person> childrenJoin = personRoot.join("children", JoinType.LEFT);

//         query.select(
//                 cb.construct(
//                         PersonDTOBasic.class,
//                         personRoot.get("id"),
//                         personRoot.get("name"),
//                         personRoot.get("sex"),
//                         personRoot.get("birthDate"),
//                         personRoot.get("birthLocation"),
//                         motherJoin.get("id"),
//                         motherJoin.get("name"),
//                         fatherJoin.get("id"),
//                         fatherJoin.get("name"),
//                         personRoot.get("deathDate"),
//                         personRoot.get("deathLocation"),
//                         cb.collectSet(
//                                 cb.construct(
//                                         PersonDTOChildren.class,
//                                         childrenJoin.get("id"),
//                                         childrenJoin.get("name")
//                                 )
//                         )
//                 )
//         );

//         query.where(cb.equal(personRoot.get("id"), id));

//         return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
//     }
// }
