/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model.dao;

import com.ropr.model.Message;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dominik
 */
@Local
public interface MessageDAOLocal {

    void addMessage(Message message);

    void editMessage(Message message);

    Message getMessage(int message);

    List<Message> getAllMessages();

    void deleteAllMessages();

    void deleteMessage(int id);
}
