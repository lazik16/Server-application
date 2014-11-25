/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.ejb.Mail;
import com.ropr.model.dao.UserDAOLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import com.ropr.model.User;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "register")
@ViewScoped
public class RegisterBean implements Serializable {

    @EJB
    private Mail mail;

    private String emailA;
    private String emailB;
    private String passwordA;
    private String passwordB;
    private String name;
    private String surname;

    @EJB
    private UserDAOLocal userDao;

    public RegisterBean() {

    }

    public String register() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (!emailA.equals(emailB)) {
            fc.addMessage(null, new FacesMessage("Emaily si neodpovídají"));
            return (emailA = emailB = null);
        } else if (!passwordA.equals(passwordB)) {
            fc.addMessage(null, new FacesMessage("Hesla si neodpovídají"));
            return (passwordA = passwordB = null);
        }

        User u = userDao.findByEmail(emailA);
        if (u != null) {
            fc.addMessage(null, new FacesMessage("Uživatel s tímto emailem již existuje"));
            return (emailA = emailB = null);
        }

        userDao.addUser(prepAdd());
        mail.send(emailA, "[APSYNC]Účet pro uživatele " + name + " " + surname + " vytvořen", 
                "Váš účet byl úspěšně vytvořen s následujícími údaji:"
                + "\n\nUživatelské jméno: "+emailA
                + "\nHeslo: "+passwordA
                + "\n\nMůžete se přihlásit na: http://apsync.duckdns.org/ROPR/faces/login.xhtml");
        return "/registerSuccess?faces-redirect=true";
    }

    private User prepAdd() {
        System.out.println(surname);
        User toAdd = new User();
        toAdd.setFirstName(name);
        toAdd.setLastName(surname);
        toAdd.setEmail(emailA);
        toAdd.setPassword(passwordA);
        return toAdd;
    }

    public String getEmailA() {
        return emailA;
    }

    public void setEmailA(String emailA) {
        this.emailA = emailA;
    }

    public String getEmailB() {
        return emailB;
    }

    public void setEmailB(String emailB) {
        this.emailB = emailB;
    }

    public String getPasswordA() {
        return passwordA;
    }

    public void setPasswordA(String passwordA) {
        this.passwordA = passwordA;
    }

    public String getPasswordB() {
        return passwordB;
    }

    public void setPasswordB(String passwordB) {
        this.passwordB = passwordB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
