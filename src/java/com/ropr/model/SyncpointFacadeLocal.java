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
public interface SyncpointFacadeLocal {

    void create(Syncpoint syncpoint);

    void edit(Syncpoint syncpoint);

    void remove(Syncpoint syncpoint);

    Syncpoint find(Object id);

    List<Syncpoint> findAll();

    List<Syncpoint> findRange(int[] range);
    
    List<Syncpoint> findByDevice(Device d);

    int count();
    
}
