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

import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author Stephen R. Williams
 * 
 * This model is used for submitting requests to this server for a projection of keys
 */
public class ProjectionRequest {
    //seed components. mostly Strings here taken from the JSON
    private List<String> serials;//identity of machine. start with "ff" byte to avoid rounding issues
    private String operator;//operator of machine
    private String heading;//future implementation
    private String gpsx;//future implementation
    private String gpsy;//future implementation
    private String crypto;//i.e. BTC
    private String fiat;//i.e. USD
    private List<String> denominations;
    private String start;//start time for dispensing
    private String end;//end time for dispensing
    private String frequency;//number of keys per time period
    
    public ProjectionRequest(String serial, String operator, String denomination, String start, String end) throws NoSuchAlgorithmException, DecoderException{
        //using default values hard-coded into the Seed class
        Seed seed = new Seed(serial,operator,denomination,start);
        this.serials.add(serial);
        this.operator=operator;
        this.heading=seed.getHeadingString();
        this.crypto=seed.getCryptoString();
        this.fiat=seed.getFiatString();
        this.denominations.add(denomination);
        this.start=start;
        this.end=end;
        this.frequency="05a0";//1440(dec) minutes in a day
    }

    public ProjectionRequest(List<String> serials, String operator, String heading, String gpsx, String gpsy, String crypto, String fiat, List<String> denominations, String start, String end, String frequency) {
        this.serials = serials;
        this.operator = operator;
        this.heading = heading;
        this.gpsx = gpsx;
        this.gpsy = gpsy;
        this.crypto = crypto;
        this.fiat = fiat;
        this.denominations = denominations;
        this.start = start;
        this.end = end;
        this.frequency = frequency;
    }

    public List<String> getSerials() {
        return serials;
    }

    public String getOperator() {
        return operator;
    }

    public String getHeading() {
        return heading;
    }

    public String getGpsx() {
        return gpsx;
    }

    public String getGpsy() {
        return gpsy;
    }

    public String getCrypto() {
        return crypto;
    }

    public String getFiat() {
        return fiat;
    }

    public List<String> getDenominations() {
        return denominations;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getFrequency() {
        return frequency;
    }

}