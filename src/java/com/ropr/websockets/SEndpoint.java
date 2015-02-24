package com.ropr.websockets;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.ropr.ejb.Detector;
import com.ropr.ejb.DeviceStatus;
import com.ropr.model.Contact;
import com.ropr.model.ContactFacadeLocal;
import com.ropr.model.DeviceFacadeLocal;
import com.ropr.model.Device;
import com.ropr.model.Message;
import com.ropr.model.Phonenumber;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.ropr.model.MessageFacadeLocal;
import com.ropr.model.PhonenumberFacadeLocal;
import com.ropr.modelCo.ContactCo;
import com.ropr.modelCo.DeviceCo;
import com.ropr.modelCo.JSONBroker;
import com.ropr.modelCo.MessageCo;
import com.ropr.modelCo.MsgPack;
import com.ropr.sync.Daemon;
import com.ropr.sync.ResponseObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ServerEndpoint("/anth")
@Stateless
public class SEndpoint {

    public static List<Session> authSessions = new ArrayList<>();
    public static Detector detector = new Detector();
    public static String address = "apsync.duckdns.org";

    @EJB
    private ContactFacadeLocal contactFacade;
    @EJB
    private MessageFacadeLocal messageFacade;
    @EJB
    private DeviceFacadeLocal deviceFacade;
    @EJB
    private PhonenumberFacadeLocal phoneFacade;
    @EJB
    private Daemon daemon;

    @OnMessage
    public String receiveMessage(String message, Session session) {
        System.out.println("CU: " + message);
        
        ResponseObject response = new ResponseObject();
        if(daemon.handleReply(message)){
            return "";
        } else if (message.matches("logoutDevice[(][)]")) {
            response = generateResponse(logoutDevice(session), ResponseType.ACK);
        } else if (message.matches("loginDevice[(][+]\\d*\\s\\d*\\s\\d*\\s\\d*[)]")) {
            message = message.substring(12, message.length() - 1);
            response = loginDevice(message, session);
        } else if (message.length() > 14 && message.substring(0, 14).matches("registerDevice")) {
            Device currentDevice = detector.getDeviceBySession(session);
            response = initializeDeviceCreation(message.substring(14, message.length()), currentDevice);
        } else if (!detector.isAuth(session)) {
            response = generateResponse("You are not logged in.", ResponseType.ERR);
        } else if (message.matches("rsync")) {
            daemon.synchronize(detector.getBySession(session));
            response = generateResponse("Synchronized", ResponseType.ACK);
        }else
        response = recognize(message, new JSONBroker(), session);
        
        return new JSONBroker().responseToJSon(response);
    }

    public ResponseObject processMessage(MsgPack<MessageCo> mes, Device s) {
        String contactNumber;
        if (s.getPhonenumberId().getNumber().equals(mes.getObject().getReciever())) {
            contactNumber = mes.getObject().getSender();
        } else {
            contactNumber = mes.getObject().getReciever();
        }
        Contact currentContact = contactFacade.findByPhone(contactNumber, s);
        mes.getObject().setContact(currentContact);

        if (currentContact != null) {
            switch (mes.getAction()) {
                case NEW:
                    return generateResponse(messageFacade.createCo(mes.getObject()), ResponseType.ACK);

                case DEL:
                    return generateResponse(messageFacade.removeCo(mes.getObject()), ResponseType.ACK);

                case REP:
                    return generateResponse(messageFacade.editCo(mes.getObject()), ResponseType.ACK);
            }
        }
        return generateResponse("Contact not found", ResponseType.ERR);
    }

    public static enum ResponseType {

        ERR, ACK, INFO
    }

    public ResponseObject processContact(MsgPack<ContactCo> mes, Device s) {
        Phonenumber p = phoneFacade.findByNumber(mes.getObject().getPhonenumberid());
        if (p == null) {
            p = new Phonenumber();
            p.setNumber(mes.getObject().getPhonenumberid());
            phoneFacade.create(p);
            p = phoneFacade.findByNumber(mes.getObject().getPhonenumberid());
        }
        mes.getObject().setRealNumber(p);
        mes.getObject().setDevice(s);
        Contact currentContact;

        switch (mes.getAction()) {
            case NEW:
                currentContact = contactFacade.findByPhone(mes.getObject().getPhonenumberid(), s);
                if (currentContact != null && currentContact.getDeviceid().equals(s)) {
                    return generateResponse("Contact already exists", ResponseType.ERR);
                }
                return generateResponse(contactFacade.createCo(mes.getObject()), ResponseType.ACK);
            case DEL:
                currentContact = contactFacade.findByPhone(mes.getObject().getPhonenumberid(), s);
                if (currentContact == null) {
                    return generateResponse("Contact does not exist", ResponseType.ERR);
                }
                eraseContactMessages(currentContact);
                contactFacade.remove(currentContact);
                return generateResponse("Contact removed", ResponseType.ACK);
            case REP:
                return generateResponse(contactFacade.editCo(mes.getObject()), ResponseType.ACK);
        }
        return generateResponse("No such contact found", ResponseType.ERR);
    }

    private void eraseContactMessages(Contact contact) {
        List<Message> tyList = new ArrayList<>();
        Collections.copy(contact.getMessageList(), tyList);
        for (Message m : tyList) {
            messageFacade.remove(m);
        }
    }

    public ResponseObject processDevice(MsgPack<DeviceCo> mes, Device s) {
        Device dev = deviceFacade.findByPhone(mes.getObject().getPhonenumberId());
        Phonenumber p = new Phonenumber();
        p.setNumber(mes.getObject().getPhonenumberId());
        phoneFacade.create(p);
        p = phoneFacade.findByNumber(mes.getObject().getPhonenumberId());
        mes.getObject().setRealNumber(p);

        switch (mes.getAction()) {
            case NEW:
                if (dev != null) {
                    return generateResponse("Device already exists", ResponseType.ERR);
                }
                return generateResponse(deviceFacade.createCo(mes.getObject()), ResponseType.ACK);
            case DEL:
                if (dev == null) {
                    return generateResponse("Device does not exists", ResponseType.ERR);

                }
                return generateResponse(deviceFacade.removeCo(mes.getObject()), ResponseType.ACK);
            case REP:
                return generateResponse(deviceFacade.editCo(mes.getObject()), ResponseType.ACK);
        }
        return generateResponse("No such device found", ResponseType.ERR);
    }

    private ResponseObject generateResponse(String text, ResponseType resp) {
        ResponseObject t = new ResponseObject();
        t.setResponseType(resp);
        t.setText(text);
        return t;
    }

    public ResponseObject recognize(String encodedMessage, JSONBroker broker, Session s) {
        MsgPack extractedPack = null;
        try {
            extractedPack = broker.extractMessage(encodedMessage);
        } catch (JsonParseException ex) {
            ex.printStackTrace();
        }
        Device currentDevice = detector.getDeviceBySession(s);
        ResponseObject response;
        switch (extractedPack.getObjectType()) {
            case DEV:
                response = generateResponse("Not implemented like that", ResponseType.ERR);
                break;
            case MES:
                response = initializeMessageCreation(encodedMessage, currentDevice);
                break;
            case CON:
                response = initializeContactCreation(encodedMessage, currentDevice);
                break;
            default:
                response = generateResponse("Type is not correct or not implemented", ResponseType.ERR);
        }
        response.setHash(extractedPack.getHash());
        return response;
    }

    private ResponseObject initializeContactCreation(String encodedMessage, Device currentDevice) {
        Type collectionType;
        Gson gson = new Gson();
        collectionType = new TypeToken<MsgPack<ContactCo>>() {
        }.getType();
        MsgPack<ContactCo> extractedPackC = gson.fromJson(encodedMessage, collectionType);
        return processContact(extractedPackC, currentDevice);
    }

    private ResponseObject initializeMessageCreation(String encodedMessage, Device currentDevice) {
        Type collectionType;
        Gson gson = new Gson();
        collectionType = new TypeToken<MsgPack<MessageCo>>() {
        }.getType();
        MsgPack<MessageCo> extractedPackM = gson.fromJson(encodedMessage, collectionType);
        return processMessage(extractedPackM, currentDevice);
    }

    private ResponseObject initializeDeviceCreation(String encodedMessage, Device currentDevice) {
        Type collectionType;
        Gson gson = new Gson();
        collectionType = new TypeToken<MsgPack<DeviceCo>>() {
        }.getType();
        MsgPack<DeviceCo> extractedPackD = gson.fromJson(encodedMessage, collectionType);

        if (deviceFacade.findByPhone(extractedPackD.getObject().getPhonenumberId()) != null) {
            return generateResponse("Device already exists", ResponseType.ERR);
        }
        return processDevice(extractedPackD, currentDevice);
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

    private ResponseObject loginDevice(String message, Session session) {
        Phonenumber ph = phoneFacade.findByNumber(message);
        if (ph == null) {
            return generateResponse("No such device registered", ResponseType.ERR);
        }
        Device d = deviceFacade.findByPhone(ph.getNumber());
        DeviceStatus ds = new DeviceStatus(d, session);
        if (!detector.isOnline(ds)) {
            detector.setOnline(ds);
            return generateResponse("Logged in", ResponseType.ACK);
        } else {
            return generateResponse("Already logged in", ResponseType.ERR);
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
