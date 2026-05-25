package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Document;
import org.atoy.atoymg.models.dto.DocumentSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class DocumentSpecification {

    public static Specification<Document> filter(DocumentSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            if(object.getIdeventvendorEventvendors()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("ideventvendorEventvendors"), object.getIdeventvendorEventvendors()
                    )
                );
            }
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("documenttype"), object.getDocumenttype(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("fileurl"), object.getFileurl(), predicates);
            
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
