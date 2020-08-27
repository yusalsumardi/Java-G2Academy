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
@RequestMapping("/Marketplace")
public class MarketplaceController {

    // -------------------Check Voucher List-------------------------
    @RequestMapping(value = "/VoucherList", method = RequestMethod.GET)
    public ResponseEntity<?> VoucherList(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(receive.receiveFromDB(), HttpStatus.OK);
    }

    // -------------------Order Voucher Voucher-------------------------
    @RequestMapping(value = "/OrderVoucher", method = RequestMethod.POST)
    public ResponseEntity<?> OrderVoucher(@RequestBody JSONObject usr) throws Exception {

        RESTSend send = new RESTSend();
        send.sendJson(usr.toString());

        RESTReceiver receive = new RESTReceiver();
//        receive.receiveFromDB();

        return new ResponseEntity<>(new message("Dari REST : " + receive.receiveFromDB()), HttpStatus.OK);
    }
}
