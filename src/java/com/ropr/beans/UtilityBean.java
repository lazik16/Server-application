/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "util")
@ViewScoped
public class UtilityBean implements Serializable{
    
    
    public String byteToString(byte[] bword){
        return new String(bword);
    }
}
