/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Device;
import com.ropr.model.DeviceFacadeLocal;
import com.ropr.model.User;
import com.ropr.model.UserFacadeLocal;
import com.ropr.utility.StaticVariables;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Dominik
 */

@ManagedBean(name = "editUser")
@ViewScoped
public class EditUserBean implements Serializable{
    private User user;
    private String emailA,emailB,passA,passB,name,surname;
    
    public EditUserBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (User)facesContext.getExternalContext().getSessionMap().get(StaticVariables.USER);
        name = user.getName();
        surname = user.getSurname();
        emailB = emailA = user.getEmail();
        passA = user.getPassword();
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

    public String getPassA() {
        return passA;
    }

    public void setPassA(String passA) {
        this.passA = passA;
    }

    public String getPassB() {
        return passB;
    }

    public void setPassB(String passB) {
        this.passB = passB;
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
    
    @EJB
    UserFacadeLocal userDao;
    @EJB
    DeviceFacadeLocal deviceDao;
    
    public String editUser(){
        user.setEmail(emailA);
        user.setPassword(passA);
        user.setName(name);
        user.setSurname(surname);
        
        userDao.edit(user);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getSessionMap().put(StaticVariables.USER,user);
        return "/restricted/userhome?faces-redirect=true";
    }
    
    public String unsignDevice(Device device){
        user.getDeviceList().remove(device);
        device.getUserList().remove(user);
        userDao.edit(user);
        deviceDao.edit(device);
        
        return "/restricted/viewAccount?faces-redirect=true";
    }
    
    
    
    
    
}
