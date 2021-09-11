package me.faun.playerauctionsdiscord;

import de.jeff_media.jefflib.JeffLib;
import me.faun.playerauctionsdiscord.commands.ReloadCommand;
import me.faun.playerauctionsdiscord.listeners.AuctionBuyListener;
import me.faun.playerauctionsdiscord.listeners.AuctionRemoveListener;
import me.faun.playerauctionsdiscord.listeners.AuctionSellListener;
import me.faun.playerauctionsdiscord.utils.EmbedType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerAuctionsDiscord extends JavaPlugin {

    static PlayerAuctionsDiscord instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new AuctionSellListener(), this);
        Bukkit.getPluginManager().registerEvents(new AuctionBuyListener(), this);
        Bukkit.getPluginManager().registerEvents(new AuctionRemoveListener(), this);
        JeffLib.init(this);
        this.getCommand("PlayerAuctionsDiscord").setExecutor(new ReloadCommand());
        initConfig();
        instance = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerAuctionsDiscord getInstance() {
        return instance;
    }

    private void initConfig() {
        for (EmbedType type : EmbedType.values()) {
            if (!getConfig().contains(type.toString().toLowerCase() + "-embed")) {
                getConfig().addDefault(type + "-embed", null);
            }
            saveDefaultConfig();
        }
    }
}
