package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;

public interface CommandInterface {

    String getName();

    boolean execute(CommandSender commandSender, String[] args);

}
