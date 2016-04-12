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

import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author Stephen R. Williams
 */
public class ProjectionService {

    public Projection projection(ProjectionRequest request) throws DecoderException, NoSuchAlgorithmException {
        return new Projection(request);
    }

    public Projection projection(String json) throws JsonPojoMismatchException, DecoderException, NoSuchAlgorithmException {
        ProjectionRequest request = new Gson().fromJson(json, ProjectionRequest.class);
        if (request == null) {
            throw new JsonPojoMismatchException();
        }
        return projection(request);//use above method
    }

    public Projection projection(String serial, String operator, String denomination, String start, String end) throws DecoderException, NoSuchAlgorithmException {
        return projection(new ProjectionRequest(serial,operator,denomination,start,end));//use above method
    }
    
}
