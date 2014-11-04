/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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

    void deleteStudent(int idUser);
    
}
