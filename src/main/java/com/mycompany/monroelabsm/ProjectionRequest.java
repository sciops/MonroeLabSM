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
    private List<String> serialNo;//identity of machine. start with "ff" byte to avoid rounding issues
    private String operatorNo;//operator of machine
    private String gpsHeading;//future implementation
    private String gpsLocX;//future implementation
    private String gpsLocY;//future implementation
    private short cryptoCurrencyType;//i.e. BTC
    private short fiatCurrencyType;//i.e. USD
    private List<Byte> denominations;
    private int dispenseStart;
    private int dispenseEnd;
    private int dispenseFrequency;

    public List<String> getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(List<String> serialNo) {
        this.serialNo = serialNo;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getGpsHeading() {
        return gpsHeading;
    }

    public void setGpsHeading(String gpsHeading) {
        this.gpsHeading = gpsHeading;
    }

    public String getGpsLocX() {
        return gpsLocX;
    }

    public void setGpsLocX(String gpsLocX) {
        this.gpsLocX = gpsLocX;
    }

    public String getGpsLocY() {
        return gpsLocY;
    }

    public void setGpsLocY(String gpsLocY) {
        this.gpsLocY = gpsLocY;
    }

    public short getCryptoCurrencyType() {
        return cryptoCurrencyType;
    }

    public void setCryptoCurrencyType(short cryptoCurrencyType) {
        this.cryptoCurrencyType = cryptoCurrencyType;
    }

    public short getFiatCurrencyType() {
        return fiatCurrencyType;
    }

    public void setFiatCurrencyType(short fiatCurrencyType) {
        this.fiatCurrencyType = fiatCurrencyType;
    }

    public List<Byte> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<Byte> denominations) {
        this.denominations = denominations;
    }

    public int getDispenseStart() {
        return dispenseStart;
    }

    public void setDispenseStart(int dispenseStart) {
        this.dispenseStart = dispenseStart;
    }

    public int getDispenseEnd() {
        return dispenseEnd;
    }

    public void setDispenseEnd(int dispenseEnd) {
        this.dispenseEnd = dispenseEnd;
    }

    public int getDispenseFrequency() {
        return dispenseFrequency;
    }

    public void setDispenseFrequency(int dispenseFrequency) {
        this.dispenseFrequency = dispenseFrequency;
    }   
}