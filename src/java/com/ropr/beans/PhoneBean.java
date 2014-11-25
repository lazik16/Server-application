/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Phonenumber;
import com.ropr.model.User;
import com.ropr.model.dao.PhoneDAOLocal;
import com.ropr.model.dao.UserDAOLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "phone")
@ViewScoped
public class PhoneBean {
    private int number;

    @EJB
    PhoneDAOLocal phoneDao;
    @EJB
    UserDAOLocal userDao;

    
    public String assignNumber(String email){
        FacesContext fc = FacesContext.getCurrentInstance();
        
        User user = userDao.findByEmail(email);
        Phonenumber phone = phoneDao.getPhonenumber(number) ;
        if(phone==null)
            fc.addMessage(null, new FacesMessage("fail(Toto číslo není připojeno do naší sítě)"));
        else{
            userDao.addNumber(phone,user);
            ///SYNCHRONIZE///
            return "/restricted/viewAccount?faces-redirect=true";  
        }
        return "/restricted/viewAccount?faces-redirect=false"; 
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
}
