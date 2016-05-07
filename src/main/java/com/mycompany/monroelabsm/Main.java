/*
 * The MIT License
 *
 * Copyright 2016 Stephen R. Williams.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 * This main class gets things started. Netbeans makes it really easy to deploy
 * to AWS Tomcat servers (click of the run button) but the process is a little
 * clunkier for far-jars. So I am deploying this on a Tomcat server instead of
 * using Spark Framework's embedded Jetty. I'm also including a pretty basic
 * AngularJS front-end
 */
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.DecoderException;
import static spark.Spark.*;
import static spark.Spark.staticFileLocation;
import spark.servlet.SparkApplication;

public class Main implements SparkApplication {

    final String VERSION = "v1.008";

    //must move all verbs to init method, out of main
    @Override
    public void init() {
        BoxKeyService bks = new BoxKeyService();
        BoxKeyController bkc = new BoxKeyController(bks);
        ProjectionController pc = new ProjectionController(new ProjectionService());
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });
        get("/hello", (req, res) -> "Hello World. This is the /hello response in MonroeLabSM " + VERSION);
        get("/hello/:name", (req, res) -> "Hello " + req.params(":name") + ". This is the /hello response in MonroeLabSM. " + VERSION);
        staticFileLocation("/public"); //http://sparkjava.com/documentation.html#static-files
        
        try {
            bks.reset();
        } catch (NoSuchAlgorithmException | DecoderException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }
}
