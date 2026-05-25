package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Client;
import org.atoy.atoymg.models.dto.ClientSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ClientSpecification {

    public static Specification<Client> filter(ClientSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("fullname"), object.getFullname(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("email"), object.getEmail(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("phone"), object.getPhone(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("notes"), object.getNotes(), predicates);
            
            if(object.getCreatedat()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("createdat"), object.getCreatedat()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("createdat"), object.getCreatedatMin(), object.getCreatedatMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
