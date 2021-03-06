package org.spigotmc.clans;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.clans.command.CommandHandler;
import org.spigotmc.clans.database.DatabaseManager;
import org.spigotmc.clans.event.Listener;

/*
   ___ _      _   _  _ ___
  / __| |    /_\ | \| / __|
 | (__| |__ / _ \| .` \__ \
  \___|____/_/ \_\_|\_|___/
 */
public class Clans extends JavaPlugin {

    public static Clans clans;
    private Economy economy;

    @Override
    public void onEnable() {
        clans = this;
        if(!setupEconomy()) {
            this.getLogger().severe("Plugin disabled due to lack of Vault dependency!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        saveDefaultConfig();
        DatabaseManager.getInstance().initialize();
        getCommand("clans").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new Listener(), this);
        getLogger().info("   ___ _      _   _  _ ___");
        getLogger().info("  / __| |    /_\\ | \\| / __|");
        getLogger().info(" | (__| |__ / _ \\| .` \\__ \\");
        getLogger().info("  \\___|____/_/ \\_\\_|\\_|___/");
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public Economy getEconomy() {
        return economy;
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin stopped!");
    }

    public static Clans getInstance() {
        return clans;
    }
}
