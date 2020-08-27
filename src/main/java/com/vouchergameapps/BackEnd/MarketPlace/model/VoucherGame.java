package com.vouchergameapps.BackEnd.MarketPlace.model;

public class VoucherGame {
    int idlistvoucher;
    int idvoucher;
    int codevoucher;
    boolean statusvoucher;
    int price;
    int value;
    String gamename;

    public int getIdlistvoucher() {
        return idlistvoucher;
    }

    public void setIdlistvoucher(int idlistvoucher) {
        this.idlistvoucher = idlistvoucher;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIdvoucher(int idvoucher) {
        this.idvoucher = idvoucher;
    }

    public int getCodevoucher() {
        return codevoucher;
    }

    public int getIdvoucher() {
        return idvoucher;
    }

    public void setCodevoucher(int codevoucher) {
        this.codevoucher = codevoucher;
    }

    public void setStatusvoucher(boolean statusvoucher) {
        this.statusvoucher = statusvoucher;
    }

    public boolean getStatusvoucher(){
        return statusvoucher;
    }

    @Override
    public String toString() {
        return "VoucherGame{" +
                "idlistvoucher=" + idlistvoucher +
                ", idvoucher=" + idvoucher +
                ", codevoucher=" + codevoucher +
                ", statusvoucher=" + statusvoucher +
                ", price=" + price +
                ", value=" + value +
                ", gamename='" + gamename + '\'' +
                '}';
    }
}
