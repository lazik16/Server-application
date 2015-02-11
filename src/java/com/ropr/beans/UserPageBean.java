/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.beans;

import com.ropr.model.Device;
import com.ropr.model.Phonenumber;
import com.ropr.model.User;
import com.ropr.model.UserFacadeLocal;
import com.ropr.utility.StaticVariables;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "userpage")
@ViewScoped
public class UserPageBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    UserFacadeLocal userDao;
    private User current;
    private List<Device> deviceList;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();

    @PostConstruct
    public void init() {
        current = (User) facesContext.getExternalContext().getSessionMap().get(StaticVariables.USER);
        deviceList =  userDao.findByEmail(current.getEmail()).getDeviceList();
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public Phonenumber getDevNumber(Device d) {
        return d.getPhonenumberId();
    }
}
