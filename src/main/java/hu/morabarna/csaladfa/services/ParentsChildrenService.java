package hu.morabarna.csaladfa.services;

import java.util.List;

import org.springframework.stereotype.Service;

import hu.morabarna.csaladfa.entities.ParentsChildren;
import hu.morabarna.csaladfa.repositories.ParentsChildrenRepository;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdName;
import hu.morabarna.csaladfa.dtos.personDtos.out.PersonDTOIdNameSex;


@Service
public class ParentsChildrenService {

    ParentsChildrenRepository parentsChildrenRepository;

    public ParentsChildrenService(ParentsChildrenRepository parentsChildrenRepository) {
        this.parentsChildrenRepository = parentsChildrenRepository;
    }
    public List<PersonDTOIdNameSex> getParentsSexByChildId(Long childId){
        return parentsChildrenRepository.getParentsSexByChildId(childId);
    }

    public List<PersonDTOIdName> getChildrenByParentId(Long parentId){
        return parentsChildrenRepository.getChildrenByParentId(parentId);
    }

}
