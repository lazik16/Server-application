/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Contact;
import com.ropr.model.Device;
import com.ropr.model.DeviceFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "device")
@ViewScoped
public class DeviceBean implements Serializable {
    private Device selectedDevice;
    private List<Contact> contactList;

    public List<Contact> getContactList() {
        return (contactList);
    }
    @EJB
    private DeviceFacadeLocal deviceDao;

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public Device getSelectedDevice() {
        return selectedDevice;
    }
    
    public void setSelectedDevice(Device selectedDevice) {
        this.selectedDevice = selectedDevice;
    }
    
    public void update(){
        if(selectedDevice!=null) {
            this.contactList = deviceDao.findByPhone(
                    selectedDevice.getPhonenumberId().getNumber()).getContactList();
        }
    }
}
