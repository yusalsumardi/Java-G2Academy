package com.vouchergameapps.BackEnd.Bank.model;

import com.vouchergameapps.BackEnd.GoogleService.model.GameAccount;
import org.apache.ibatis.annotations.*;

public interface Bank_Mapper {
    final String insertBankTf = "INSERT INTO tfbank (statustf, reknum) VALUES (#{statustf}, #{reknum})";
    final String insertBankVA = "INSERT INTO tfbank (statustf, nova) VALUES (#{statustf}, #{nova})";
    final String getBankTfbyNoVA = "SELECT * FROM tfbank WHERE nova = #{nova}";
    final String getBankTfbyIdTf = "SELECT * FROM tfbank WHERE idtfbank = #{idtfbank}";
    final String updateBankTfStatus = "UPDATE tfbank SET statustf = #{statustf} WHERE idtfbank = #{idtfbank}";

    @Select(getBankTfbyNoVA)
    @Results(value = {
            @Result(property = "idtfbank", column = "idtfbank"),
            @Result(property = "statustf", column = "statustf"),
            @Result(property = "nova", column = "nova")
    })
    Bank getBankTfbyNoVA(int nova);

    @Select(getBankTfbyIdTf)
    @Results(value = {
            @Result(property = "idtfbank", column = "idtfbank"),
            @Result(property = "statustf", column = "statustf"),
            @Result(property = "reknum", column = "reknum")
    })
    Bank getBankTfbyIdTf(int idtfbank);



    @Insert(insertBankTf)
    @Options(useGeneratedKeys = true, keyProperty = "idtfbank")
    void insertBankTf(Object bank);

    @Insert(insertBankVA)
    @Options(useGeneratedKeys = true, keyProperty = "idtfbank")
    void insertBankVA(Object bank);

    @Update(updateBankTfStatus)
    void updateBankTfStatus(Object bank);
}
