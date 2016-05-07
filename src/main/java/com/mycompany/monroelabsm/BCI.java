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

import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.bitcoin.core.ECKey;

/**
 *
 * @author Stephen R. Williams
 * 
 * This utility class makes api calls to blockchain.info for wallet management tasks.
 */
public class BCI {

    private final String identifier;
    private final String password;
    private final String secondPassword;

    //default account for debugging purposes. DON'T PUT MONEY ON THIS.
    public BCI() {
        this.identifier = "1887271c-ed5d-4c31-971e-aefcb8d83762";
        this.password = "adafretvert";
        this.secondPassword = "september";
    }

    public BCI(String identifier, String password, String secondPassword) {
        this.identifier = identifier;
        this.password = password;
        this.secondPassword = secondPassword;
    }

    /*  Getting the balance of an address
    Retrieve the balance of a bitcoin address. Querying the balance of an address by label is depreciated.
    New API: https://blockchain.info/q/addressbalance/1EzwoHtiXB4iFwedPr49iywjZn2nnekhoj?confirmations=6
    Doc: https://blockchain.info/q
    */
    public long addressBalance(String address) throws IOException {
        String uri = "https://blockchain.info/q/addressbalance/" + address + "?confirmations=6";
        String result = bciCall(uri);
        long balance = Long.parseLong(result);
        return balance;
    }

    /* Fetching the wallet balance
    Fetch the balance of a wallet. This should be used as an estimate only and will include unconfirmed transactions and possibly double spends.
    https://blockchain.info/merchant/$guid/balance?password=$main_password
    */
    public long walletBalance() throws IOException {
        String uri = "https://blockchain.info/merchant/" + identifier + "/balance?password=" + password;
        String result = bciCall(uri);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jn = mapper.readTree(result);
        JsonNode balanceNode = jn.path("balance");
        long balance = balanceNode.asLong();
        return balance;
    }

    /* Listing Addresses
    List all active addresses in a wallet. Also includes a 0 confirmation balance which should be used as an estimate only and will include unconfirmed transactions and possibly double spends.
    https://blockchain.info/merchant/$guid/list?password=$main_password
     */
    public String listAddresses() throws IOException {
        String uri = "https://blockchain.info/merchant/" + identifier + "/list?password=" + password;
        String result = bciCall(uri);
        return result;
    }

    /* Generating a new address
    https://blockchain.info/merchant/$guid/new_address?password=$main_password&second_password=$second_password&label=$label
    */
    public String newAddress() throws IOException {

        String uri = "https://blockchain.info/merchant/" + identifier + "/new_address?password=" + password + "&second_password=" + secondPassword;
        String result = bciCall(uri);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jn = mapper.readTree(result);
        JsonNode addressNode = jn.path("address");
        String address = addressNode.asText();
        return address;
    }

    /*
    Making Outgoing Payments
    Send bitcoin from your wallet to another bitcoin address. All transactions include a 0.0001 BTC miners fee.
    https://blockchain.info/merchant/$guid/payment?password=$main_password&second_password=$second_password&to=$address&amount=$amount&from=$from&shared=$shared&fee=$fee&note=$note
    */
    public String payment(String address, long amount) throws IOException {
        String uri
                = "https://blockchain.info/merchant/" + identifier
                + "/payment?password=" + password
                + "&second_password=" + secondPassword
                + "&to=" + address
                + "&amount=" + amount
                + "&note=" + "http://monroebox.wordpress.com/";
        String result = bciCall(uri);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jn = mapper.readTree(result);
        JsonNode messageNode = jn.path("message");
        String message = messageNode.asText();
        return message;
    }

    /*
    Making Outgoing Payments
    Send bitcoin from your wallet to another bitcoin address. All transactions include a 0.0001 BTC miners fee.
    https://blockchain.info/merchant/$guid/payment?password=$main_password&second_password=$second_password&to=$address&amount=$amount&from=$from&shared=$shared&fee=$fee&note=$note
    */
    public String payment(BoxKey bk) throws IOException {
        ECKey key = bk.getKey();
        String address = B58.encode(key.getPubKeyHash(), (byte) 0);
        long amount = bk.getTargetBalance() - bk.getCurrentBalance();
        String uri
                = "https://blockchain.info/merchant/" + identifier
                + "/payment?password=" + password
                + "&second_password=" + secondPassword
                + "&to=" + address
                + "&amount=" + amount
                + "&note=" + "http://monroebox.wordpress.com/";
        String result = bciCall(uri);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jn = mapper.readTree(result);
        JsonNode messageNode = jn.path("message");
        String message = messageNode.asText();
        return message;
    }

    /*
    Send Many Transactions
    Send a transaction to multiple recipients in the same transaction.
    https://blockchain.info/merchant/$guid/sendmany?password=$main_password&second_password=$second_password&recipients=$recipients&shared=$shared&fee=$fee
     */
    public String sendmany(List<BoxKey> bks) throws IOException {
        String uri
                = "https://blockchain.info/merchant/" + identifier
                + "/sendmany?password=" + password
                + "&second_password=" + secondPassword
                + "&recipients={";
        boolean firstOne = true;
        for (BoxKey bk : bks) {
            if (firstOne == false) {
                uri += ",";
            } else {
                firstOne = false;
            }
            String address = bk.getPublicKey();
            long amount = bk.getTargetBalance() - bk.getCurrentBalance();
            uri += "\"" + address + "\":" + amount;
        }
        uri += "}&note=" + "http://monroebox.wordpress.com/";
        System.out.println("uri: " + uri);
        String result = bciCall(uri);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jn = mapper.readTree(result);
        JsonNode messageNode = jn.path("message");
        String message = messageNode.asText();
        return message;
    }

    /*
    Send Many Transactions
    Send a transaction to multiple recipients in the same transaction.
    https://blockchain.info/merchant/$guid/sendmany?password=$main_password&second_password=$second_password&recipients=$recipients&shared=$shared&fee=$fee
     */
    public String sendmany(List<String> addresses, long amount) throws IOException {
        String uri
                = "https://blockchain.info/merchant/" + identifier
                + "/sendmany?password=" + password
                + "&second_password=" + secondPassword
                + "&recipients={";
        boolean firstOne = true;
        for (String address : addresses) {
            if (firstOne == false) {
                uri += ",";
            } else {
                firstOne = false;
            }
            uri += "\"" + address + "\":" + amount;
        }
        uri += "}&note=" + "http://monroebox.wordpress.com/";
        System.out.println("uri: " + uri);
        String result = bciCall(uri);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jn = mapper.readTree(result);
        JsonNode messageNode = jn.path("message");
        String message = messageNode.asText();
        return message;
    }

    //returns the dollars per btc at a 24 hr average rate
    //TODO:make a method that handles other currencies
    public double rateCheck() throws IOException {
        //https://blockchain.info/ticker
        //https://blockchain.info/q/24hrprice
        String result = bciCall("https://blockchain.info/q/24hrprice");
        double rate = Double.parseDouble(result);
        return rate;
    }

    //returns the rate of dollars per satoshi at a 24 hour market average
    //TODO:make a method that handles other currencies
    public long rateSatoshis() throws IOException {
        String result = bciCall("https://blockchain.info/q/24hrprice");
        double rate = Double.parseDouble(result);
        return (long) (rate * 100000000);
    }

    //this is the general http request method used for all api calls to blockchain.info
    public String bciCall(String request) throws MalformedURLException, IOException {
        URL url = new URL(request);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        InputStream ins = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);
        String inputLine, response = null;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            response = inputLine;
        }
        in.close();
        return response;
    }

}
