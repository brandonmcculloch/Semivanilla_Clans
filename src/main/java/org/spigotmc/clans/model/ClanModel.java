package org.spigotmc.clans.model;

import java.sql.Timestamp;

public class ClanModel {

    private int id;
    private String name;
    private String desc;
    private int wallet;
    private Timestamp origin;
    private String status;

    public ClanModel(int id, String name, String desc, Timestamp origin, String status) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.origin = origin;
        this.status = status;
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

}
