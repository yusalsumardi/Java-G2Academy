package com.vouchergameapps.BackEnd.Bank.model;

public class Bank {
    int idtfbank;
    boolean statustf;
    int reknum;
    int nova;

    public int getNova() {
        return nova;
    }

    public void setNova(int nova) {
        this.nova = nova;
    }

    public int getIdtfbank() {
        return idtfbank;
    }

    public int getReknum() {
        return reknum;
    }

    public void setIdtfbank(int idtfbank) {
        this.idtfbank = idtfbank;
    }

    public void setReknum(int reknum) {
        this.reknum = reknum;
    }

    public void setStatustf(boolean statustf) {
        this.statustf = statustf;
    }

    public boolean getStatustf(){
        return statustf;
    }
}
