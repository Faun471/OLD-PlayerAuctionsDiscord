package me.faun.playerauctiondiscord;


import me.faun.playerauctiondiscord.commands.ReloadCommand;
import me.faun.playerauctiondiscord.listeners.AuctionBuyListener;
import me.faun.playerauctiondiscord.listeners.AuctionRemoveListener;
import me.faun.playerauctiondiscord.listeners.AuctionSellListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerAuctionsDiscord extends JavaPlugin implements Listener {

    static PlayerAuctionsDiscord instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new AuctionSellListener(), this);
        Bukkit.getPluginManager().registerEvents(new AuctionBuyListener(), this);
        Bukkit.getPluginManager().registerEvents(new AuctionRemoveListener(), this);
        this.getCommand("PlayerAuctionsDiscord").setExecutor(new ReloadCommand());
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerAuctionsDiscord getInstance() {
        return instance;
    }
}
