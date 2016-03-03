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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.websystique.springmvc.model.BoxKey;
import com.websystique.springmvc.service.BoxKeyService;
 
@RestController
public class BoxKeyController {
    @Autowired
    BoxKeyService boxkeyService;  //Service which will do all data retrieval/manipulation work
    
    //-------------------Retrieve All BoxKeys--------------------------------------------------------
    @RequestMapping(value = "/boxkey/", method = RequestMethod.GET)
    public ResponseEntity<List<BoxKey>> listAllBoxKeys() {
        List<BoxKey> boxkeys = boxkeyService.findAllBoxKeys();
        if(boxkeys.isEmpty()){
            return new ResponseEntity<List<BoxKey>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<BoxKey>>(boxkeys, HttpStatus.OK);
    }
    
    //-------------------Retrieve Single BoxKey--------------------------------------------------------
    @RequestMapping(value = "/boxkey/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoxKey> getBoxKey(@PathVariable("id") long id) {
        System.out.println("Fetching BoxKey with id " + id);
        BoxKey boxkey = boxkeyService.findById(id);
        if (boxkey == null) {
            System.out.println("BoxKey with id " + id + " not found");
            return new ResponseEntity<BoxKey>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<BoxKey>(boxkey, HttpStatus.OK);
    }
     
    //-------------------Create a BoxKey--------------------------------------------------------
    @RequestMapping(value = "/boxkey/", method = RequestMethod.POST)
    public ResponseEntity<Void> createBoxKey(@RequestBody BoxKey boxkey,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating BoxKey " + boxkey.getSerial());
 
        if (boxkeyService.isBoxKeyExist(boxkey)) {
            System.out.println("A BoxKey with name " + boxkey.getSerial() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        boxkeyService.saveBoxKey(boxkey);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/boxkey/{id}").buildAndExpand(boxkey.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
     
    //------------------- Update a BoxKey --------------------------------------------------------
    @RequestMapping(value = "/boxkey/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BoxKey> updateBoxKey(@PathVariable("id") long id, @RequestBody BoxKey boxkey) {
        System.out.println("Updating BoxKey " + id);
         
        BoxKey currentBoxKey = boxkeyService.findById(id);
         
        if (currentBoxKey==null) {
            System.out.println("BoxKey with id " + id + " not found");
            return new ResponseEntity<BoxKey>(HttpStatus.NOT_FOUND);
        }
 
        currentBoxKey.setSerial(boxkey.getSerial());
        currentBoxKey.setOpnum(boxkey.getOpnum());
        currentBoxKey.setDatetime(boxkey.getDatetime());
        currentBoxKey.setCurrtype(boxkey.getCurrtype());
        currentBoxKey.setDenom(boxkey.getDenom());
        currentBoxKey.setDenomone(boxkey.isDenomone());
        currentBoxKey.setDenomtwo(boxkey.isDenomtwo());
        currentBoxKey.setDenomfive(boxkey.isDenomfive());
        currentBoxKey.setDenomten(boxkey.isDenomten());
        currentBoxKey.setDenomtwenty(boxkey.isDenomtwenty());
        currentBoxKey.setDenomfifty(boxkey.isDenomfifty());
        currentBoxKey.setDenomhundred(boxkey.isDenomhundred());
         
        boxkeyService.updateBoxKey(currentBoxKey);
        return new ResponseEntity<BoxKey>(currentBoxKey, HttpStatus.OK);
    }
 
    //------------------- Delete a BoxKey --------------------------------------------------------
    @RequestMapping(value = "/boxkey/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BoxKey> deleteBoxKey(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting BoxKey with id " + id);
 
        BoxKey boxkey = boxkeyService.findById(id);
        if (boxkey == null) {
            System.out.println("Unable to delete. BoxKey with id " + id + " not found");
            return new ResponseEntity<BoxKey>(HttpStatus.NOT_FOUND);
        }
 
        boxkeyService.deleteBoxKeyById(id);
        return new ResponseEntity<BoxKey>(HttpStatus.NO_CONTENT);
    }
 
    //------------------- Delete All BoxKeys --------------------------------------------------------
    @RequestMapping(value = "/boxkey/", method = RequestMethod.DELETE)
    public ResponseEntity<BoxKey> deleteAllBoxKeys() {
        System.out.println("Deleting All BoxKeys");
 
        boxkeyService.deleteAllBoxKeys();
        return new ResponseEntity<BoxKey>(HttpStatus.NO_CONTENT);
    }
 
}
