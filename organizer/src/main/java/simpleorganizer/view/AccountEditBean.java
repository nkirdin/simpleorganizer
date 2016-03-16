/*
 * 
 * AccountEditBean 
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

package simpleorganizer.view;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.security.Base64Encoder;

import simpleorganizer.auth.Auth;
import simpleorganizer.model.User;
import simpleorganizer.model.UserGroupType;
import simpleorganizer.service.UserService;

@Named
@ViewScoped
public class AccountEditBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    private String username;

    private String firstPasswordString;

    private String retypedPasswordString;

    @Inject
    private UserService userService;

    @Inject
    private Auth auth;

    public void viewAction() {
        user = auth.getUser();
        username = user.getUsername();
        firstPasswordString = retypedPasswordString = null;
    }

    public void savePassword() {
        if (firstPasswordString == null || firstPasswordString.isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Password is empty!",
                    "Password is empty! You should enter some symbols to change password.");
            FacesContext.getCurrentInstance()
            .addMessage("idEditAccount:idFirstPassword", message);
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

    public Set<String> getGroupSet() {
        SortedSet<String> groupSet = new TreeSet<>();
        for (UserGroupType group : user.getUserGroupSet()) {
            groupSet.add(group.toString());
        }
        return groupSet;
    }

    public String getUsername() {
        return username;
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

    public boolean isFieldRenderedForRole() {
        return !auth.getUser().getUserGroupSet().contains(UserGroupType.GUEST);
    }

}
