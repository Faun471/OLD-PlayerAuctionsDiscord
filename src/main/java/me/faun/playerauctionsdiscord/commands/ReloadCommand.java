package me.faun.playerauctionsdiscord.commands;

import me.faun.playerauctionsdiscord.PlayerAuctionsDiscord;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    //Todo this command looks dum, I'll probs change it later
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("playerauctionsdiscord") || label.equalsIgnoreCase("pad")){
            if (args[0].equalsIgnoreCase("reload")){
                PlayerAuctionsDiscord.getInstance().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + PlayerAuctionsDiscord.getInstance().getDescription().getName() + " reloaded");
            }
            else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cUsage /playerauctionsdiscord reload"));
            return true;
        }
        return false;
    }
}
