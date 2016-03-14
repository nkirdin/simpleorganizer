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
public class DeleteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long selectedPersonId;
    private Person person;

    @Inject
    private ViewPanelBean viewPanelBean;

    @EJB
    private PersonService personService;

    public void viewAction() {
        viewPanelBean.setSelectedPersonId(selectedPersonId);
        viewPanelBean.init();
        person = personService.getPersonById(selectedPersonId);
    }

    public void deleteHandler() {
        personService.delete(person);
    }

    public Long getSelectedPersonId() {
        return selectedPersonId;
    }

    public void setSelectedPersonId(Long selectedPersonId) {
        this.selectedPersonId = selectedPersonId;
    }
}
