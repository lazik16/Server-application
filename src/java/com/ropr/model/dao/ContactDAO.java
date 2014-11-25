/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model.dao;

import com.ropr.model.Contact;
import com.ropr.model.Phonenumber;
import com.ropr.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik
 */
@Stateless
public class ContactDAO implements ContactDAOLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addContact(Contact contact) {
        em.persist(contact);
    }

    @Override
    public void editContact(Contact contact) {
        em.merge(contact);
    }

    @Override
    public Contact getContact(int contact) {
        return em.find(Contact.class, contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return em.createNamedQuery("Contact.findAll").getResultList();
    }

    @Override
    public void deleteAllContacts() {
        for (Contact u : getAllContacts()) {
            em.remove(u);
        }
    }

    @Override
    public void deleteContact(int id) {
        em.remove(getContact(id));
    }

    @Override
    public Contact findContactByUserNumber(User user, Phonenumber number) {
        try {
        return em.createNamedQuery("Contact.findUserByNumber", Contact.class)
                .setParameter("user", user).setParameter("phoneNumber",number).getResultList().get(0);
        }catch(NoResultException nre){
            return null;
        }
    }

}
