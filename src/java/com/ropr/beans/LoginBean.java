/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;


import com.ropr.model.User;
import com.ropr.model.UserFacadeLocal;
import com.ropr.utility.StaticVariables;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private User current;

    public User getCurrent() {
        return current;
    }

    @EJB
    private UserFacadeLocal userDao;
    
    public String login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        current = userDao.findByEmail(email);
        if (current != null && !current.getPassword().equals(password)) {
            current = null;
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Chyba!","Špatné jméno nebo heslo"));
        }
        if (current == null) {
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Chyba!","Tento uživatel neexistuje"));
            return (email = password = null);
        } else {
            facesContext.getExternalContext().getSessionMap().put(
                    StaticVariables.USER, current);
            System.out.println("User >> "+current + "logged in");
            return "/restricted/userhome?faces-redirect=true";
        }
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        System.out.println("User >> "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(StaticVariables.USER)
        +" logged out");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(StaticVariables.USER);
        
        return "/login?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap().get(StaticVariables.USER) != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
