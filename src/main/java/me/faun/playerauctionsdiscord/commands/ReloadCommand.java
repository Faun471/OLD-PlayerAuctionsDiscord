package me.faun.playerauctionsdiscord.commands;

import me.faun.playerauctionsdiscord.PlayerAuctionsDiscord;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    List<String> results = new ArrayList<>();

    //Todo this command looks dum, I'll probs change it later
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("playerauctionsdiscord") || label.equalsIgnoreCase("pad")){
            if (args[0].equalsIgnoreCase("reload") && args.length == 1){
                PlayerAuctionsDiscord.getInstance().reloadConfig();
                if (sender instanceof Player) sender.sendMessage(ChatColor.GREEN + PlayerAuctionsDiscord.getInstance().getDescription().getName() + " reloaded");
                else PlayerAuctionsDiscord.getInstance().getLogger().info(PlayerAuctionsDiscord.getInstance().getDescription().getName() + " reloaded");
            }
            else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cUsage /playerauctionsdiscord reload"));
            return true;
        }
        return false;
    }

    @Override
    public List <String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (cmd.getLabel().equalsIgnoreCase("playerauctionsdiscord") || cmd.getLabel().equalsIgnoreCase("pad")) {
                results.clear();
                results.add("reload");
            }
        }
        return sortedResults(args[0]);
    }

    public List <String> sortedResults(String arg) {
        final List <String> completions = new ArrayList <> ();
        StringUtil.copyPartialMatches(arg, results, completions);
        Collections.sort(completions);
        results.clear();
        results.addAll(completions);
        return results;
    }
}
