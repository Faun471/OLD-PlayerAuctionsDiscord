package me.faun.playerauctiondiscord.listeners;

import com.olziedev.playerauctions.api.events.PlayerAuctionSellEvent;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.faun.playerauctiondiscord.PlayerAuctionDiscord;
import me.faun.playerauctiondiscord.utils.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.awt.Color;

public class AuctionSellEvent implements Listener {

    @EventHandler
    public void onSellEvent(PlayerAuctionSellEvent event){
        EmbedBuilder eb = new EmbedBuilder();
        ItemStack itemStack = event.getPlayerAuction().getItem();
        String item = StringUtils.itemName(itemStack);

        eb.setThumbnail(StringUtils.getLink(itemStack));
        eb.setAuthor(event.getSeller().getName()+ " is selling " + item + " for $" + event.getPlayerAuction().getPrice(), null,
                "https://crafatar.com/avatars/"+ event.getSeller().getUniqueId());
        eb.setColor(new Color(0x4287f5));
        eb.setTitle("Auction Information:",null);
        eb.addField("Seller", event.getSeller().getName(), false);
        eb.addField("Item", item, true);
        eb.addField("Amount", String.valueOf(itemStack.getAmount()), true);
        eb.addBlankField(true);
        eb.addField("Price", String.valueOf(event.getPlayerAuction().getPrice()), false);
        eb.setFooter("Auction ID: " + event.getPlayerAuction().getID());

        DiscordUtil.getTextChannelById(PlayerAuctionDiscord.getInstance().getConfig().getString("channel")).sendMessage(eb.build()).queue();
    }
}
