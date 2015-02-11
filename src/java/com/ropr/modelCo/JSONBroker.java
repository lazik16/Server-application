/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.modelCo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    public String contactToJson(MsgPack pack){
        return GSON.toJson(pack);
    }
    public String deviceToJson(MsgPack pack){
        return GSON.toJson(pack);
    }
    
    public MsgPack extractObject(String json){
        MsgPack mes = null;
        Type type2 = new TypeToken<MsgPack<MessageCo>>() {}.getType();
        mes = GSON.fromJson(json,type2);
        return mes;
    }
    
    
    
}
