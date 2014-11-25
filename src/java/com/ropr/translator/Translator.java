/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ropr.translator;

import java.io.IOException;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;

/**
 *
 * @author Dominik
 */
public class Translator {
    
    public static void main(String[] args) {

        //registerUser[(](\\S*)[)]
        
        String json2 = "\"registerUser\"{\"type\":\"USER\","
                + "\"object\":{\"idUser\":1,\"userName\":\"První\"}}";

        doLex(json2);
        doSyn(json2);
    }
    
    public static void doSyn(String json){
        JSONParserTests tests = new JSONParserTests();
        try {
        //JSONParser p = tests.createParser(json);
        JSONTree pt = tests.createTreeParser(json);
        
        } catch (IOException | RecognitionException io) {
            io.printStackTrace();
        }
    }
    
    public static void doLex(String json){
        CharStream ch = new ANTLRStringStream(json);
        JSONLexer lex = new JSONLexer(ch);
        boolean run = true;
        while (!lex.failed() && run) {
            Token t = lex.nextToken();
            if(t.getText()==null)
               run=false;
            System.out.println(t.getText());
        }
        if(lex.failed())
            System.out.println("Lex failed");
        else System.out.println("Lex succeded");
    }

}
