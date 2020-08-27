package com.vouchergameapps.RESTApi.controller;

import com.vouchergameapps.RESTApi.broker.RESTReceiver;
import com.vouchergameapps.RESTApi.broker.RESTSend;
import com.vouchergameapps.RESTApi.util.message;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Payment")
public class PaymentController{
    // -------------------Check Status Payment-------------------------
    @RequestMapping(value = "/CheckPayment", method = RequestMethod.GET)
    public ResponseEntity<?> CheckPayment(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }

    // -------------------Payment-------------------------
    @RequestMapping(value = "/Payment", method = RequestMethod.POST)
    public ResponseEntity<?> Payment(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }
}
