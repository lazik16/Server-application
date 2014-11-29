/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik
 */
@Stateless
public class PhonenumberFacade extends AbstractFacade<Phonenumber> implements PhonenumberFacadeLocal {
    @PersistenceContext(unitName = "SMSCorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhonenumberFacade() {
        super(Phonenumber.class);
    }
    
    @Override
    public Phonenumber findByNumber(String number){
        Phonenumber phone;
        phone = (Phonenumber)em.createNamedQuery("Phonenumber.findByNumber").setParameter("number", number).getSingleResult();
        return phone;
    }
    
}
