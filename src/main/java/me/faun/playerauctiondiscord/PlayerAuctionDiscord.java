package me.faun.playerauctiondiscord;


import me.faun.playerauctiondiscord.listeners.AuctionBuyEvent;
import me.faun.playerauctiondiscord.listeners.AuctionRemoveEvent;
import me.faun.playerauctiondiscord.listeners.AuctionSellEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerAuctionDiscord extends JavaPlugin implements Listener {


    static PlayerAuctionDiscord instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new AuctionSellEvent(), this);
        Bukkit.getPluginManager().registerEvents(new AuctionBuyEvent(), this);
        Bukkit.getPluginManager().registerEvents(new AuctionRemoveEvent(), this);
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerAuctionDiscord getInstance() {
        return instance;
    }
}
