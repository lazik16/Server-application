/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model.dao;

import com.ropr.model.Contact;
import com.ropr.model.Phonenumber;
import com.ropr.model.User;
import java.util.ArrayList;
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
public class UserDAO implements UserDAOLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void editUser(User user) {
        em.merge(user);
    }

    @Override
    public User getUser(int user) {
        return em.find(User.class, user);
    }

    @Override
    public List<User> getAllUsers() {
        return em.createNamedQuery("User.findAll").getResultList();
    }

    @Override
    public void deleteAllUsers() {
        for (User u : getAllUsers()) {
            em.remove(u);
        }
    }

    @Override
    public void deleteUser(int id) {
        em.remove(getUser(id));
    }

    @Override
    public User findByEmail(String email) {
        User u;
        try {
            u = (User) em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return u;
    }

    @Override
    public List<Contact> getContacts(User u) {
        List<Contact> cl;
        try {
           cl = em.createNamedQuery("Contact.findByUser", Contact.class)
                   .setParameter("user", u).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
        return cl;
    }

    @Override
    public void addNumber(Phonenumber number, User u) {
       u.getPhonenumberCollection().add(number);
    }

    @Override
    public List<Phonenumber> getPhoneNumbers(User u) {
       return new ArrayList<>(u.getPhonenumberCollection());
    }
    
    

}
