package com.vouchergameapps.BackEnd.Bank;

import com.vouchergameapps.BackEnd.Bank.model.Bank;
import com.vouchergameapps.BackEnd.Bank.model.Bank_Mapper;
import com.vouchergameapps.BackEnd.broker.BESend;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

public class VABank {
    public void run() throws Exception {
        Bank newtransfer = new Bank();
        int genVA = (int) (Math.random()*100000000);
        newtransfer.setNova(genVA);
        newtransfer.setStatustf(false);
        SqlSession session = null;
        BESend beSend = new BESend();

        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(Bank_Mapper.class);
        Bank_Mapper mapperTfBank = session.getMapper(Bank_Mapper.class);
        mapperTfBank.insertBankVA(newtransfer);
        beSend.SendLog("Silahkan Transfer dengan nomor VA " + newtransfer.getNova());
        session.commit();
        session.close();
    }
}
