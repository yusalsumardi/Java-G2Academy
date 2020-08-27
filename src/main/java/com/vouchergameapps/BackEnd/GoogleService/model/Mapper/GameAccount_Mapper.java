package com.vouchergameapps.BackEnd.GoogleService.model.Mapper;

import com.vouchergameapps.BackEnd.GoogleService.model.GameAccount;
import com.vouchergameapps.BackEnd.GoogleService.model.VoucherPackage;
import com.vouchergameapps.BackEnd.model.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GameAccount_Mapper {
    String getAllAccount = "SELECT * FROM gameaccount";
    String getAccountByNameaccount = "SELECT * FROM gameaccount WHERE nameaccount=#{nameaccount}";
    String updateAccountBalance = "UPDATE gameaccount SET balance = #{balance} WHERE nameaccount = #{nameaccount}";


    @Select(getAllAccount)
    @Results(value = {
            @Result(property = "idaccount", column = "idaccount"),
            @Result(property = "nameaccount", column = "nameaccount"),
            @Result(property = "gamename", column = "gamename"),
            @Result(property = "balance", column = "balance")
    })
    List<GameAccount> getAllAccount();

    @Select(getAccountByNameaccount)
    @Results(value = {
            @Result(property = "idaccount", column = "idaccount"),
            @Result(property = "nameaccount", column = "nameaccount"),
            @Result(property = "gamename", column = "gamename"),
            @Result(property = "balance", column = "balance")
    })
    GameAccount getAccountByNameaccount(String nameaccount);

    @Update(updateAccountBalance)
    void updateAccountBalance(Object account);
}
