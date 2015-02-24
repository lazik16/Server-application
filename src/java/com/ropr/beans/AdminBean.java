/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.ejb.DeviceStatus;
import com.ropr.websockets.SEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.websocket.CloseReason;
import static javax.websocket.CloseReason.CloseCodes.NORMAL_CLOSURE;
import javax.websocket.Session;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "admin")
@ViewScoped
public class AdminBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public AdminBean() {
        this.checkedSessions = new ArrayList<>();
    }
    public List<DeviceStatus> getOnlineDevice() {
        return SEndpoint.detector.getOnlineDevices();
    }

    private String textToSend;
    private List<DeviceStatus> checkedSessions;

    public List<DeviceStatus> getCheckedSessions() {
        return (checkedSessions);
    }

    public void setCheckedSessions(List<DeviceStatus> checkedSessions) {
        this.checkedSessions = checkedSessions;
    }

    public void setSelected(DeviceStatus s) {
        if (checkedSessions.contains(s)) {
            checkedSessions.remove(s);
            System.out.println("Device " + s.getDevice() + " removed");
        } else {
            checkedSessions.add(s);
            System.out.println("Device " + s.getDevice() + " added");
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
            checkedSessions.stream().forEach((deviceStatus) -> {
                SEndpoint.sendAsync(deviceStatus.getSession(), textToSend);
            });
            textToSend = "";
        }
    }

    public void killDevice(Session s) {
        for (DeviceStatus deviceStatus : checkedSessions) {
            try {
                SEndpoint.detector.getOnlineDevices().remove(deviceStatus);
                CloseReason cr = new CloseReason(NORMAL_CLOSURE, "Device killed by admin");
                deviceStatus.getSession().close(cr);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    public void refresh() {
        FacesContext context;
        try {
            context = FacesContext.getCurrentInstance();
            String viewId = context.getViewRoot().getViewId();
            ViewHandler handler = context.getApplication().getViewHandler();
            UIViewRoot root = handler.createView(context, viewId);
            root.setViewId(viewId);
            context.setViewRoot(root);
            System.out.println(checkedSessions.size());
            checkedSessions.stream().filter((s) -> (!getOnlineDevice().contains(s))).forEach((s) -> {
                checkedSessions.remove(s);
            });
        } catch (Exception e) {
            try{
                FacesContext.getCurrentInstance().getExternalContext().redirect("http://apsync.duckdns.org/");
                e.printStackTrace();
            }catch(Exception o){
                o.printStackTrace();
            }
        }
    }
}
