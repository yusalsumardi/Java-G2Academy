package com.vouchergameapps.BackEnd.GoogleService.model;

public class GameAccount {
    int idaccount;
    String nameaccount;
    String gamename;
    int balance;

    public void setIdaccount(int idaccount) {
        this.idaccount = idaccount;
    }

    public int getIdaccount() {
        return idaccount;
    }

    public void setNameaccount(String nameaccount) {
        this.nameaccount = nameaccount;
    }

    public String getNameaccount() {
        return nameaccount;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGamename() {
        return gamename;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
}
