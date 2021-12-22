package org.spigotmc.clans.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.UserModel;

public class InviteCommand implements CommandInterface{
    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        Player player = (Player)commandSender;
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        ClanModel clan = DatabaseQuery.getInstance().retrieveClan(user.getClanId());
        if(args.length == 2) {
            String invitedName = args[1];
            Player invitedPlayer = Bukkit.getPlayer(invitedName);
            if(invitedPlayer != null) {
                if(DatabaseQuery.getInstance()
                        .createClanInvite(user.getUUID(), invitedPlayer.getUniqueId().toString(), clan.getId())) {
                    commandSender.sendMessage(invitedName + " has been invited to " + clan.getName());
                    invitedPlayer.sendMessage("You have been invited to the " + clan.getName() + " clan.");
                    invitedPlayer.sendMessage("Type \"/clan accept " + clan.getName() + "\" to accept this invite.");
                } else commandSender.sendMessage("Database failed to create player invite.");
            } else commandSender.sendMessage("Could not find a player with the name " + invitedName);
        } else commandSender.sendMessage("Invalid command. Type /clans help to learn more.");
        return true;
    }
}
