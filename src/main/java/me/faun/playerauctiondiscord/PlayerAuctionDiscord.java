package me.faun.playerauctiondiscord;


import me.faun.playerauctiondiscord.listeners.AuctionBuyListener;
import me.faun.playerauctiondiscord.listeners.AuctionRemoveListener;
import me.faun.playerauctiondiscord.listeners.AuctionSellListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerAuctionDiscord extends JavaPlugin implements Listener {


    static PlayerAuctionDiscord instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new AuctionSellListener(), this);
        Bukkit.getPluginManager().registerEvents(new AuctionBuyListener(), this);
        Bukkit.getPluginManager().registerEvents(new AuctionRemoveListener(), this);
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerAuctionDiscord getInstance() {
        return instance;
    }
}
