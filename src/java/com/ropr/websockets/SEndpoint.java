package com.ropr.websockets;

import com.google.gson.JsonParseException;
import com.ropr.ejb.Detector;
import com.ropr.ejb.DeviceStatus;
import com.ropr.model.Contact;
import com.ropr.model.ContactFacadeLocal;
import com.ropr.model.DeviceFacadeLocal;
import com.ropr.model.Device;
import com.ropr.model.Message;
import com.ropr.model.Phonenumber;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.ropr.model.MessageFacadeLocal;
import com.ropr.model.PhonenumberFacadeLocal;
import com.ropr.sync.Daemon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint("/anth")
@Stateless
public class SEndpoint {

    private final int ASC = 0;
    private final int DESC = 1;

    public static List<Session> authSessions = new ArrayList<>();
    public static Detector detector = new Detector();

    @Resource
    ManagedExecutorService mes;

    public static String address = "apsync.duckdns.org";

    @EJB
    private ContactFacadeLocal contactDao;
    @EJB
    private MessageFacadeLocal messageDao;
    @EJB
    private DeviceFacadeLocal deviceDao;
    @EJB
    private PhonenumberFacadeLocal phoneDao;
    @EJB
    private Daemon daemon;

    @PersistenceUnit
    EntityManagerFactory enf;

    @OnMessage
    public String receiveMessage(String message, Session session) {
        //////////////////PUBLIC SECTION//////////////////
        if (message.matches("logoutDevice[(][)]")) {
            return logoutDevice(session);
        } else if (message.matches("loginDevice[(][+]\\d*\\s\\d*\\s\\d*\\s\\d*[)]")) {
            message = message.substring(12, message.length() - 1);
            return loginDevice(message, session);
        }

        ////////////////AUTHORIZED SECTION//////////////////
        if (!detector.isAuth(session)) {
            return "You are not prepared!";
        }
        
        else if (message.matches("rsync")){
            daemon.synchronize(detector.getBySession(session));
            return "Synchronized";
        }
        
        else if(message.matches("rep*")){
            daemon.handleReply(message);
        } 
        
        else if (message.matches("devMessages[(][)]")) {
            return getMessages(session);
        } 
        
        else if (message.matches("devMessages[(][+]\\d*\\s\\d*\\s\\d*\\s\\d*[)]")) {
            message = message.substring(12, message.length() - 1);
            return getMessages(message, session);
        }

        return addMessage(message);
    }
    
    public String addMessage(String message){
        Message back;
        try {
            back = Message.fromJSON(message);
        } catch (JsonParseException ex) {
            return "Bad JSON syntax";
        }
        Contact scapegoat = contactDao.findByPhone(back.getContactidContact().getPhonenumberid());
        if (scapegoat != null) {
            back.setContactidContact(scapegoat);
            messageDao.create(back);
        } else {
            return "Contact not found!";
        }
        return "Message to contact " + back.getContactidContact().getNick() + " added.";
    }
    

    public static String getMessages(Session s) {
        sendAsync(s, "Transmission initialized.");
        Device currDev = detector.getDeviceBySession(s);

        for (Contact c : currDev.getContactList()) {
            String cToSend = c.toJSON();
            sendAsync(s, cToSend);
            for (Message m : c.getMessageList()) {
                String mToSend = m.toJSON();
                sendAsync(s, mToSend);
            }
        }
        return "Transmission complete.";
    }

    public static String getMessages(String message, Session s) {
        sendAsync(s, "Transmission initialized.");
        Device currDev = detector.getDeviceBySession(s);
        for (Contact c : currDev.getContactList()) {
            if (c.getPhonenumberid().getNumber().equals(message)) {
                for (Message m : c.getMessageList()) {
                    sendAsync(s, m.toJSON());
                }
            }
        }
        return "Transmission complete.";
    }

    private String logoutDevice(Session session) {
        detector.setOffline(session);
        return "Logged out";
    }

    @OnOpen
    public void open(Session session) {
        System.out.println(("Open session:" + session.getId()));
    }

    @OnClose
    public void close(Session session, CloseReason c) {
        logoutDevice(session);
        System.out.println("Closing:" + session.getId() + " reason: " + c.getReasonPhrase());
    }

    private String loginDevice(String message, Session session) {
        System.out.println(message);
        Phonenumber ph = phoneDao.findByNumber(message);
        if (ph == null) {
            return "No such device registered";
        }
        Device d = deviceDao.findByPhone(ph);

        DeviceStatus ds = new DeviceStatus(d, session);
        if (!detector.isOnline(ds)) {
            detector.setOnline(ds);
            return "Logged in";
        } else {
            return "Already logged in";
        }
    }

    public static void sendAsync(Session s, String text) {
        try {
            s.getBasicRemote().sendText(text);
        } catch (IOException ei) {
            ei.printStackTrace();
        }
    }
}
