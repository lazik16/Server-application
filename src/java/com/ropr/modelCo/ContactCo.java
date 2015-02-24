/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.modelCo;

import com.google.gson.annotations.Expose;
import com.ropr.model.Contact;
import com.ropr.model.Device;
import com.ropr.model.Phonenumber;

/**
 *
 * @author Dominik
 */
public class ContactCo {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String nick;
    @Expose
    private String email;
    @Expose
    private String phonenumberid;
    private Phonenumber realNumber;
    private Device device;

    public Phonenumber getRealNumber() {
        return realNumber;
    }

    public void setRealNumber(Phonenumber realNumber) {
        this.realNumber = realNumber;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public ContactCo(Contact c) {
        this.firstName = c.getFirstName();
        this.lastName = c.getLastName();
        this.nick = c.getNick();
        this.email = c.getEmail();
        this.phonenumberid = c.getPhonenumberid().getNumber();
    }

    public ContactCo() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /*
    public P getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(P deviceid) {
        this.deviceid = deviceid;
    }
    */
    public String getPhonenumberid() {
        return phonenumberid;
    }

    public void setPhonenumberid(String phonenumberid) {
        this.phonenumberid = phonenumberid;
    }

}
