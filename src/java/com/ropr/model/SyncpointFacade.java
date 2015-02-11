/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik
 */
@Stateless
public class SyncpointFacade extends AbstractFacade<Syncpoint> implements SyncpointFacadeLocal {
    @PersistenceContext(unitName = "SMSCorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SyncpointFacade() {
        super(Syncpoint.class);
    }

    @Override
    public List<Syncpoint> findByDevice(Device d) {
        List<Syncpoint> syncs;
        syncs = em.createNamedQuery("Syncpoint.findByDevice").setParameter("device", d).getResultList();
        return syncs;
    }
    
}
