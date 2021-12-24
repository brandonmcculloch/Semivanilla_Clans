package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.UserModel;

public class LeaveCommand implements CommandInterface{
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getUsage() {
        return "/clan leave <confirm>";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(args.length != 2 || !args[1].equalsIgnoreCase("confirm")) {
            commandSender.sendMessage("Improper command usage. Type " + getUsage());
            return true;
        }
        Player player = (Player)commandSender;
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        if(user.getClanId() == -1) {
            commandSender.sendMessage("It appears you are not in a clan.");
            return true;
        }
        ClanModel clan = DatabaseQuery.getInstance().retrieveClan(user.getClanId());
        if(DatabaseQuery.getInstance().updateUserClan(user, -1, 0)) {
            commandSender.sendMessage("You are no longer apart of the " + clan.getName() + " clan.");
        }
        return true;
    }
}