/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;


import com.ropr.model.Contact;
import com.ropr.model.Message;
import com.ropr.model.MessageFacadeLocal;
import com.ropr.utility.StaticVariables;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "message")
@ViewScoped
public class MessageBean implements Serializable{

    private String text;
    private Contact selectedContact;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private List<Message> messageList;
    
    @EJB
    MessageFacadeLocal messageDao;
    
    @PostConstruct
    public void init(){
        selectedContact = (Contact)facesContext.getExternalContext().getSessionMap().get(StaticVariables.CONTACT);
        if(selectedContact !=null){
            messageList = new ArrayList<>();
            messageList.addAll(selectedContact.getMessageList());
        }
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public String getPrintoutNick() {
        return selectedContact == null ? "None" : selectedContact.getNick();
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
        facesContext.getExternalContext().getSessionMap().put(StaticVariables.CONTACT, selectedContact);
        if(selectedContact != null){
            messageList = new ArrayList<>();
            messageList.addAll(selectedContact.getMessageList());
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void send() {
            Message m = new Message();
            
            if(selectedContact == null){
                facesContext.addMessage(null, new FacesMessage("Vyberte kontakt, kterému chcete psát."));
            }
            if(text!=null && text.equals("")){
                facesContext.addMessage(null, new FacesMessage("Nic jste nenapsal/a."));
            }
            
            m.setText(text);
            m.setContactidContact(selectedContact);
            m.setSender(selectedContact.getDeviceid().getPhonenumberId().getNumber());
            m.setReciever(selectedContact.getPhonenumberid().getNumber());
            m.setSendTime(new Date());
            messageDao.create(m);
            messageList.add(m);
            selectedContact.setMessageList(messageList);
            text = "";
    }
    
    public String getRealSender(Message m){
        if(m.getSender().equals(m.getContactidContact().getPhonenumberid().getNumber())){
            return m.getContactidContact().getNick();
        }else 
            return "Vás";
    }
    
    public String getRealReceiver(Message m){
        if(m.getReciever().equals(m.getContactidContact().getPhonenumberid().getNumber())){
            return m.getContactidContact().getNick();
        }else 
            return "Vás";
    }
}
