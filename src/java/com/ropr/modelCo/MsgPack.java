/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.modelCo;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Dominik
 */
public class MsgPack<T> {

    @Expose
    private ActionType action;
    @Expose
    private ObjectType objectType;
    @Expose
    private T object;
    @Expose
    private int hash;

    public static enum ActionType {

        NEW, REP, DEL
    }

    public static enum ObjectType {

        DEV, CON, MES
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }
}
