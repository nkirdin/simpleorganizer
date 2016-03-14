package simpleorganizer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import simpleorganizer.auth.Auth;
import simpleorganizer.model.Person;
import simpleorganizer.model.Phone;
import simpleorganizer.model.PhoneType;

@Stateless
public class PersonService {


    @PersistenceContext(unitName = "simpleorganizerPU")
    private EntityManager em;

    @Inject
    private Auth auth;

    public Person getPersonById(Long id) {
        return em.find(Person.class, id);
    }

    public List<Person> getAllPersonsForUser() {
        TypedQuery<Person> query = em.createNamedQuery("Person.findAllPersonsForUser", Person.class);
        query.setParameter("user", auth.getUser());
        return query.getResultList();
    }

    public Set<Phone> getPhoneSetByPersonAndPhoneTypeForUser(Person person, PhoneType phoneType) {
        TypedQuery<Phone> query = em.createNamedQuery("Person.findPhoneByPhoneTypeForUser", Phone.class);
        query.setParameter("person", person);
        query.setParameter("phoneType", phoneType);
        query.setParameter("user", auth.getUser());
        return new HashSet<Phone>(query.getResultList());
    }

    public Person update(Person person) {
        Person originalPerson  = em.find(Person.class, person.getId());
        Set<Phone> addedPhoneSet = new HashSet<Phone>(person.getPhones());
        addedPhoneSet.removeAll(new HashSet<Phone>(originalPerson.getPhones()));

        for(Phone phone : addedPhoneSet) {
            phone = em.merge(phone);
        }

        Set<Phone> removedPhoneSet = new HashSet<Phone>(originalPerson.getPhones());
        removedPhoneSet.removeAll(new HashSet<Phone>(person.getPhones()));

        for (Phone phone : removedPhoneSet) {
            phone = em.merge(phone);
            phone.setPerson(null);
            em.remove(phone);
        }
        return em.merge(person);
    }

    public Person add(Person person) {
        Set<Phone> phoneSet = new HashSet<Phone>(person.getPhones());

        em.persist(person);

        for(Phone phone : phoneSet) {
            em.persist(phone);
        }

        return person;
    }

    public Person delete(Person person) {
        person = em.merge(person);
        em.remove(person);
        return person;

    }

    public Person getNewPerson() {
        return new Person(auth.getUser());
    }

    public Phone getNewPhone(Person person) {
        Phone phone = new Phone(auth.getUser());
        phone.setPerson(person);
        return phone;
    }

}
