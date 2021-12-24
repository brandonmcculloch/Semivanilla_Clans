package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.UserModel;

public class DisbandCommand implements CommandInterface {
    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getUsage() {
        return "/clans disband <confirm>";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        Player player = (Player)commandSender;
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        if(user.getPermission() == 0) {
            commandSender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        if(args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
            if(DatabaseQuery.getInstance().disbandClan(user.getClanId())) {
                commandSender.sendMessage("Clan successfully disbanded.");
            } else {
                commandSender.sendMessage("Failed to disband clan.");
            }
        } else {
            commandSender.sendMessage("To disband a clan type /clans disband confirm. Once a clan is disbanded it " +
                    "can never be accessed again.");
        }
        return true;
    }
}
