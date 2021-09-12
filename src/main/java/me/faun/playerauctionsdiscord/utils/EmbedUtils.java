package me.faun.playerauctionsdiscord.utils;

import com.olziedev.playerauctions.api.auction.Auction;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import me.faun.playerauctionsdiscord.PlayerAuctionsDiscord;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.Map;
import java.util.Set;

public class EmbedUtils  {
    public static MessageEmbed getEmbedBuilder(EmbedType type, ItemStack itemStack, OfflinePlayer seller, @Nullable Player buyer, Auction auction) {
        EmbedBuilder eb = new EmbedBuilder();

        String configType = type.toString().toLowerCase();

        Configuration config = PlayerAuctionsDiscord.getInstance().getConfig();

        ConfigurationSection mainConfig = config.getConfigurationSection(configType + "-embed");
        ConfigurationSection fields = config.getConfigurationSection(configType + "-embed.fields");

        eb.setThumbnail(StringUtils.getLink(itemStack));

        if (mainConfig != null) {
            Set<String> mainConfigKeys = mainConfig.getKeys(false);
            for (String ignored : mainConfigKeys) {
                ConfigurationSection configurationSection = config.getConfigurationSection(configType + "-embed");
                if (configurationSection != null) {
                    Map<String, Object> values = configurationSection.getValues(false);
                    String author = (String) values.get("author");
                    Color color = Color.decode((String) values.get("color"));
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
        if (string != null) {
            return string
                    .replace("%seller%", auction.getAuctionPlayer().getName())
                    .replace("%item%", StringUtils.itemName(auction.getItem()))
                    .replace("%amount%", String.valueOf(auction.getItem().getAmount()))
                    .replace("%price%", String.valueOf(auction.getPrice()))
                    .replace("%auction_id%", String.valueOf(auction.getID()))
                    .replace("%buyer%", buyerName);
        } else return " ";
    }
}

