/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.monroelabsm;

/**
 *
 * @author Stephen R. Williams
 * 
 * Started with git clone of SparkTomcat by Leonan Luppi
 * https://github.com/leonanluppi/SparkTomcat
 * https://github.com/leonanluppi/SparkTomcat/blob/master/src/main/java/com/sparkjava/tomcat/Main.java
 */
import static spark.Spark.*;
import spark.servlet.SparkApplication;

public class Main implements SparkApplication {
    //must move all verbs to init method, out of main
    public void init() {
        new BoxKeyController(new BoxKeyService());
        get("/hello", (req, res) -> "Hello World. This is the /hello response in MonroeLabSM");
        get("/hello123", (req, res) -> "Hello World, test 123. This is the /hello123 response in MonroeLabSM");
        //get("/listkeys", (req, res) -> bkc.listAllBoxKeys(req, res));
    }   
    
    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }
}
