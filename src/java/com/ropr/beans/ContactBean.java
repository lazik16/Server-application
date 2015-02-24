/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Contact;
import com.ropr.model.ContactFacadeLocal;
import com.ropr.model.Message;
import com.ropr.model.MessageFacadeLocal;
import com.ropr.sync.Daemon;
import com.ropr.utility.StaticVariables;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "contact")
@ViewScoped
public class ContactBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    private Contact selectedContact;
    private List<Message> messageList;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    
    @EJB
    private MessageFacadeLocal messageDao;
    @EJB
    private Daemon daemon;

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
    }

    public String getRealSender(Message m) {
        if (m != null) {
            if (m.getSender().equals(m.getContactidContact().getPhonenumberid().getNumber())) {
                return m.getContactidContact().getNick();
            } else {
                return "Vás";
            }
        }
        return null;
    }

    public String getRealReceiver(Message m) {
        if (m != null) {
            if (m.getReciever().equals(
                    m.getContactidContact().getPhonenumberid().getNumber())) {
                return m.getContactidContact().getNick();
            } else {
                return "Vás";
            }
        }
        return "";
    }

    public String getPrintoutNick() {
        return selectedContact == null ? "None" : selectedContact.getNick();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void update() {
        if (selectedContact != null) {
            this.messageList = selectedContact.getMessageList();
        }
    }

    public void send() {
        if (selectedContact == null) {
            facesContext.addMessage(null, new FacesMessage("Vyberte kontakt, kterému chcete psát."));
        }
        if (text != null && text.equals("")) {
            facesContext.addMessage(null, new FacesMessage("Nic jste nenapsal/a."));
        }
        if (messageList == null) {
            messageList = new ArrayList<>();
        }
        
        Message m = daemon.sendMessage(text, selectedContact);
        messageList.add(m);
        messageDao.create(m);

        selectedContact.setMessageList(messageList);
        text = "";
    }

    public String getStyle(Message m) {
        String textAlign = "leftie";
        if (m != null) {
            if (m.getSender().equals(m.getContactidContact().getDeviceid().getPhonenumberId().getNumber())) {
                textAlign = "rightie";
            } else {
                textAlign = "leftie";
            }
        }
        return textAlign;
    }
}
