package simpleorganizer.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name = "user")
@TableGenerator(name = "usergenerator", initialValue = 2000, allocationSize = 50)
@NamedQueries({
    @NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username = :username"),
    @NamedQuery(name = "User.getAllUsers", query = "select u from User u"),
    @NamedQuery(name = "User.deleteAllPersonForUser", query = "delete from Person p where p.user=:user"),
    @NamedQuery(name = "User.deleteAllPhoneForUser", query = "delete from Phone p where p.user=:user"),

})
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "usergenerator")
    @Column(name = " id", insertable = true)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username = "";

    @Column(name = "passwd")
    private String passwd;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "user_group")
    private Set<UserGroupType> userGroupSet = new HashSet<UserGroupType>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.getUsername());
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Set<UserGroupType> getUserGroupSet() {
        return userGroupSet;
    }

    public void setUserGroupSet(Set<UserGroupType> userGroupSet) {
        this.userGroupSet = userGroupSet;
    }

    @Override
    public String toString() {
        return String.valueOf(id) + " " + username + " " + userGroupSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
