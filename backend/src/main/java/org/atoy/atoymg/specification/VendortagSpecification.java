package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Vendortag;
import org.atoy.atoymg.models.dto.VendortagSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class VendortagSpecification {

    public static Specification<Vendortag> filter(VendortagSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            if(object.getIdvendorcategoryVendorcategories()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idvendorcategoryVendorcategories"), object.getIdvendorcategoryVendorcategories()
                    )
                );
            }
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("label"), object.getLabel(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
