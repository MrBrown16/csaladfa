package hu.morabarna.csaladfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdName;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdNameSex;
import hu.morabarna.csaladfa.entities.ParentsChildren;

public interface ParentsChildrenRepository extends JpaRepository<ParentsChildren, Long> {
    

    
    @Query("SELECT NEW hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdName(p.parent.id, p.parent.name) FROM ParentsChildren p WHERE p.child.id = :childId")
    List<PersonDTOIdName> getParentsByChildId(@Param("childId") Long childId);
    
    @Query("SELECT NEW hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdName(p.child.id, p.child.name) FROM ParentsChildren p WHERE p.parent.id = :parentId")
    List<PersonDTOIdName> getChildrenByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT new hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdNameSex(p.parent.id, p.parent.name, p.parent.sex) FROM ParentsChildren p WHERE p.child.id = :childId")
    List<PersonDTOIdNameSex> getParentsSexByChildId(@Param("childId") Long childId);
    

    
    @Modifying
    @Query("DELETE FROM ParentsChildren pc WHERE pc.parent.id = :personId OR pc.child.id = :personId")
    void deleteByParentIdOrChildId(@Param("personId") Long personId);
}
