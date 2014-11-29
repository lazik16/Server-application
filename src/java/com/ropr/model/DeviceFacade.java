/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

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
    public Device findByPhone(Phonenumber number){
        Device device;
        try{
        device = (Device)em.createNamedQuery("Device.findByPhone").setParameter("phonenumberId", number).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return device;
    }
    
}
