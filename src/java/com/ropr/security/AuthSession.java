/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.security;

import com.ropr.model.User;
import javax.websocket.Session;

/**
 *
 * @author Dominik
 */
public class AuthSession {
    private Session session;
    private User user;

    public AuthSession(Session session, User user) {
        this.session = session;
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
