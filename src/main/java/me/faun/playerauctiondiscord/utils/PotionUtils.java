package me.faun.playerauctiondiscord.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.awt.Color;

public class PotionUtils {
    //Yoinked from InteractiveChatDiscordSRVAddon
    public static final Color WATER_COLOR = Color.decode("#385dc6");
    public static final Color UNCRAFTABLE_COLOR = Color.decode("#ff5bde");

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

    public static Boolean isPotion (ItemStack itemStack) {
        return itemStack.getItemMeta() instanceof PotionMeta;
    }
}
