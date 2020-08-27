package com.vouchergameapps.BackEnd.model.Mapper;

import com.vouchergameapps.BackEnd.model.Transaction;
import com.vouchergameapps.BackEnd.model.User;
import org.apache.ibatis.annotations.*;

public interface Transaction_Mapper {
    final String getTransactionById = "SELECT * FROM transaction Where idtransaction=#{idtransaction};";
    final String updateStatusPayment = "UPDATE transaction SET statuspayment = #{statuspayment}, paymethod = #{paymethod}, paydate = #{paydate} WHERE idtransaction = #{idtransaction}";
    final String insertTransactionOrder = "INSERT INTO transaction (idpackage, iduser, nameaccount, paymethod, orderdate, statuspayment, bill) VALUES (#{idpackage}, #{iduser}, #{nameaccount}, #{paymethod}, #{orderdate}, #{statuspayment}, #{bill})";
    final String insertTransactionVoucher = "INSERT INTO transaction (idlistvoucher, iduser, paymethod, orderdate, statuspayment, bill) VALUES (#{idlistvoucher}, #{iduser}, #{paymethod}, #{orderdate}, #{statuspayment}, #{bill})";

    @Select(getTransactionById)
    @Results(value = {
            @Result(property = "idtransaction", column = "idtransaction"),
            @Result(property = "idpackage", column = "idpackage"),
            @Result(property = "idvoucher", column = "idvoucher"),
            @Result(property = "iduser", column = "iduser"),
            @Result(property = "paymethod", column = "paymethod"),
            @Result(property = "orderdate", column = "orderdate"),
            @Result(property = "paydate", column = "paydate"),
            @Result(property = "statuspayment", column = "statuspayment"),
            @Result(property = "bill", column = "bill")
    })
    Transaction getTransactionById(int idtransaction);

    @Update(updateStatusPayment)
    void updateStatusPayment(Object transaction);

    @Insert(insertTransactionOrder)
    @Options(useGeneratedKeys = true, keyProperty = "idtransaction")
    void insertTransactionPackage(Object transaction);

    @Insert(insertTransactionVoucher)
    @Options(useGeneratedKeys = true, keyProperty = "idtransaction")
    void insertTransactionVoucher(Object transaction);

}
