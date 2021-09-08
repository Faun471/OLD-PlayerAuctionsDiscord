package me.faun.playerauctiondiscord.listeners;

import com.olziedev.playerauctions.api.events.PlayerAuctionSellEvent;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.faun.playerauctiondiscord.PlayerAuctionsDiscord;
import me.faun.playerauctiondiscord.utils.EmbedType;
import me.faun.playerauctiondiscord.utils.EmbedUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class AuctionSellListener implements Listener {

    @EventHandler
    public void onSellEvent(PlayerAuctionSellEvent event){
        if (PlayerAuctionsDiscord.getInstance().getConfig().getBoolean("sell-embed.enabled")){
            ItemStack itemStack = event.getPlayerAuction().getItem();

            DiscordUtil.getTextChannelById(PlayerAuctionsDiscord.getInstance().getConfig().getString("channel")).sendMessage
                    (EmbedUtils.getEmbedBuilder(
                            EmbedType.SELL,
                            itemStack,
                            event.getSeller(),
                            null,
                            event.getPlayerAuction()
                    )).queue();
        }
    }
}
