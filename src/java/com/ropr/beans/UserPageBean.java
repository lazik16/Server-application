/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Phonenumber;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "userpage")
@SessionScoped
public class UserPageBean implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Phonenumber selectedNumber;

    public Phonenumber getSelectedNumber() {
        return selectedNumber;
    }

    public void setSelectedNumber(Phonenumber selectedNumber) {
        this.selectedNumber = selectedNumber;
         System.out.println("Device changed");
    }
    
    public void change(AjaxBehaviorEvent ev){
        System.out.println("Device changed");
    }
    
}
