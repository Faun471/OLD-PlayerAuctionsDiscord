package me.faun.playerauctiondiscord.listeners;

import com.olziedev.playerauctions.api.events.PlayerAuctionRemoveEvent;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.faun.playerauctiondiscord.PlayerAuctionsDiscord;
import me.faun.playerauctiondiscord.utils.EmbedType;
import me.faun.playerauctiondiscord.utils.EmbedUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;


public class AuctionRemoveListener implements Listener {

    @EventHandler
    public void onRemoveEvent(PlayerAuctionRemoveEvent event){
        if (event.getPlayerAuction().getItem().getAmount() != 0 && PlayerAuctionsDiscord.getInstance().getConfig().getBoolean("remove-embed.enabled")) {
            ItemStack itemStack = event.getPlayerAuction().getItem();
            DiscordUtil.getTextChannelById(PlayerAuctionsDiscord.getInstance().getConfig().getString("channel")).sendMessage
                    (EmbedUtils.getEmbedBuilder(
                            EmbedType.REMOVE,
                            itemStack,
                            event.getPlayerAuction().getAuctionPlayer().getPlayer(),
                            null,
                            event.getPlayerAuction()
                    )).queue();
        }
    }
}
