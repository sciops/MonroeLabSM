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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author stephen.williams@monco.info
 */
public class BoxKey {

    //this serial number identifies the unit.
    //it should begin with FF to avoid leading zeroes problem in the box's algo.
    private byte[] serial = new byte[13];
    //the operator (owner) of the box
    private byte[] operator = new byte[3];
    //heading and location data if a gps device is implemented.
    private byte[] heading = new byte[1];
    private byte[] locationX = new byte[3];
    private byte[] locationY = new byte[3];
    //crypto-currency type (e.g. bitcoin, litecoin, etc.)
    private byte[] cryptoCurrency = new byte[2];
    //inserted currency (e.g. USD)
    private byte[] fiatCurrency = new byte[2];
    private byte[] denomination = new byte[1];
    //rounded time it will be dispensed
    private byte[] dispenseTime = new byte[4];
    private byte[] digest = new byte[32];

    //copy constructor
    public BoxKey(BoxKey key) throws NoSuchAlgorithmException {
        this.serial = key.serial;
        this.operator = key.operator;
        this.heading = key.heading;
        this.locationX = key.locationX;
        this.locationY = key.locationY;
        this.cryptoCurrency = key.cryptoCurrency;
        this.fiatCurrency = key.fiatCurrency;
        this.denomination = key.denomination;
        this.dispenseTime = key.dispenseTime;
        this.setDigest();
    }

    public BoxKey(byte[] seed) throws NoSuchAlgorithmException {
        this.setSeed(seed);
        this.setDigest();
    }

    public BoxKey(
            String serial,
            String operator,
            String heading,
            String locationX,
            String locationY,
            String cryptoCurrency,
            String fiatCurrency,
            String denomination,
            String dispenseTime
    ) throws NoSuchAlgorithmException, DecoderException {
        this.serial = B58.hexToBytes(serial);
        this.operator = B58.hexToBytes(operator);
        this.heading = B58.hexToBytes(heading);
        this.locationX = B58.hexToBytes(locationX);
        this.locationY = B58.hexToBytes(locationY);
        this.setCryptoCurrency(cryptoCurrency);
        this.fiatCurrency = B58.hexToBytes(fiatCurrency);
        this.denomination = B58.hexToBytes(denomination);
        this.dispenseTime = B58.hexToBytes(dispenseTime);
        this.setDigest();
    }

    //default values for heading and location if disabled
    public BoxKey(
            String serial,
            String operator,
            String cryptoCurrency,
            String fiatCurrency,
            String denomination,
            String dispenseTime
    ) throws NoSuchAlgorithmException, DecoderException {
        this(serial, operator, "66", "66", "66", cryptoCurrency, fiatCurrency, denomination, dispenseTime);
    }

    private byte[] getSeed() {
        byte[] seed = new byte[32];
        for (byte i = 0; i <= 12; i++) {
            seed[i] = this.serial[i];
        }
        for (byte i = 13; i <= 15; i++) {
            seed[i] = this.operator[i - 13];
        }
        seed[16] = heading[0];
        for (byte i = 17; i <= 19; i++) {
            seed[i] = locationX[i - 17];
        }
        for (byte i = 20; i <= 22; i++) {
            seed[i] = locationY[i - 20];
        }
        for (byte i = 23; i <= 24; i++) {
            seed[i] = cryptoCurrency[i - 23];
        }
        for (byte i = 25; i <= 26; i++) {
            seed[i] = fiatCurrency[i - 25];
        }
        seed[27] = denomination[0] = seed[27];
        for (byte i = 28; i <= 31; i++) {
            seed[i] = dispenseTime[i - 28];
        }
        return seed;
    }

    private void setSeed(byte[] seed) {
        for (byte i = 0; i <= 12; i++) {
            this.serial[i] = seed[i];
        }
        for (byte i = 13; i <= 15; i++) {
            this.operator[i - 13] = seed[i];
        }
        heading[0] = seed[16];
        for (byte i = 17; i <= 19; i++) {
            locationX[i - 17] = seed[i];
        }
        for (byte i = 20; i <= 22; i++) {
            locationY[i - 20] = seed[i];
        }
        for (byte i = 23; i <= 24; i++) {
            cryptoCurrency[i - 23] = seed[i];
        }
        for (byte i = 25; i <= 26; i++) {
            fiatCurrency[i - 25] = seed[i];
        }
        denomination[0] = seed[27];
        for (byte i = 28; i <= 31; i++) {
            dispenseTime[i - 28] = seed[i];
        }
    }

    public byte[] getSerial() {
        return serial;
    }

    public String getSerialString() {
        return B58.bytesToHex(this.serial);
    }

    public void setSerial(byte[] serial) throws NoSuchAlgorithmException {
        this.serial = serial;
        this.setDigest();
    }

    public byte[] getOperator() {
        return operator;
    }

    public void setOperator(byte[] operator) throws NoSuchAlgorithmException {
        this.operator = operator;
        this.setDigest();
    }

    public byte[] getHeading() {
        return heading;
    }

    public void setHeading(byte[] heading) throws NoSuchAlgorithmException {
        this.heading = heading;
        this.setDigest();
    }

    public byte[] getLocationX() {
        return locationX;
    }

    public void setLocationX(byte[] locationX) throws NoSuchAlgorithmException {
        this.locationX = locationX;
        this.setDigest();
    }

    public byte[] getLocationY() {
        return locationY;
    }

    public void setLocationY(byte[] locationY) throws NoSuchAlgorithmException {
        this.locationY = locationY;
        this.setDigest();
    }

    public byte[] getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(byte[] cryptoCurrency) throws NoSuchAlgorithmException {
        this.cryptoCurrency = cryptoCurrency;
        this.setDigest();
    }

    //todo:throw exception for currency not supported
    public void setCryptoCurrency(String cryptoCurrency) throws NoSuchAlgorithmException, DecoderException {
        if (cryptoCurrency.equals("Bitcoin")) {
            cryptoCurrency = "01";
        } else if (cryptoCurrency.equals("BTC")) {
            cryptoCurrency = "01";
        } else if (cryptoCurrency.equals("Litecoin")) {
            cryptoCurrency = "02";
        } else if (cryptoCurrency.equals("LTC")) {
            cryptoCurrency = "02";
        }
        this.cryptoCurrency = B58.hexToBytes(cryptoCurrency);
        this.setDigest();
    }

    public byte[] getFiatCurrency() {
        return fiatCurrency;
    }

    public void setFiatCurrency(byte[] fiatCurrency) throws NoSuchAlgorithmException {
        this.fiatCurrency = fiatCurrency;
        this.setDigest();
    }

    //todo:throw exception for currency not supported
    public void setFiatCurrency(String fiatCurrency) throws NoSuchAlgorithmException, DecoderException {
        if (fiatCurrency.equals("US Dollars")) {
            fiatCurrency = "0348";//840d, ISO4217
        } else if (fiatCurrency.equals("USD")) {
            fiatCurrency = "0348";//840d, ISO4217
        } else if (fiatCurrency.equals("Euro")) {
            fiatCurrency = "03D2";//978d, ISO4217
        } else if (fiatCurrency.equals("EUR")) {
            fiatCurrency = "03D2";//978d, ISO4217
        } else {
            this.fiatCurrency = B58.hexToBytes(fiatCurrency);            
        }
        this.setDigest();
    }

    public byte[] getDenomination() {
        return denomination;
    }

    public void setDenomination(byte[] denomination) throws NoSuchAlgorithmException {
        this.denomination = denomination;
        this.setDigest();
    }

    public byte[] getDispenseTime() {
        return dispenseTime;
    }

    public void setDispenseTime(byte[] dispenseTime) throws NoSuchAlgorithmException {
        this.dispenseTime = dispenseTime;
        this.setDigest();
    }

    public byte[] getDigest() {
        return digest;
    }

    private void setDigest() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(this.getSeed());
        this.digest = md.digest();
    }

    @Override
    public int hashCode() {
        return digest[31];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoxKey other = (BoxKey) obj;
        if (this.hashCode() != other.hashCode()) {
            return false;
        }
        return true;
    }
    
}
