package com.vouchergameapps.BackEnd.GoogleService.model.Mapper;

import com.vouchergameapps.BackEnd.GoogleService.model.VoucherPackage;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VoucherPackage_Mapper {
    String getAllPackage = "SELECT * FROM voucherpackage";
    String getPackageById = "SELECT * FROM voucherpackage WHERE idpackage = #{idpackage}";


    @Select(getAllPackage)
    @Results(value = {
            @Result(property = "idpackage", column = "idpackage"),
            @Result(property = "price", column = "price"),
            @Result(property = "value", column = "value"),
            @Result(property = "gamename", column = "gamename")
    })
    List<VoucherPackage> getAllPackage();

    @Select(getPackageById)
    @Results(value = {
            @Result(property = "idpackage", column = "idpackage"),
            @Result(property = "price", column = "price"),
            @Result(property = "value", column = "value"),
            @Result(property = "gamename", column = "gamename")
    })
    VoucherPackage getPackageById(int idpackage);
}
