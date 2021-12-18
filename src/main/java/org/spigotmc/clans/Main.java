package org.spigotmc.clans;

import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.clans.command.CommandHandler;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("clan").setExecutor(new CommandHandler());
        getLogger().info("[Clans] Plugin started!");
    }

    @Override
    public void onDisable() {
        getLogger().info("[Clans] Plugin stopped!");
    }
}
