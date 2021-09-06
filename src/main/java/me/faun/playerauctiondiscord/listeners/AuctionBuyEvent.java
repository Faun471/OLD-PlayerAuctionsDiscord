package me.faun.playerauctiondiscord.listeners;

import com.olziedev.playerauctions.api.events.PlayerAuctionBuyEvent;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.faun.playerauctiondiscord.utils.RandomUtils;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.awt.*;

public class AuctionBuyEvent implements Listener {

    @EventHandler
    public void onBuyEvent(PlayerAuctionBuyEvent event){
        EmbedBuilder eb = new EmbedBuilder();
        ItemStack itemStack = event.getPlayerAuction().getItem();
        String item = RandomUtils.capitalizeString(itemStack.getType().toString());

        if (itemStack.hasItemMeta() && itemStack.getItemMeta() instanceof PotionMeta) {
            if (itemStack.getType() == Material.TIPPED_ARROW) {
                PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
                PotionType potionType = meta.getBasePotionData().getType();
                item = RandomUtils.capitalizeString(RandomUtils.prettierEffectType(potionType));

                eb.setThumbnail("https://res.cloudinary.com/pryormc/image/upload/l_tipped_arrow_head,e_tint:100:"+ RandomUtils.colorToHex(meta, potionType) +"/h_250/tipped_arrow_base.png");
                eb.setAuthor(event.getBuyer().getName()+ " bought " + item + "for $" + event.getPlayerAuction().getPrice(), null,
                        "https://crafatar.com/avatars/"+ event.getBuyer().getUniqueId());
            }
            else {
                PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
                PotionType potionType = meta.getBasePotionData().getType();
                item = RandomUtils.capitalizeString(RandomUtils.prettierEffectType(potionType));

                eb.setThumbnail("https://res.cloudinary.com/pryormc/image/upload/l_potion_overlay,e_tint:100:" + RandomUtils.colorToHex(meta, potionType) + "/h_250/" + itemStack.getType() + ".png");
                eb.setAuthor(event.getBuyer().getName()+ " bought " + item + "for $" + event.getPlayerAuction().getPrice(), null,
                        "https://crafatar.com/avatars/"+ event.getBuyer().getUniqueId());
            }
        }
        else {
            eb.setThumbnail("https://ik.imagekit.io/pryormc/"+ itemStack.getType().toString().toLowerCase() +".png?tr=w-128,h-128");

        }

        eb.setAuthor(event.getBuyer().getName()+ " bought " + item + "for $" + event.getPlayerAuction().getPrice(), null,
                "https://crafatar.com/avatars/"+ event.getBuyer().getUniqueId());
        eb.setColor(new Color(0x48f542));
        eb.setTitle("Auction Information:",null);
        eb.addField("Seller", event.getPlayerAuction().getAuctionPlayer().getName(), true);
        eb.addField("Buyer", event.getBuyer().getName(), true);
        eb.addBlankField(true);
        eb.addField("Item", item, false);
        eb.addField("Amount", String.valueOf(event.getPlayerAuction().getItem().getAmount()), false);
        eb.addField("Price", String.valueOf(event.getPlayerAuction().getPrice()), false);
        eb.setFooter("Auction ID: " + event.getPlayerAuction().getID());

        DiscordUtil.getTextChannelById("860650048407601182").sendMessage(eb.build()).queue();
    }
}
