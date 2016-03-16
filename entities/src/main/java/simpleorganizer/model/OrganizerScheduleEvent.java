/*
 * 
 * OrganizerScheduleEvent 
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

package simpleorganizer.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.primefaces.model.ScheduleEvent;

@Entity
@Table(name = "schedule")
@TableGenerator(name = "schedulegenerator", initialValue = 2000, allocationSize = 50)
@NamedQueries(
        {
            @NamedQuery(name = "OrganizerScheduleEvent.findAllEventsForUser", query = "select ose from OrganizerScheduleEvent ose where ose.user=:user"),
            @NamedQuery(name = "OrganizerScheduleEvent.countEventsForUser", query = "select count(ose) from OrganizerScheduleEvent ose where ose.user=:user"),
            @NamedQuery(name = "OrganizerScheduleEvent.clearEventForUsers", query = "delete from OrganizerScheduleEvent ose where ose.user=:user")
        }
        )

public class OrganizerScheduleEvent implements  ScheduleEvent, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "schedulegenerator")
    @Column(name = "id")
    private Long entityId;

    @Transient
    private String scheduleEventId;

    @Column(name = "title", length = 512)
    private String title;

    @Column(name = "start_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "all_day")
    private boolean allDay;

    @Column(name = "style_class")
    private String styleClass;

    @Column(name = "editable")
    private boolean editable = true;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private Object data = null;

    public OrganizerScheduleEvent() {}

    public OrganizerScheduleEvent(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String getId() {
        return scheduleEventId;
    }

    @Override
    public void setId(String scheduleEventId) {
        this.scheduleEventId = scheduleEventId;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    @Override
    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    @Override
    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (allDay ? 1231 : 1237);
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result
                + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof OrganizerScheduleEvent))
            return false;
        OrganizerScheduleEvent other = (OrganizerScheduleEvent) obj;
        if (allDay != other.allDay)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OrganizerScheduleEvent [entityId=" + entityId
                + ", scheduleEventId=" + scheduleEventId + ", title=" + title
                + ", startDate=" + startDate + ", endDate=" + endDate
                + ", allDay=" + allDay + ", description=" + description
                + ", user=" + user + ", data=" + data + "]";
    }


}
