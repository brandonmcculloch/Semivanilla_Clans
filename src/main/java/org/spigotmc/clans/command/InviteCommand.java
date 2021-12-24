package org.spigotmc.clans.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.UserModel;

public class InviteCommand implements CommandInterface {
    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getUsage() {
        return "/clans invite <username>";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            commandSender.sendMessage("Invalid command usage. Type " + getUsage());
            return true;
        }
        Player player = (Player) commandSender;
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        ClanModel clan = DatabaseQuery.getInstance().retrieveClan(user.getClanId());
        if(user.getPermission() < 1) {
            commandSender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        String invitedName = args[1];
        Player invitedPlayer = Bukkit.getPlayer(invitedName);
        UserModel invitedUser = DatabaseQuery.getInstance().retrieveUser(invitedPlayer);
        if (invitedUser.getClanId() != -1) {
            commandSender.sendMessage("It appears that player is already a member of another clan.");
            return true;
        }
        if (invitedPlayer == null) {
            commandSender.sendMessage("Could not find a player with the name " + invitedName);
            return true;
        }
        if (DatabaseQuery.getInstance().createClanInvite(user.getUUID(), invitedUser.getUUID(), clan.getId())) {
            commandSender.sendMessage(invitedName + " has been invited to " + clan.getName());
            invitedPlayer.sendMessage("You have been invited to the " + clan.getName() + " clan.");
            invitedPlayer.sendMessage("Type \"/clan accept " + clan.getName() + "\" to accept this invite.");
        } else commandSender.sendMessage("Database failed to create player invite.");
        return true;
    }
}
