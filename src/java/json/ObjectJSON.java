/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import com.google.gson.Gson;

/**
 *
 * @author Dominik
 */
public class ObjectJSON {
    private Type type;
    private Object object;
    
    
    public enum Type{
        SMS, USER, DEVICE, ERROR
    }
    
    public ObjectJSON(Object o, Type type) {
        this.object = o;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
    
    public String toJSON(){
        Gson gson = new Gson();
        String parsed = gson.toJson(this);
        return parsed;
    }
    
    public static ObjectJSON fromJSON(String s){
        Gson gson = new Gson();
        ObjectJSON obj = gson.fromJson(s, ObjectJSON.class);
        return obj;
    }
}
