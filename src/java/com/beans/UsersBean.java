/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.User;
import model.UserDAOLocal;

/**
 *
 * @author Dominik
 */
@ManagedBean
@SessionScoped
public class UsersBean {
    @EJB
    private UserDAOLocal userDao;
    
    private List<User> users;

    public UsersBean() {
    }
    
    public List<User> getUsers(){
        users = userDao.getAllUsers();
        return users;
    }

   
    
    
}
