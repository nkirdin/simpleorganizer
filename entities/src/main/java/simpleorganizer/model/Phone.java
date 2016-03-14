package simpleorganizer.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity
@Table(name="phone")
@TableGenerator(name="phonegenerator", initialValue=2000, allocationSize=50)
@NamedQueries ({
    @NamedQuery(name="Phone.findAllPhones", query="select p from Phone p"),
    @NamedQuery(name="Phone.findPersonByNumber", 
    query="select phone_user from Phone p join p.person phone_user where p like :number"),
    @NamedQuery(name="Phone.findByNumber", query="select p from Phone p where p.number = :number")
})
public class Phone implements Comparable<Phone> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="phonegenerator")
    @Column(name = "id", updatable = true, nullable = false, insertable=true)
    private Long id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name="phone_type", nullable=true)
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    public Phone() {
    }

    public Phone(User user) {
        this.user = user;
    }

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return phoneType + ": " + number;
    }


    @Override
    public int compareTo(Phone p) {
        if (phoneType == p.getPhoneType()) {
            if(Objects.equals(number, p.getNumber())) {
                return 0;
            }
            if (number == null) return -1;
            if (p.getNumber() == null) return 1;
            return number.compareTo(p.getNumber());
        }
        if (phoneType == null)
            return -1;
        if (p.getPhoneType() == null)
            return 1;
        return Integer.compare(phoneType.ordinal(),
                p.getPhoneType().ordinal());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result
                + ((phoneType == null) ? 0 : phoneType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Phone))
            return false;
        Phone other = (Phone) obj;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (phoneType != other.phoneType)
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
