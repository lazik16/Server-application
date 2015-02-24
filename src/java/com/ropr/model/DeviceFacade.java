/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import com.ropr.modelCo.DeviceCo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik
 */
@Stateless
public class DeviceFacade extends AbstractFacade<Device> implements DeviceFacadeLocal {
    @PersistenceContext(unitName = "SMSCorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeviceFacade() {
        super(Device.class);
    }
    
    @Override
    public Device findByPhone(String number){
        Device device;
        try{
        device = (Device)em.createNamedQuery("Device.findByPhone").setParameter("number", number).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return device;
    }

    @Override
    public String createCo(DeviceCo device) {
        Device dev = new Device(device);
        create(dev);
        return "Device created";
    }

    @Override
    public String editCo(DeviceCo device) {
        return "Not implemented";
    }

    @Override
    public String removeCo(DeviceCo device) {
        Device dev = findByPhone(device.getPhonenumberId());
        if(dev==null) return "Device with this number not found";
        else return "Device removed";
        
        //////////SHOULD VERIFY DELETING EVERYTHING BELOW!//////////
    }
    
}
