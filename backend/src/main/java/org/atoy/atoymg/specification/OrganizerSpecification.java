package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Organizer;
import org.atoy.atoymg.models.dto.OrganizerSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class OrganizerSpecification {

    public static Specification<Organizer> filter(OrganizerSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("agencyname"), object.getAgencyname(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("slug"), object.getSlug(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("email"), object.getEmail(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("phone"), object.getPhone(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("city"), object.getCity(), predicates);
            
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
