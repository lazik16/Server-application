/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dominik
 */
@Entity
@Table(name = "phonenumber")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Phonenumber.findAll", query = "SELECT p FROM Phonenumber p"),
    @NamedQuery(name = "Phonenumber.findById", query = "SELECT p FROM Phonenumber p WHERE p.id = :id"),
    @NamedQuery(name = "Phonenumber.findByKey", query = "SELECT p FROM Phonenumber p WHERE p.key = :key")})
public class Phonenumber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "key")
    private String key;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "number")
    private byte[] number;
    @OneToMany(mappedBy = "phoneNumber")
    private Collection<Contact> contactCollection;
    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "from1")
    private Collection<Message> messageCollection;
    @OneToMany(mappedBy = "to")
    private Collection<Message> messageCollection1;

    public Phonenumber() {
    }

    public Phonenumber(Integer id) {
        this.id = id;
    }

    public Phonenumber(Integer id, String key, byte[] number) {
        this.id = id;
        this.key = key;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getNumber() {
        return number;
    }

    public void setNumber(byte[] number) {
        this.number = number;
    }

    @XmlTransient
    public Collection<Contact> getContactCollection() {
        return contactCollection;
    }

    public void setContactCollection(Collection<Contact> contactCollection) {
        this.contactCollection = contactCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection1() {
        return messageCollection1;
    }

    public void setMessageCollection1(Collection<Message> messageCollection1) {
        this.messageCollection1 = messageCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Phonenumber)) {
            return false;
        }
        Phonenumber other = (Phonenumber) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ropr.model.Phonenumber[ id=" + id + " ]";
    }
    
}
