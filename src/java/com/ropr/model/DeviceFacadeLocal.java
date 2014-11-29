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
public interface DeviceFacadeLocal {

    void create(Device device);

    void edit(Device device);

    void remove(Device device);

    Device find(Object id);

    List<Device> findAll();

    List<Device> findRange(int[] range);

    int count();
    
    Device findByPhone(Phonenumber number);
    
}
