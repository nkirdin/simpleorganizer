/*
 * 
 * DeleteUserBean 
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
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import simpleorganizer.model.User;
import simpleorganizer.model.UserGroupType;
import simpleorganizer.service.UserService;

@Named
@ViewScoped
public class DeleteUserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    private Long selectedUserId;

    private User user;

    public void deleteUserViewAction() {
        user = userService.findById(selectedUserId);
    }

    public void deleteDeleteUserHandler(ActionEvent event) {
        userService.delete(user);
    }

    public Set<String> getGroupSet() {
        SortedSet<String> groupSet = new TreeSet<>();
        for (UserGroupType group : user.getUserGroupSet()) {
            groupSet.add(group.toString());
        }
        return groupSet;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

}
