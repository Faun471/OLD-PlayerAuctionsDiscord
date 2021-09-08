package me.faun.playerauctiondiscord.utils;

import com.olziedev.playerauctions.api.auction.Auction;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.awt.Color;

public class EmbedUtils  {
    public static MessageEmbed getEmbedBuilder(EmbedType type, ItemStack itemStack, Player seller, @Nullable Player buyer, Auction auction) {
        EmbedBuilder eb = new EmbedBuilder();
        String item = StringUtils.itemName(itemStack);
        eb.setThumbnail(StringUtils.getLink(itemStack));

        switch (type) {
            case SELL:
                eb.setAuthor(seller.getName() + " is selling " + item + " for $" + auction.getPrice(), null,
                        "https://crafatar.com/avatars/" + seller.getUniqueId());
                eb.setColor(new Color(0x4287f5));
                break;
            case BUY:
                eb.setAuthor(buyer.getName() + " bought " + item + " for $" + auction.getPrice(), null,
                        "https://crafatar.com/avatars/"+ buyer.getUniqueId());
                eb.setColor(new Color(0x48f542));
                break;
            case REMOVE:
                eb.setAuthor(item + " was removed from the Auction!", null, "https://crafatar.com/avatars/"+ seller.getUniqueId());
                eb.setColor(new Color(0xbf2a2a));
                break;
        }

        eb.setTitle("Auction Information:",null);
        eb.addField("Seller", seller.getName(), false);
        if (buyer != null) {
            eb.addField("Buyer", buyer.getName(), true);
            eb.addBlankField(true);
        }
        eb.addField("Item", item, true);
        eb.addField("Amount", String.valueOf(itemStack.getAmount()), true);
        eb.addBlankField(true);
        eb.addField("Price", "$" + auction.getPrice(), false);
        eb.setFooter("Auction ID: " + auction.getID());
        return eb.build();
    }
}
