/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "Phonenumber.findByIdPhone", query = "SELECT p FROM Phonenumber p WHERE p.idPhone = :idPhone"),
    @NamedQuery(name = "Phonenumber.findByNumber", query = "SELECT p FROM Phonenumber p WHERE p.number = :number")})
public class Phonenumber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPhone")
    private Integer idPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 42)
    @Column(name = "number")
    private String number;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phonenumberid")
    private List<Contact> contactList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phonenumberId")
    private List<Device> deviceList;

    public Phonenumber() {
    }

    public Phonenumber(Integer idPhone) {
        this.idPhone = idPhone;
    }

    public Phonenumber(Integer idPhone, String number) {
        this.idPhone = idPhone;
        this.number = number;
    }

    public Integer getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(Integer idPhone) {
        this.idPhone = idPhone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @XmlTransient
    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @XmlTransient
    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPhone != null ? idPhone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Phonenumber)) {
            return false;
        }
        Phonenumber other = (Phonenumber) object;
        if ((this.idPhone == null && other.idPhone != null) || (this.idPhone != null && !this.idPhone.equals(other.idPhone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Phonenumber[ idPhone=" + idPhone + " ]";
    }
    
}
