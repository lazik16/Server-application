/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;


import com.ropr.model.DeviceFacadeLocal;
import com.ropr.model.PhonenumberFacadeLocal;
import com.ropr.model.UserFacadeLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "phone")
@ViewScoped
public class PhoneBean {
    private int number;

    @EJB
    PhonenumberFacadeLocal  phoneDao;
    @EJB
    DeviceFacadeLocal  deviceDao;
    @EJB
    UserFacadeLocal  userDao;

    
    public String assignNumber(String email){
        FacesContext fc = FacesContext.getCurrentInstance();
        /*
        User user = userDao.findByEmail(email);
        Device dev = deviceDao.getDeviceByNumber(number);
        //Phonenumber phone = phoneDao.getPhonenumber(number) ;
        
        if(dev==null)
            fc.addMessage(null, new FacesMessage("fail(Zařízení s tímto číslem není připojeno do naší sítě)"));
        else{
            userDao.addDevice(dev,user);
            ///SYNCHRONIZE///
            return "/restricted/viewAccount?faces-redirect=true";  
        }*/
        return "/restricted/viewAccount?faces-redirect=false"; 
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
}
