/*
 * 
 * ScheduleBean 
 * is part of Simple Organizer
 *
 *
 * Copyright (С) 2016 Nikolay Kirdin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License Version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License Version 3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License Version 3 along with this program.  If not, see 
 * <http://www.gnu.org/licenses/>.
 *
 */

package simpleorganizer.view.schedule;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import simpleorganizer.model.OrganizerScheduleEvent;
import simpleorganizer.view.OrganizerScheduleModel;

@Named
@SessionScoped
public class ScheduleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private ScheduleEvent scheduleEvent;

    private Object initialDate;

    private Object currentDate;

    @Inject
    private OrganizerScheduleModel model;

    @PostConstruct
    public void init() {
        scheduleEvent = new OrganizerScheduleEvent("", null, null);
    }

    public void dateSelectHandler(SelectEvent selectEvent) {
        Date date = (Date) selectEvent.getObject();
        scheduleEvent = new OrganizerScheduleEvent("", date, date);
        initialDate = date;
    }

    public void eventSelectHandler(SelectEvent selectEvent) {
        scheduleEvent = (OrganizerScheduleEvent) selectEvent.getObject();
    }

    public void viewChangeHandler(SelectEvent selectEvent) {
    }

    public void saveEventDetailsDialogHandler() {
        if (scheduleEvent.getId() == null) {
            model.addEvent(scheduleEvent);
        } else {
            model.updateEvent(scheduleEvent);
        }
        scheduleEvent = new OrganizerScheduleEvent("", null, null);
    }

    public void deleteEventDetailsDialogHandler() {
        model.deleteEvent(scheduleEvent);
        scheduleEvent = new OrganizerScheduleEvent("", null, null);
    }

    public void eventMoveHandler(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Event moved", "Day delta:" + event.getDayDelta()
                + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
        model.update(event.getScheduleEvent());
        System.out.println(
                "ScheduleView eventMoveHandler(): " + event.getScheduleEvent());
    }

    public void eventResizeHandler(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Event resized", "Day delta:" + event.getDayDelta()
                + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
        model.update(event.getScheduleEvent());
        System.out.println(
                "ScheduleBean eventMoveHandler(): " + event.getScheduleEvent());
    }

    public ScheduleModel getModel() {
        return model;
    }

    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    public void setScheduleEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Object getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Object initialDate) {
        this.initialDate = initialDate;
    }

    public Object getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Object currentDate) {
        this.currentDate = currentDate;
    }

    public boolean isEventPersisted() {
        if (scheduleEvent == null) return false;
        return model.isEventPersisted(scheduleEvent);
    }

}
