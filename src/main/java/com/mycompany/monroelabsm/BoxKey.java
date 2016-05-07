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
 *
 * This class holds the seed class and handles requests for encryption
 *
 * TODO: add more encryption methods here for other cryptocurrency types.
 */

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.DecoderException;
import com.google.bitcoin.core.*;

public class BoxKey {

    private Seed seed;//this Seed object contains all the values that go into a public key hash
    private byte[] digest = new byte[32];//this is the digest of a SHA256 hash function, aka public key
    private String publicKey;//this is a Base58Check encoded String commonly used to represent bitcoin addresses
    private String privateKey;//this is the private key
    private ECKey key;
    private long targetBalance;
    private long currentBalance;

    //copy constructor
    public BoxKey(BoxKey key) throws NoSuchAlgorithmException, IOException {
        this.seed = key.getSeed();
        this.setDigest();
    }

    public BoxKey(byte[] seed) throws NoSuchAlgorithmException, IOException {
        this.setSeed(seed);
        this.setDigest();
    }

    public BoxKey(Seed seed) throws NoSuchAlgorithmException, IOException {
        this.seed = seed;
        this.setDigest();
    }

    public BoxKey(String seed) throws NoSuchAlgorithmException, DecoderException, IOException {
        this.setSeed(B58.hexToBytes(seed));
        this.setDigest();
    }

    public Seed getSeed() {
        return this.seed;
    }

    public List<String> getSeedStrings() {
        List<String> list = new ArrayList();
        list.add(seed.getSerialString());
        list.add(seed.getOperatorString());
        list.add(seed.getHeadingString());
        list.add(seed.getGpsxString());
        list.add(seed.getGpsyString());
        list.add(seed.getCryptoString());
        list.add(seed.getDenominationString());
        list.add(seed.getTimeString());
        return list;
    }

    private void setSeed(byte[] seed) throws NoSuchAlgorithmException, IOException {
        //TODO: validate this byte array first!
        this.seed = new Seed(seed);
        this.setDigest();
    }

    public byte[] getDigest() {
        return digest;
    }

    public String getDigestString() {
        return B58.bytesToHex(digest);
    }

    private void setDigest() throws NoSuchAlgorithmException, IOException {
        BCI bci = new BCI();
        this.digest = DigestUtils.sha256(this.seed.getSeed());
        //store private key in ECKey object
        BigInteger bigDig = new BigInteger(1, this.digest);
        ECKey ecKey = new ECKey(bigDig);
        //set creation time with utc of the timestamp
        ecKey.setCreationTimeSeconds(60 * Bitwise.getInt(seed.getTime(), 0));
        this.publicKey = B58.encode(ecKey.getPubKeyHash(), (byte) 0);
        this.privateKey = B58.encode(ecKey.getPrivKeyBytes(), (byte) -128);
        this.key=ecKey;
        this.currentBalance = bci.addressBalance(this.publicKey);
        this.targetBalance = this.seed.getDenominationByte() * bci.rateSatoshis();
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
    
    public ECKey getKey() {
        return key;
    }

    public long getTargetBalance() {
        return targetBalance;
    }

    public long getCurrentBalance() {
        return currentBalance;
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
        if (this.getDigest() != other.getDigest()) {
            return false;
        }
        return true;
    }

}
