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
        ItemStack item = event.getPlayerAuction().getItem();

        if (item.hasItemMeta() && item.getItemMeta() instanceof PotionMeta) {
            if (item.getType() == Material.TIPPED_ARROW) {
                PotionMeta meta = (PotionMeta) item.getItemMeta();
                PotionType potionType = meta.getBasePotionData().getType();
                eb.setThumbnail("https://res.cloudinary.com/pryormc/image/upload/l_tipped_arrow_head,e_tint:100:"+ RandomUtils.colorToHex(meta, potionType) +"/h_250/tipped_arrow_base.png");
                System.out.println("https://res.cloudinary.com/pryormc/image/upload/l_tipped_arrow_head,e_tint:100:"+ RandomUtils.colorToHex(meta, potionType) +"/h_250/tipped_arrow_base.png");
            }
            else {
                PotionMeta meta = (PotionMeta) item.getItemMeta();
                PotionType potionType = meta.getBasePotionData().getType();
                eb.setThumbnail("https://res.cloudinary.com/pryormc/image/upload/l_potion_overlay,e_tint:100:" + RandomUtils.colorToHex(meta, potionType) + "/h_250/" + item.getType() + ".png");
                System.out.println("https://res.cloudinary.com/pryormc/image/upload/l_potion_overlay,e_tint:100:" + RandomUtils.colorToHex(meta, potionType) + "/h_250/" + item.getType() + ".png");
            }
        }
        else {
            eb.setThumbnail("https://ik.imagekit.io/pryormc/"+ item.getType().toString().toLowerCase() +".png?tr=w-128,h-128");
        }

        eb.setColor(new Color(0x4287f5)); //blue
        eb.setAuthor(event.getBuyer().getName()+ " bought " + RandomUtils.capitalizeString(event.getPlayerAuction().getItem().getType().toString()) + "for $" + event.getPlayerAuction().getPrice(), null,
                "https://crafatar.com/avatars/"+ event.getBuyer().getUniqueId());
        eb.setTitle("Auction Information:",null);
        eb.addField("Seller", event.getPlayerAuction().getAuctionPlayer().getName(), true);
        eb.addField("Buyer", event.getBuyer().getName(), true);
        eb.addBlankField(true);
        eb.addField("Item", RandomUtils.capitalizeString(event.getPlayerAuction().getItem().getType().toString()), false);
        eb.addField("Amount", String.valueOf(event.getPlayerAuction().getItem().getAmount()), false);
        eb.addField("Price", String.valueOf(event.getPlayerAuction().getPrice()), false);
        eb.setFooter("Auction ID: " + event.getPlayerAuction().getID());

        DiscordUtil.getTextChannelById("860650048407601182").sendMessage(eb.build()).queue();
    }
}
