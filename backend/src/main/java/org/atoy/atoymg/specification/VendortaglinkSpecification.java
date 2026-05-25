package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Vendortaglink;
import org.atoy.atoymg.models.dto.VendortaglinkSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class VendortaglinkSpecification {

    public static Specification<Vendortaglink> filter(VendortaglinkSearch object) {
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
            
            if(object.getIdtagVendortags()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idtagVendortags"), object.getIdtagVendortags()
                    )
                );
            }
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
