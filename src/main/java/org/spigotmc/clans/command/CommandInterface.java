package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;

public interface CommandInterface {

    String getName();

    String getUsage();

    boolean execute(CommandSender commandSender, String[] args);

}
