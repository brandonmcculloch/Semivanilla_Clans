package org.spigotmc.clans.model;

import org.bukkit.entity.Player;

public class UserModel {

    private String UUID;
    private Player player;
    private int clanId;
    private int permission;

    public UserModel(String UUID, Player player, int clanId, int permission) {
        this.UUID = UUID;
        this.player = player;
        this.clanId = clanId;
        this.permission = permission;
    }

    public String getUUID() {
        return UUID;
    }

    public Player getPlayer() {
        return player;
    }

    public int getClanId() {
        return clanId;
    }

    public int getPermission() {
        return permission;
    }

}
