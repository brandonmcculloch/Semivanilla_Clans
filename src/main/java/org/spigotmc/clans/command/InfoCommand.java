package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.UserModel;

public class InfoCommand implements CommandInterface {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getUsage() {
        return "/clans info <clan-name>";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        if (args.length == 2) {
            String name = args[1];
            ClanModel clan = DatabaseQuery.getInstance().retrieveClan(name);
            if (clan != null) showClanInformation(commandSender, clan);
             else commandSender.sendMessage("Failed to find any results for the clan name " + name);

        } else if (user.getClanId() == -1) {
            commandSender.sendMessage("You do not appear to be in a clan.");
        } else {
            showClanInformation(commandSender, DatabaseQuery.getInstance().retrieveClan(user.getClanId()));
        }
        return true;
    }

    private void showClanInformation(CommandSender commandSender, ClanModel clan) {
        commandSender.sendMessage("------------------------------");
        commandSender.sendMessage("Clan Information");
        commandSender.sendMessage("------------------------------");
        commandSender.sendMessage("ID: " + clan.getId());
        commandSender.sendMessage("Name: " + clan.getName());
        commandSender.sendMessage("Description: " + clan.getDesc());
        commandSender.sendMessage("Origin: " + clan.getOrigin());
        commandSender.sendMessage("------------------------------");
    }
}
