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

import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.DecoderException;
import com.google.bitcoin.core.Base58;

/**
 *
 * @author Stephen R. Williams
 *
 * https://en.bitcoin.it/wiki/Base58Check_encoding
 *
 * This static utility class handles Base58Check encoding as well as converting
 * numbers between byte arrays and hex strings
 */
public class B58 {

    public static String encode(byte[] toEncode, byte prefix) {
        //TODO:validate toEncode for correct size
        //TODO:implement an enum for this parameter instead.
        //validate prefix against known prefixes
        byte[] validPrefixes = {
            0, //Bitcoin pubkey hash
            5, //Bitcoin script hash
            21, //Bitcoin (compact) public key (proposed)
            52, //Namecoin pubkey hash
            -128, //Private key
            111, //Bitcoin testnet pubkey hash
            -60, //Bitcoin testnet script hash
            -17 //Testnet Private key
        };
        Arrays.sort(validPrefixes);
        if (Arrays.binarySearch(validPrefixes, prefix) < 0) {//not found. see https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html
            return "Base58 error: Invalid address prefix byte specified.";
        }

        int size = 21;
        if (prefix==(byte)-128) {
            size = 33;
        }

        byte[] step1 = new byte[size];
        step1[0] = prefix;
        for (int i = 1; i < size; i++) {
            step1[i] = toEncode[i - 1];
        }

        byte[] step2 = new byte[4];
        byte[] step2a = DigestUtils.sha256(DigestUtils.sha256(step1));
        step2[0] = step2a[0];
        step2[1] = step2a[1];
        step2[2] = step2a[2];
        step2[3] = step2a[3];

        byte[] step3 = new byte[size+4];
        for (int i = 0; i < size; i++) {
            step3[i] = step1[i];
        }
        step3[size] = step2[0];
        step3[size+1] = step2[1];
        step3[size+2] = step2[2];
        step3[size+3] = step2[3];

        String output = Base58.encode(step3);
        return output;
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
