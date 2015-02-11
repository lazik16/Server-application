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
public class ContactFacade extends AbstractFacade<Contact> implements ContactFacadeLocal {

    @PersistenceContext(unitName = "SMSCorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactFacade() {
        super(Contact.class);
    }

    @Override
    public Contact findByNick(String nick) {
        Contact contact;
        try {
            contact = (Contact) em.createNamedQuery("Contact.findByNick", Contact.class).setParameter("nick", nick).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return contact;
    }

    @Override
    public Contact findByPhone(Phonenumber phone) {
        Contact contact;
        try {
            contact = (Contact) em.createNamedQuery("Contact.findByPhone", Contact.class).setParameter("number", phone.getNumber()).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return contact;
    }

}
