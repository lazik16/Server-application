/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Phonenumber;
import com.ropr.model.User;
import com.ropr.model.dao.ContactDAOLocal;
import com.ropr.model.dao.PhoneDAOLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "contact")
@ViewScoped
public class ContactBean implements Serializable{

    private String name, surname, nickname, email;
    int number;

    @EJB
    ContactDAOLocal contactDao;
    @EJB
    PhoneDAOLocal phoneDao;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String addContact(User user) {
        Phonenumber ph = new Phonenumber();
        if (phoneDao.getPhonenumber(number) != null) {
            ph = phoneDao.getPhonenumber(number);
        } else {
            ph.setKey("4567ds");
            ph.setNumber(Integer.toString(number).getBytes());
            ph.setUser(user);
            //phoneDao.addPhonenumber(ph);
        }

        /*if (contactDao.findContactByUserNumber(user,ph) != null) {
            System.out.println("Contact is already in your list.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tento kontakt již máte přidaný"));
            return "/restricted/userhome?faces-redirect=false";

        } else {*/
            //Contact c = new Contact(
            //        name, surname, nickname, email, ph, user);
            //contactDao.addContact(c);
            //System.out.println("Contact added.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tento kontakt již máte přidaný"));
            return "/restricted/newContact?faces-redirect=false";
        //}
    }
}
