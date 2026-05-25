package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Eventvendor;
import org.atoy.atoymg.models.dto.EventvendorSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class EventvendorSpecification {

    public static Specification<Eventvendor> filter(EventvendorSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
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
            
            if(object.getIdvendorVendors()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idvendorVendors"), object.getIdvendorVendors()
                    )
                );
            }
            
            if(object.getIdeventvendorstatusEventvendorstatuses()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("ideventvendorstatusEventvendorstatuses"), object.getIdeventvendorstatusEventvendorstatuses()
                    )
                );
            }
            
            if(object.getAgreedprice()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("agreedprice"), object.getAgreedprice()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("agreedprice"), object.getAgreedpriceMin(), object.getAgreedpriceMax(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("notes"), object.getNotes(), predicates);
            
            if(object.getCreatedat()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("createdat"), object.getCreatedat()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("createdat"), object.getCreatedatMin(), object.getCreatedatMax(), predicates);
            
            if(object.getUpdatedat()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("updatedat"), object.getUpdatedat()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("updatedat"), object.getUpdatedatMin(), object.getUpdatedatMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
