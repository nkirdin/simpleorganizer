package simpleorganizer.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity
@Table(name="person")
@TableGenerator(name="persongenerator", initialValue=2000, allocationSize=50)
@NamedQueries({
    @NamedQuery(name = "Person.findAllPersons", query = "select p from Person p order by p.lastname, p.firstname"),
    @NamedQuery(name = "Person.findAllPersonsForUser", query = "select p from Person p where p.user=:user order by p.lastname, p.firstname"),
    @NamedQuery(name = "Person.findById", query = "select p from Person p where p.id = :person_id"),
    @NamedQuery(name = "Person.findLikeFirstname", query = "select p from Person p left join fetch p.phones where UPPER(p.firstname) like :firstname"),
    @NamedQuery(name = "Person.findLikeLastname", query = "select p from Person p left join fetch p.phones where UPPER(p.lastname) like :lastname"),
    @NamedQuery(name = "Person.findPhoneByPhoneType", 
    query="select ph from Person p left join p.phones ph where p = :person and ph.phoneType = :phoneType"),
    @NamedQuery(name = "Person.findPhoneByPhoneTypeForUser", 
    query="select ph from Person p left join p.phones ph where p = :person and ph.phoneType = :phoneType and p.user=:user and ph.user=:user"),    
    @NamedQuery(name = "Person.findAllPhonesExeceptPhoneType", 
    query="select ph from Person p left join p.phones ph where p = :person and ph.phoneType <> :phoneType")
})
public class Person implements Comparable<Person> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="persongenerator")
    @Column(name = "id", updatable = true, nullable = false, insertable=true)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Version
    @Column(name = "version")
    private Integer version;

    @OneToMany(mappedBy="person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Phone> phones = new HashSet<>();

    public Person() {
    }

    public Person(User user) {
        this.user = user;
    }

    public Person(Long id, String firstname, String lastname,
            Set<Phone> phones) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phones = phones;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }



    @Override
    public String toString() {
        return id + " |" + firstname + "|" + lastname + ": " + phones;
    }

    @Override
    public int compareTo(Person person) {
        return (lastname + firstname).compareTo(person.lastname + person.firstname);
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result
                + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + ((phones == null) ? 0 : phones.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Person))
            return false;
        Person other = (Person) obj;
        if (firstname == null) {
            if (other.firstname != null)
                return false;
        } else if (!firstname.equals(other.firstname))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;

        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
