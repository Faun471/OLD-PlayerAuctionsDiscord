package me.faun.playerauctionsdiscord.utils;

import com.olziedev.playerauctions.api.auction.Auction;
import de.jeff_media.jefflib.SkullUtils;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import me.faun.playerauctionsdiscord.PlayerAuctionsDiscord;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionType;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

public class EmbedUtils  {
    public static MessageEmbed getEmbedBuilder(EmbedType type, ItemStack itemStack, OfflinePlayer seller, @Nullable Player buyer, Auction auction) {
        EmbedBuilder eb = new EmbedBuilder();

        String configType = type.toString().toLowerCase();

        Configuration config = PlayerAuctionsDiscord.getInstance().getConfig();

        ConfigurationSection mainConfig = config.getConfigurationSection(configType + "-embed");
        ConfigurationSection fields = config.getConfigurationSection(configType + "-embed.fields");

        eb.setThumbnail(getImageLink(itemStack));

        if (mainConfig != null) {
            Set<String> mainConfigKeys = mainConfig.getKeys(false);
            for (String ignored : mainConfigKeys) {
                ConfigurationSection configurationSection = config.getConfigurationSection(configType + "-embed");
                if (configurationSection != null) {
                    Map<String, Object> values = configurationSection.getValues(false);
                    String author = (String) values.get("author");
                    Color color = values.containsKey("color") ? Color.decode((String) values.get("color")) : Color.CYAN;
                    String title = (String) values.get("title");
                    String footer = (String) values.get("footer");
                    eb.setAuthor(processString(author, auction, buyer), null, "https://www.mc-heads.net/head/" + seller.getUniqueId() + "/100/.png");
                    eb.setColor(color);
                    eb.setTitle(processString(title, auction, buyer), null);
                    eb.setFooter(processString(footer, auction, buyer), null);
                }
            }
        }
        if (fields != null){
            Set<String> fieldKeys = fields.getKeys(false);
            for (String string : fieldKeys) {
                ConfigurationSection configurationSection = config.getConfigurationSection(configType + "-embed.fields." + string);
                if (configurationSection != null) {
                    Map<String, Object> values = configurationSection.getValues(false);
                    String name = (String) values.get("name");
                    String value = (String) values.get("value");
                    Boolean inline = (Boolean) values.get("inline");
                    if (name == null && value == null) eb.addBlankField(inline);
                    else eb.addField(processString(name, auction, buyer), processString(value, auction, buyer), inline);
                }
            }
        }
        return eb.build();
    }

    public static String processString(String string, Auction auction, @Nullable Player buyer) {
        String buyerName = buyer != null ? buyer.getName() : "N/A";
        String displayName = auction.getItem().hasItemMeta() ? auction.getItem().getItemMeta().getDisplayName() : StringUtils.itemName(auction.getItem());
        if (string != null) {
            return string
                    .replace("%seller%", auction.getAuctionPlayer().getName())
                    .replace("%item%", StringUtils.itemName(auction.getItem()))
                    .replace("%displayname%", StringUtils.capitalizeString(displayName))
                    .replace("%amount%", String.valueOf(auction.getItem().getAmount()))
                    .replace("%price%", String.valueOf(auction.getPrice()))
                    .replace("%auction_id%", String.valueOf(auction.getID()))
                    .replace("%buyer%", buyerName);
        } else return " ";
    }

    public static String getImageLink(ItemStack itemStack) {
        if (PotionUtils.isPotion(itemStack)) {
            PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
            assert meta != null;
            PotionType potionType = meta.getBasePotionData().getType();
            //Cloudinary allows me to add tint to images, so they're used to handle potions and arrows
            if (itemStack.getType().equals(Material.TIPPED_ARROW)) {
                if (itemStack.getAmount() >= 2) {
                    return "https://res.cloudinary.com/pryormc/image/upload/c_scale,q_100,w_64/c_mfit,e_tint:100:" + StringUtils.colorToHex(meta, potionType) + ",l_tipped_arrow_head,w_64/l_text:font.ttf_15:"+ itemStack.getAmount() +",y_0.3,x_0.25,co_white/v1630686090/tipped_arrow_base.png";
                } else {
                    return "https://res.cloudinary.com/pryormc/image/upload/c_scale,q_100,w_64/c_mfit,e_tint:100:" + StringUtils.colorToHex(meta, potionType) + ",l_tipped_arrow_head,w_64/l_text:font.ttf_15:%20,y_0.3,x_0.25,co_white/v1630686090/tipped_arrow_base.png";
                }
            }
            else {
                return "https://res.cloudinary.com/pryormc/image/upload/c_scale,q_100,w_64/c_mfit,e_tint:100:" + StringUtils.colorToHex(meta, potionType) + ",l_potion_overlay,w_64/l_text:font.ttf_15:%20,y_0.3,x_0.25,co_white/v1630686090/"+ itemStack.getType() + ".png";
            }
        }
        if (itemStack.getItemMeta() instanceof SkullMeta) {
            SkullMeta head = (SkullMeta) itemStack.getItemMeta();
            String base64Texture = SkullUtils.getBase64Texture(head);
            byte[] bytes = Base64.getDecoder().decode(base64Texture);
            String decoded = new String(bytes);
            JSONObject json = new JSONObject(decoded);
            JSONObject textures = json.getJSONObject("textures");
            JSONObject skin = textures.getJSONObject("SKIN");
            String url = skin.getString("url");
            return "https://www.mc-heads.net/head" + url.replace("http://textures.minecraft.net/texture/", "/") + "/100/.png";

        }
        //Unlike cloudinary, imagekit doesn't add random characters at the end of each uploaded image, which made it possible for me to mass upload images
        if (itemStack.getAmount() >= 2) {
            return "https://ik.imagekit.io/pryormc/tr:w-64,h-auto,otf-font.ttf,ot-" + itemStack.getAmount() + ",otc-FFFFFF,ofo-bottom_right/" + itemStack.getType().toString().toLowerCase() + ".png";
        } else {
            return "https://ik.imagekit.io/pryormc/tr:w-64,h-auto,otf-font.ttf,ot-%20,otc-FFFFFF,ofo-bottom_right/" + itemStack.getType().toString().toLowerCase() + ".png";
        }
    }
}


