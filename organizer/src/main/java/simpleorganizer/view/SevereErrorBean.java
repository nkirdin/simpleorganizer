package simpleorganizer.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import simpleorganizer.service.UserService;

@Named
@RequestScoped
public class SevereErrorBean {

    @Inject
    private UserService userService;

    public void viewAction() {
        userService.logout();
    }

}
