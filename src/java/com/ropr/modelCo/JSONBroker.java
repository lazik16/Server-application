/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.modelCo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ropr.model.Contact;
import com.ropr.model.Message;
import com.ropr.modelCo.MsgPack.ActionType;
import com.ropr.modelCo.MsgPack.ObjectType;
import com.ropr.sync.ResponseObject;
import java.lang.reflect.Type;

/**
 *
 * @author Dominik
 */
public class JSONBroker {
    private final Gson GSON;

    public JSONBroker() {
        GSON = new Gson();
    }
    
    public String messageToJson(MsgPack pack){
        return GSON.toJson(pack);
    }
    
    public String responseToJSon(ResponseObject response){
        return GSON.toJson(response);
    }

    public MsgPack createMessagePack(ObjectType t, MessageCo m, ActionType a){
        MsgPack<MessageCo> msgPack = new MsgPack<>();
        msgPack.setObject(m);
        msgPack.setAction(a);
        msgPack.setObjectType(t);
        msgPack.setHash(m.hashCode());
        return msgPack;
    }
    
    public MsgPack createContactPack(ObjectType t, ContactCo c, ActionType a){
        MsgPack<ContactCo> msgPack = new MsgPack<>();
        msgPack.setObject(c);
        msgPack.setAction(a);
        msgPack.setObjectType(t);
        msgPack.setHash(c.hashCode());
        return msgPack;
    }
    
    public MsgPack createDevicePack(ObjectType t, DeviceCo c, ActionType a){
        MsgPack<DeviceCo> msgPack = new MsgPack<>();
        msgPack.setObject(c);
        msgPack.setAction(a);
        msgPack.setObjectType(t);
        msgPack.setHash(c.hashCode());
        return msgPack;
    }
    
    public MsgPack extractMessage(String json){
        MsgPack mes = null;
        Type type2 = new TypeToken<MsgPack<MessageCo>>() {}.getType();
        mes = GSON.fromJson(json,type2);
        return mes;
    }
    
    public MsgPack extractContact(String json){
        MsgPack mes = null;
        Type type2 = new TypeToken<MsgPack<ContactCo>>() {}.getType();
        mes = GSON.fromJson(json,type2);
        return mes;
    } 
    
    public MsgPack extractDevice(String json){
        MsgPack mes = null;
        Type type2 = new TypeToken<MsgPack<DeviceCo>>() {}.getType();
        mes = GSON.fromJson(json,type2);
        return mes;
    } 
    
    public ResponseObject extractResponse(String json){
        ResponseObject response = null;
        Type type2 = new TypeToken<ResponseObject>() {}.getType();
        response = GSON.fromJson(json,type2);
        return response;
    } 
    
    
}
