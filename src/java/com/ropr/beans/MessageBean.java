/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Contact;
import com.ropr.model.Message;
import com.ropr.model.Phonenumber;
import com.ropr.model.dao.MessageDAOLocal;
import java.io.Serializable;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "message")
@SessionScoped
public class MessageBean implements Serializable{

    private String text;
    private Phonenumber from;
    private Phonenumber to;
    private Contact selectedContact;

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public String getPrintoutNick() {
        return selectedContact == null ? "None" : selectedContact.getNick();
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
    }
    // + send time

    @EJB
    MessageDAOLocal messageDao;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Phonenumber getFrom() {
        return from;
    }

    public void setFrom(Phonenumber from) {
        System.out.println("From set to "+from);
        this.from = from;
    }

    public Phonenumber getTo() {
        return to;
    }

    public void setTo(Phonenumber to) {
        this.to = to;
    }

    public void send() {
            to = selectedContact.getPhoneNumber();

            Message m = new Message();
            m.setFrom1(from); //active number
            // Tohle se musí přepracovat v databázi, jinak by došlo ke kolizi v tom, že bychom o čísla přicházeli
            // jelikož by se neukládaly do databáze.

            Phonenumber ph = new Phonenumber();
            ph.setNumber(to.getNumber());
            m.setTo(ph);
            
            m.setSendTime(new Date());

            if (to != null && from != null) {
                //messageDao.addMessage(m);
            }
            if(from==null){
                System.out.println("From is null");
            }
            //System.out.println("Message from:" + Arrays.toString(from.getNumber()) + " to: " + Arrays.toString(to.getNumber()));
    }
}
