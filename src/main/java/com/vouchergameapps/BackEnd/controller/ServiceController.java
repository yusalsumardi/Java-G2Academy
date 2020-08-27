package com.vouchergameapps.BackEnd.controller;

import com.vouchergameapps.BackEnd.GoogleService.controller.GoogleServiceController;
import com.vouchergameapps.BackEnd.broker.BESend;
import com.vouchergameapps.BackEnd.model.Adapter;
import com.vouchergameapps.BackEnd.MarketPlace.controller.MarketPlaceController;
import org.json.simple.JSONObject;

public class ServiceController {
    Adapter adapter = new Adapter();
    Login lgn = new Login();
    Register rgs = new Register();
    Logout lgt = new Logout();
    Payment pay = new Payment();
    BESend beSend = new BESend();
    GoogleServiceController gs = new GoogleServiceController();
    MarketPlaceController mp = new MarketPlaceController();


    public void run(String str) throws Exception {
        JSONObject jsonuser = adapter.parse(str);
        boolean ceklogin;
        String task = (String) jsonuser.get("task");
        switch (task){
            case "login":
                try {
                    lgn.login(jsonuser);
                } catch (Exception e){
                    beSend.SendLog("BACKEND LOG: Login Failed");
                }
                break;
            case "register":
                rgs.register(jsonuser);
                break;
            case "logout":
                lgt.logout(jsonuser);
                break;
            case "payment":
                ceklogin = lgn.ceklogin(jsonuser);
                if(ceklogin){
                    pay.pay(jsonuser);
                }
                else {
                    beSend.SendLog("Please login first!");
                }
                break;
            case "cekpayment":
                ceklogin = lgn.ceklogin(jsonuser);
                if(ceklogin){
                    pay.cek(jsonuser);
                }
                else {
                    beSend.SendLog("Please login first!");
                }
                break;
            case "google service":
                ceklogin = lgn.ceklogin(jsonuser);
                if(ceklogin) {
                    gs.run(jsonuser);
                }
                else {
                    beSend.SendLog("Please login first!");
                }
                break;
            case "marketplace":
                ceklogin = lgn.ceklogin(jsonuser);
                if(ceklogin) {
                    mp.run(jsonuser);
                }
                else {
                    beSend.SendLog("Please login first!");
                }
                break;
        }
    }
}
