/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import com.ropr.modelCo.MessageCo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik
 */
@Stateless
public class MessageFacade extends AbstractFacade<Message> implements MessageFacadeLocal {
    @PersistenceContext(unitName = "SMSCorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessageFacade() {
        super(Message.class);
    }

    @Override
    public void create(Message entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String createCo(MessageCo message) {
        Message mes = new Message(message);
        create(mes);
        return "Message created";
    }

    @Override
    public String editCo(MessageCo message) {
        
       return "Not implemented";
    }

    @Override
    public String removeCo(MessageCo message) {
        
        
        return "Not implemented";
    }

}
