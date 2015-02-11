/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Contact;
import com.ropr.model.ContactFacadeLocal;
import com.ropr.model.Device;
import com.ropr.model.DeviceFacadeLocal;
import com.ropr.model.Phonenumber;
import com.ropr.model.PhonenumberFacadeLocal;
import com.ropr.model.User;
import com.ropr.model.UserFacadeLocal;
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
@ManagedBean(name = "newContact")
@ViewScoped
public class NewContactBean implements Serializable {

    private String name, surname, nickname, email;
    String number;
    private Device selectedDevice;
    
    public Device getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(Device selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    @EJB
    ContactFacadeLocal contactDao;
    @EJB
    PhonenumberFacadeLocal phoneDao;
    @EJB
    UserFacadeLocal userDao;
    @EJB
    DeviceFacadeLocal deviceDao;
    
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String addContact(User user) {
        if (selectedDevice != null) {
            Contact c = new Contact();
            c.setEmail(email);
            c.setFirstName(name);
            c.setLastName(surname);
            c.setNick(nickname);
            c.setDeviceid(selectedDevice);
            
            Phonenumber p = new Phonenumber();
            p.setNumber(number);
            
            phoneDao.create(p);
            
            p = phoneDao.findByNumber(number);
            c.setPhonenumberid(p);
            
            contactDao.create(c);
            //c = contactDao.findByNick(c.getNick());
            selectedDevice.getContactList().add(c);
            //deviceDao.edit(selectedDevice);
            
            return "/restricted/userhome?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Musíte vybrat zařízení"));
        return "/restricted/newContact?faces-redirect=false";
    }
}
