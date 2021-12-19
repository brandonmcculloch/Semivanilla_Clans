package org.spigotmc.clans;

import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.clans.command.CommandHandler;
import org.spigotmc.clans.database.DatabaseManager;

public class Clans extends JavaPlugin {

    public static Clans clans;

    @Override
    public void onEnable() {
        clans = this;
        saveDefaultConfig();
        DatabaseManager.getInstance().initialize();
        getCommand("clans").setExecutor(new CommandHandler());
        getLogger().info("Plugin started!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin stopped!");
    }

    public static Clans getInstance() {
        return clans;
    }
}
