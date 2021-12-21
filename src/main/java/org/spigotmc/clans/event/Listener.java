package org.spigotmc.clans.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.UserModel;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        UserModel user = DatabaseQuery.getInstance().retrieveUser(event.getPlayer());
        if(user == null) {
            DatabaseQuery.getInstance().createUser(event.getPlayer());
        }
    }

}
