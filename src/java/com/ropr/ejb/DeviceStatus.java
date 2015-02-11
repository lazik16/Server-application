/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.ejb;

import com.ropr.model.Device;
import javax.websocket.Session;

/**
 *
 * @author Dominik
 */
public class DeviceStatus {
    private Device device;
    private Session session;

    public DeviceStatus(Device device, Session session) {
        this.device = device;
        this.session = session;
    }
    public DeviceStatus() {

    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
