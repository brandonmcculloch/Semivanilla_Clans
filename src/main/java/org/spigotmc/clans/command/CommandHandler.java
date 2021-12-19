package org.spigotmc.clans.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandHandler implements CommandExecutor {

    private ArrayList<CommandInterface> commands = new ArrayList();

    public CommandHandler() {
        commands.add(new HelpCommand());
        commands.add(new CreateClanCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length > 0) {
                for (CommandInterface cmd : commands) {
                    if (strings[0].equals(cmd.getName())) {
                        cmd.execute(commandSender, strings);
                    }
                }
            } else {
                 commandSender.sendMessage("Invalid command. Type /clan help to learn more.");
            }
        } else {
            commandSender.sendMessage("Clan commands must be issued by a player.");
        }
        return true;
    }

}
