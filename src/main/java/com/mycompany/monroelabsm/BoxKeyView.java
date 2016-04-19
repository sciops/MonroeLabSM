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
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author Stephen R. Williams
 *
 * Immutable response for easy/proper JSON parsing of values into hex strings
 */
public class BoxKeyView {

    private SeedView seed;
    private String digest;
    private String publickey;//this is a Base58Check encoded String commonly used to represent bitcoin addresses
    private String privatekey;//this is the private key

    public BoxKeyView(BoxKey key) {
        this.seed = new SeedView(key.getSeed());
        this.digest = key.getDigestString();
        this.publickey = key.getPublicKey();
        this.privatekey = key.getPrivateKey();
    }

    public SeedView getSeedView() {
        return this.seed;
    }

    public String getDigest() {
        return this.digest;
    }

    public String getPublicKey() {
        return publickey;
    }

    public String getPrivateKey() {
        return privatekey;
    }
    
    public BoxKey getBoxKey() throws NoSuchAlgorithmException, DecoderException{
        return new BoxKey(seed.getSeed());
    }
}
