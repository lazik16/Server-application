/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dominik
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")})
public class User implements Serializable, Comparable<User> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Expose
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Expose
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Expose
    @Column(name = "lastName")
    private String lastName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Expose
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Expose
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Contact> contactCollection = new ArrayList<Contact>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Phonenumber> phonenumberCollection = new ArrayList<Phonenumber>();

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<Contact> getContactCollection() {
        return contactCollection;
    }

    public void setContactCollection(Collection<Contact> contactCollection) {
        this.contactCollection = contactCollection;
    }

    @Transactional
    @XmlTransient
    public Collection<Phonenumber> getPhonenumberCollection() {
        return phonenumberCollection;
    }

    public void setPhonenumberCollection(Collection<Phonenumber> phonenumberCollection) {
        this.phonenumberCollection = phonenumberCollection;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ropr.model.User[ id=" + id + " ]";
    }
    
    public String toJSON(){
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        String json = gson.toJson(this);
        return json;
    }
    
    public static User fromJSON(String json){
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        User u = gson.fromJson(json, User.class);
        return u;
    }
    
    @Override
    public int compareTo(User o) {
        int compareId = o.getId();
        return compareId - id;
    }
    
}
