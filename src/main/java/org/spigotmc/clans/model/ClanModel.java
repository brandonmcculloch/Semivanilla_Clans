package org.spigotmc.clans.model;

import java.sql.Timestamp;

public class ClanModel {

    private int id;
    private String name;
    private String desc;
    private Timestamp origin;
    private String status;
    private double balance;

    public ClanModel(int id, String name, String desc, Timestamp origin, String status, Double balance) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.origin = origin;
        this.status = status;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Timestamp getOrigin() {
        return origin;
    }

    public String getStatus() {
        return status;
    }

    public double getBalance() {
        return balance;
    }

}
