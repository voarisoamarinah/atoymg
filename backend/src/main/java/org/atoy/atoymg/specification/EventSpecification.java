package org.atoy.atoymg.specification;

import org.atoy.atoymg.models.Event;
import org.atoy.atoymg.models.dto.EventSearch;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class EventSpecification {

    public static Specification<Event> filter(EventSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
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
            
            if(object.getIdeventtypeEventtypes()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("ideventtypeEventtypes"), object.getIdeventtypeEventtypes()
                    )
                );
            }
            
            if(object.getIdeventstatusEventstatuses()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("ideventstatusEventstatuses"), object.getIdeventstatusEventstatuses()
                    )
                );
            }
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("title"), object.getTitle(), predicates);
            
            if(object.getEventdate()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("eventdate"), object.getEventdate()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("eventdate"), object.getEventdateMin(), object.getEventdateMax(), predicates);
            
            if(object.getEnddate()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("enddate"), object.getEnddate()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("enddate"), object.getEnddateMin(), object.getEnddateMax(), predicates);
            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("venuecity"), object.getVenuecity(), predicates);
            
            if(object.getGuestcount()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("guestcount"), object.getGuestcount()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("guestcount"), object.getGuestcountMin(), object.getGuestcountMax(), predicates);
            
            if(object.getTotalbudget()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("totalbudget"), object.getTotalbudget()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("totalbudget"), object.getTotalbudgetMin(), object.getTotalbudgetMax(), predicates);
            
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
