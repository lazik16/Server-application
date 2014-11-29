/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dominik
 */
@Local
public interface PhonenumberFacadeLocal {

    void create(Phonenumber phonenumber);

    void edit(Phonenumber phonenumber);

    void remove(Phonenumber phonenumber);

    Phonenumber find(Object id);

    List<Phonenumber> findAll();

    List<Phonenumber> findRange(int[] range);

    int count();
    
    Phonenumber findByNumber(String number);
    
}
