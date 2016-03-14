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
