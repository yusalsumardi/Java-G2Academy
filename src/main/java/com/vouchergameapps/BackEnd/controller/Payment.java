package com.vouchergameapps.BackEnd.controller;

import com.vouchergameapps.BackEnd.Bank.model.Bank;
import com.vouchergameapps.BackEnd.Bank.model.Bank_Mapper;
import com.vouchergameapps.BackEnd.GoogleService.controller.GoogleServiceController;
import com.vouchergameapps.BackEnd.MarketPlace.controller.MarketPlaceController;
import com.vouchergameapps.BackEnd.broker.BESend;
import com.vouchergameapps.BackEnd.model.Adapter;
import com.vouchergameapps.BackEnd.model.Mapper.Transaction_Mapper;
import com.vouchergameapps.BackEnd.model.Mapper.User_Mapper;
import com.vouchergameapps.BackEnd.model.Transaction;
import com.vouchergameapps.BackEnd.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.Reader;


public class Payment {
    Adapter adapter = new Adapter();
    SqlSession session = null;
    BESend beSend = new BESend();
    GoogleServiceController gs = new GoogleServiceController();
    MarketPlaceController mp = new MarketPlaceController();


    public void pay(JSONObject jsonuser) throws Exception {
        Transaction newtransaction = adapter.parsePayment(jsonuser);
        String usernamejson = (String) jsonuser.get("username");

        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Transaction_Mapper.class);
        Transaction_Mapper mapperTransactionCheck = session.getMapper(Transaction_Mapper.class);
        Transaction transactioncheck = mapperTransactionCheck.getTransactionById(newtransaction.getIdtransaction());
        session.commit();
        session.close();

        String Checkstatus = transactioncheck.getStatuspayment();
        if( !Checkstatus.equals("Done")){
            session = sqlSessionFactory.openSession();
            Transaction_Mapper mapperTransaction = session.getMapper(Transaction_Mapper.class);
            Transaction transaction = mapperTransaction.getTransactionById(newtransaction.getIdtransaction());
            session.commit();
            if(newtransaction.getPaymethod().equals("emoney")){
                session.getConfiguration().addMapper(User_Mapper.class);
                User_Mapper mapperUser = session.getMapper(User_Mapper.class);
                User user = mapperUser.getUserByUsername(usernamejson);
                int updatebalance = user.getBalance()-transaction.getBill();
                System.out.println(updatebalance);
                if(updatebalance>=0){
                    user.setBalance(updatebalance);
                    mapperUser.updateBalance(user);
                    session.commit();
                    beSend.SendLog("BACKEND LOG: Payment Success");

                    transaction.setStatuspayment("Done");
                    transaction.setPaymethod("emoney");
                    transaction.setPaydate(newtransaction.getPaydate());
                    Transaction_Mapper mapperTransactionAfter = session.getMapper(Transaction_Mapper.class);
                    mapperTransactionAfter.updateStatusPayment(transaction);
                    session.commit();

                    Transaction PraPayment = new Transaction();
                    PraPayment = mapperTransactionAfter.getTransactionById(newtransaction.getIdtransaction());
                    if(PraPayment.getIdpackage()!=0){
                        gs.updatedata(jsonuser);
                    }else if(PraPayment.getIdlistvoucher()!=0){
                        int test = transaction.getIdlistvoucher();
                        System.out.println(test);
                        mp.generatevoucher(transaction);
                    }
                } else {
                    beSend.SendLog("BACKEND LOG: your balance is not enough!");
                }
                session.close();

            }
            else if(newtransaction.getPaymethod().equals("vabank")){
                Bank novatemp = adapter.parseTfBank(jsonuser);
                session = sqlSessionFactory.openSession();
                session.getConfiguration().addMapper(Bank_Mapper.class);
                Bank_Mapper mapperBank = session.getMapper(Bank_Mapper.class);
                Bank newbank = mapperBank.getBankTfbyNoVA(novatemp.getNova());
                session.commit();

                if(newbank.getStatustf() == false){
                    newbank.setStatustf(true);
                    mapperBank.updateBankTfStatus(newbank);
                    session.commit();

                    transaction.setPaydate(newtransaction.getPaydate());
                    transaction.setStatuspayment("Done");
                    transaction.setPaymethod("vabank");

                    Transaction_Mapper mapperTransactionAfter = session.getMapper(Transaction_Mapper.class);
                    mapperTransactionAfter.updateStatusPayment(transaction);
                    session.commit();
                    beSend.SendLog("BACKEND LOG: Payment Success");

                    Transaction PraPayment = new Transaction();
                    PraPayment = mapperTransactionAfter.getTransactionById(newtransaction.getIdtransaction());
                    if(PraPayment.getIdpackage()!=0){
                        gs.updatedata(jsonuser);
                    }else if(PraPayment.getIdlistvoucher()!=0){
                        mp.generatevoucher(transaction);
                    }
                } else {
                    beSend.SendLog("BACKEND LOG: Payment failed, Payment has been paid");
                }
                session.commit();
                session.close();
            }
            else if(newtransaction.getPaymethod().equals("tfbank")){
                Bank banktemp = adapter.parseVABank(jsonuser);
                session = sqlSessionFactory.openSession();
                session.getConfiguration().addMapper(Bank_Mapper.class);
                Bank_Mapper mapperBank = session.getMapper(Bank_Mapper.class);
                Bank newbank = mapperBank.getBankTfbyIdTf(banktemp.getIdtfbank());
                session.commit();

                if(newbank.getStatustf() == false){
                    newbank.setStatustf(true);
                    mapperBank.updateBankTfStatus(newbank);
                    session.commit();

                    transaction.setPaydate(newtransaction.getPaydate());
                    transaction.setStatuspayment("Done");
                    transaction.setPaymethod("tfbank");

                    Transaction_Mapper mapperTransactionAfter = session.getMapper(Transaction_Mapper.class);
                    mapperTransactionAfter.updateStatusPayment(transaction);
                    session.commit();
                    beSend.SendLog("BACKEND LOG: Payment Success");

                    Transaction PraPayment = new Transaction();
                    PraPayment = mapperTransactionAfter.getTransactionById(newtransaction.getIdtransaction());
                    if(PraPayment.getIdpackage()!=0){
                        gs.updatedata(jsonuser);
                    }else if(PraPayment.getIdlistvoucher()!=0){
                        mp.generatevoucher(transaction);
                    }
                } else {
                    beSend.SendLog("BACKEND LOG: Payment failed, Payment has been paid");
                }
                session.commit();
                session.close();
            }
        }
        else {
            beSend.SendLog("BACKEND LOG: Payment failed, Payment has been paid");
        }
    }

    public void cek(JSONObject jsonuser) throws Exception {
        Transaction temptrans = adapter.parseCheckTrans(jsonuser);

        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        try {
            session = sqlSessionFactory.openSession();
            session.getConfiguration().addMapper(Transaction_Mapper.class);
            Transaction_Mapper mapperTransactionCheck = session.getMapper(Transaction_Mapper.class);
            Transaction transactioncheck = mapperTransactionCheck.getTransactionById(temptrans.getIdtransaction());
            session.commit();
            session.close();

            beSend.SendLog("BACKEND LOG: Payment Status : " + transactioncheck.getStatuspayment());
        } catch (Exception e){
            beSend.SendLog("BACKEND LOG: Payment ID Not Found!");
        }


    }
}
