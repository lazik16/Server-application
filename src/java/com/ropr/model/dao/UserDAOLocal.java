/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model.dao;

import com.ropr.model.Contact;
import com.ropr.model.Phonenumber;
import com.ropr.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dominik
 */
@Local
public interface UserDAOLocal {

    void addUser(User user);

    void editUser(User user);

    User getUser(int user);

    List<User> getAllUsers();

    void deleteAllUsers();

    void deleteUser(int id);
    
    User findByEmail(String email);
    
    List<Contact> getContacts(User u); 
    
    void addNumber(Phonenumber number,User u);
    
    List<Phonenumber> getPhoneNumbers(User u);
}
