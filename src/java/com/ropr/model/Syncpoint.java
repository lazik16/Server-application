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
@Table(name = "syncpoint")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Syncpoint.findAll", query = "SELECT s FROM Syncpoint s"),
    @NamedQuery(name = "Syncpoint.findByIdSyncpoint", query = "SELECT s FROM Syncpoint s WHERE s.idSyncpoint = :idSyncpoint"),
    @NamedQuery(name = "Syncpoint.findByDevice", query = "SELECT s FROM Syncpoint s WHERE s.toDevice = :device"),
    @NamedQuery(name = "Syncpoint.findByTime", query = "SELECT s FROM Syncpoint s WHERE s.time = :time")})
public class Syncpoint implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSyncpoint")
    private Integer idSyncpoint;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @JoinColumn(name = "toDevice", referencedColumnName = "idDevice")
    @ManyToOne(optional = false)
    private Device toDevice;

    public Syncpoint() {
    }

    public Syncpoint(Integer idSyncpoint) {
        this.idSyncpoint = idSyncpoint;
    }

    public Syncpoint(Integer idSyncpoint, String message, Date time) {
        this.idSyncpoint = idSyncpoint;
        this.message = message;
        this.time = time;
    }

    public Integer getIdSyncpoint() {
        return idSyncpoint;
    }

    public void setIdSyncpoint(Integer idSyncpoint) {
        this.idSyncpoint = idSyncpoint;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Device getToDevice() {
        return toDevice;
    }

    public void setToDevice(Device toDevice) {
        this.toDevice = toDevice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSyncpoint != null ? idSyncpoint.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Syncpoint)) {
            return false;
        }
        Syncpoint other = (Syncpoint) object;
        if ((this.idSyncpoint == null && other.idSyncpoint != null) || (this.idSyncpoint != null && !this.idSyncpoint.equals(other.idSyncpoint))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ropr.model.Syncpoint[ idSyncpoint=" + idSyncpoint + " ]";
    }
    
}
