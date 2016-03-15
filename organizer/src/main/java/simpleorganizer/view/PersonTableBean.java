package simpleorganizer.view;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.PageEvent;

import simpleorganizer.model.Person;
import simpleorganizer.model.Phone;
import simpleorganizer.model.PhoneType;
import simpleorganizer.service.PersonService;

@Named
@SessionScoped
public class PersonTableBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Person selectedPerson;

    private int firstRowOnPersonTablePage;

    @EJB
    private PersonService personService;

    public void viewAction() {
        if (
                (selectedPerson != null) && 
                (selectedPerson.getId() == null || 
                personService.getPersonById(selectedPerson.getId()) == null || 
                !selectedPerson.equals(personService.getPersonById(selectedPerson.getId())))) {
            selectedPerson = null;
        }
    }

    public List<Person> getPersons() {
        return personService.getAllPersonsForUser();
    }

    public String getDefaultPhoneNumber(Person person) {
        Set<Phone> phones = personService
                .getPhoneSetByPersonAndPhoneTypeForUser(person, PhoneType.DEFAULT);
        return phones.isEmpty() ? null : phones.stream().findFirst().get().getNumber();
    }

    public void viewPersonTableHandler(PageEvent event) {
        selectedPerson = null;
    }

    public void viewPersonTableRowselectHandler(SelectEvent event) {
    }

    public void viewPersonTableRowunselectHandler(UnselectEvent event) {
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public Long getSelectedPersonId() {
        return selectedPerson != null ? selectedPerson.getId() : null;
    }

    public int getFirstRowOnPersonTablePage() {
        return firstRowOnPersonTablePage;
    }

    public void setFirstRowOnPersonTablePage(int firstRowOnPersonTablePage) {
        this.firstRowOnPersonTablePage = firstRowOnPersonTablePage;
    }

}
