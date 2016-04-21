/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.monroelabsm;

/**
 *
 * @author Stephen R. Williams (c) 2014
 *
 * This Seed class is modified from my MonroeLab desktop app. It holds all the
 * components in byte arrays, then concats them together into a larger array
 * when requested. The seed used in the SHA algo is a 32-byte/256-bit number.
 */
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang.ArrayUtils;

public class Seed {

    private byte[] serial = new byte[13];//13-byte identity of machine. start with "ff" byte to avoid rounding issues
    private byte[] operator = new byte[3];//3-byte operator of machine
    private byte[] heading = new byte[1];//future implementation
    private byte[] gpsx = new byte[3];//future implementation
    private byte[] gpsy = new byte[3];//future implementation
    private byte[] crypto = new byte[2];//i.e. BTC
    private byte[] fiat = new byte[2];//i.e. USD
    private byte[] denomination = new byte[1];//fiat denomination
    private byte[] time = new byte[4];//box generation time divided by 60 for one key per minute

    public Seed(
            byte[] serial,
            byte[] operator,
            byte[] heading,
            byte[] gpsx,
            byte[] gpsy,
            byte[] crypto,
            byte[] fiat,
            byte[] denomination,
            byte[] time
    ) {
        this.serial = serial;
        this.operator = operator;
        this.heading = heading;
        this.gpsx = gpsx;
        this.gpsy = gpsy;
        this.crypto = crypto;
        this.fiat = fiat;
        this.denomination = denomination;
        this.time = time;
    }

    public Seed(
            String serial,
            String operator,
            String heading,
            String gpsx,
            String gpsy,
            short crypto,
            short fiat,
            byte denomination,
            int time
    ) throws DecoderException {
        this.serial = B58.hexToBytes(serial);
        this.operator = B58.hexToBytes(operator);
        this.heading = B58.hexToBytes(heading);
        this.gpsx = B58.hexToBytes(gpsx);
        this.gpsy = B58.hexToBytes(gpsy);
        this.crypto = B58.toByteArray(crypto);
        this.fiat = B58.toByteArray(fiat);
        this.denomination[0] = denomination;
        this.time = B58.toByteArray(time);
    }

    public Seed(byte[] seed) {
        //plug in big array into all seed values.
        serial = ArrayUtils.subarray(seed, 0, 13);
        operator = ArrayUtils.subarray(seed, 13, 16);
        heading = ArrayUtils.subarray(seed, 16, 17);
        gpsx = ArrayUtils.subarray(seed, 17, 20);
        gpsy = ArrayUtils.subarray(seed, 20, 23);
        crypto = ArrayUtils.subarray(seed, 23, 25);
        fiat = ArrayUtils.subarray(seed, 25, 27);
        denomination = ArrayUtils.subarray(seed, 27, 28);
        time = ArrayUtils.subarray(seed, 28, 32);
    }

    public Seed(
            String serial,
            String operator,
            String heading,
            String gpsx,
            String gpsy,
            String crypto,
            String fiat,
            String denom,
            String time
    ) throws DecoderException, NoSuchAlgorithmException {
        this.serial = B58.hexToBytes(serial);
        this.operator = B58.hexToBytes(operator);
        this.heading = B58.hexToBytes(heading);
        this.gpsx = B58.hexToBytes(gpsx);
        this.gpsy = B58.hexToBytes(gpsy);
        setCrypto(crypto);
        setFiat(fiat);
        this.denomination = B58.hexToBytes(denom);
        this.time = B58.hexToBytes(time);
    }

    //default values for heading and location if disabled. calls the constructor above
    public Seed(
            String serial,
            String operator,
            String crypto,
            String fiat,
            String denomination,
            String time
    ) throws NoSuchAlgorithmException, DecoderException {
        this(serial, operator, "66", "66", "66", crypto, fiat, denomination, time);
    }

    //more defaults    
    public Seed(
            String serial,
            String operator,
            String denomination,
            String time
    ) throws NoSuchAlgorithmException, DecoderException {
        this(serial, operator, "66", "66", "66", "01", "0348", denomination, time);
    }

    //http://stackoverflow.com/questions/4827622/copy-several-byte-arrays-to-one-big-byte-array
    public byte[] getSeed() {
        byte[] seed = new byte[32];
        ByteBuffer bb = ByteBuffer.wrap(seed);
        bb.put(serial);
        bb.put(operator);
        bb.put(heading);
        bb.put(gpsx);
        bb.put(gpsy);
        bb.put(crypto);
        bb.put(fiat);
        bb.put(denomination);
        bb.put(time);
        return bb.array();
    }

    public byte[] getSerial() {
        return serial;
    }

    public String getSerialString() {
        return B58.bytesToHex(serial);
    }

    public byte[] getOperator() {
        return operator;
    }

    public String getOperatorString() {
        return B58.bytesToHex(operator);
    }

    public byte[] getHeading() {
        return heading;
    }

    public String getHeadingString() {
        return B58.bytesToHex(heading);
    }

    public byte[] getGpsx() {
        return gpsx;
    }

    public String getGpsxString() {
        return B58.bytesToHex(gpsx);
    }

    public byte[] getGpsy() {
        return gpsy;
    }

    public String getGpsyString() {
        return B58.bytesToHex(gpsy);
    }

    public byte[] getCrypto() {
        return crypto;
    }

    public String getCryptoString() {
        return B58.bytesToHex(crypto);
    }

    public byte[] getFiat() {
        return fiat;
    }

    public String getFiatString() {
        return B58.bytesToHex(fiat);
    }

    public byte[] getDenomination() {
        return denomination;
    }
    
    public byte getDenominationByte() {
        return denomination[0];
    }

    public String getDenominationString() {
        return B58.bytesToHex(denomination);
    }

    public byte[] getTime() {
        return time;
    }

    public String getTimeString() {
        return B58.bytesToHex(time);
    }

    public void setSerial(byte[] serial) {
        this.serial = serial;
    }

    public void setOperator(byte[] operator) {
        this.operator = operator;
    }

    public void setHeading(byte[] heading) {
        this.heading = heading;
    }

    public void setGpsx(byte[] gpsx) {
        this.gpsx = gpsx;
    }

    public void setGpsy(byte[] gpsy) {
        this.gpsy = gpsy;
    }

    public void setCrypto(byte[] crypto) {
        this.crypto = crypto;
    }

    //TODO: throw exception for currency not supported
    public void setCrypto(String cryptoCurrency) throws NoSuchAlgorithmException, DecoderException {
        if (cryptoCurrency.equals("Bitcoin")) {
            cryptoCurrency = "01";
        } else if (cryptoCurrency.equals("BTC")) {
            cryptoCurrency = "01";
        } else if (cryptoCurrency.equals("Litecoin")) {
            cryptoCurrency = "02";
        } else if (cryptoCurrency.equals("LTC")) {
            cryptoCurrency = "02";
        }
        this.crypto = B58.hexToBytes(cryptoCurrency);
    }

    public void setFiat(byte[] fiat) {
        this.fiat = fiat;
    }

    //todo:throw exception for currency not supported
    public void setFiat(String fiatCurrency) throws NoSuchAlgorithmException, DecoderException {
        if (fiatCurrency.equals("US Dollars")) {
            fiatCurrency = "0348";//840d, ISO4217
        } else if (fiatCurrency.equals("USD")) {
            fiatCurrency = "0348";//840d, ISO4217
        } else if (fiatCurrency.equals("Euro")) {
            fiatCurrency = "03D2";//978d, ISO4217
        } else if (fiatCurrency.equals("EUR")) {
            fiatCurrency = "03D2";//978d, ISO4217
        }
        this.fiat = B58.hexToBytes(fiatCurrency);
    }

    public void setDenomination(byte[] denomination) {
        this.denomination = denomination;
    }

    public void setDenomination(byte denomination) {
        this.denomination[0] = denomination;
    }

    public void setTime(byte[] time) {
        this.time = time;
    }

    public void setTime(int time) {
        Bitwise.putInt(time, this.time, 0);
    }

}
