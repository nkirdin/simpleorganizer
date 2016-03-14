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
