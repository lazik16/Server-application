/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.ejb;

import com.ropr.model.Device;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.Session;


public class Detector {
    private List<DeviceStatus> onlineDevices;

    public Detector() {
        this.onlineDevices = new CopyOnWriteArrayList<>();
    }

    public List<DeviceStatus> getOnlineDevices() {
        return Collections.unmodifiableList(onlineDevices);
    }

    public void setOnlineDevices(List<DeviceStatus> onlineDevices) {
        this.onlineDevices = onlineDevices;
    }
    
    public void setOnline(DeviceStatus d){
        onlineDevices.add(d);
        System.out.println(d+" has logged in.");
    }
    
    public void setOffline(Session s){
        onlineDevices.stream().filter((deviceStatus) -> (deviceStatus.getSession().equals(s))).map((deviceStatus) -> {
            onlineDevices.remove(deviceStatus);
            return deviceStatus;
        }).forEach((deviceStatus) -> {
            System.out.println(deviceStatus+" has logged out.");
        });
    }
    
    public DeviceStatus getByDevice(Device device){
        for (DeviceStatus ds : onlineDevices) {
            if(ds.getDevice().equals(device)){
                return ds;
            }
        }
        return null;
    }
    
    public DeviceStatus getBySession(Session s){
        for (DeviceStatus ds : onlineDevices) {
            if(ds.getSession().equals(s)){
                return ds;
            }
        }
        return null;
    }
    
    public Device getDeviceBySession(Session s){
        for (DeviceStatus ds : onlineDevices) {
            if(ds.getSession().equals(s)){
                return ds.getDevice();
            }
        }
        return null;
    }
    
    public Boolean isOnline(DeviceStatus ds){
        return onlineDevices.stream().anyMatch((deviceStatus) -> (deviceStatus.getDevice().equals(ds.getDevice()) && ds.getSession().equals(deviceStatus.getSession())));
    }
    
    public Boolean isAuth(Session s){
        return onlineDevices.stream().anyMatch((ds) -> (ds.getSession().equals(s)));
    }
}
