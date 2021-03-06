/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.modelCo;

import com.google.gson.annotations.Expose;
import com.ropr.model.Device;
import com.ropr.model.Phonenumber;

/**
 *
 * @author Dominik
 */
public class DeviceCo<T> {
    @Expose
    private String phone;
    private Phonenumber realNumber;

    public Phonenumber getRealNumber() {
        return realNumber;
    }

    public void setRealNumber(Phonenumber realNumber) {
        this.realNumber = realNumber;
    }
    

    public DeviceCo(Device d) {
        phone = d.getPhonenumberId().getNumber();
    }
    
    public DeviceCo() {
        
    }
    
    public String getPhonenumberId() {
        return phone;
    }

    public void setPhonenumberId(String phonenumberId) {
        this.phone = phonenumberId;
    }
    
    
    
    
}
