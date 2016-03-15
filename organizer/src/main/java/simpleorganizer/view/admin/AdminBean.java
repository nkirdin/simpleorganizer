package simpleorganizer.view.admin;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;

import simpleorganizer.model.User;
import simpleorganizer.service.UserService;

@Named
@SessionScoped
public class AdminBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    private User selectedUser;

    private Long selectedUserId;

    private int firstRowOnUserTablePage;

    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public int getFirstRowOnUserTablePage() {
        return firstRowOnUserTablePage;
    }

    public void setFirstRowOnUserTablePage(int firstRowOnUserTablePage) {
        this.firstRowOnUserTablePage = firstRowOnUserTablePage;
    }

    public Long getSelectedUserId() {
        return selectedUser != null ? selectedUser.getId() : null;
    }

    public void viewUserTableHandler(PageEvent event) {
        selectedUser = null;
    }

    public void changeGroupHandler(ActionEvent event) {
    }

    public void revertAddUserHandler(ActionEvent event) {
    }

    public String saveAddUserHandler(ActionEvent event) {
        return null;
    }

    public void editUserHandler() {
    }

    public void deleteUserHandler() {
    }

    public void rowSelectHandler() {
    }

    public void rowUnselectHandler() {
        selectedUserId = null;
    }

}
