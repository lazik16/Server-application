/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import model.User;

/**
 *
 * @author Dominik
 */
public class Handler {

    public static Object getResult(String toDecode) {

        for (int i = 0; i < toDecode.length(); i++) {
            if (getAt(toDecode,i) == "#") {
                String ver = toDecode.substring(i+1,i+6);
                switch(ver){
                    case "user": 
                        ObjectJSON u = ObjectJSON.fromJSON(toDecode.substring(i+7));
                        return u;
                }
            }
        }
        return null;
    }

    private static String getAt(String s, int i) {
        return Character.toString(s.charAt(i));

    }
}
