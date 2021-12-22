package org.spigotmc.clans.model;

import java.sql.Timestamp;

public class InviteModel {

    private int id;
    private String fromUUID;
    private String toUUID;
    private int clanId;
    private Timestamp origin;
    private boolean status;

    public InviteModel(int id, String fromUUID, String toUUID, int clanId, Timestamp origin, boolean status) {
        this.id = id;
        this.fromUUID = fromUUID;
        this.toUUID = toUUID;
        this.clanId = clanId;
        this.origin = origin;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getFromUUID() {
        return fromUUID;
    }

    public String getToUUID() {
        return toUUID;
    }

    public int getClanId() {
        return clanId;
    }

    public Timestamp getOrigin() {
        return origin;
    }
}
