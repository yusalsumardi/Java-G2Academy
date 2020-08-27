package com.vouchergameapps.BackEnd.model;

import java.util.Date;

public class Transaction {
    int idtransaction;
    int idpackage;
    int idlistvoucher;
    int iduser;
    String nameaccount;
    String paymethod;
    Date orderdate;
    Date paydate;
    String statuspayment;
    int bill;

    public int getIdlistvoucher() {
        return idlistvoucher;
    }

    public void setIdlistvoucher(int idlistvoucher) {
        this.idlistvoucher = idlistvoucher;
    }

    public String getNameaccount() {
        return nameaccount;
    }

    public void setNameaccount(String nameaccount) {
        this.nameaccount = nameaccount;
    }

    public void setIdpackage(int idpackage) {
        this.idpackage = idpackage;
    }

    public int getIdpackage() {
        return idpackage;
    }

    public String getStatuspayment() {
        return statuspayment;
    }

    public void setStatuspayment(String statuspayment) {
        this.statuspayment = statuspayment;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public int getIdtransaction() {
        return idtransaction;
    }

    public void setIdtransaction(int idtransaction) {
        this.idtransaction = idtransaction;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public Date getPaydate() {
        return paydate;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }
}