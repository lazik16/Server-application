/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import com.ropr.modelCo.ContactCo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dominik
 */
@Local
public interface ContactFacadeLocal {

    void create(Contact contact);

    void edit(Contact contact);

    void remove(Contact contact);
    
    String createCo(ContactCo contact);

    String editCo(ContactCo contact);
    
    List<Contact> findAll();
    
    Contact findByNick(String nick);
    
    Contact findByPhone(String phone, Device d);
    
    
}
