/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import com.ropr.modelCo.MessageCo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dominik
 */
@Local
public interface MessageFacadeLocal {

    void create(Message message);

    void edit(Message message);

    void remove(Message message);
    
    String createCo(MessageCo message);

    String editCo(MessageCo message);

    String removeCo(MessageCo message);

    /*
    Message find(Object id);

    List<Message> findAll();

    List<Message> findRange(int[] range);

    int count();
    */
}
