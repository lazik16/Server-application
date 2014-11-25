/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.json;

import com.google.gson.Gson;
import com.ropr.model.User;

/**
 *
 * @author Dominik
 */
public class ObjectJSON {
    
    public static String toJSON(Object u){
        Gson gson = new Gson();
        String parsed = gson.toJson(u);
        System.out.println(parsed);
        return "..";
    }
    
    public static ObjectJSON fromJSON(String s){
        Gson gson = new Gson();
        ObjectJSON obj = gson.fromJson(s, ObjectJSON.class);
        return obj;
    }
}
