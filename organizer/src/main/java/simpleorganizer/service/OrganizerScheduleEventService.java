package simpleorganizer.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import simpleorganizer.auth.Auth;
import simpleorganizer.model.OrganizerScheduleEvent;

@Stateless
public class OrganizerScheduleEventService {

    @PersistenceContext(unitName = "simpleorganizerPU")
    private EntityManager em;

    @Inject
    private Auth auth; 

    public List<OrganizerScheduleEvent> getAllEvents() {
        TypedQuery<OrganizerScheduleEvent> query = em.createNamedQuery(
                "OrganizerScheduleEvent.findAllEventsForUser",
                OrganizerScheduleEvent.class);
        query.setParameter("user", auth.getUser());
        return query.getResultList();
    }

    public OrganizerScheduleEvent findById(Long id) {
        return em.find(OrganizerScheduleEvent.class, id);
    }

    public OrganizerScheduleEvent remove(OrganizerScheduleEvent event) {
        em.remove(event);
        return event;
    }

    public OrganizerScheduleEvent add(OrganizerScheduleEvent event) {
        event.setUser(auth.getUser());
        em.persist(event);
        return event;
    }

    public OrganizerScheduleEvent update(OrganizerScheduleEvent event) {
        event = em.merge(event);
        return event;
    }

    public int count() {
        TypedQuery<Integer> query = em.createNamedQuery("OrganizerScheduleEvent.countEventsForUser", Integer.class);
        query.setParameter("user", auth.getUser());
        return query.getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteAll() {
        Query query = em.createNamedQuery("OrganizerScheduleEvent.clearEventsForUser");
        query.setParameter("user", auth.getUser());
        query.executeUpdate();
    }

}
