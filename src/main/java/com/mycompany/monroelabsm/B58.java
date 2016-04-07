/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.monroelabsm;

import com.google.bitcoin.core.Base58;
import java.util.Arrays;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Stephen R. Williams
 *
 * https://en.bitcoin.it/wiki/Base58Check_encoding
 */
public class B58 {

    public static String encode(byte[] toEncode, byte prefix) {

        //validate prefix against known prefixes
        byte[] validPrefixes = {0, 5, 21, 52, -128, 111, -60, -17};
        Arrays.sort(validPrefixes);
        if (Arrays.binarySearch(validPrefixes, prefix) < 0) {//not found. see https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html
            return "Base58 error: Invalid address prefix byte specified.";
        }

        byte[] step1 = new byte[33];
        //step1[0] = 0;//mainnet
        //step1[0] = 111;//testnet
        //step1[0] = -128;//private key
        step1[0] = prefix;
        for (int i = 1; i <= 32; i++) {
            step1[i] = toEncode[i - 1];
        }

        byte[] step2 = new byte[4];
        byte[] step2a = DigestUtils.sha256(DigestUtils.sha256(step1));
        step2[0] = step2a[0];
        step2[1] = step2a[1];
        step2[2] = step2a[2];
        step2[3] = step2a[3];

        byte[] step3 = new byte[37];
        for (int i = 0; i <= 32; i++) {
            step3[i] = step1[i];
        }
        step3[33] = step2[0];
        step3[34] = step2[1];
        step3[35] = step2[2];
        step3[36] = step2[3];

        String output = Base58.encode(step3);
        return output;
    }

    public static String encodePrivKey(byte[] toEncode) {
        byte b = -128;
        return encode(toEncode, b);
    }

    //returns a Base58-encoded bitcoin string for a given array of bytes
    public static String encodeMainNetAddr(byte[] toEncode) {
        byte b = 0;
        return encode(toEncode, b);
    }

    //returns a Base58-encoded testnet string for a given array of bytes
    public static String encodeTestNetAddr(byte[] toEncode) {
        byte b = 111;
        return encode(toEncode, b);
    }

    //converts 32-bit integer to 4-byte array via evil bitwise logic
    public static byte[] toByteArray(int givenValue) {
        byte[] returnedBytes = new byte[4];
        returnedBytes[3] = (byte) (givenValue >> (0 * 8) & 0xFF);
        returnedBytes[2] = (byte) (givenValue >> (1 * 8) & 0xFF);
        returnedBytes[1] = (byte) (givenValue >> (2 * 8) & 0xFF);
        returnedBytes[0] = (byte) (givenValue >> (3 * 8) & 0xFF);
        return returnedBytes;
    }

    //converts 16-bit integer to 2-byte array via evil bitwise logic
    public static byte[] toByteArray(short givenValue) {
        byte[] returnedBytes = new byte[2];
        returnedBytes[1] = (byte) (givenValue >> (0 * 8) & 0xFF);
        returnedBytes[0] = (byte) (givenValue >> (1 * 8) & 0xFF);
        return returnedBytes;
    }

    //converts hexadecimal encoded string to byte array via org.apache.commons.codec.binary.Hex
    public static byte[] hexToBytes(String s) throws DecoderException {
        return Hex.decodeHex(s.toCharArray());
    }

    //converts byte array to hexadecimal encoded string via org.apache.commons.codec.binary.Hex
    public static String bytesToHex(byte[] b) {
        return Hex.encodeHexString(b);
    }
}
