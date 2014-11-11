package websockets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.CloseReason;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import model.User;
import model.UserDAOLocal;
import json.ObjectJSON;

@ServerEndpoint("/echo")
@Stateless
public class EchoEndpoint {

    private final int ASC = 0;
    private final int DESC = 1;

    @Resource
    ManagedExecutorService mes;

    public static String address = "apsync.duckdns.org";

    @EJB
    private UserDAOLocal userDao;

    @PersistenceUnit
    EntityManagerFactory enf;

    @OnMessage
    public String receiveMessage(String message, Session session) {
        System.out.println(("Received : " + message + ", session:" + session.getId()));
        message = message.replaceAll("\\s+", "");
        
        if (message.matches("addUser[(]((\\S*),(\\S*))[)]")) {
            String fullname;
            fullname = message.substring(8,message.length()-1);
            String[] s = fullname.split(",");
            User u = new User();
            u.setUserName(s[0]+" "+s[1]);
            userDao.addUser(u);
            return u.getUserName()+" added.";
            
        } else if (message.matches("getDB")) {
            sendData(session);
            return "Transmission complete.";
        } else if (message.matches("getDB[(]((ASC)|(DESC)),([1-9]|[0-9][0-9]*)[)]")) {
            if (message.matches("getDB[(]ASC,\\S*")) {
                int num = Integer.parseInt(message.substring(10, message.length() - 1));
                sendData(session, ASC, num);
            } else {
                int num = Integer.parseInt(message.substring(11, message.length() - 1));
                sendData(session, DESC, num);
            }
            return "Transmission complete.";
        } else if (message.matches("getUser(\\S*)")) {
            String userId = message.substring(8, message.length() - 1);
            //if (message.matches("getUser([(]([0-9]|[1-9][0-9]*)[)])")){
            User us = userDao.getUser(Integer.parseInt(userId));
            if (us != null) {
                ObjectJSON u = new ObjectJSON(us, ObjectJSON.Type.USER);
                return u.toJSON();
            } else {
                return ObjectJSON.Type.USER + " not found";
            }

        } else {
            return "Unknown command";
        }
    }

    private User addUser(String message) {
        String name = "";
        for (int i = 0; i < message.length(); i++) {
            if (String.valueOf(message.charAt(i)).equals(",")) {
                break;
            }
            name += String.valueOf(message.charAt(i));
        }

        User u = new User();
        u.setUserName(name);
        userDao.addUser(u);
        return u;
    }

    @OnOpen
    public void open(Session session) {
        System.out.println(("Open session:" + session.getId()));
    }

    @OnClose
    public void close(Session session, CloseReason c) {
        System.out.println("Closing:" + session.getId());
    }

    private void sendData(Session s) {
        List<User> userList = enf.createEntityManager().createNamedQuery("User.findAll").getResultList();

        for (User user : userList) {
            ObjectJSON u = new ObjectJSON(user, ObjectJSON.Type.USER);
            try {
                s.getBasicRemote().sendText(u.toJSON());
            } catch (IOException ei) {
                ei.printStackTrace();
            }
        }
    }

    private void sendData(Session s, int sort, int ammount) {
        List<User> userList = enf.createEntityManager().createNamedQuery("User.findAll").getResultList();

        if (sort == DESC) {
            Collections.sort(userList);
        }

        ammount = ammount <= userList.size() ? ammount : userList.size();

        for (int i = 0; i < ammount; i++) {
            ObjectJSON u = new ObjectJSON(userList.get(i), ObjectJSON.Type.USER);
            try {
                s.getBasicRemote().sendText(u.toJSON());
            } catch (IOException ei) {
                ei.printStackTrace();
            }
        }
    }
}
