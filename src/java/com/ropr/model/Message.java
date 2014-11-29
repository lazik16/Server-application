/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dominik
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByIdMessage", query = "SELECT m FROM Message m WHERE m.idMessage = :idMessage"),
    @NamedQuery(name = "Message.findByReciever", query = "SELECT m FROM Message m WHERE m.reciever = :reciever"),
    @NamedQuery(name = "Message.findBySendTime", query = "SELECT m FROM Message m WHERE m.sendTime = :sendTime"),
    @NamedQuery(name = "Message.findBySender", query = "SELECT m FROM Message m WHERE m.sender = :sender")})
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMessage")
    private Integer idMessage;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "text")
    private String text;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 42)
    @Column(name = "reciever")
    private String reciever;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sendTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 42)
    @Column(name = "sender")
    private String sender;
    @JoinColumn(name = "Contact_idContact", referencedColumnName = "idContact")
    @ManyToOne(optional = false)
    private Contact contactidContact;

    public Message() {
    }

    public Message(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public Message(Integer idMessage, String text, String reciever, Date sendTime, String sender) {
        this.idMessage = idMessage;
        this.text = text;
        this.reciever = reciever;
        this.sendTime = sendTime;
        this.sender = sender;
    }

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Contact getContactidContact() {
        return contactidContact;
    }

    public void setContactidContact(Contact contactidContact) {
        this.contactidContact = contactidContact;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMessage != null ? idMessage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.idMessage == null && other.idMessage != null) || (this.idMessage != null && !this.idMessage.equals(other.idMessage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ropr.model.Message[ idMessage=" + idMessage + " ]";
    }
    
}
