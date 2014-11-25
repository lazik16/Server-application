package com.ropr.websockets;

import com.ropr.model.dao.UserDAOLocal;
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
import com.ropr.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ServerEndpoint("/anth")
@Stateless
public class SEndpoint {

    private final int ASC = 0;
    private final int DESC = 1;

    public static List<Session> authSessions = new ArrayList<>();

    @Resource
    ManagedExecutorService mes;

    public static String address = "apsync.duckdns.org";

    @EJB
    private UserDAOLocal userDao;

    @PersistenceUnit
    EntityManagerFactory enf;

    @OnMessage
    public String receiveMessage(String message, Session session) {
        message = message.replaceAll("\\s+", "");
        //////////////////PUBLIC SECTION//////////////////
        if (message.matches("registerUser[(](\\S*)[)]")) {
          // REGISTER
            return registerUser(message, session);
        } // LOGIN
        else if (message.matches("login[(]((\\S*),(\\S*))[)]")) {
            return loginUser(message, session);
        } // LOGOUT
        else if (message.matches("logout[(](\\S*)[)]")) {
            return logoutUser(message, session);
        }
        ////////////////AUTHORIZED SECTION//////////////////
        if (!testAuth(session)) {
            return "fail(You are not authorized)";
        } // GET DATABASE DATA (USER)
        else if (message.matches("getDB")) {
            sendData(session);
            return "success(Transmission complete)";
        } // GET SPECIFIC DATABASE DATA
        else if (message.matches("getDB[(]((ASC)|(DESC)),([1-9]|[0-9][0-9]*)[)]")) {
            return getData(message, session);
        } // GET SPECIFIC USER
        else if (message.matches("getUser(\\S*)")) {
            return getUser(message, session);
        }
        return "fail(Unknown command)";
    }

    private String logoutUser(String message, Session session) {
        String json = message.substring(7, message.length() - 1);
        User u = User.fromJSON(json);
        if (testAuth(session)) {
            authSessions.remove(session);
            return "success(" + u.getEmail() + " logged out)";
        } else {
            return "fail(" + u.getEmail() + " is not logged in)";
        }
    }

    private String getData(String message, Session session) {
        if (!testAuth(session)) {
            return "fail(Session not authorized)";
        }

        if (message.matches("getDB[(]ASC,\\S*")) {
            int num = Integer.parseInt(message.substring(10, message.length() - 1));
            sendData(session, ASC, num);
        } else {
            int num = Integer.parseInt(message.substring(11, message.length() - 1));
            sendData(session, DESC, num);
        }
        return "success(Transmission complete)";
    }

    @OnOpen
    public void open(Session session) {
        System.out.println(("Open session:" + session.getId()));
    }

    @OnClose
    public void close(Session session, CloseReason c) {
        authSessions.remove(session);
        System.out.println("Closing:" + session.getId());
    }

    private String registerUser(String message, Session session){

        return "Not implemented";
    }
    
    private String getUser(String message, Session session) {
        String userId = message.substring(8, message.length() - 1);
        User us = userDao.getUser(Integer.parseInt(userId));
        if (us != null) {
            return us.toJSON();
        } else {
            return "fail(User not found)";
        }
    }

    private boolean testAuth(Session s) {
        for (Session as : authSessions) {
            if (as.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private String loginUser(String message, Session session) {
        String fullname = message.substring(6, message.length() - 1);
        String[] s = fullname.split(",");

        User u = userDao.findByEmail(s[0]);
        if (u != null) {
            if (u.getPassword().equals(s[1])) {
                if (testAuth(session)) {
                    return "fail(Already logged in)";
                }
                authSessions.add(session);
                return "loggedAs(" + u.toJSON() + ")";
            } else {
                return "fail(Password not valid)";
            }
        } else {
            return "fail(User not found)";
        }
    }

    private void sendData(Session s) {
        List<User> userList = enf.createEntityManager().createNamedQuery("User.findAll").getResultList();

        for (User user : userList) {
            try {
                s.getBasicRemote().sendText(user.toJSON());
            } catch (IOException ei) {
                ei.printStackTrace();
            }
        }
    }

    private void sendData(Session s, int sort, int ammount) {
        List<User> userList = enf.createEntityManager().createNamedQuery("User.findAll").getResultList();
        ammount = userList.size() < ammount ? userList.size() : ammount;
        userList = userList.subList(0, ammount);

        if (sort == DESC) {
            Collections.sort(userList);
        }

        ammount = ammount <= userList.size() ? ammount : userList.size();

        for (int i = 0; i < ammount; i++) {
            try {
                s.getBasicRemote().sendText(userList.get(i).toJSON());
            } catch (IOException ei) {
                ei.printStackTrace();
            }
        }
    }
}
