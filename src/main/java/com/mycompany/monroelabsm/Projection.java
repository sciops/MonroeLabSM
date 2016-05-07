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

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author Stephen R. Williams 
 * This class generates keys for given time and seed values.
 *
 */
public class Projection {

    //list of keys generated
    private List<BoxKey> keys;

    public Projection(ProjectionRequest request) throws DecoderException, NoSuchAlgorithmException, IOException {
        keys = new ArrayList();
        
        //temp vars to hold values for each seed. most are the same for all keys
        byte[] serial = new byte[13];
        byte[] operator = B58.hexToBytes(request.getOperator());
        byte[] heading = B58.hexToBytes(request.getHeading());//future implementation
        byte[] gpsx = B58.hexToBytes(request.getGpsx());//future implementation
        byte[] gpsy = B58.hexToBytes(request.getGpsy());//future implementation
        byte[] crypto = B58.hexToBytes(request.getCrypto());//i.e. BTC
        byte[] fiat = B58.hexToBytes(request.getFiat());//i.e. USD
        byte[] denomination = new byte[1];
        byte[] time = new byte[4];
        
        //make seeds from the request. we'll need a key for every serial, denomination and time value
        int start = Bitwise.getInt(B58.hexToBytes(request.getStart()),0);
        int end = Bitwise.getInt(B58.hexToBytes(request.getEnd()),0);
        int freq = Bitwise.getInt(B58.hexToBytes(request.getFrequency()),0);
        for (String denomination_s : request.getDenominations()) {
            denomination=B58.hexToBytes(denomination_s);
            for (int i = start; i <= end; i += freq) {
                time = B58.toByteArray(i);
                for (String s : request.getSerials()) {
                    serial = B58.hexToBytes(s);
                    //System.out.println("Seed values: "+serial+operator+heading+gpsx+gpsy+crypto+fiat+denomination+time);
                    Seed seed = new Seed(serial,operator,heading,gpsx,gpsy,crypto,fiat,denomination,time);
                    BoxKey key = new BoxKey(seed);
                    //System.out.println("List keys: "+keys);
                    //System.out.println("Adding key: "+key);
                    keys.add(key);
                }
            }
        }
    }

    public List<BoxKey> getKeys() {
        return keys;
    }    
    
    public List<BoxKeyView> getViews() {
        List<BoxKeyView> views = new ArrayList();
        for (BoxKey key : keys)
            views.add(new BoxKeyView(key));        
        return views;
    }
}