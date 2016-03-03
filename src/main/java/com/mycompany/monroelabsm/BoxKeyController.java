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
 */
import java.util.List;
import static spark.Spark.*;
import com.mycompany.monroelabsm.JsonUtil;

public class BoxKeyController {

    //http://www.mscharhag.com/java/building-rest-api-with-spark
    //bks: Service which will do all data retrieval/manipulation work
    public BoxKeyController(BoxKeyService service) {
        get("/keys", (req, res) -> service.findAllBoxKeys(), JsonUtil.json());
        get("/key/:id", (req, res) -> {
            String id_s = req.params(":id");
            long id = Long.parseLong(id_s);
            BoxKey key = service.findById(id);
            if (key != null) {
                return key;
            }
            res.status(404);
            return new ResponseError("No key with id '%s' found", id_s);
        }, JsonUtil.json());
        post("/key", (req, res) -> service.saveBoxKey(req.queryParams("key")), JsonUtil.json());
        put("/key", (req,res) -> service.updateBoxKey(req.queryParams("key")), JsonUtil.json());
        delete("/key/:id", (req,res)-> service.deleteBoxKeyById(Long.parseLong(req.queryParams(":id"))), JsonUtil.json());
        delete("/keys", (req, res) -> service.deleteAllBoxKeys());

        exception(NotFoundException.class, (e, request, response) -> {
            response.status(404);
            response.body("Resource not found");
        });
        exception(AlreadyExistsException.class, (e, request, response) -> {
            response.status(409);
            response.body("Resource already exists");
        });
    }
}
