package me.faun.playerauctionsdiscord.listeners;

import com.olziedev.playerauctions.api.events.PlayerAuctionBuyEvent;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.faun.playerauctionsdiscord.PlayerAuctionsDiscord;
import me.faun.playerauctionsdiscord.utils.EmbedType;
import me.faun.playerauctionsdiscord.utils.EmbedUtils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class AuctionBuyListener implements Listener {

    @EventHandler
    public void onBuyEvent(PlayerAuctionBuyEvent event) {
        if (PlayerAuctionsDiscord.getInstance().getConfig().getBoolean("buy-embed.enabled")) {
            ItemStack itemStack = event.getPlayerAuction().getItem();
            DiscordUtil.getTextChannelById(PlayerAuctionsDiscord.getInstance().getConfig().getString("channel")).sendMessage
                    (EmbedUtils.getEmbedBuilder(
                            EmbedType.BUY,
                            itemStack,
                            event.getPlayerAuction().getAuctionPlayer().getPlayer(),
                            event.getBuyer(),
                            event.getPlayerAuction()
                    )).queue();
        }
    }
}
