/*
 * 
 * Auth 
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

package simpleorganizer.auth;

import java.io.Serializable;
import java.security.Principal;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import simpleorganizer.model.User;
import simpleorganizer.service.UserService;

@Named
@SessionScoped
public class Auth implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    @Inject
    private UserService userService;

    public User getUser() {
        if (user == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                user = userService.find(principal.getName());
            }
        }

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
