package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Eventvendorstatuse;
import org.atoy.atoymg.models.dto.EventvendorstatuseSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class EventvendorstatuseSpecification {

    public static Specification<Eventvendorstatuse> filter(EventvendorstatuseSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("code"), object.getCode(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("label"), object.getLabel(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
