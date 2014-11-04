/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import model.UserDAOLocal;

/**
 *
 * @author Dominik
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    @EJB
    private UserDAOLocal userDao;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String userIdStr = request.getParameter("idUser");
        String username = request.getParameter("username");
       
        int idUser = 0;
        if(userIdStr!=null && !userIdStr.equals(""))
            idUser = Integer.parseInt(userIdStr);
        
        User user = new User();
        user.setUserName(username);
        
        if("Add".equalsIgnoreCase(action)){
            userDao.addUser(user);
        }else if ("Edit".equalsIgnoreCase(action)){
            userDao.editUser(user);
        }else if ("Delete".equalsIgnoreCase(action)){
            userDao.deleteStudent(idUser);
        }else if ("Search".equalsIgnoreCase(action)){
            userDao.getUser(idUser);
        }
        request.setAttribute("user", user);
        request.setAttribute("allUsers", userDao.getAllUsers());
        request.getRequestDispatcher("userInfo.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
