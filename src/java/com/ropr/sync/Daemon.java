/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.sync;

import com.ropr.ejb.DeviceStatus;
import com.ropr.model.Contact;
import com.ropr.model.Device;
import com.ropr.model.Message;
import com.ropr.model.Phonenumber;
import com.ropr.model.Syncpoint;
import com.ropr.model.SyncpointFacadeLocal;
import com.ropr.modelCo.ContactCo;
import com.ropr.modelCo.JSONBroker;
import com.ropr.modelCo.MessageCo;
import com.ropr.modelCo.MsgPack;
import com.ropr.websockets.SEndpoint;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.websocket.Session;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "daemon")
@Stateless
public class Daemon implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private SyncpointFacadeLocal syncDao;

    private static final List<MsgPack> confirmationList = new CopyOnWriteArrayList<>();

    //Message from web/desktop user to send via mobile;
    public Message sendMessage(String text, Contact to) {
        Message m = new Message();
        m.setText(text);
        m.setContactidContact(to);
        m.setSender(to.getDeviceid().getPhonenumberId().getNumber());
        m.setReciever(to.getPhonenumberid().getNumber());
        m.setSendTime(new Date());
        
        trySendMessage(m, to.getDeviceid());
        return m;
    }

    public Contact sendContact(String email, String name, String surname, String nickname, Phonenumber number, Device selectedDevice) {
        Contact c = new Contact();
        c.setEmail(email);
        c.setFirstName(name);
        c.setLastName(surname);
        c.setNick(nickname);
        c.setDeviceid(selectedDevice);
        c.setPhonenumberid(number);

        trySendContact(c);
        return c;
    }

    private void trySendMessage(Message m, Device to) {
        System.out.println("Constructing JSON message syntax");
        String jsonToSend;
        JSONBroker broker = new JSONBroker();
        MsgPack<MessageCo> pack = broker.createMessagePack(MsgPack.ObjectType.MES, new MessageCo(m), MsgPack.ActionType.NEW);
        jsonToSend = broker.messageToJson(pack);
        commitTransmission(pack, to, jsonToSend);
    }

    private void commitTransmission(MsgPack pack, Device to, String jsonToSend) {
        DeviceStatus ds = SEndpoint.detector.getByDevice(to);
        if (ds == null) {
            System.out.println("Not online, storing");
            addToSyncpoint(to, jsonToSend);
        } else {
            System.out.println("Device is online, sending now");
            sendMessage2Client(ds.getSession(), jsonToSend);
            confirmationList.add(pack);
        }
    }

    private void trySendContact(Contact c) {
        System.out.println("Constructing JSON contact syntax");
        JSONBroker broker = new JSONBroker();
        MsgPack<ContactCo> pack = broker.createContactPack(MsgPack.ObjectType.CON, new ContactCo(c), MsgPack.ActionType.NEW);
        String jsonToSend = broker.messageToJson(pack);
        commitTransmission(pack, c.getDeviceid(), jsonToSend);
    }

    /* This method creates new synchronization point based on device and JSON message */
    private void addToSyncpoint(Device dev, String json) {
        Syncpoint newSync = new Syncpoint();
        newSync.setToDevice(dev);
        newSync.setMessage(json);
        newSync.setTime(new Date());

        syncDao.create(newSync);
        System.out.println("Sync point added");
    }

    /* 
     Will find all syncpoints based on device and retrieve them from the database and send them to the devices session afterwards. 
     If the messsage is recieved successfuly - the receiving member will reply with uniquie ID
     corresponding to the message sent to him, so that will be cleared from both confirmation list
     and database aswell.
     */
    public void synchronize(DeviceStatus des) {
        List<Syncpoint> syncList;

        syncList = syncDao.findByDevice(des.getDevice());
        System.out.println("Found device for syncing");
        syncList.stream().map((syncpoint) -> {
            sendMessage2Client(des.getSession(), syncpoint.getMessage());
            return syncpoint;
        }).forEach((syncpoint) -> {
            syncDao.remove(syncpoint);
        });
    }

    /* Sends the message and add it to confirm list, plain and simple */
    private void sendMessage2Client(Session s, String mes) {
        SEndpoint.sendAsync(s, mes);
        //here add to confirm list
    }

    /* Handles the replys from remote devices concerning synchronization points */
    public Boolean handleReply(String message) {
        //1. parse it through
        ResponseObject response = null;
        try{
        response = new JSONBroker().extractResponse(message);
        }catch(Exception ex){
            System.out.println("Not a response, passing on to next.");
            return false;
        }
        //2. verify if hash is in confirm list
        System.out.println("Looking for match.");
        for (MsgPack cfp : confirmationList) {
            if(cfp.getHash()==response.getHash()){
                confirmationList.remove(cfp);
                System.out.println("Match found, message received.");
            }
        }
        return true;
    }
}
