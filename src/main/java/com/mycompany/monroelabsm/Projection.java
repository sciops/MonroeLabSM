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
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author Stephen R. Williams This class generates keys for given time and seed
 * values.
 */
public class Projection {

    //list of keys generated
    private List<BoxKey> keys;
    private List<Seed> seeds;

    public Projection(ProjectionRequest request) throws DecoderException {
        byte[] serial = new byte[13];
        byte[] opnum = B58.hexToBytes(request.getOperatorNo());
        byte[] gpsHeading = B58.hexToBytes(request.getGpsHeading());//future implementation
        byte[] gpsLocX = B58.hexToBytes(request.getGpsLocX());//future implementation
        byte[] gpsLocY = B58.hexToBytes(request.getGpsLocY());//future implementation
        byte[] cryptoCurrencyType = B58.toByteArray(request.getCryptoCurrencyType());//i.e. BTC
        byte[] fiatCurrencyType = B58.toByteArray(request.getFiatCurrencyType());//i.e. USD
        byte[]denom = new byte[1];
        byte[]time = new byte[4];
        
        //make seeds from the request. we'll need a key for every serial, denomination and time value
        for (Byte denom_B : request.getDenominations()) {
            denom[0]=denom_B.byteValue();
            for (int i = request.getDispenseStart(); i <= request.getDispenseEnd(); i += request.getDispenseFrequency()) {
                time = B58.toByteArray(i);
                for (String s : request.getSerialNo()) {
                    serial = B58.hexToBytes(s);
                    Seed seed = new Seed(serial,opnum,gpsHeading,gpsLocX,gpsLocY,cryptoCurrencyType,fiatCurrencyType,denom,time);
                    seeds.add(seed);
                    BoxKey key = new BoxKey(seed);
                    keys.add(key);
                }
            }
        }
    }

    public List<BoxKey> getKeys() {
        return keys;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }
    
    
}
