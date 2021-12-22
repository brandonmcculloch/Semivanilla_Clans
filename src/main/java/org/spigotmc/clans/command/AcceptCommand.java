package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;
import org.spigotmc.clans.model.InviteModel;
import org.spigotmc.clans.model.UserModel;

public class AcceptCommand implements CommandInterface {
    @Override
    public String getName() {
        return "accept";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        UserModel user = DatabaseQuery.getInstance().retrieveUser(player);
        if(args.length == 2) {
            String clanName = args[1];
            ClanModel clan = DatabaseQuery.getInstance().retrieveClan(clanName);
            InviteModel invite = DatabaseQuery.getInstance().retrieveClanInvite(user.getUUID(), clan.getId());
            if(invite != null) {
                long originTime = invite.getOrigin().getTime();
                long currentTime = System.currentTimeMillis();
                long difference = currentTime - originTime;
                // 7200000 milliseconds is 2 hours
                if(difference < 7200000) {
                    if(DatabaseQuery.getInstance().updateUserClan(user, invite.getClanId(), 0)) {
                        if(DatabaseQuery.getInstance().redeemClanInvite(invite)) {
                            commandSender.sendMessage("You are now a member of the " + clanName + " clan.");
                        } else commandSender.sendMessage("Failed to redeem clan invite.");
                    } else commandSender.sendMessage("Database failed to update user clan.");
                } else commandSender.sendMessage("Your invite has expired. Please request a new one.");
            } else commandSender.sendMessage("You do not have an active invite from the " + clanName + " clan.");
        } else commandSender.sendMessage("Invalid command. Type /clan help to learn more.");
        return true;
    }
}
