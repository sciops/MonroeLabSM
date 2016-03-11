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
import static spark.Spark.staticFileLocation;
import spark.servlet.SparkApplication;

public class Main implements SparkApplication {

    //must move all verbs to init method, out of main
    @Override
    public void init() {
        //http://sparkjava.com/documentation.html#static-files
        staticFileLocation("/public"); 

        BoxKeyController bkc = new BoxKeyController(new BoxKeyService());
        
        //get("/", (req, res) -> "<html>Hello World. This is the / (root) response in MonroeLabSM <br><br><img src=\"http://img.memecdn.com/square-root_o_895898.jpg\" /></html>"); 

        get("/hello", (req, res) -> "Hello World. This is the /hello response in MonroeLabSM");
        //get("/hello123", (req, res) -> "Hello World, test 123. This is the /hello123 response in MonroeLabSM");

    }

    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }
}
