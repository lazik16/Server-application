/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Contact;
import com.ropr.model.Device;
import com.ropr.model.MessageFacadeLocal;
import com.ropr.model.Phonenumber;
import com.ropr.model.User;
import com.ropr.model.UserFacadeLocal;
import com.ropr.utility.StaticVariables;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "userpage")
@ViewScoped
public class UserPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    UserFacadeLocal userDao;

    private Device selectedDevice;
    private User current;
    private List<Contact> contactList;
    private List<Device> deviceList;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();

    @EJB
    MessageFacadeLocal messageDao;

    public Device getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(Device selectedDevice) {
        facesContext.getExternalContext().getSessionMap().put(StaticVariables.DEVICE, selectedDevice);
        facesContext.getExternalContext().getSessionMap().put(StaticVariables.CONTACT, null);
        this.selectedDevice = selectedDevice;

        if (selectedDevice != null) {
            contactList = selectedDevice.getContactList();
        }
    }

    @PostConstruct
    public void init() {
        current = (User) facesContext.getExternalContext().getSessionMap().get(StaticVariables.USER);
        System.out.println("User >> "+current);
        deviceList = current.getDeviceList();
        setSelectedDevice((Device) facesContext.getExternalContext().getSessionMap().get(StaticVariables.DEVICE));

        if (selectedDevice == null && !deviceList.isEmpty()) {
            setSelectedDevice(deviceList.get(0));
            facesContext.getExternalContext().getSessionMap().put(StaticVariables.DEVICE, selectedDevice);
        }
        if (selectedDevice != null) {
            contactList = selectedDevice.getContactList();
        }
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public Phonenumber getDevNumber(Device d) {
        return d.getPhonenumberId();
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
