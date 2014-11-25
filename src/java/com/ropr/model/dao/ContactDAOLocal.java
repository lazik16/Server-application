package com.ropr.model.dao;

import java.util.List;
import javax.ejb.Local;
import com.ropr.model.Contact;
import com.ropr.model.Phonenumber;
import com.ropr.model.User;

/**
 *
 * @author Dominik
 */
@Local
public interface ContactDAOLocal {

    void addContact(Contact contact);

    void editContact(Contact contact);

    Contact getContact(int contact);

    List<Contact> getAllContacts();

    void deleteAllContacts();

    void deleteContact(int id);

    Contact findContactByUserNumber(User user, Phonenumber number);
}
