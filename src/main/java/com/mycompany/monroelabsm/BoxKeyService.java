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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class BoxKeyService {
    private static final AtomicLong counter = new AtomicLong();
    private static List<BoxKey> keys;

    static {
        keys = reset();
    }

    public List<BoxKey> findAllBoxKeys() {
        return keys;
    }

    public BoxKey findById(long id) {
        for (BoxKey key : keys) {
            if (key.getId() == id) {
                return key;
            }
        }
        return null;
    }

    public BoxKey findBySerial(String serial) {
        for (BoxKey key : keys) {
            if (key.getSerial().equalsIgnoreCase(serial)) {
                return key;
            }
        }
        return null;
    }

    public BoxKey saveBoxKey(BoxKey key) throws AlreadyExistsException {
        //check for pre-existing key first
        if(this.isBoxKeyExist(key)) throw new AlreadyExistsException();
        key.setId(counter.incrementAndGet());
        keys.add(key);
        return key;
    }
    
    public BoxKey saveBoxKey(String json) throws AlreadyExistsException {
        //convert string json to boxkey and then save it with previous method.
        return saveBoxKey(new BoxKey(json));
    }

    public BoxKey updateBoxKey(BoxKey key) throws NotFoundException {
        if(!this.isBoxKeyExist(key)) throw new NotFoundException();
        keys.set(keys.indexOf(key), key);
        return key;
    }
    
    public BoxKey updateBoxKey(String json) throws NotFoundException {
        return updateBoxKey(new BoxKey(json));
    }

    public BoxKey deleteBoxKeyById(long id) throws NotFoundException {
        BoxKey key = null;
        BoxKey deletedKey = null;
        for (Iterator<BoxKey> iterator = keys.iterator(); iterator.hasNext();) {
            key = iterator.next();
            if (key.getId() == id) {
                iterator.remove();
                deletedKey = key;
            }
            else if (key == null){
                throw new NotFoundException();
            }
        }
        return deletedKey;
    }

    public boolean isBoxKeyExist(BoxKey key) {
        return findBySerial(key.getSerial()) != null;
    }

    public List<BoxKey> deleteAllBoxKeys() {
        keys.clear();
        return keys;
    }

    public static List<BoxKey> reset() {
        if (keys != null) keys.clear();
        keys = new ArrayList<BoxKey>();
        keys.add(new BoxKey(counter.incrementAndGet(), "HWzj4eCP", "kg3Wr5cN", "1449864824", "US Dollars", null,
                true, false, false, false, false, false, false));
        keys.add(new BoxKey(counter.incrementAndGet(), "S7AyQck9", "SLDwGajJ", "1449868424", "Mexican Pesos", null,
                false, false, true, false, false, false, false));
        keys.add(new BoxKey(counter.incrementAndGet(), "tZ4UX9Ak", "s5Q5DBJQ", "1449951224", "British Pounds", "quid",
                false, false, false, false, false, false, false));
        return keys;
    }

}