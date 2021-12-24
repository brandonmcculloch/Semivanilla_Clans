package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.ClanModel;

import java.util.ArrayList;

public class LeaderboardCommand implements CommandInterface {
    @Override
    public String getName() {
        return "top";
    }

    @Override
    public String getUsage() {
        return "/clans top <page-number>";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(args.length == 2) {
            try {
                int page = Integer.parseInt(args[1]);
                showLeaderboards(commandSender, page);
            } catch(NumberFormatException e) {
                commandSender.sendMessage("Improper command usage. Type " + getUsage());
                return true;
            }
        } else showLeaderboards(commandSender, 1);
        return true;
    }

    private void showLeaderboards(CommandSender commandSender, int page) {
        commandSender.sendMessage("------------------------------");
        commandSender.sendMessage("Clan Leaderboards");
        commandSender.sendMessage("------------------------------");
        ArrayList<ClanModel> clanList = DatabaseQuery.getInstance().retrieveLeaderboards(page);
        if(clanList != null) {
            int rank = ((page-1)*10)+1;
            for(ClanModel clan: clanList) {
                commandSender.sendMessage(rank++ + ". " + clan.getName() + ", " + clan.getBalance());
            }
        } else {
            commandSender.sendMessage("No clans found");
        }
        commandSender.sendMessage("------------------------------");
    }
}
