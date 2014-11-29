/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Device;
import com.ropr.model.DeviceFacadeLocal;
import com.ropr.model.User;
import com.ropr.model.Phonenumber;
import com.ropr.model.PhonenumberFacadeLocal;
import com.ropr.model.UserFacadeLocal;
import com.ropr.utility.StaticVariables;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "device")
@ViewScoped
public class DeviceBean implements Serializable{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    private String number;
    private Device device;
    
    @EJB
    DeviceFacadeLocal deviceDao;
    @EJB
    UserFacadeLocal userDao;
    @EJB
    PhonenumberFacadeLocal phoneDao;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
    public String assignDevice(User user){
        Phonenumber phone;
        phone = phoneDao.findByNumber(number);
        if(phone == null){
            System.out.println("Telefon je null");
            facesContext.addMessage(null, new FacesMessage("Toto toto telefonní číslo se nenachází v naší databázi"));
            return "/restricted/viewAccount?faces-redirect=false";
        }
        
        device = deviceDao.findByPhone(phone);
        if(device == null){
            facesContext.addMessage(null, new FacesMessage("Toto zařízení se nenachází v naší databázi."));
            return "/restricted/viewAccount?faces-redirect=false";
        }
        
        if(device.getUserList().contains(user)){
            System.out.println("User není je null");
            facesContext.addMessage(null, new FacesMessage("Toto zařízení již máte přidané."));
            return "/restricted/viewAccount?faces-redirect=false";
        }
                
        user.getDeviceList().add(device);
        device.getUserList().add(user);
        userDao.edit(user);
        deviceDao.edit(device);
        number = "";
        return "/restricted/viewAccount?faces-redirect=true";
    }
    
}
