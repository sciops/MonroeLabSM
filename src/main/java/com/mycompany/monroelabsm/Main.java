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
        BoxKeyController bkc = new BoxKeyController(new BoxKeyService());
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });
        get("/hello", (req, res) -> "Hello World. This is the /hello response in MonroeLabSM. 3");
        staticFileLocation("/public"); //http://sparkjava.com/documentation.html#static-files  
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }
}
