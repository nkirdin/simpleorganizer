package simpleorganizer.view.admin;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.EnumSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.security.Base64Encoder;

import simpleorganizer.exception.DataDuplicatedException;
import simpleorganizer.model.User;
import simpleorganizer.model.UserGroupType;
import simpleorganizer.service.UserService;

@Named
@ViewScoped
public class AddUserBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    private String username;

    private String firstPasswordString;

    private String retypedPasswordString;

    private EnumSet<UserGroupType> editedGroups = EnumSet
            .noneOf(UserGroupType.class);

    public String saveAddUser() {
        String outcome = null;

        if (firstPasswordString == null || firstPasswordString.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Password is empty!",
                    "Password is empty! You should enter some symbols to change password.");
            FacesContext.getCurrentInstance()
            .addMessage("idAdminAddUserForm:idFirstPassword", message);
        } else {
            User user = new User();
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(firstPasswordString.getBytes());
                user.setPasswd(Base64Encoder.encode(md.digest()));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                user.setUsername(username);
                user.setUserGroupSet(editedGroups);
                userService.addWithCatchPersistenceException(user);
                outcome = "index?faces-redirect=true";
            } catch (DataDuplicatedException dde) {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Duplicated username: " + username + "!",
                        "Duplicated username: " + username
                        + "! You should choose another username.");
                context.addMessage("idAdminAddUserForm:idInputUsername",
                        message);
            }
        }
        return outcome;
    }

    public Set<String> getEditedGroupSet() {
        SortedSet<String> groupSet = new TreeSet<>();
        for (UserGroupType group : editedGroups) {
            groupSet.add(group.toString());
        }
        return groupSet;
    }

    public void setEditedGroupSet(Set<String> editedGroupSet) {
        editedGroups = EnumSet.noneOf(UserGroupType.class);
        for (String group : editedGroupSet) {
            editedGroups.add(UserGroupType.valueOf(UserGroupType.class, group));
        }
    }

    public EnumSet<UserGroupType> getPermittedGroupSet() {
        return EnumSet.allOf(UserGroupType.class);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstPasswordString() {
        return firstPasswordString;
    }

    public void setFirstPasswordString(String firstPasswordString) {
        this.firstPasswordString = firstPasswordString;
    }

    public String getRetypedPasswordString() {
        return retypedPasswordString;
    }

    public void setRetypedPasswordString(String retypedPasswordString) {
        this.retypedPasswordString = retypedPasswordString;
    }

}
