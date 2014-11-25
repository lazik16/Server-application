/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Contact;
import com.ropr.model.Phonenumber;
import com.ropr.model.User;
import com.ropr.model.dao.UserDAOLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "user")
@SessionScoped
public class LoginBean implements Serializable{

    public static final String AUTH_KEY = "app.user.email";
    
    private String email;
    private String password;
    private User current;
    private String name;
    private String surname;
    private String passwordB;
    private String emailB;

    @EJB
    private UserDAOLocal userDao;

    public String login() {
        current = userDao.findByEmail(email);
        if (current != null && !current.getPassword().equals(password)) {
            current = null;
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (current == null) {
            facesContext.addMessage(null, new FacesMessage("Tento uživatel neexistuje"));
            return (email = password = null);
        } else {
            facesContext.getExternalContext().getSessionMap().put(
            AUTH_KEY, email);
            return "/restricted/userhome?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
        .remove(AUTH_KEY);
        return "/login?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap().get(AUTH_KEY) != null;
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

    public String getName() {
        return current.getFirstName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return current.getLastName();
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPasswordB() {
        return current.getPassword();
    }

    public void setPasswordB(String passwordB) {
        this.passwordB = password;
    }

    public String getEmailB() {
        return current.getEmail();
    }

    public void setEmailB(String emailB) {
        this.emailB = emailB;
    }

    public List<Contact> getContacts() {
        return userDao.getContacts(current);
    }
    
    public List<Phonenumber> getPhoneNumbers(){
        return userDao.getPhoneNumbers(current);
    }
    
    public void restoreBackup(User backup){
        setEmail(backup.getEmail());
        setName(backup.getFirstName());
        setSurname(backup.getLastName());
        setPassword(backup.getPassword());
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }
    
    public String edit(){
        FacesContext fc = FacesContext.getCurrentInstance();
        User backup = userDao.getUser(current.getId());
        restoreBackup(backup);
        
        if (!email.equals(emailB)) {
            fc.addMessage(null, new FacesMessage("Emaily si neodpovídají"));
            restoreBackup(backup);
            return (email = emailB = null);
        } else if (!password.equals(passwordB)) {
            fc.addMessage(null, new FacesMessage("Hesla si neodpovídají"));
            restoreBackup(backup);
            return (password = passwordB = null);
        }

        User u = userDao.findByEmail(email);
        if (u != null && !u.getId().equals(current.getId())) {
            fc.addMessage(null, new FacesMessage("Uživatel s tímto emailem již existuje"));
            restoreBackup(backup);
            return (email = emailB = null);
        }

        userDao.editUser(prepAdd());
        return "/restricted/userhome?faces-redirect=true";  
    }
    
    private User prepAdd(){
        User toAdd = new User();
        toAdd.setFirstName(name);
        toAdd.setLastName(surname);
        toAdd.setEmail(email);
        toAdd.setPassword(password);
        return toAdd;
    }
}
