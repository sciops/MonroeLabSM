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
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author Stephen R. Williams
 */
public class SeedView {

    private String serial;
    private String operator;
    private String heading;
    private String gpsx;
    private String gpsy;
    private String crypto;
    private String fiat;
    private String denomination;
    private String time;

    public SeedView(Seed seed) {
        this.serial = seed.getSerialString();
        this.operator = seed.getOperatorString();
        this.heading = seed.getHeadingString();
        this.gpsx = seed.getGpsxString();
        this.gpsy = seed.getGpsyString();
        this.crypto = seed.getCryptoString();
        this.fiat = seed.getFiatString();
        this.denomination = seed.getDenominationString();
        this.time = seed.getTimeString();
    }

    public String getSerial() {
        return serial;
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

    public String getDenomination() {
        return denomination;
    }

    public String getTime() {
        return time;
    }

    public Seed getSeed() throws DecoderException, NoSuchAlgorithmException {
        //first check if some values are null. if they are, use another constructor.
        Set set = new HashSet();
        set.add(heading);
        set.add(gpsx);
        set.add(gpsy);
        if (set.contains(null)) {
            set.add(crypto);
            set.add(fiat);
            if (set.contains(null)) {
                return new Seed(serial, operator, denomination, time);
            } else {
                return new Seed(serial, operator, crypto, fiat, denomination, time);
            }
        } else {
            return new Seed(serial, operator, heading, gpsx, gpsy, crypto, fiat, denomination, time);
        }
    }

    @Override
    public String toString() {
        return serial + operator + heading + gpsx + gpsy + crypto + fiat + denomination + time;
    }

}
