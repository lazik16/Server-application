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
import com.ropr.model.Syncpoint;
import com.ropr.model.SyncpointFacadeLocal;
import com.ropr.websockets.SEndpoint;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.faces.bean.ManagedBean;
import javax.websocket.Session;

/**
 *
 * @author Dominik
 */
@ManagedBean(name = "daemon")
@Stateless
public class Daemon implements Serializable {

    @EJB
    private SyncpointFacadeLocal syncDao;

    @Resource
    ManagedExecutorService mes;

    private static final List confirmationList = new CopyOnWriteArrayList<>();

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

    //1. get to JSON
    //2. look for device
    //3. send if online
    //4. add for synchronization if not
    private void trySendMessage(Message m, Device to) {
        //Phonenumber toPhone = phoneDao.findByNumber(m.getSender());
        //Device to = deviceDao.findByPhone(toPhone);

        //Need to create propper JSON form, DO NOT FORGET
        String jsonToSend = m.toJSON();

        DeviceStatus ds = SEndpoint.detector.getByDevice(to);
        if (ds == null) {
            System.out.println("Not online");
            addToSyncpoint(to, jsonToSend);
        } else {
            System.out.println("Device is online");
            sendMessage2Client(ds.getSession(), jsonToSend);
        }
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
        for (Syncpoint syncpoint : syncList) {
            sendMessage2Client(des.getSession(), syncpoint.getMessage());
            syncDao.remove(syncpoint);
        }
    }

    /* Sends the message and add it to confirm list, plain and simple */
    private void sendMessage2Client(Session s, String mes) {
        SEndpoint.sendAsync(s, mes);
        //here add to confirm list
    }

    /* Handles the replys from remote devices concerning synchronization points */
    public static void handleReply(String message) {
        //1. parse it through
        String hashCode = "..";
        //2. verify if hash is in confirm list

        //3. remove message from confirmList and then from syncpoint database
    }
}
