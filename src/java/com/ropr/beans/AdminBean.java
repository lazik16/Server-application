/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.ejb.DeviceStatus;
import com.ropr.model.Contact;
import com.ropr.model.ContactFacadeLocal;
import com.ropr.model.Message;
import com.ropr.websockets.SEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.websocket.Session;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "admin")
@ViewScoped
public class AdminBean implements Serializable {

    public List<DeviceStatus> getOnlineDevice() {
        return SEndpoint.detector.getOnlineDevices();
    }
    
    private String textToSend;
    private List<DeviceStatus> checkedSessions = new ArrayList<>();

    public List<DeviceStatus> getCheckedSessions() {
        return checkedSessions;
    }

    public void setCheckedSessions(List<DeviceStatus> checkedSessions) {
        this.checkedSessions = checkedSessions;
    }
    

    public void setSelected(DeviceStatus s) {
        if (checkedSessions.contains(s)) {
            checkedSessions.remove(s);
            System.out.println("Device "+s.getDevice()+" removed");
        } else {
            checkedSessions.add(s);
            System.out.println("Device "+s.getDevice()+" added");
        }
    }

    public String getTextToSend() {
        return textToSend;
    }

    public void setTextToSend(String textToSend) {
        this.textToSend = textToSend;
    }

    public void sendToDevice(Session s) {
        if (textToSend != null && !textToSend.isEmpty()) {
            for (DeviceStatus deviceStatus : checkedSessions) {
                SEndpoint.sendAsync(deviceStatus.getSession(), textToSend);
            }
            textToSend = "";
        }
    }

    @EJB
    ContactFacadeLocal contact;

    public void doTest() {
        List<Contact> contacts = contact.findAll();
        for (Contact c : contacts) {
            if (c.getNick().equals("PanOtrok")) {
                for (Message m : c.getMessageList()) {
                    System.out.println(m.getText());
                }
            }
        }

    }

    public void refresh() {
        FacesContext context;
        try{
        context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
        System.out.println(checkedSessions.size());
        for (DeviceStatus s : checkedSessions) {
            if (!getOnlineDevice().contains(s)) {
                checkedSessions.remove(s);
            }
        }
        }catch(Exception e){
            try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("http://apsync.duckdns.org/");
            }catch(IOException io){
                
            }
            }
    }

    public void resyncDevice(Session s) {
        SEndpoint.getMessages(s);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Synchronizováno"));
    }
    
    /*
    public void onMessageSent(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Message sent", ((Session) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }*/
 
    /*
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    */
}
