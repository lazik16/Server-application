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
    
    public String assignNumber(String email){
        FacesContext.getCurrentInstance();
        return "/restricted/viewAccount?faces-redirect=false"; 
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
}
