package simpleorganizer.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import simpleorganizer.auth.Auth;
import simpleorganizer.exception.DataDuplicatedException;
import simpleorganizer.model.User;

@Named
@Transactional(Transactional.TxType.REQUIRES_NEW)
@RolesAllowed({"ADMIN"})
@RequestScoped
public class UserService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "simpleorganizerPU")
    private EntityManager em;

    @Inject 
    private Auth auth;

    @Inject
    private UserService self;


    @PermitAll
    public User find(String username) {
        TypedQuery<User> query = em.createNamedQuery("User.findByUsername",
                User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }


    public List<User> getAllUsers() {
        TypedQuery<User> query = em.createNamedQuery("User.getAllUsers",
                User.class);
        return query.getResultList();
    }

    @PermitAll
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext()
        .invalidateSession();
        auth.setUser(null);
        return null;
    }

    public User add(User user) {
        em.persist(user);
        return user;
    }

    public User addWithCatchPersistenceException(User user) {
        User resultUser = user;
        try {
            resultUser = self.add(user);
        } catch (Exception e) {
            Throwable cause = e;
            do {
                if (cause instanceof PersistenceException) {
                    throw new DataDuplicatedException(
                            "Duplicated username: " + user.getUsername());
                }
                cause = cause.getCause();
            } while (cause != null);
            throw e;
        }
        return resultUser;
    }

    public User findById(Long userId) {
        return em.find(User.class, userId);
    }

    public User update(User user) {
        user = em.merge(user);
        em.flush();
        return user;
    }

    public User delete(User user) {
        user = em.merge(user);

        Query deletePhoneQuery = em.createNamedQuery("User.deleteAllPhoneForUser");
        deletePhoneQuery.setParameter("user", user);
        deletePhoneQuery.executeUpdate();

        Query deletePersonQuery = em.createNamedQuery("User.deleteAllPersonForUser");
        deletePersonQuery.setParameter("user", user);
        deletePersonQuery.executeUpdate();

        user.setUserGroupSet(null);
        em.remove(user);
        return user;
    }

}
