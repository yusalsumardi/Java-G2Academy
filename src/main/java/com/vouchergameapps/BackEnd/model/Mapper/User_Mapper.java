package com.vouchergameapps.BackEnd.model.Mapper;

import com.vouchergameapps.BackEnd.model.User;
import org.apache.ibatis.annotations.*;

public interface User_Mapper {
    final String getPassByUsername = "SELECT password FROM user WHERE username=#{username};";
    final String insertUser = "INSERT INTO user (username, password, name, statuslogin, balance) VALUES (#{username}, #{password}, #{name}, #{statuslogin}, #{balance})";
    final String getUserByUsername = "SELECT * FROM user Where username=#{username};";
    final String updateStatusLogin = "UPDATE user SET statuslogin = #{statuslogin} WHERE iduser = #{iduser}";
    final String updateBalance = "UPDATE user SET balance = #{balance} WHERE iduser = #{iduser}";
    final String checkUserExists = "SELECT * FROM user WHERE username=#{username}";

    @Select(getUserByUsername)
    @Results(value = {
            @Result(property = "iduser", column = "iduser"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "statuslogin", column = "statuslogin"),
            @Result(property = "balance", column = "balance")
    })
    User getUserByUsername(String username);

    @Select(getPassByUsername)
    @Results(value = {
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "statuslogin", column = "statuslogin")
    })
    User getPassByUsername(String username);

    @Insert(insertUser)
    @Options(useGeneratedKeys = true, keyProperty = "iduser")
    void insertUser(Object user);

    @Update(updateStatusLogin)
    void updatestatus(Object user);

    @Update(updateBalance)
    void updateBalance(Object user);

    @Select(checkUserExists)
    boolean checkUserExists(@Param("email")String username);
}
