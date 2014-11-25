/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model.dao;

import com.ropr.model.Phonenumber;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik
 */
@Stateless
public class PhoneDAO implements PhoneDAOLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addPhonenumber(Phonenumber numbr) {
        em.persist(numbr);
    }

    @Override
    public void editPhonenumber(Phonenumber numbr) {
        em.merge(numbr);
    }

    @Override
    public Phonenumber getPhonenumber(int numbr) {
        return em.find(Phonenumber.class, numbr);
    }

    @Override
    public List<Phonenumber> getAllPhonenumbers() {
        return em.createNamedQuery("Phonenumber.findAll").getResultList();
    }

    @Override
    public void deleteAllPhonenumbers() {
        for (Phonenumber u : getAllPhonenumbers()) {
            em.remove(u);
        }
    }

    @Override
    public void deletePhonenumber(int id) {
        em.remove(getPhonenumber(id));
    }

}
