package simpleorganizer.view;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import simpleorganizer.model.Person;
import simpleorganizer.model.Phone;
import simpleorganizer.model.PhoneType;
import simpleorganizer.service.PersonService;

@Named
@ViewScoped
public class EditPanelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Person person;

    private Phone addingPhone;
    private SortedSet<PhoneType> phoneTypeSet;

    @Inject
    private PersonService personService;

    public void init() {
        addingPhone = personService.getNewPhone(person);
    }

    public void addPhoneHandler(ActionEvent event) {
        person.getPhones().add(addingPhone);
        init();
    }

    public void deletePhone(Phone phone) {
        person.getPhones().remove(phone);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFirstname() {
        return person.getFirstname();
    }

    public void setFirstname(String firstname) {
        person.setFirstname(firstname);
    }

    public String getLastname() {
        return person.getLastname();
    }

    public void setLastname(String lastname) {
        person.setLastname(lastname);
    }

    public Phone getAddingPhone() {
        return addingPhone;
    }

    public void setAddingPhone(Phone addingPhone) {
        this.addingPhone = addingPhone;
    }

    public SortedSet<PhoneType> permittedPhoneTypeSet() {
        phoneTypeSet = new TreeSet<PhoneType>(EnumSet.allOf(PhoneType.class));
        for (Phone phone : person.getPhones()) {
            if (phone.getPhoneType() == PhoneType.DEFAULT) {
                phoneTypeSet.remove(PhoneType.DEFAULT);
                break;
            }
        }
        return phoneTypeSet;
    }

    public SortedSet<PhoneType> permittedPhoneTypeSet(Phone phone) {
        return phone.getPhoneType() == PhoneType.DEFAULT
                ? new TreeSet<PhoneType>(EnumSet.allOf(PhoneType.class))
                        : permittedPhoneTypeSet();
    }

    public void setPhoneTypeSet(SortedSet<PhoneType> phoneTypeSet) {
        this.phoneTypeSet = phoneTypeSet;
    }

}
