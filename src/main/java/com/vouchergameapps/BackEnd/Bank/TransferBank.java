package com.vouchergameapps.BackEnd.Bank;

import com.vouchergameapps.BackEnd.Bank.model.Bank;
import com.vouchergameapps.BackEnd.Bank.model.Bank_Mapper;
import com.vouchergameapps.BackEnd.GoogleService.model.Mapper.VoucherPackage_Mapper;
import com.vouchergameapps.BackEnd.GoogleService.model.VoucherPackage;
import com.vouchergameapps.BackEnd.broker.BESend;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class TransferBank {
    public void run() throws Exception {
        Bank newtransfer = new Bank();
        newtransfer.setReknum(123456);
        newtransfer.setStatustf(false);
        SqlSession session = null;
        BESend beSend = new BESend();

        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Bank_Mapper.class);
        Bank_Mapper mapperTfBank = session.getMapper(Bank_Mapper.class);
        mapperTfBank.insertBankTf(newtransfer);
        beSend.SendLog("Silahkan Transfer ke Rekening " + newtransfer.getReknum());
        session.commit();
        session.close();
    }
}
