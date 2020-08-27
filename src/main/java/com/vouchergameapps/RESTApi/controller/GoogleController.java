package com.vouchergameapps.RESTApi.controller;

import com.google.gson.Gson;
import com.vouchergameapps.BackEnd.GoogleService.model.VoucherPackage;
import com.vouchergameapps.RESTApi.broker.RESTReceiver;
import com.vouchergameapps.RESTApi.broker.RESTSend;
import com.vouchergameapps.RESTApi.util.message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/GoogleService")
public class GoogleController {

    // -------------------Check Topup Package-------------------------
    @RequestMapping(value = "/CheckPackage", method = RequestMethod.GET)
    public ResponseEntity<?> CheckPackage(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

//        Gson g = new Gson();
        RESTReceiver receive = new RESTReceiver();
//        String tempreceiver = receive.receiveFromDB();
//        JSONParser parser = new JSONParser();
//        ArrayList<VoucherPackage> arrlistvoucher = new ArrayList();
////        List<VoucherPackage> prodList = g.fromJson(tempreceiver, VoucherPackage.class);
//        JSONArray array = (JSONArray)parser.parse(tempreceiver);
//
//
//        for(int i=0; i<array.size(); i++){
//            JSONObject obj = (JSONObject) array.get(i);
//            VoucherPackage p = g.fromJson(String.valueOf(obj), VoucherPackage.class);
//            arrlistvoucher.add(p);
//        }

        return new ResponseEntity<>(receive.receiveFromDB(), HttpStatus.OK);
    }

    // -------------------Order Package-------------------------
    @RequestMapping(value = "/OrderPackage", method = RequestMethod.POST)
    public ResponseEntity<?> OrderPackage(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }

    // -------------------Check Account Balance-------------------------
    @RequestMapping(value = "/CheckBalance", method = RequestMethod.GET)
    public ResponseEntity<?> CheckBalance(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }

    // -------------------Topup With Voucher-------------------------
    @RequestMapping(value = "/VoucherTopup", method = RequestMethod.POST)
    public ResponseEntity<?> VoucherTopup(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }
}
