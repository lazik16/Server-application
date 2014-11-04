/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik
 */
@Stateless
public class UserDAO implements UserDAOLocal {
    @PersistenceContext
    private EntityManager em;
    
    
    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void editUser(User user) {
        em.merge(user);
    }


    @Override
    public User getUser(int user) {
        return em.find(User.class, user);
    }

    @Override
    public List<User> getAllUsers() {
        return em.createNamedQuery("User.findAll").getResultList();
    }

    @Override
    public void deleteStudent(int userId) {
        em.remove(getUser(userId));
    }
    
    
}