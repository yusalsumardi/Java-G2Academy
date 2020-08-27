package com.vouchergameapps.BackEnd.model;


public class User {
    int iduser;
    String username;
    String password;
    String name;
    Boolean statuslogin;
    int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public User(int iduser, String username, String password, String name, Boolean statuslogin){
        this.iduser = iduser;
        this.username = username;
        this.password = password;
        this.name = name;
        this.statuslogin = statuslogin;
    }

    public User(String username, String password, String name, Boolean statuslogin){
        this.username = username;
        this.password = password;
        this.name = name;
        this.statuslogin = statuslogin;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatuslogin() {
        return statuslogin;
    }

    public void setStatuslogin(Boolean statuslogin) {
        this.statuslogin = statuslogin;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Override
    public String toString() {
        return "User{" +
                "iduser=" + iduser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", statuslogin=" + statuslogin +
                '}';
    }
}
