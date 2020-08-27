package com.vouchergameapps.BackEnd.GoogleService.model;

public class VoucherPackage {
    int idpackage;
    int price;
    int value;
    String gamename;


    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getIdpackage() {
        return idpackage;
    }

    public int getValue() {
        return value;
    }

    public void setIdpackage(int idpackage) {
        this.idpackage = idpackage;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGamename() {
        return gamename;
    }

    @Override
    public String toString() {
        return "VoucherPackage{" +
                "idpackage=" + idpackage +
                ", price=" + price +
                ", value=" + value +
                ", gamename='" + gamename + '\'' +
                '}';
    }
}
