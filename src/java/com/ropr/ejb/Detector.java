/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.ejb;

import com.ropr.model.Device;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.Session;


public class Detector {
    private List<DeviceStatus> onlineDevices;

    public Detector() {
        this.onlineDevices = new CopyOnWriteArrayList<DeviceStatus>();
    }

    public List<DeviceStatus> getOnlineDevices() {
        return onlineDevices;
    }

    public void setOnlineDevices(List<DeviceStatus> onlineDevices) {
        this.onlineDevices = onlineDevices;
    }
    
    public void setOnline(DeviceStatus d){
        onlineDevices.add(d);
        System.out.println(d+" has logged in.");
    }
    
    public void setOffline(Session s){
        for (DeviceStatus deviceStatus : onlineDevices) {
            if(deviceStatus.getSession().equals(s)){
               onlineDevices.remove(deviceStatus);
               System.out.println(deviceStatus+" has logged out.");
            }
        }
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
        for (DeviceStatus deviceStatus : onlineDevices) {
            if(deviceStatus.getDevice().equals(ds.getDevice()) && ds.getSession().equals(deviceStatus.getSession()))
              return true;
        }
        return false;
    }
    
    public Boolean isAuth(Session s){
        for (DeviceStatus ds : onlineDevices) {
            if(ds.getSession().equals(s)){
                return true;
            }
        }
        return false;
    }
}
