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

public class Register {
    BESend beSend = new BESend();
    SqlSession session = null;

    public void register(JSONObject jsn) throws Exception {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(User_Mapper.class);
        User_Mapper mapper = session.getMapper(User_Mapper.class);
        User newuser = new User();

        String username = (String) jsn.get("username");
        String password = (String) jsn.get("password");
        String name = (String) jsn.get("name");
        newuser.setUsername(username);
        newuser.setPassword(password);
        newuser.setName(name);
        newuser.setStatuslogin(false);
        newuser.setBalance(0);

        try {
            mapper.insertUser(newuser);
            beSend.SendLog("BACKEND LOG: Register Success, Welcome " + newuser.getName());
        } catch (Exception e){
            beSend.SendLog("BACKEND LOG: Register Failed");
        }
        session.commit();
        session.close();
    }
}
