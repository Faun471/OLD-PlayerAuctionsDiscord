package me.faun.playerauctiondiscord.utils;

import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.awt.*;

public class RandomUtils {
    //Yoinked from InteractiveChatDiscordSRVAddon
    public static final Color WATER_COLOR = Color.decode("#385dc6");
    public static final Color UNCRAFTABLE_COLOR = Color.decode("#ff5bde");

    public static String capitalizeString(String string) {
        StringBuilder sb = new StringBuilder(string.replace("_", " ").toLowerCase());
        int i = 0;
        do {
            sb.replace(i, i + 1, sb.substring(i, i + 1).toUpperCase());
            i = sb.indexOf(" ", i) + 1;
        } while (i > 0 && i < sb.length());
        return sb.toString();
    }

    //Yoinked from InteractiveChatDiscordSRVAddon
    public static Color getPotionBaseColor(PotionType type) {
        PotionEffectType effect = type.getEffectType();
        if (effect == null) {
            if (type.equals(PotionType.UNCRAFTABLE)) {
                return UNCRAFTABLE_COLOR;
            } else {
                return WATER_COLOR;
            }
        } else {
            return new Color(effect.getColor().asRGB());
        }
    }

    public static String colorToHex(PotionMeta meta, PotionType potionType) {
        int rgb;
        String hex;
        try {
            if (meta.hasColor()) {
                rgb = meta.getColor().asRGB();
            } else {
                rgb = RandomUtils.getPotionBaseColor(potionType).getRGB();
            }
            hex = Integer.toHexString(rgb);
        } catch (Throwable e) {
            rgb = RandomUtils.getPotionBaseColor(PotionType.WATER).getRGB();
            hex = Integer.toHexString(rgb);
        }
        return hex;
    }


}
