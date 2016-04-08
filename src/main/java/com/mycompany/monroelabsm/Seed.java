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
 * This Seed class is modified from my MonroeLab desktop app.
 * It holds all the components in byte arrays, then concats them together into a larger array when requested.
 * The seed used in the SHA algo is a 32-byte/256-bit number.
 */
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.DecoderException;

public class Seed {
    private byte[] serialNo = new byte[13];//13-byte identity of machine. start with "ff" byte to avoid rounding issues
    private byte[] operatorNo = new byte[3];//3-byte operator of machine
    private byte[] gpsHeading = new byte[1];//future implementation
    private byte[] gpsLocX = new byte[3];//future implementation
    private byte[] gpsLocY = new byte[3];//future implementation
    private byte[] cryptoCurrencyType = new byte[2];//i.e. BTC
    private byte[] fiatCurrencyType = new byte[2];//i.e. USD
    private byte[] denom = new byte[1];//fiat denomination
    private byte[] utcDiv60 = new byte[4];//box generation time divided by 60 for one key per minute

    public Seed(
            byte[] serialNo,
            byte[] operatorNo,
            byte[] gpsHeading,
            byte[] gpsLocX,
            byte[] gpsLocY,
            byte[] cryptoCurrencyType,
            byte[] fiatCurrencyType,
            byte[] denom,
            byte[] utcDiv60
    ) {
        this.serialNo = serialNo;
        this.operatorNo = operatorNo;
        this.gpsHeading = gpsHeading;
        this.gpsLocX = gpsLocX;
        this.gpsLocY = gpsLocY;
        this.cryptoCurrencyType = cryptoCurrencyType;
        this.fiatCurrencyType = fiatCurrencyType;
        this.denom = denom;
        this.utcDiv60 = utcDiv60;
    }

    public Seed(
            String serialNo_s,
            String operatorNo_s,
            String gpsHeading_s,
            String gpsLocX_s,
            String gpsLocY_s,
            int cryptoCurrencyType_int,
            int fiatCurrencyType_int,
            int denom_int,
            int utcDiv60
    ) throws DecoderException {
        this.serialNo = B58.hexToBytes(serialNo_s);
        this.operatorNo = B58.hexToBytes(operatorNo_s);
        this.gpsHeading = B58.hexToBytes(gpsHeading_s);
        this.gpsLocX = B58.hexToBytes(gpsLocX_s);
        this.gpsLocY = B58.hexToBytes(gpsLocY_s);
        byte[] temp = null;
        temp[0] = (byte) cryptoCurrencyType_int;
        this.cryptoCurrencyType = temp;
        temp[0] = (byte) fiatCurrencyType_int;
        this.fiatCurrencyType = temp;
        temp[0] = (byte) denom_int;
        this.denom = temp;
        temp = B58.toByteArray(utcDiv60);
        this.utcDiv60 = temp;
    }

    public Seed(byte[] seed) {
        //TODO:plug in big array into all seed values.
        for (int i = 0; i <= 12; i++) {
            serialNo[i]=seed[i];
        }
        for (int i = 13; i <= 15; i++) {
            operatorNo[i - 13]=seed[i];
        }
        for (int i = 16; i <= 16; i++) {
            gpsHeading[i - 16]=seed[i];
        }
        for (int i = 17; i <= 19; i++) {
            gpsLocX[i - 17]=seed[i];
        }
        for (int i = 20; i <= 22; i++) {
            gpsLocY[i - 20]=seed[i];
        }
        for (int i = 23; i <= 24; i++) {
            cryptoCurrencyType[i - 23]=seed[i];
        }
        for (int i = 25; i <= 26; i++) {
            fiatCurrencyType[i - 25]=seed[i];
        }
        for (int i = 27; i <= 27; i++) {
            denom[i - 27]=seed[i];
        }
        for (int i = 28; i <= 31; i++) {
            utcDiv60[i - 28]=seed[i];
        }
    }

    public Seed(
            String serial,
            String operator,
            String heading,
            String gpsX,
            String gpsY,
            String crypto,
            String fiat,
            String denom,
            String time) throws DecoderException {
        this.serialNo = B58.hexToBytes(serial);
        this.operatorNo = B58.hexToBytes(operator);
        this.gpsHeading = B58.hexToBytes(heading);
        this.gpsLocX = B58.hexToBytes(gpsX);
        this.gpsLocY = B58.hexToBytes(gpsY);
        this.cryptoCurrencyType = B58.hexToBytes(crypto);
        this.fiatCurrencyType = B58.hexToBytes(fiat);
        this.denom = B58.hexToBytes(denom);
        this.utcDiv60 = B58.hexToBytes(time);
    }

    //default values for heading and location if disabled. calls the constructor above
    public Seed(
            String serial,
            String operator,
            String cryptoCurrency,
            String fiatCurrency,
            String denomination,
            String dispenseTime
    ) throws NoSuchAlgorithmException, DecoderException {
        this(serial, operator, "66", "66", "66", cryptoCurrency, fiatCurrency, denomination, dispenseTime);
    }

    public byte[] getSeed() {
        byte[] seed = new byte[32];
        for (int i = 0; i <= 12; i++) {
            seed[i] = serialNo[i];
        }
        for (int i = 13; i <= 15; i++) {
            seed[i] = operatorNo[i - 13];
        }
        for (int i = 16; i <= 16; i++) {
            seed[i] = gpsHeading[i - 16];
        }
        for (int i = 17; i <= 19; i++) {
            seed[i] = gpsLocX[i - 17];
        }
        for (int i = 20; i <= 22; i++) {
            seed[i] = gpsLocY[i - 20];
        }
        for (int i = 23; i <= 24; i++) {
            seed[i] = cryptoCurrencyType[i - 23];
        }
        for (int i = 25; i <= 26; i++) {
            seed[i] = fiatCurrencyType[i - 25];
        }
        for (int i = 27; i <= 27; i++) {
            seed[i] = denom[i - 27];
        }
        for (int i = 28; i <= 31; i++) {
            seed[i] = utcDiv60[i - 28];
        }
        return seed;
    }

    public byte[] getSerialNo() {
        return serialNo;
    }

    public String getSerialString() {
        return B58.bytesToHex(serialNo);
    }

    public byte[] getOperatorNo() {
        return operatorNo;
    }

    public byte[] getGpsHeading() {
        return gpsHeading;
    }

    public byte[] getGpsLocX() {
        return gpsLocX;
    }

    public byte[] getGpsLocY() {
        return gpsLocY;
    }

    public byte[] getCryptoCurrencyType() {
        return cryptoCurrencyType;
    }

    public byte[] getFiatCurrencyType() {
        return fiatCurrencyType;
    }

    public byte[] getDenom() {
        return denom;
    }

    public byte[] getUtcDiv60() {
        return utcDiv60;
    }

    public void setSerialNo(byte[] serialNo) {
        this.serialNo = serialNo;
    }

    public void setOperatorNo(byte[] operatorNo) {
        this.operatorNo = operatorNo;
    }

    public void setGpsHeading(byte[] gpsHeading) {
        this.gpsHeading = gpsHeading;
    }

    public void setGpsLocX(byte[] gpsLocX) {
        this.gpsLocX = gpsLocX;
    }

    public void setGpsLocY(byte[] gpsLocY) {
        this.gpsLocY = gpsLocY;
    }

    public void setCryptoCurrencyType(byte[] cryptoCurrencyType) {
        this.cryptoCurrencyType = cryptoCurrencyType;
    }

    //TODO: throw exception for currency not supported
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
        this.cryptoCurrencyType = B58.hexToBytes(cryptoCurrency);
    }

    public void setFiatCurrencyType(byte[] fiatCurrencyType) {
        this.fiatCurrencyType = fiatCurrencyType;
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
            this.fiatCurrencyType = B58.hexToBytes(fiatCurrency);
        }
    }

    public void setDenom(byte[] denom) {
        this.denom = denom;
    }

    public void setDenom(byte denom) {
        byte[] temp = new byte[1];
        temp[0] = denom;
        this.denom = temp;
    }

    public void setUtcDiv60(byte[] utcDiv60) {
        this.utcDiv60 = utcDiv60;
    }

    public void setUtcDiv60(long utvDiv60) {
        byte[] temp = new byte[4];
        Bitwise.putInt((int) utvDiv60, temp, 0);
        this.utcDiv60 = temp;
    }
}
