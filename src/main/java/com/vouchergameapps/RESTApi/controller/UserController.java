package com.vouchergameapps.RESTApi.controller;

import com.vouchergameapps.RESTApi.broker.RESTReceiver;
import com.vouchergameapps.RESTApi.broker.RESTSend;
import com.vouchergameapps.RESTApi.util.message;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/User")
public class UserController {

    // -------------------Register User-------------------------
    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    public ResponseEntity<?> Register(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }


    // -------------------Login User-------------------------
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public ResponseEntity<?> LoginUser(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }

    // -------------------Logout User-------------------------
    @RequestMapping(value = "/Logout", method = RequestMethod.POST)
    public ResponseEntity<?> LogoutUser(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }
}
