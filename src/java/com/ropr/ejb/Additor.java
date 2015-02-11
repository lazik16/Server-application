/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.ejb;

import com.ropr.model.Contact;
import com.ropr.model.ContactFacadeLocal;
import com.ropr.model.Device;
import com.ropr.model.DeviceFacadeLocal;
import com.ropr.model.Message;
import com.ropr.model.MessageFacadeLocal;
import com.ropr.model.Phonenumber;
import com.ropr.model.PhonenumberFacadeLocal;
import com.ropr.model.User;
import com.ropr.model.UserFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ejb.EJB;

/**
 *
 * @author Dominik
 */
public class Additor {
    @EJB
    static PhonenumberFacadeLocal phone;
    @EJB
    static UserFacadeLocal user;
    @EJB
    static ContactFacadeLocal contact;
    @EJB
    static DeviceFacadeLocal device;
    @EJB
    static MessageFacadeLocal message;
    
    public static void addContact(Contact c){
        contact.create(c);
    }
    public static void addMessage(Message m){
        messages.add(m);
        message.create(m);
    }
    public static void addDevice(Device d){
        device.create(d);
    }
    public static void addUser(User u){
        user.create(u);
    }
    public static void addPhone(Phonenumber p){
        phone.create(p);
    }
    
    public static List<Message> messages = new CopyOnWriteArrayList<Message>();
    public static List<Contact> contacts = new CopyOnWriteArrayList<Contact>();
    public static List<Phonenumber> phonenumbers = new CopyOnWriteArrayList<Phonenumber>();
    public static List<User> users = new CopyOnWriteArrayList<User>();
    public static List<Device> devices = new CopyOnWriteArrayList<Device>();
    
}
