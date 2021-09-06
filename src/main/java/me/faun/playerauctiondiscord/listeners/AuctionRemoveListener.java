package me.faun.playerauctiondiscord.listeners;

import com.olziedev.playerauctions.api.events.PlayerAuctionRemoveEvent;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.faun.playerauctiondiscord.PlayerAuctionDiscord;
import me.faun.playerauctiondiscord.utils.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.awt.Color;


public class AuctionRemoveListener implements Listener {

    @EventHandler
    public void onRemoveEvent(PlayerAuctionRemoveEvent event){
        if (event.getPlayerAuction().getItem().getAmount() != 0) {
            EmbedBuilder eb = new EmbedBuilder();
            ItemStack itemStack = event.getPlayerAuction().getItem();
            String item = StringUtils.itemName(itemStack);

            eb.setThumbnail(StringUtils.getLink(itemStack));
            eb.setColor(new Color(0xbf2a2a));
            eb.setAuthor(item + " was removed from the Auction!", null, null);
            eb.setTitle("Auction Information:",null);
            eb.addField("Seller", event.getPlayerAuction().getAuctionPlayer().getName(), false);
            eb.addField("Item", item, true);
            eb.addField("Amount", String.valueOf(event.getPlayerAuction().getItem().getAmount()), true);
            eb.addBlankField(true);
            eb.addField("Price", String.valueOf(event.getPlayerAuction().getPrice()), false);
            eb.setFooter("Auction ID: " + event.getPlayerAuction().getID());

            DiscordUtil.getTextChannelById(PlayerAuctionDiscord.getInstance().getConfig().getString("channel")).sendMessage(eb.build()).queue();
        }
    }
}
