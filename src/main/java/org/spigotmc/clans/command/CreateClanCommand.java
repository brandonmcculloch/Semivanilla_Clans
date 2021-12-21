package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.Clans;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.UserModel;

public class CreateClanCommand implements CommandInterface {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        boolean clan_creation_enabled = Clans.getInstance().getConfig().getBoolean("clan_creation_enabled");
        if (!clan_creation_enabled) {
            commandSender.sendMessage("Clan creation is not enabled!");
            return true;
        }

        double clan_creation_cost = Clans.getInstance().getConfig().getDouble("clan_creation_cost");
        String symbol = Clans.getInstance().getConfig().getString("clan_currency_symbol");

        Player player = (Player) commandSender; // the command handler checks for a player instance
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        if (user.getClanId() != -1) {
            commandSender.sendMessage("It appears you are already in a clan.");
            return true;
        }

        String confirmCost = "There is a cost of " + symbol + clan_creation_cost + ". Type confirm at " +
                "the end of the command to confirm these costs. I.e \"/clans create <name> confirm\".";

        if (args.length == 2) {
            if (clan_creation_cost == 0) {
                commandSender.sendMessage(createClan(args[1], user));
            } else {
                commandSender.sendMessage(confirmCost);
            }
        } else if (args.length == 3) {
            if (args[2].equalsIgnoreCase("confirm")) {
                commandSender.sendMessage(createClan(args[1], user));
            } else {
                commandSender.sendMessage(confirmCost);
            }
        } else {
            commandSender.sendMessage("Invalid command. Type /clans help to learn more.");
        }
        return true;
    }

    private String createClan(String name, UserModel user) {
        if (DatabaseQuery.getInstance().retrieveClan(name) != null) return "A clan with that name already exists.";
        if (DatabaseQuery.getInstance().createClan(name)) {
            ClanModel clan = DatabaseQuery.getInstance().retrieveClan(name);
            if (DatabaseQuery.getInstance().updateUserClan(user, clan.getId(), 1)) {
                return "Clan " + name + " has been successfully created!";
            } else {
                return "Database failed to update user row";
            }
        } else {
            return "Database failed to create clan";
        }
    }

}
