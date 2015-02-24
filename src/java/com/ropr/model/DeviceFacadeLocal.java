/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import com.ropr.modelCo.DeviceCo;
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
    
    String createCo(DeviceCo device);

    String editCo(DeviceCo device);

    String removeCo(DeviceCo device);

    Device findByPhone(String number);
    
}
