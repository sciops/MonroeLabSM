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
    private short crypto;//i.e. BTC
    private short fiat;//i.e. USD
    private List<Byte> denominations;
    private int start;//start time for dispensing
    private int dispenseEnd;//end time for dispensing
    private int frequency;//number of keys per time period

    public List<String> getSerialNo() {
        return serials;
    }

    public void setSerialNo(List<String> serialNo) {
        this.serials = serialNo;
    }

    public String getOperatorNo() {
        return operator;
    }

    public void setOperatorNo(String operatorNo) {
        this.operator = operatorNo;
    }

    public String getGpsHeading() {
        return heading;
    }

    public void setGpsHeading(String gpsHeading) {
        this.heading = gpsHeading;
    }

    public String getGpsLocX() {
        return gpsx;
    }

    public void setGpsLocX(String gpsLocX) {
        this.gpsx = gpsLocX;
    }

    public String getGpsLocY() {
        return gpsy;
    }

    public void setGpsLocY(String gpsLocY) {
        this.gpsy = gpsLocY;
    }

    public short getCryptoCurrencyType() {
        return crypto;
    }

    public void setCryptoCurrencyType(short cryptoCurrencyType) {
        this.crypto = cryptoCurrencyType;
    }

    public short getFiatCurrencyType() {
        return fiat;
    }

    public void setFiatCurrencyType(short fiatCurrencyType) {
        this.fiat = fiatCurrencyType;
    }

    public List<Byte> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<Byte> denominations) {
        this.denominations = denominations;
    }

    public int getDispenseStart() {
        return start;
    }

    public void setDispenseStart(int dispenseStart) {
        this.start = dispenseStart;
    }

    public int getDispenseEnd() {
        return dispenseEnd;
    }

    public void setDispenseEnd(int dispenseEnd) {
        this.dispenseEnd = dispenseEnd;
    }

    public int getDispenseFrequency() {
        return frequency;
    }

    public void setDispenseFrequency(int dispenseFrequency) {
        this.frequency = dispenseFrequency;
    }   
}