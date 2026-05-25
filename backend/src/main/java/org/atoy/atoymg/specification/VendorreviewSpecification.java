package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Vendorreview;
import org.atoy.atoymg.models.dto.VendorreviewSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class VendorreviewSpecification {

    public static Specification<Vendorreview> filter(VendorreviewSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            if(object.getIdvendorVendors()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idvendorVendors"), object.getIdvendorVendors()
                    )
                );
            }
            
            if(object.getIdeventEvents()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("ideventEvents"), object.getIdeventEvents()
                    )
                );
            }
            
            if(object.getIdorganizerOrganizers()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idorganizerOrganizers"), object.getIdorganizerOrganizers()
                    )
                );
            }
            
            if(object.getIdclientClients()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idclientClients"), object.getIdclientClients()
                    )
                );
            }
            
            if(object.getScore()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("score"), object.getScore()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("score"), object.getScoreMin(), object.getScoreMax(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("comment"), object.getComment(), predicates);
            
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
