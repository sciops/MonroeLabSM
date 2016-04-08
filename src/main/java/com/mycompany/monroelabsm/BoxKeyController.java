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
 * //http://www.mscharhag.com/java/building-rest-api-with-spark
 * 
 * This controller provides API endpoints for the front-end.
 */
import static spark.Spark.*;

public class BoxKeyController {
    //BoxKeyService is a service class which will do all data retrieval/manipulation work
    public BoxKeyController(BoxKeyService service) {
        //search for key by id. TODO: still implemented, but need to return the id in new endpoint.
        get("/key/:id", (req, res) -> {
            BoxKey key = service.findById(req.params(":id"));
            if (key != null) {
                return key;//found it            
            }
            res.status(404);
            return new ResponseError("404: No key with that id found");
        }, JsonUtil.json());
        //get all the keys in memory.
        get("/keys", (req, res) -> service.findAllBoxKeys(), JsonUtil.json());
        //post a new key
        post("/key/*", (req, res) -> service.saveBoxKey(req.splat()[0]), JsonUtil.json());
        //put changes to a key
        put("/key/*", (req, res) -> service.updateBoxKey(req.splat()[0]), JsonUtil.json());
        //remove a key by id
        delete("/key/:id", (req, res) -> {
            String id = req.params(":id");
            BoxKey keyToDel = service.findById(id);
            if (keyToDel != null) {//juggle and return the key after deleting it
                BoxKey temp = new BoxKey(keyToDel);
                service.deleteBoxKeyById(id);
                return temp;
            }
            res.status(404);
            return new ResponseError("404: No key with that id found");
        }, JsonUtil.json());
        //delete them all
        delete("/keys", (req, res) -> service.deleteAllBoxKeys(), JsonUtil.json());
        //reset the keys to hardcoded defaults
        get("/reset", (req, res) -> service.reset(), JsonUtil.json());
        post("/reset", (req, res) -> service.reset(), JsonUtil.json());
        //endpoint to project out a list of keys
        post("/projection/*", (req, res) -> service.projection(req.splat()[0]), JsonUtil.json());

        exception(NotFoundException.class, (e, req, res) -> {
            res.status(404);
            res.body("Resource not found");
        });
        exception(AlreadyExistsException.class, (e, req, response) -> {
            response.status(409);
            response.body("Resource already exists");
        });
    }
}
