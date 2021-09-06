package me.faun.playerauctiondiscord.listeners;

import com.olziedev.playerauctions.api.events.PlayerAuctionSellEvent;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.faun.playerauctiondiscord.PlayerAuctionDiscord;
import me.faun.playerauctiondiscord.utils.RandomUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.awt.*;



public class AuctionSellEvent implements Listener {

    @EventHandler
    public void onSellEvent(PlayerAuctionSellEvent event){
        EmbedBuilder eb = new EmbedBuilder();
        ItemStack itemStack = event.getPlayerAuction().getItem();
        String item = RandomUtils.capitalizeString(itemStack.getType().toString());

        if (itemStack.getItemMeta() instanceof PotionMeta) {
            if (itemStack.getType() == Material.TIPPED_ARROW) {
                PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
                PotionType potionType = meta.getBasePotionData().getType();
                if (potionType.getEffectType() != null){
                    item = "Arrow of " + RandomUtils.capitalizeString(RandomUtils.prettierEffectType(potionType));
                } else {
                    if (potionType.equals(PotionType.UNCRAFTABLE)){
                        item = "Uncraftable Tipped Arrow";
                    }
                    else {
                        item = "Tipped Arrow";
                    }
                }
                eb.setThumbnail("https://res.cloudinary.com/pryormc/image/upload/l_tipped_arrow_head,e_tint:100:"+ RandomUtils.colorToHex(meta, potionType) + "/h_250/tipped_arrow_base.png");
                eb.setAuthor(event.getSeller().getName()+ " is selling " + item + " for $" + event.getPlayerAuction().getPrice(), null,
                        "https://crafatar.com/avatars/"+ event.getSeller().getUniqueId());
                System.out.println("https://res.cloudinary.com/pryormc/image/upload/l_tipped_arrow_head,e_tint:100:"+ RandomUtils.colorToHex(meta, potionType) + "/h_250/tipped_arrow_base.png");
            }
            else {
                PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
                PotionType potionType = meta.getBasePotionData().getType();
                if (potionType.getEffectType() != null)
                    item = RandomUtils.capitalizeString(itemStack.getType().toString()) + " of " + RandomUtils.capitalizeString(RandomUtils.prettierEffectType(potionType));
                else {
                    item = RandomUtils.prettierEffectType(potionType) + " " + RandomUtils.capitalizeString(itemStack.getType().toString());
                }

                eb.setThumbnail("https://res.cloudinary.com/pryormc/image/upload/l_potion_overlay,e_tint:100:" + RandomUtils.colorToHex(meta, potionType) + "/h_250/" + itemStack.getType() + ".png");
                eb.setAuthor(event.getSeller().getName()+ " is selling " + item + " for $" + event.getPlayerAuction().getPrice(), null,
                        "https://crafatar.com/avatars/"+ event.getSeller().getUniqueId());
                System.out.println("https://res.cloudinary.com/pryormc/image/upload/l_potion_overlay,e_tint:100:" + RandomUtils.colorToHex(meta, potionType) + "/h_250/" + itemStack.getType() + ".png");
            }
        }
        else {
            eb.setThumbnail("https://ik.imagekit.io/pryormc/"+ itemStack.getType().toString().toLowerCase() +".png?tr=w-128,h-128");

        }
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
