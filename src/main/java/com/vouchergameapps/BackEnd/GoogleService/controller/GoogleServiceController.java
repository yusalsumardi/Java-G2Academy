package com.vouchergameapps.BackEnd.GoogleService.controller;

import com.google.gson.Gson;
import com.vouchergameapps.BackEnd.Bank.TransferBank;
import com.vouchergameapps.BackEnd.Bank.VABank;
import com.vouchergameapps.BackEnd.GoogleService.model.GameAccount;
import com.vouchergameapps.BackEnd.GoogleService.model.Mapper.GameAccount_Mapper;
import com.vouchergameapps.BackEnd.GoogleService.model.Mapper.VoucherPackage_Mapper;
import com.vouchergameapps.BackEnd.GoogleService.model.VoucherPackage;
import com.vouchergameapps.BackEnd.MarketPlace.model.VoucherGame;
import com.vouchergameapps.BackEnd.MarketPlace.model.mapper.VoucherGame_Mapper;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Reader;
import java.util.List;

public class GoogleServiceController extends Thread{
    public void run(JSONObject jsonuser) throws Exception {
        Adapter adapter = new Adapter();
        BESend beSend = new BESend();
        TransferBank tf = new TransferBank();
        VABank va = new VABank();
        SqlSession session = null;
        String service = (String) jsonuser.get("service");
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        switch (service){
            case "checkpackage":
                session = sqlSessionFactory.openSession();
                session.getConfiguration().addMapper(VoucherPackage_Mapper.class);
                VoucherPackage_Mapper mapperPackage = session.getMapper(VoucherPackage_Mapper.class);
                List<VoucherPackage> lstpackage = mapperPackage.getAllPackage();
                session.commit();

                Gson g = new Gson();
                beSend.SendLog(g.toJson(lstpackage));
                session.close();
                break;
            case "orderpackage":
                Transaction newtransaction = adapter.parseGoogleService(jsonuser);
                session = sqlSessionFactory.openSession();
                session.getConfiguration().addMapper(User_Mapper.class);
                User_Mapper mapperUser = session.getMapper(User_Mapper.class);
                User newuser = new User();
                newuser = mapperUser.getUserByUsername((String) jsonuser.get("username"));
                session.commit();

                session.getConfiguration().addMapper(GameAccount_Mapper.class);
                GameAccount_Mapper mapperAccount = session.getMapper(GameAccount_Mapper.class);
                GameAccount newaccount = new GameAccount();
                newaccount = mapperAccount.getAccountByNameaccount(newtransaction.getNameaccount());
                session.commit();

                session.getConfiguration().addMapper(VoucherPackage_Mapper.class);
                VoucherPackage_Mapper mapperPackage1 = session.getMapper(VoucherPackage_Mapper.class);
                VoucherPackage orderPackage = new VoucherPackage();
                orderPackage = mapperPackage1.getPackageById(newtransaction.getIdpackage());
                session.commit();

                newtransaction.setIduser(newuser.getIduser());
                newtransaction.setStatuspayment("Waiting to payment");
                newtransaction.setBill(orderPackage.getPrice());
                newtransaction.setPaymethod(newtransaction.getPaymethod());

                if(newtransaction.getPaymethod().equals("tfbank")){
                    tf.run();
                }else if(newtransaction.getPaymethod().equals("vabank")){
                    va.run();
                }

                session.getConfiguration().addMapper(Transaction_Mapper.class);
                Transaction_Mapper mapperTransaction = session.getMapper(Transaction_Mapper.class);
                System.out.println(newtransaction.getPaymethod());
                mapperTransaction.insertTransactionPackage(newtransaction);
                beSend.SendLog("Order have been set!");
                session.commit();
                session.close();
                break;
            case "checkbalanceaccount":
                GameAccount accounttemp = adapter.parseBalanceAccount(jsonuser);
                try {
                    session = sqlSessionFactory.openSession();
                    session.getConfiguration().addMapper(GameAccount_Mapper.class);
                    GameAccount_Mapper mapperAccount1 = session.getMapper(GameAccount_Mapper.class);
                    GameAccount account = new GameAccount();
                    account = mapperAccount1.getAccountByNameaccount(accounttemp.getNameaccount());
                    beSend.SendLog("BACKEND LOG: Name Account: " + account.getNameaccount() +
                            "\nID Account: " + account.getIdaccount() +
                            "\nGame: " + account.getGamename() +
                            "\nBalance: " + account.getBalance());

                } catch (Exception e) {
                    beSend.SendLog("Account name does not exist");
                }
                session.commit();
                session.close();
                break;
            case "topupvoucher":
                String nameaccount = (String) jsonuser.get("nameaccount");
                VoucherGame tempvoucer = adapter.parseTopupVoucher(jsonuser);

                try {
                    session = sqlSessionFactory.openSession();
                    session.getConfiguration().addMapper(VoucherGame_Mapper.class);
                    VoucherGame_Mapper mapperVoucher = session.getMapper(VoucherGame_Mapper.class);
                    VoucherGame newvoucher = mapperVoucher.getVoucherByCode(tempvoucer.getCodevoucher());
                    session.commit();

                    session.getConfiguration().addMapper(GameAccount_Mapper.class);
                    GameAccount_Mapper mapperAccount1 = session.getMapper(GameAccount_Mapper.class);
                    GameAccount newaccount1 = mapperAccount1.getAccountByNameaccount(nameaccount);

                    if(newvoucher.getStatusvoucher()){
                        int newbalance = newaccount1.getBalance()+newvoucher.getValue();
                        newaccount1.setBalance(newbalance);
                        newvoucher.setStatusvoucher(false);
                        mapperAccount1.updateAccountBalance(newaccount1);
                        mapperVoucher.updateVoucherStatus(newvoucher);
                        session.commit();
                        beSend.SendLog("Topup Success");
                    } else {
                        beSend.SendLog("Topup Failed, Voucher already used");
                    }

                } catch (Exception e) {
                    beSend.SendLog("Topup Failed");
                }
                session.close();

                break;
    }
    }

    public void updatedata(JSONObject jsonuser) throws Exception {
        Adapter adapter = new Adapter();
        BESend beSend = new BESend();
        SqlSession session = null;

        try{
            Thread.sleep(5000);
            Transaction trtemp = adapter.parsePayment(jsonuser);
            Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
            session.getConfiguration().addMapper(Transaction_Mapper.class);
            Transaction_Mapper mapperTransaction = session.getMapper(Transaction_Mapper.class);
            Transaction tr = new Transaction();
            tr = mapperTransaction.getTransactionById(trtemp.getIdtransaction());
            session.commit();

            String Checkstatus = tr.getStatuspayment();
            if(Checkstatus.equals("Done")){
                session.getConfiguration().addMapper(VoucherPackage_Mapper.class);
                VoucherPackage_Mapper mapperPackage = session.getMapper(VoucherPackage_Mapper.class);
                VoucherPackage selectedpackage = new VoucherPackage();
                selectedpackage = mapperPackage.getPackageById(tr.getIdpackage());
                session.commit();


                session.getConfiguration().addMapper(GameAccount_Mapper.class);
                GameAccount_Mapper mapperAccount = session.getMapper(GameAccount_Mapper.class);
                GameAccount newaccount = new GameAccount();
                newaccount = mapperAccount.getAccountByNameaccount(tr.getNameaccount());
                int newBalance = (newaccount.getBalance()+selectedpackage.getValue());
                newaccount.setBalance(newBalance);
                mapperAccount.updateAccountBalance(newaccount);
                session.commit();
                beSend.SendLog("Balance have been added ! ");
            }
            session.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
