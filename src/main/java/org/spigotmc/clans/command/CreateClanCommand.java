package org.spigotmc.clans.command;

import org.bukkit.command.CommandSender;

public class CreateClanCommand implements CommandInterface {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        return false;
    }

}