package org.spigotmc.clans.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.Clans;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.UserModel;

public class CreateCommand implements CommandInterface {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getUsage() {
        return "/clans create <name> <confirm>";
    }

    private String getConfirmation() {
        return "The price to create a clan is " + getCostString() + ". To create a clan type " + getUsage();
    }

    private String getCostString() {
        double clan_creation_cost = Clans.getInstance().getConfig().getDouble("clan_creation_cost");
        String symbol = Clans.getInstance().getConfig().getString("clan_currency_symbol");
        return symbol + clan_creation_cost;
    }

    private double getCost() {
        return Clans.getInstance().getConfig().getDouble("clan_creation_cost");
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        boolean clan_creation_enabled = Clans.getInstance().getConfig().getBoolean("clan_creation_enabled");
        if (!clan_creation_enabled) {
            commandSender.sendMessage("Clan creation is not enabled!");
            return true;
        }
        Player player = (Player)commandSender; // the command handler checks for a player instance
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        if (user.getClanId() != -1) {
            commandSender.sendMessage("It appears you are already in a clan.");
            return true;
        }
        if (args.length > 1 && getCost() == 0 || args[2].equalsIgnoreCase("confirm")) {
            commandSender.sendMessage(createClan(args[1], user, getCost()));
        } else {
            commandSender.sendMessage(getConfirmation());
        }
        return true;
    }

    private String createClan(String name, UserModel user, double cost) {
        if(!name.matches("[a-zA-Z0-9]*") || name.length() < 3 || name.length() > 24) return "Invalid clan name." +
                " Clan names can only be 3 - 24 characters composing of letters and numbers.";
        if (DatabaseQuery.getInstance().retrieveClan(name) != null) return "A clan with that name already exists.";
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(user.getPlayer().getUniqueId());
        double balance = Clans.getInstance().getEconomy().getBalance(offlinePlayer);
        if (balance >= cost) {
            if (DatabaseQuery.getInstance().createClan(name)) {
                ClanModel clan = DatabaseQuery.getInstance().retrieveClan(name);
                if (DatabaseQuery.getInstance().updateUserClan(user, clan.getId(), 1)) {
                    Clans.getInstance().getEconomy().withdrawPlayer(offlinePlayer, cost);
                    return "Clan " + name + " has been successfully created!";
                } else {
                    return "Database failed to update user row";
                }
            } else {
                return "Database failed to create clan";
            }
        } else {
            return "You can't afford to create a new clan. The price is " + cost;
        }
    }
}
