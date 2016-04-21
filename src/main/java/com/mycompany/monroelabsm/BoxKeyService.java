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
 * This class performs the actions requested by the controller.
 */
import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.DecoderException;

public class BoxKeyService {

    //private static final AtomicLong counter = new AtomicLong();
    //storing all keys here in memory until user authentication / persistence is implemented
    private static List<BoxKey> keys;

    public List<BoxKeyView> findAllBoxKeys() {
        //System.out.println("DEBUG findAllBoxKeys called. current content of keys:");
        //for (BoxKey k:keys) System.out.println(k.getSeedStrings());
        return keysToViews(this.keys);
    }

    public List<BoxKeyView> keysToViews(List<BoxKey> keys) {
        List<BoxKeyView> views = new ArrayList();
        if (keys == null) {
            return views;
        } else {
            for (BoxKey bk : keys) {
                views.add(new BoxKeyView(bk));
            }
        }
        //System.out.println("DEBUG keysToViews called. current content of views:");
        //for (BoxKeyView v:views) System.out.println(v.getSeedView().toString());
        return views;
    }

    //lookup the key by id
    public BoxKey findById(long id) {
        for (BoxKey key : keys) {
            if (key.hashCode() == id) {
                return key;//found
            }
        }
        return null; //404: not found
    }

    //parse long value from string and use the other method
    public BoxKey findById(String id_s) {
        return this.findById(Long.parseLong(id_s));
    }

    public List<BoxKeyView> findBySerial(String serial) {
        List<BoxKey> foundKeys = null;
        for (BoxKey key : keys) {
            if (key.getSeed().getSerialString().equalsIgnoreCase(serial)) {
                foundKeys.add(key);
            }
        }
        return keysToViews(foundKeys);
    }//returns null if nothing found

    public BoxKeyView findByDigest(String digest) {
        for (BoxKey key : keys) {
            if (key.getDigestString().equalsIgnoreCase(digest)) {
                return new BoxKeyView(key);
            }
        }
        return null;
    }//returns null if nothing found

    public BoxKeyView findByPublicKey(String pubkey) {
        for (BoxKey key : keys) {
            if (key.getPublicKey().equalsIgnoreCase(pubkey)) {
                return new BoxKeyView(key);
            }
        }
        return null;
    }//returns null if nothing found

    public BoxKeyView findByPrivateKey(String privkey) {
        for (BoxKey key : keys) {
            if (key.getPrivateKey().equalsIgnoreCase(privkey)) {
                return new BoxKeyView(key);
            }
        }
        return null;
    }//returns null if nothing found

    public BoxKeyView saveBoxKey(BoxKeyView view) throws AlreadyExistsException, NoSuchAlgorithmException, DecoderException {
        BoxKey newKey = view.getBoxKey();
        for (BoxKey oldKey : keys) {//search for a key by public key
            if (oldKey.getPublicKey().equalsIgnoreCase(newKey.getPublicKey())) {
                throw new AlreadyExistsException();
            }
        }
        //if the list is exhausted, we may add the new key
        keys.add(newKey);
        return new BoxKeyView(newKey);
    }

    public BoxKeyView saveBoxKey(String json) throws AlreadyExistsException, JsonPojoMismatchException, NoSuchAlgorithmException, DecoderException {
        //convert string json to boxkey and then save it with previous method.
        BoxKeyView view = new Gson().fromJson(json, BoxKeyView.class);
        if (view == null) {
            throw new JsonPojoMismatchException();
        }
        return saveBoxKey(view);
    }

    public BoxKeyView updateBoxKey(BoxKeyView view) throws NotFoundException, NoSuchAlgorithmException, DecoderException {
        BoxKey newKey = view.getBoxKey();
        System.out.println("compare newKey: " + newKey.getPublicKey());
        for (BoxKey oldKey : keys) {//search for a key by public key
            System.out.println("compare oldKey: " + oldKey.getPublicKey());
            if (oldKey.getPublicKey().equalsIgnoreCase(newKey.getPublicKey())) {
                System.out.println("Found!");
                keys.remove(oldKey);
                keys.add(newKey);
                return new BoxKeyView(newKey);
            }
        }
        System.out.println("Not found!");
        //if the list is exhausted, return 404 with the following exception.
        throw new NotFoundException();
    }

    public BoxKeyView updateBoxKey(String json) throws NotFoundException, JsonPojoMismatchException, NoSuchAlgorithmException, DecoderException {
        System.out.println("json string passed to updateBoxKey:" + json);
        BoxKeyView view = new Gson().fromJson(json, BoxKeyView.class);
        if (view == null) {
            throw new JsonPojoMismatchException();
        }
        return updateBoxKey(view);
    }

    public BoxKeyView deleteById(int id) throws NotFoundException, NoSuchAlgorithmException {
        BoxKey deletedKey = null;
        for (BoxKey key : keys) {
            if (key.hashCode() == id) {
                deletedKey = new BoxKey(key);
                keys.remove(key);
            }
        }
        return new BoxKeyView(deletedKey);
    }

    //parse id and use the above method
    public BoxKeyView deleteById(String id_s) throws NotFoundException, NoSuchAlgorithmException {
        return this.deleteById(Integer.parseInt(id_s));
    }

    public BoxKeyView deleteByDigest(String digest) throws NoSuchAlgorithmException {
        BoxKey deletedKey = null;
        for (BoxKey key : keys) {
            if (key.getDigestString().equalsIgnoreCase(digest)) {
                deletedKey = new BoxKey(key);
                keys.remove(key);
                return new BoxKeyView(deletedKey);
            }
        }
        return null;//not found
    }

    public BoxKeyView deleteByPublicKey(String pubkey) throws NoSuchAlgorithmException {
        BoxKey deletedKey = null;
        for (BoxKey key : keys) {
            if (key.getPublicKey().equalsIgnoreCase(pubkey)) {
                deletedKey = new BoxKey(key);
                keys.remove(key);
                return new BoxKeyView(deletedKey);
            }
        }
        return null;//not found
    }

    public BoxKeyView deleteByPrivateKey(String privkey) throws NoSuchAlgorithmException {
        BoxKey deletedKey = null;
        for (BoxKey key : keys) {
            if (key.getPrivateKey().equalsIgnoreCase(privkey)) {
                deletedKey = new BoxKey(key);
                keys.remove(key);
                return new BoxKeyView(deletedKey);
            }
        }
        return null;//not found
    }

    public List<BoxKeyView> deleteAll() {
        keys.clear();
        return keysToViews(keys);
    }

    //this method will clear the memory and insert hard-coded values for testing the CRUD front-end
    public List<BoxKeyView> reset() throws NoSuchAlgorithmException, DecoderException {
        if (keys != null) {
            keys.clear();
        }
        keys = new ArrayList<BoxKey>();
        //constructor parameters: serial,operator,crypto,fiat,denomination,time
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "01", "56F6BB45")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "05", "56F6BB45")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "0A", "56F6BB45")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "14", "56F6BB45")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "01", "56F6BB46")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "05", "56F6BB46")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "0A", "56F6BB46")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "14", "56F6BB46")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a56", "666666", "BTC", "USD", "01", "56F6BB47")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a56", "666666", "BTC", "USD", "05", "56F6BB47")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a56", "666666", "BTC", "USD", "0A", "56F6BB47")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a56", "666666", "BTC", "USD", "14", "56F6BB47")));
        return keysToViews(keys);
    }
}
