package com.vouchergameapps.BackEnd.MarketPlace.model.mapper;

import com.vouchergameapps.BackEnd.GoogleService.model.VoucherPackage;
import com.vouchergameapps.BackEnd.MarketPlace.model.VoucherGame;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface VoucherGame_Mapper {
    String getAllListVoucher = "SELECT * FROM listvoucher";
    String getVoucherListById = "SELECT * FROM listvoucher WHERE idlistvoucher = #{idlistvoucher}";
    String insertNewVoucher = "INSERT INTO vouchergame (idvoucher, codevoucher, statusvoucher, value, gamename) VALUES (#{idvoucher}, #{codevoucher}, #{statusvoucher}, #{value}, #{gamename})";
    String getVoucherByCode = "SELECT * FROM vouchergame WHERE codevoucher = #{codevoucher}";
    String updateVoucherStatus = "UPDATE vouchergame SET statusvoucher = #{statusvoucher} WHERE codevoucher = #{codevoucher}";


    @Select(getAllListVoucher)
    @Results(value = {
            @Result(property = "idlistvoucher", column = "idlistvoucher"),
            @Result(property = "price", column = "price"),
            @Result(property = "value", column = "value"),
            @Result(property = "gamename", column = "gamename")

    })
    List<VoucherGame> getAllListVoucher();

    @Select(getVoucherListById)
    @Results(value = {
            @Result(property = "idlistvoucher", column = "idlistvoucher"),
            @Result(property = "price", column = "price"),
            @Result(property = "value", column = "value"),
            @Result(property = "gamename", column = "gamename")
    })
    VoucherGame getVoucherListById(int idlistvoucher);

    @Select(getVoucherByCode)
    @Results(value = {
            @Result(property = "idvoucher", column = "idvoucher"),
            @Result(property = "codevoucher", column = "codevoucher"),
            @Result(property = "statusvoucher", column = "statusvoucher"),
            @Result(property = "value", column = "value"),
            @Result(property = "gamename", column = "gamename")
    })
    VoucherGame getVoucherByCode(int code);

    @Insert(insertNewVoucher)
    @Options(useGeneratedKeys = true, keyProperty = "idvoucher")
    void insertNewVoucher(Object voucher);

    @Update(updateVoucherStatus)
    void updateVoucherStatus(Object voucher);
}
