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
 * 
 * This main class gets things started. 
 * Netbeans makes it really easy to deploy to AWS Tomcat servers (click of the run button) but the process is a little clunkier for far-jars.
 * So I am deploying this on a Tomcat server instead of using Spark Framework's embedded Jetty.
 * I'm also including a pretty basic AngularJS front-end
 */
import static spark.Spark.*;
import static spark.Spark.staticFileLocation;
import spark.servlet.SparkApplication;

public class Main implements SparkApplication {
    final String VERSION = "v1.006";
    //must move all verbs to init method, out of main
    @Override
    public void init() {
        BoxKeyController bkc = new BoxKeyController(new BoxKeyService());
        ProjectionController pc = new ProjectionController(new ProjectionService());
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });
        get("/hello", (req, res) -> "Hello World. This is the /hello response in MonroeLabSM "+VERSION);
        get("/hello/:name", (req, res) -> "Hello "+req.params(":name")+". This is the /hello response in MonroeLabSM. "+VERSION);
        staticFileLocation("/public"); //http://sparkjava.com/documentation.html#static-files  
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }
}
