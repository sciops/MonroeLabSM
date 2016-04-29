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
import java.util.List;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author Stephen R. Williams
 */
public class ProjectionService {

    public List<BoxKeyView> projection(ProjectionRequest request) throws DecoderException, NoSuchAlgorithmException {
        Projection projection = new Projection(request);
        return projection.getViews();
    }

    public List<BoxKeyView> projection(String json) throws JsonPojoMismatchException, DecoderException, NoSuchAlgorithmException {
        //TODO: validate for empty strings , etc.
        ProjectionRequest request = new Gson().fromJson(json, ProjectionRequest.class);
        
        //remake this object using another constructor so that defaults are used.
        ProjectionRequest requestWithDefaults = new ProjectionRequest(request.getSerials(), request.getOperator(), request.getDenominations(), request.getStart(), request.getEnd());
        //TODO:replace this mess with a set or select case of some kind
        //need to check if any of the request's parameters are null.
        if (request == null) throw new JsonPojoMismatchException();
        else if (request.getCrypto()==null) request = requestWithDefaults;
        else if (request.getFiat()==null) request = requestWithDefaults;
        else if (request.getHeading()==null) request = requestWithDefaults;
        else if (request.getGpsx()==null) request = requestWithDefaults;
        else if (request.getGpsy()==null) request = requestWithDefaults;
        else if (request.getFrequency()==null) request = requestWithDefaults;
        //also check if any parameters are empty strings
        if (request.getCrypto().equalsIgnoreCase("")) request = requestWithDefaults;
        else if (request.getFiat().equalsIgnoreCase("")) request = requestWithDefaults;
        else if (request.getHeading().equalsIgnoreCase("")) request = requestWithDefaults;
        else if (request.getGpsx().equalsIgnoreCase("")) request = requestWithDefaults;
        else if (request.getGpsy().equalsIgnoreCase("")) request = requestWithDefaults;
        else if (request.getFrequency().equalsIgnoreCase("")) request = requestWithDefaults;

        return projection(request);//use above method
    }

    public List<BoxKeyView> projection(List<String> serials, String operator, List<String> denominations, String start, String end) throws DecoderException, NoSuchAlgorithmException {
        return projection(new ProjectionRequest(serials, operator, denominations, start, end));//use above method
    }

}
