package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandInterface {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getUsage() {
        return "/clans help";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        commandSender.sendMessage("You executed the " + getName() + " command!");
        return false;
    }
}
