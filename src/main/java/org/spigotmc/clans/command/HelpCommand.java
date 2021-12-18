package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandInterface {
    @Override
    public String getName() {
        return "rename";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        return false;
    }
}
