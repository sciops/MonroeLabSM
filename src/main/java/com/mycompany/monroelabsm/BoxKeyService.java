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
        return keysToViews(this.keys);
    }

    public List<BoxKeyView> keysToViews(List<BoxKey> keys) {
        List<BoxKeyView> views = new ArrayList();
        if (keys == null) return views;
        else for (BoxKey bk : keys) {
            views.add(new BoxKeyView(bk));
        }
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

    public BoxKeyView saveBoxKey(BoxKey key) throws AlreadyExistsException {
        //check for pre-existing key first
        if (this.keys.contains(key)) {
            throw new AlreadyExistsException();
        }
        keys.add(key);
        return new BoxKeyView(key);
    }

    public BoxKeyView saveBoxKey(String json) throws AlreadyExistsException, JsonPojoMismatchException {
        //convert string json to boxkey and then save it with previous method.
        BoxKey key = new Gson().fromJson(json, BoxKey.class);
        if (key == null) {
            throw new JsonPojoMismatchException();
        }
        return saveBoxKey(key);
    }

    public BoxKeyView updateBoxKey(BoxKey key) throws NotFoundException {
        if (!this.keys.contains(key)) {
            throw new NotFoundException();
        }
        keys.set(keys.indexOf(key), key);
        return new BoxKeyView(key);
    }

    public BoxKeyView updateBoxKey(String json) throws NotFoundException, JsonPojoMismatchException {
        BoxKey key = new Gson().fromJson(json, BoxKey.class);
        if (key == null) {
            throw new JsonPojoMismatchException();
        }
        return updateBoxKey(key);
    }

    public BoxKeyView deleteBoxKeyById(long id) throws NotFoundException, NoSuchAlgorithmException {
        BoxKey deletedKey = null;
        for (BoxKey key : keys) {
            if (key.hashCode() == id) {
                deletedKey = new BoxKey(key);
                keys.remove(key);
            }
        }
        return new BoxKeyView(deletedKey);
    }

    //parse id and use the other method
    public BoxKeyView deleteBoxKeyById(String id_s) throws NotFoundException, NoSuchAlgorithmException {
        return this.deleteBoxKeyById(Long.parseLong(id_s));
    }

    public List<BoxKeyView> deleteAllBoxKeys() {
        keys.clear();
        return keysToViews(keys);
    }

    //this method will clear the memory and insert hard-coded values for testing the CRUD front-end
    public List<BoxKeyView> reset() throws NoSuchAlgorithmException, DecoderException {
        if (keys != null) {
            keys.clear();
        }
        keys = new ArrayList<BoxKey>();
        //old hard-coded key
        //keys.add(new BoxKey("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "1", "56F6BB45"));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "1", "56F6BB45")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "5", "56F6BB45")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "10", "56F6BB45")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "20", "56F6BB45")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "1", "56F6BB46")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "5", "56F6BB46")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "10", "56F6BB46")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a55", "666666", "BTC", "USD", "20", "56F6BB46")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a56", "666666", "BTC", "USD", "1", "56F6BB47")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a56", "666666", "BTC", "USD", "5", "56F6BB47")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a56", "666666", "BTC", "USD", "10", "56F6BB47")));
        keys.add(new BoxKey(new Seed("ff0154d4b792d4d69c62217a56", "666666", "BTC", "USD", "20", "56F6BB47")));
        return keysToViews(keys);
    }
}
