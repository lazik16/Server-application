/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model.dao;

import com.ropr.model.Message;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik
 */
@Stateless
public class MessageDAO implements MessageDAOLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addMessage(Message message) {
        em.persist(message);
    }

    @Override
    public void editMessage(Message message) {
        em.merge(message);
    }

    @Override
    public Message getMessage(int message) {
        return em.find(Message.class, message);
    }

    @Override
    public List<Message> getAllMessages() {
        return em.createNamedQuery("Message.findAll").getResultList();
    }

    @Override
    public void deleteAllMessages() {
        for (Message u : getAllMessages()) {
            em.remove(u);
        }
    }

    @Override
    public void deleteMessage(int id) {
        em.remove(getMessage(id));
    }

}
