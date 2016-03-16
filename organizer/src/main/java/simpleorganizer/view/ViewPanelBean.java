/*
 * 
 * ViewPanelBean 
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

package simpleorganizer.view;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import simpleorganizer.model.Person;
import simpleorganizer.service.PersonService;

@Named
@ViewScoped
public class ViewPanelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long selectedPersonId;

    private Person person;

    @EJB
    private PersonService personService;

    public void init() {
        person = personService.getPersonById(selectedPersonId);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getSelectedPersonId() {
        return selectedPersonId;
    }

    public void setSelectedPersonId(Long selectedPersonId) {
        this.selectedPersonId = selectedPersonId;
    }

}
