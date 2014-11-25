/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model.dao;

import com.ropr.model.Phonenumber;
import javax.ejb.Local;
import com.ropr.model.Phonenumber;
import java.util.List;

/**
 *
 * @author Dominik
 */
@Local
public interface PhoneDAOLocal {

    void addPhonenumber(Phonenumber numbr);

    void editPhonenumber(Phonenumber numbr);

    Phonenumber getPhonenumber(int numbr);

    List<Phonenumber> getAllPhonenumbers();

    void deleteAllPhonenumbers();

    void deletePhonenumber(int id);
}
