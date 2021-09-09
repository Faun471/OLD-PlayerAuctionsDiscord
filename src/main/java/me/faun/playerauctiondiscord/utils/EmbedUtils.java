package me.faun.playerauctiondiscord.utils;

import com.olziedev.playerauctions.api.auction.Auction;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import me.faun.playerauctiondiscord.PlayerAuctionsDiscord;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.Map;
import java.util.Set;

public class EmbedUtils  {
    public static MessageEmbed getEmbedBuilder(EmbedType type, ItemStack itemStack, Player seller, @Nullable Player buyer, Auction auction) {
        EmbedBuilder eb = new EmbedBuilder();

        String item = StringUtils.itemName(itemStack);
        String price = String.valueOf(auction.getPrice());
        String buyerName = buyer != null ? buyer.getName() : "N/A";
        String configType = type.toString().toLowerCase();

        Configuration config = PlayerAuctionsDiscord.getInstance().getConfig();

        ConfigurationSection mainConfig = config.getConfigurationSection(configType + "-embed");
        ConfigurationSection fields = config.getConfigurationSection(configType + "-embed.fields");

        eb.setThumbnail(StringUtils.getLink(itemStack));

        //Todo having a bunch of .replace looks stupid, do something about it lmfao
        try {
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
                        eb.setAuthor(author
                                .replace("%seller%", seller.getName())
                                .replace("%item%", item)
                                .replace("%price%", price)
                                .replace("%buyer%", buyerName)
                                .replace("%auction_id%", String.valueOf(auction.getID())), null, "https://crafatar.com/avatars/" + seller.getUniqueId());
                        eb.setColor(color);
                        eb.setTitle(title
                                .replace("%seller%", seller.getName())
                                .replace("%item%", item)
                                .replace("%price%", price)
                                .replace("%buyer%", buyerName)
                                .replace("%auction_id%", String.valueOf(auction.getID())), null);
                        eb.setFooter(footer
                                .replace("%seller%", seller.getName())
                                .replace("%item%", item)
                                .replace("%price%", price)
                                .replace("%buyer%", buyerName)
                                .replace("%auction_id%", String.valueOf(auction.getID())), null);
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
                        eb.addField(name.replace("%seller%", seller.getName())
                                .replace("%item%", item)
                                .replace("%price%", price)
                                .replace("%buyer%", buyerName)
                                .replace("%auction_id%", String.valueOf(auction.getID())), value.replace("%seller%", seller.getName())
                                .replace("%item%", item)
                                .replace("%price%", price)
                                .replace("%buyer%", buyerName)
                                .replace("%auction_id%", String.valueOf(auction.getID())), inline);
                    }
                }
            }
            return eb.build();
        }
        catch (Exception e) {
            e.printStackTrace();
            PlayerAuctionsDiscord.getInstance().getLogger().severe("Invalid config.yml, disabling plugin");
            Bukkit.getPluginManager().disablePlugin(PlayerAuctionsDiscord.getInstance());
        }
        return eb.build();
    }
}
