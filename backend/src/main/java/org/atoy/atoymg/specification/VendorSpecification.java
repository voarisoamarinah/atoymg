package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Vendor;
import org.atoy.atoymg.models.dto.VendorSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class VendorSpecification {

    public static Specification<Vendor> filter(VendorSearch object) {
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
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("businessname"), object.getBusinessname(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("contactemail"), object.getContactemail(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("contactphone"), object.getContactphone(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("website"), object.getWebsite(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("city"), object.getCity(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("countrycode"), object.getCountrycode(), predicates);
            
            if(object.getBaseprice()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("baseprice"), object.getBaseprice()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("baseprice"), object.getBasepriceMin(), object.getBasepriceMax(), predicates);
            
            if(object.getRating()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("rating"), object.getRating()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("rating"), object.getRatingMin(), object.getRatingMax(), predicates);
            
            if(object.getRatingcount()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("ratingcount"), object.getRatingcount()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("ratingcount"), object.getRatingcountMin(), object.getRatingcountMax(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("description"), object.getDescription(), predicates);
            
            if(object.getIsverified()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("isverified"), object.getIsverified()
                    )
                );
            }
            
            if(object.getIsactive()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("isactive"), object.getIsactive()
                    )
                );
            }
            
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
