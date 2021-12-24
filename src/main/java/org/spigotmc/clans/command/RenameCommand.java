package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.UserModel;

public class RenameCommand implements CommandInterface{
    @Override
    public String getName() {
        return "rename";
    }

    @Override
    public String getUsage() {
        return "/clans rename <new-name>";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(args.length != 3 || !args[1].equalsIgnoreCase("confirm")) {
            commandSender.sendMessage("Improper command usage. Type " + getUsage());
            return true;
        }
        Player player = (Player)commandSender;
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        if(user.getClanId() == -1) {
            commandSender.sendMessage("It appears you are not in a clan.");
            return true;
        }
        if(user.getPermission() < 1) {
            commandSender.sendMessage("You do not have permission to perform this command.");
            return true;
        }
        String name = args[1];
        boolean valid = name.matches("[a-zA-Z0-9]*") && name.length() >= 3 && name.length() <= 24;
        if(!valid) {
            commandSender.sendMessage("Clan names can only be 3 - 24 characters composing of letters and numbers.");
            return true;
        }
        ClanModel clan = DatabaseQuery.getInstance().retrieveClan(user.getClanId());
        if(DatabaseQuery.getInstance().updateClanName(clan, name)) {
            commandSender.sendMessage("Clan name successfully updated.");
        }
        return true;
    }
}
