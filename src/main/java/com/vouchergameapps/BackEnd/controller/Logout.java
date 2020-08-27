package com.vouchergameapps.BackEnd.controller;

import com.vouchergameapps.BackEnd.broker.BESend;
import com.vouchergameapps.BackEnd.model.Mapper.User_Mapper;
import com.vouchergameapps.BackEnd.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.simple.JSONObject;

import java.io.Reader;

public class Logout {
    BESend beSend = new BESend();
    SqlSession session = null;

    //=======================Logout String===================
    public void logout(JSONObject jsn) throws Exception {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(User_Mapper.class);
        User_Mapper mapper = session.getMapper(User_Mapper.class);
        User cekuser = new User();

        String username = (String) jsn.get("username");
        String password = (String) jsn.get("password");
        cekuser.setUsername(username);
        cekuser.setPassword(password);

        User cekuserdb = mapper.getUserByUsername(username);
        boolean result = false;
        if (cekuser.getPassword().equals(cekuserdb.getPassword())){
            result = true;
            cekuserdb.setStatuslogin(false);
            mapper.updatestatus(cekuserdb);
            beSend.SendLog("BACKEND LOG: Logout Success");
        } else {
            result = false;
            beSend.SendLog("BACKEND LOG: Logout Failed, Wrong password!");
        }
        session.commit();
        session.close();
    }
}
