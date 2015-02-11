/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dominik
 */
@Entity
@Table(name = "device")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d"),
    @NamedQuery(name = "Device.findByPhone", query = "SELECT d FROM Device d WHERE d.phonenumberId = :phonenumberId"),
    @NamedQuery(name = "Device.findByIdDevice", query = "SELECT d FROM Device d WHERE d.idDevice = :idDevice")})
public class Device implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toDevice")
    private List<Syncpoint> syncpointList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toDevice")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDevice")
    private Integer idDevice;
    @JoinTable(name = "user_has_device", joinColumns = {
        @JoinColumn(name = "device", referencedColumnName = "idDevice")}, inverseJoinColumns = {
        @JoinColumn(name = "user", referencedColumnName = "idUser")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceid", fetch = FetchType.EAGER)
    private List<Contact> contactList;
    @JoinColumn(name = "Phonenumber_Id", referencedColumnName = "idPhone")
    @ManyToOne(optional = false)
    @Expose
    @SerializedName("phone")
    private Phonenumber phonenumberId;

    public Device() {
    }

    public Device(Integer idDevice) {
        this.idDevice = idDevice;
    }

    public Integer getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Integer idDevice) {
        this.idDevice = idDevice;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public Phonenumber getPhonenumberId() {
        return phonenumberId;
    }

    public void setPhonenumberId(Phonenumber phonenumberId) {
        this.phonenumberId = phonenumberId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDevice != null ? idDevice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.idDevice == null && other.idDevice != null) || (this.idDevice != null && !this.idDevice.equals(other.idDevice))) {
            return false;
        }
        return true;
    }
    
    public String toJSON(){
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        String json = gson.toJson(this);
        return json;
    }
    
    public static Device fromJSON(String json){
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        Device u = gson.fromJson(json, Device.class);
        return u;
    }

    @Override
    public String toString() {
        return "com.model.Device[ idDevice=" + idDevice + " ]";
    }

    @XmlTransient
    public List<Syncpoint> getSyncpointList() {
        return syncpointList;
    }

    public void setSyncpointList(List<Syncpoint> syncpointList) {
        this.syncpointList = syncpointList;
    }
    
}
