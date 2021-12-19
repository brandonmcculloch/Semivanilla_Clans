package org.spigotmc.clans;

import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.clans.command.CommandHandler;
import org.spigotmc.clans.database.DatabaseManager;

public class Clans extends JavaPlugin {

    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        databaseManager = new DatabaseManager(this.getConfig());
        getCommand("clans").setExecutor(new CommandHandler());
        getLogger().info("Plugin started!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin stopped!");
    }
}
