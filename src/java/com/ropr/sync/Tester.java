/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.sync;

import com.ropr.model.Contact;
import com.ropr.model.Device;
import com.ropr.model.Message;
import com.ropr.model.Phonenumber;

import com.ropr.modelCo.MessageCo;
import com.ropr.modelCo.MsgPack;

import com.google.gson.Gson;
import com.ropr.modelCo.ContactCo;
import com.ropr.modelCo.DeviceCo;
import com.ropr.modelCo.JSONBroker;
import com.ropr.modelCo.ObjectFactory;

/**
 *
 * @author Dominik
 */
public class Tester {

    public static void main(String[] args) {
        Gson gson = new Gson();

        Phonenumber p = new Phonenumber();
        p.setNumber("77777");

        Device d = new Device();
        d.setPhonenumberId(p);

        Contact c = new Contact();
        c.setDeviceid(d);
        c.setEmail("dsdasd@dasds.cy");
        c.setFirstName("dasd");
        c.setLastName("sadas");
        c.setNick("aqq");
        c.setPhonenumberid(p);

        Message m = new Message();
        m.setReciever("54654");
        m.setSender("44555");
        m.setText("TEST");
        m.setContactidContact(c);

        JSONBroker broker = new JSONBroker();
        ObjectFactory factory = new ObjectFactory();
        
        MessageCo mesCo = factory.createMessage(m);
        DeviceCo devCo = factory.createDevice(d);
        ContactCo conCo = factory.createContact(c);
        
        
        MsgPack packedContact = factory.createMsgPack(conCo, conCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.MES);
        MsgPack packedDevice = factory.createMsgPack(devCo, devCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.DEV);
        MsgPack packedMessage = factory.createMsgPack(mesCo, mesCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.CON);
        
        String encodedMessage = broker.messageToJson(packedMessage);
        String encodedContact = broker.contactToJson(packedContact);
        String encodedDevice = broker.deviceToJson(packedDevice);
        
        System.out.println(encodedMessage);
        System.out.println(encodedContact);
        System.out.println(encodedDevice);
        
        MsgPack extractedPack1 = broker.extractObject(encodedMessage);
        MsgPack extractedPack2 = broker.extractObject(encodedContact);
        MsgPack extractedPack3 = broker.extractObject(encodedDevice);
        
        recognize(extractedPack1);
        recognize(extractedPack2);
        recognize(extractedPack3);  
    }
    
    public static void recognize(MsgPack extractedPack){
        switch(extractedPack.getObjectType()){
            case DEV: System.out.println("Device found"); break;
            case MES: System.out.println("Message found"); break;
            case CON: System.out.println("Contact found"); break;
        }
    }

}
