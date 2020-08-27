package com.vouchergameapps.BackEnd.model;

import com.vouchergameapps.BackEnd.Bank.TransferBank;
import com.vouchergameapps.BackEnd.Bank.model.Bank;
import com.vouchergameapps.BackEnd.GoogleService.model.GameAccount;
import com.vouchergameapps.BackEnd.MarketPlace.model.VoucherGame;
import com.vouchergameapps.BackEnd.controller.Payment;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Adapter {

    //=======================Parse Inputan String===================
    public JSONObject parse(String str) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(str);
        JSONObject temp1 = (JSONObject) obj;
        return temp1;
    }

    //=======================Parse Payment ===================
    public Transaction parsePayment(JSONObject py) throws Exception {
        JSONObject payment = (JSONObject) py.get("payment");
        long idtransactiontemp = (long) payment.get("idtransaction");
        int idtransaction = (int) idtransactiontemp;
        String method = (String) payment.get("method");
        String datepaymenttemp = (String) payment.get("date");
        Date datepayment = new SimpleDateFormat("dd/MM/yyyy").parse(datepaymenttemp);
        Transaction newpayment = new Transaction();
        newpayment.setIdtransaction(idtransaction);
        newpayment.setPaymethod(method);
        newpayment.setPaydate(datepayment);
        return newpayment;
    }

    //=======================Parse order GoogleService===================
    public Transaction parseGoogleService(JSONObject gs) throws Exception {
        JSONObject orderpackage = (JSONObject) gs.get("orderpackage");
        long idpackagetemp = (long) orderpackage.get("idpackage");
        int idpackage = (int) idpackagetemp;
        String nameaccount = (String) orderpackage.get("nameaccount");
        String payment = (String) orderpackage.get("payment");
        String tempdate = (String) orderpackage.get("orderdate");
        Date orderdate = new SimpleDateFormat("dd/MM/yyyy").parse(tempdate);
        Transaction newtransaction = new Transaction();
        newtransaction.setOrderdate(orderdate);
        newtransaction.setIdpackage(idpackage);
        newtransaction.setPaymethod(payment);
        newtransaction.setNameaccount(nameaccount);
        return newtransaction;
    }

    //=======================Parse check balance Account===================
    public GameAccount parseBalanceAccount(JSONObject cba) throws Exception {
        JSONObject detailcba = (JSONObject) cba.get("checkbalanceaccount");
        String nameaccount = (String) detailcba.get("nameaccount");
        GameAccount checkaccount = new GameAccount();
        checkaccount.setNameaccount(nameaccount);
        return checkaccount;
    }

    //=======================Parse order MarketPlace===================
    public Transaction parseMarketPlace(JSONObject mp) throws Exception {
        JSONObject ordervoucher = (JSONObject) mp.get("ordervoucher");
        String paymethod = (String) ordervoucher.get("payment");
        long idlistvouchertemp = (long) ordervoucher.get("idlistvoucher");
        int idlistvoucher = (int) idlistvouchertemp;
        String tempdate = (String) ordervoucher.get("orderdate");
        Date orderdate = new SimpleDateFormat("dd/MM/yyyy").parse(tempdate);
        Transaction newtransaction = new Transaction();
        newtransaction.setPaymethod(paymethod);
        newtransaction.setOrderdate(orderdate);
        newtransaction.setIdlistvoucher(idlistvoucher);
        return newtransaction;
    }

    //=======================Parse Topup Voucher===================
    public VoucherGame parseTopupVoucher(JSONObject tp) throws Exception {
        JSONObject tpvoucher = (JSONObject) tp.get("topupvoucher");
        long idcodevoucher = (long) tpvoucher.get("codevoucher");
        int codevoucher = (int) idcodevoucher;
        VoucherGame newvoucher = new VoucherGame();
        newvoucher.setCodevoucher(codevoucher);
        System.out.println(codevoucher);
        return newvoucher;
    }

    //=======================Parse TfBank===================
    public Bank parseTfBank(JSONObject tfbank) throws Exception {
        JSONObject detailtfbank = (JSONObject) tfbank.get("payment");
        long tempnova = (long) detailtfbank.get("nova");
        int nova = (int) tempnova;
        Bank newbank = new Bank();
        newbank.setNova(nova);
        return newbank;
    }

    //=======================Parse VABank===================
    public Bank parseVABank(JSONObject vabank) throws Exception {
        JSONObject detailtfbank = (JSONObject) vabank.get("payment");
        long tempid = (long) detailtfbank.get("idtfbank");
        int idtfbank = (int) tempid;
        Bank newbank = new Bank();
        newbank.setIdtfbank(idtfbank);
        return newbank;
    }

    //=======================Parse for Check Transaction===================
    public Transaction parseCheckTrans(JSONObject trans) throws Exception {
        JSONObject detailttrans = (JSONObject) trans.get("cekpayment");
        long tempid = (long) detailttrans.get("idtransaction");
        int idtrans = (int) tempid;
        Transaction newtrans = new Transaction();
        newtrans.setIdtransaction(idtrans);
        return newtrans;
    }

//    //=======================Parse JSON to User===================
//    public User parseToUser(JSONObject usr) throws Exception {
//
//    }
}
