package websockets;

import java.io.IOException;
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
//import org.jboss.logging.Logger;

@ServerEndpoint("/echo")
@Stateless
public class EchoEndpoint {

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

        if (message.equals("getDB")) {
            sendData(session);
            return "Transmitted";
        } else {
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
            return "User -" + u.getUserName() + "- added, proceed to >> http://" + address + ":8080/HelloServlet";
        }
    }

    @OnOpen
    public void open(Session session) {
        System.out.println(("Open session:" + session.getId()));
        final Session s = session;
        mes.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 3; i++) {
                        Thread.sleep(10000);
                        s.getBasicRemote().sendText("Message from server");
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClose
    public void close(Session session, CloseReason c) {
        System.out.println("Closing:" + session.getId());
    }
    
    private void sendData(Session s){
        List<User> userList = enf.createEntityManager().createNamedQuery("User.findAll").getResultList();

        try{
            for (User user : userList) {
                s.getBasicRemote().sendText(user.getIdUser()+", "+user.getUserName());
            }
        }catch(IOException ei){
            ei.printStackTrace();
        }
    }
}
