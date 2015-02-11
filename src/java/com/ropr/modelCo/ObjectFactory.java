/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.modelCo;

import com.ropr.model.Contact;
import com.ropr.model.Device;
import com.ropr.model.Message;
import com.ropr.modelCo.MsgPack.ActionType;
import com.ropr.modelCo.MsgPack.ObjectType;
import java.util.Date;

/**
 *
 * @author Dominik
 */
public class ObjectFactory<T> {

    public ContactCo createContact(String email, String firstName, String lastName, String nick, String phone) {
        ContactCo mc = new ContactCo();
        mc.setEmail(email);
        mc.setFirstName(firstName);
        mc.setLastName(lastName);
        mc.setNick(nick);
        mc.setPhonenumberid(phone);
        return mc;
    }
    
    public ContactCo createContact(Contact c) {
        ContactCo mc = new ContactCo();
        mc.setEmail(c.getEmail());
        mc.setFirstName(c.getFirstName());
        mc.setLastName(c.getLastName());
        mc.setNick(c.getNick());
        mc.setPhonenumberid(c.getPhonenumberid().getNumber());
        return mc;
    }

    public DeviceCo createDevice(String phone) {
        DeviceCo md = new DeviceCo();
        md.setPhonenumberId(phone);
        return md;
    }
    
    public DeviceCo createDevice(Device d) {
        DeviceCo md = new DeviceCo();
        md.setPhonenumberId(d.getPhonenumberId().getNumber());
        return md;
    }

    public MessageCo createMessage(String reciever, String sender, String text, Date sendTime) {
        MessageCo ms = new MessageCo();
        ms.setReciever(reciever);
        ms.setSender(sender);
        ms.setText(text);
        ms.setSendTime(sendTime);
        return ms;
    }
    
    public MessageCo createMessage(Message m) {
        MessageCo ms = new MessageCo();
        ms.setReciever(m.getReciever());
        ms.setSender(m.getSender());
        ms.setText(m.getText());
        ms.setSendTime(m.getSendTime());
        return ms;
    }
    
    public MsgPack createMsgPack(T object, int hash, ActionType type, ObjectType type2){
        MsgPack<T> pack = new MsgPack();
        pack.setObject(object);
        pack.setHash(hash);
        pack.setAction(type);
        pack.setObjectType(type2);
        return pack;
    }

}
