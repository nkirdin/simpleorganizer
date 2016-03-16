/*
 * 
 * Utils 
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

package simpleorganizer.utils;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@RequestScoped
public class Utils {

    public static void validatePassword(FacesContext context, UIComponent component,
            Object value) {
        UIInput fistPasswordUI = (UIInput) component
                .findComponent("idFirstPassword");
        UIInput retypedPasswordUI = (UIInput) component
                .findComponent("idRetypedPassword");
        String firstPassword = String.valueOf(fistPasswordUI.getLocalValue());
        String retypedPassword = String
                .valueOf(retypedPasswordUI.getLocalValue());
        if (retypedPassword == null || !retypedPassword.equals(firstPassword)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Retyped password does not match password!",
                    "Retyped password does not match password!");
            throw new ValidatorException(message);
        }

    }

}
