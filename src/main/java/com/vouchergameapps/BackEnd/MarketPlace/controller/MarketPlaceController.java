package com.vouchergameapps.BackEnd.MarketPlace.controller;

import com.google.gson.Gson;
import com.vouchergameapps.BackEnd.Bank.TransferBank;
import com.vouchergameapps.BackEnd.Bank.VABank;
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

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class MarketPlaceController {
    public void run(JSONObject jsonuser) throws Exception {
        Adapter adapter = new Adapter();
        BESend beSend = new BESend();
        SqlSession session = null;
        TransferBank tf = new TransferBank();
        VABank va = new VABank();
        String service = (String) jsonuser.get("service");
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        switch (service){
            case "checkvoucher":
                session = sqlSessionFactory.openSession();
                session.getConfiguration().addMapper(VoucherGame_Mapper.class);
                VoucherGame_Mapper mapperVoucher = session.getMapper(VoucherGame_Mapper.class);
                List<VoucherGame> lstvoucher = mapperVoucher.getAllListVoucher();
                session.commit();
                Gson g = new Gson();
                beSend.SendLog(g.toJson(lstvoucher));
                break;
            case "ordervoucher":
                Transaction newtransaction = adapter.parseMarketPlace(jsonuser);
                session = sqlSessionFactory.openSession();
                session.getConfiguration().addMapper(User_Mapper.class);
                User_Mapper mapperUser = session.getMapper(User_Mapper.class);
                User newuser = new User();
                newuser = mapperUser.getUserByUsername((String) jsonuser.get("username"));
                session.commit();
                try {
                    session.getConfiguration().addMapper(VoucherGame_Mapper.class);
                    VoucherGame_Mapper mapperVoucher1 = session.getMapper(VoucherGame_Mapper.class);
                    VoucherGame orderVoucher = new VoucherGame();
                    orderVoucher = mapperVoucher1.getVoucherListById(newtransaction.getIdlistvoucher());
                    session.commit();

                    newtransaction.setIduser(newuser.getIduser());
                    newtransaction.setStatuspayment("Waiting to payment");
                    newtransaction.setBill(orderVoucher.getPrice());
                    System.out.println(newtransaction.getPaymethod());
                    session.getConfiguration().addMapper(Transaction_Mapper.class);
                    Transaction_Mapper mapperTransaction = session.getMapper(Transaction_Mapper.class);
                    mapperTransaction.insertTransactionVoucher(newtransaction);

                    if(newtransaction.getPaymethod().equals("tfbank")){
                        tf.run();
                    }else if(newtransaction.getPaymethod().equals("vabank")){
                        va.run();
                    }

                    beSend.SendLog("Order have been set!");
                    session.commit();
                } catch (Exception e) {
                    System.out.println(e);
                    beSend.SendLog("Voucher tidak ada");
                }
                session.close();

                break;
        }

    }

    public void generatevoucher(Transaction transaction) throws Exception{
        BESend beSend = new BESend();
        SqlSession session = null;
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        VoucherGame tempnewvoucher = new VoucherGame();
        int idlistvoucher = transaction.getIdlistvoucher();
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(VoucherGame_Mapper.class);
        VoucherGame_Mapper mapperVoucher = session.getMapper(VoucherGame_Mapper.class);
        tempnewvoucher = mapperVoucher.getVoucherListById(idlistvoucher);

        VoucherGame newvoucher = new VoucherGame();
        newvoucher.setCodevoucher((int) (Math.random()*100000000));
        newvoucher.setStatusvoucher(true);
        newvoucher.setGamename(tempnewvoucher.getGamename());
        newvoucher.setValue(tempnewvoucher.getValue());
        int code = newvoucher.getCodevoucher();
        mapperVoucher.insertNewVoucher(newvoucher);
        session.commit();
        session.close();

        beSend.SendLog("Code Voucher Anda = " + code);
    }
}
