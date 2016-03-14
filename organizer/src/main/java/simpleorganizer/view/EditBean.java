package simpleorganizer.view;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import simpleorganizer.model.Person;
import simpleorganizer.service.PersonService;

@Named
@ViewScoped
public class EditBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long selectedPersonId;
    private Long personId;

    @EJB
    private PersonService personService;

    @Inject
    private EditPanelBean editPanelBean;

    public void viewAction() {
        if (personId == null) {
            personId = selectedPersonId;
            Person person = personService.getPersonById(personId);
            editPanelBean.setPerson(person);
            editPanelBean.init();
        }
    }

    public void saveHandler() {
        Person person = editPanelBean.getPerson();
        personService.update(person);
    }

    public void revertHandler() {
        Person person = personService.getPersonById(personId);
        editPanelBean.setPerson(person);
        editPanelBean.init();
    }

    public Long getSelectedPersonId() {
        return selectedPersonId;
    }

    public void setSelectedPersonId(Long selectedPersonId) {
        this.selectedPersonId = selectedPersonId;
    }

}
