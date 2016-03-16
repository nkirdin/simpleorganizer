/*
 * 
 * EditUserBean 
 * is part of Simple Organizer
 *
 *
 * Copyright (С) 2016 Nikolay Kirdin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License Version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License Version 3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License Version 3 along with this program.  If not, see 
 * <http://www.gnu.org/licenses/>.
 *
 */

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

import simpleorganizer.model.User;
import simpleorganizer.model.UserGroupType;
import simpleorganizer.service.UserService;

@Named
@ViewScoped
public class EditUserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    private Long selectedUserId;

    private User user;

    private String firstPasswordString;

    private String retypedPasswordString;

    private Set<String> editedGroupSet;

    private Set<UserGroupType> editedGroups = new TreeSet<>(
            EnumSet.noneOf(UserGroupType.class));

    public void editUserViewAction() {
        user = userService.findById(selectedUserId);
        firstPasswordString = retypedPasswordString = null;
    }

    public void saveGroupSet() {
        user.setUserGroupSet(editedGroups);
        userService.update(user);
    }

    public void resetGroupSet() {
        setEditedGroupSet(getEditedGroupSet());
    }

    public void savePassword() {
        if (firstPasswordString == null || firstPasswordString.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Password is empty!",
                    "Password is empty! You should enter some symbols to change password.");
            FacesContext.getCurrentInstance()
            .addMessage("idEditUserForm:idFirstPassword", message);
        } else {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(firstPasswordString.getBytes());
                user.setPasswd(Base64Encoder.encode(md.digest()));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            userService.update(user);
        }
    }

    public void resetPassword() {
        firstPasswordString = retypedPasswordString = null;
    }

    public Set<String> getEditedGroupSet() {
        SortedSet<String> groupSet = new TreeSet<>();
        for (UserGroupType group : user.getUserGroupSet()) {
            groupSet.add(group.toString());
        }
        return groupSet;
    }

    public void setEditedGroupSet(Set<String> editedGroupSet) {
        editedGroups = new TreeSet<>();
        for (String group : editedGroupSet) {
            editedGroups.add(UserGroupType.valueOf(UserGroupType.class, group));
        }
    }

    public EnumSet<UserGroupType> getPermittedGroupSet() {
        return EnumSet.allOf(UserGroupType.class);
    }

    public String getUsername() {
        return user.getUsername();
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

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

}
