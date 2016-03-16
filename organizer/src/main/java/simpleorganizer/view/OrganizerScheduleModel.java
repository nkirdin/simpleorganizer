package simpleorganizer.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import simpleorganizer.model.OrganizerScheduleEvent;
import simpleorganizer.service.OrganizerScheduleEventService;

@Named
@ViewScoped
public class OrganizerScheduleModel implements ScheduleModel, Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private OrganizerScheduleEventService service;

    private boolean eventLimit = false;

    private List<ScheduleEvent> scheduleEventList;

    @PostConstruct
    private void init() {
        scheduleEventList = new ArrayList<ScheduleEvent>(); 
        for (OrganizerScheduleEvent scheduleEvent : service.getAllEvents()) {
            scheduleEvent.setId(String.valueOf(scheduleEvent.getEntityId()));
            scheduleEventList.add(scheduleEvent);
        }
    }

    @Override
    @Transactional
    public void addEvent(ScheduleEvent scheduleEvent) {
        OrganizerScheduleEvent event = (OrganizerScheduleEvent) scheduleEvent;
        service.add(event);
        event.setId(String.valueOf(event.getEntityId()));
        scheduleEventList.add(event);
    }

    @Override
    @Transactional
    public boolean deleteEvent(ScheduleEvent scheduleEvent) {
        OrganizerScheduleEvent event = (OrganizerScheduleEvent) scheduleEvent;
        service.remove(event);
        scheduleEventList.remove(event);
        return true;
    }

    @Override
    public List<ScheduleEvent> getEvents() {
        return scheduleEventList;
    }

    @Override
    public ScheduleEvent getEvent(String id) {
        for (ScheduleEvent event : scheduleEventList) {
            if (id.equals(event.getId())) return event;
        }
        return null;
    }

    @Override
    @Transactional
    public void updateEvent(ScheduleEvent scheduleEvent) {
        for (ScheduleEvent event : scheduleEventList) {
            if (scheduleEvent.getId().equals(event.getId())){
                scheduleEventList.remove(event);
                scheduleEventList.add(scheduleEvent);
                service.update((OrganizerScheduleEvent) scheduleEvent);
                return;
            }
        }
    }

    public void update(ScheduleEvent scheduleEvent) {
        service.update((OrganizerScheduleEvent) scheduleEvent);
    }

    @Override
    public int getEventCount() {
        return service.count();
    }

    @Override
    public void clear() {
        service.deleteAll();
    }

    @Override
    public boolean isEventLimit() {
        return eventLimit;
    }

    public void setEventLimit(boolean eventLimit) {
        this.eventLimit = eventLimit;
    }

    public boolean isEventPersisted(ScheduleEvent scheduleEvent) {
        OrganizerScheduleEvent event = (OrganizerScheduleEvent) scheduleEvent;
        if(event.getEntityId() == null) return false;
        return service.findById(event.getEntityId()) != null;
    }

}
