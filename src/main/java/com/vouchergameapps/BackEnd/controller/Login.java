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

public class Login {
    BESend beSend = new BESend();
    SqlSession session = null;

    //=======================Login String===================
    public void login(JSONObject jsn) throws Exception {
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
//        System.out.println("Password login : " +cekuser.getPassword());
//        System.out.println("Password di DB : " + cekuserdb.getPassword());
        boolean result = false;
        if (cekuser.getPassword().equals(cekuserdb.getPassword())){
            result = true;
            cekuserdb.setStatuslogin(true);
            mapper.updatestatus(cekuserdb);
            beSend.SendLog("BACKEND LOG: Login Success, Welcome back " + cekuserdb.getName());
        } else {
            result = false;
            beSend.SendLog("BACKEND LOG: Login Failed, Wrong password!");
        }
        session.commit();
        session.close();
    }

    //=======================Cek Login Status===================
    public boolean ceklogin(JSONObject jsn) throws Exception {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(User_Mapper.class);
        User_Mapper mapper = session.getMapper(User_Mapper.class);
        User cekuser = new User();

        String username = (String) jsn.get("username");
        cekuser.setUsername(username);

        User cekuserdb = mapper.getUserByUsername(username);
        boolean result;
        result = cekuserdb.getStatuslogin();
        session.commit();
        session.close();
        return result;
    }

}
