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

/**
 *
 * @author stephen.williams@monco.info
 */
public class BoxKey {

    private Seed seed;
    private byte[] digest = new byte[32];

    //copy constructor
    public BoxKey(BoxKey key) throws NoSuchAlgorithmException {
        this.seed = key.getSeed();
        this.setDigest();
    }

    public BoxKey(byte[] seed) throws NoSuchAlgorithmException {
        this.setSeed(seed);
        this.setDigest();
    }

    public BoxKey(Seed seed) throws NoSuchAlgorithmException {
        this.seed=seed;
        this.setDigest();
    }

    public Seed getSeed() {
        return this.seed;
    }

    private void setSeed(byte[] seed) throws NoSuchAlgorithmException {
        //TODO: validate this byte array first!
        this.seed = new Seed(seed);
        this.setDigest();
    }

    public byte[] getDigest() {
        return digest;
    }

    private void setDigest() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(this.seed.getSeed());
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