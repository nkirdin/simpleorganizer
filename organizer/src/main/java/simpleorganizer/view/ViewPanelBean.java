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
