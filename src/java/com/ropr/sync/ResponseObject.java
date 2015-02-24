/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.sync;

import com.google.gson.annotations.Expose;
import com.ropr.modelCo.MsgPack;
import com.ropr.websockets.SEndpoint.ResponseType;

/**
 *
 * @author Dominik
 */
public class ResponseObject{
    
    @Expose
    private String text;
    @Expose
    private ResponseType responseType;
    @Expose
    private int hash;

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public ResponseObject(ResponseType ResponseType) {
        this.responseType = ResponseType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ResponseObject() {
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
