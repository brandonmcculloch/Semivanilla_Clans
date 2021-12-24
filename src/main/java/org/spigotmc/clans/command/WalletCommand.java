package org.spigotmc.clans.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.clans.Clans;
import org.spigotmc.clans.database.DatabaseQuery;
import org.spigotmc.clans.model.UserModel;

public class WalletCommand implements CommandInterface {
    @Override
    public String getName() {
        return "wallet";
    }

    @Override
    public String getUsage() {
        return "/clans wallet <deposit/withdraw> <amount>";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        UserModel user = DatabaseQuery.getInstance().retrieveUser((Player) commandSender);
        if (user.getClanId() == -1) {
            commandSender.sendMessage("You must be in a clan to use this command");
            return true;
        }
        switch (args.length) {
            case 1:
                commandSender.sendMessage(showWalletBalance(user));
                break;
            case 3:
                commandSender.sendMessage(handleTransaction(user, args[1], args[2]));
                break;
            default:
                commandSender.sendMessage(instructions());
                break;
        }
        return false;
    }

    private String showWalletBalance(UserModel user) {
        double balance = DatabaseQuery.getInstance().checkClanBalance(user.getClanId());
        return "Your clan balance is: " + balance;
    }

    private String instructions() {
        return "Improper command usage. /clans wallet <deposit/withdraw> <amount>";
    }

    private String handleTransaction(UserModel user, String transaction, String amount) {
        double amt;
        try {
            amt = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return instructions();
        }
        if (amt < 1) return "Please enter a value greater than 0.";
        if (transaction.equalsIgnoreCase("deposit")) {
            return depositTransaction(amt, user);
        } else if (transaction.equalsIgnoreCase("withdraw")) {
            return withdrawTransaction(amt, user);
        } else {
            return instructions();
        }
    }

    private String depositTransaction(double amount, UserModel user) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(user.getPlayer().getUniqueId());
        double balance = Clans.getInstance().getEconomy().getBalance(offlinePlayer);
        if (balance < amount) return "Insufficient funds. Your balance is too low to perform this transaction.";
        if (Clans.getInstance().getEconomy().withdrawPlayer(offlinePlayer, amount).transactionSuccess()) {
            if (DatabaseQuery.getInstance().depositClanBalance(user.getClanId(), amount)) {
                if (DatabaseQuery.getInstance().createWalletHistory(user.getClanId(), user.getUUID(), "DEPOSIT", amount)) {
                    return "Transaction performed successfully. " + amount + " deposited.";
                } else return "Transaction performed successfully. Failed to create transaction history.";
            } else {
                Clans.getInstance().getEconomy().depositPlayer(offlinePlayer, amount);
                return "Database failed to deposit clan balance.";
            }
        } else {
            return "Failed to withdraw funds from player account.";
        }
    }

    private String withdrawTransaction(double amount, UserModel user) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(user.getPlayer().getUniqueId());
        double balance = DatabaseQuery.getInstance().checkClanBalance(user.getClanId());
        if (user.getPermission() < 1) return "You do not have permission to perform this command.";
        if (balance < amount) return "Insufficient funds. Your clan balance is too low to perform this transaction.";
        if (Clans.getInstance().getEconomy().depositPlayer(offlinePlayer, amount).transactionSuccess()) {
            if (DatabaseQuery.getInstance().withdrawClanBalance(user.getClanId(), amount)) {
                if (DatabaseQuery.getInstance().createWalletHistory(user.getClanId(), user.getUUID(), "WITHDRAW", amount)) {
                    return "Transaction performed successfully. " + amount + " withdrawn.";
                } else return "Transaction performed successfully. Failed to create transaction history.";
            } else {
                Clans.getInstance().getEconomy().withdrawPlayer(offlinePlayer, amount);
                return "Database failed to withdraw clan balance.";
            }
        } else {
            return "Failed to deposit funds to player account.";
        }
    }
}
