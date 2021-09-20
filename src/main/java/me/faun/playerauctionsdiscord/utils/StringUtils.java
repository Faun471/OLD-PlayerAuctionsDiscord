package me.faun.playerauctionsdiscord.utils;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import java.util.Map;

public class StringUtils {

    public static final Map<String, String> effectNames = ImmutableMap.<String, String>builder()
            .put("DAMAGE_RESISTANCE", "Resistance")
            .put("DOLPHINS_GRACE", "Dolphin's Grace")
            .put("FAST_DIGGING", "Haste")
            .put("HARM", "Harming")
            .put("HEAL", "Healing")
            .put("INCREASE_DAMAGE", "Strength")
            .put("JUMP", "Leaping")
            .put("SLOW", "Slowness")
            .put("SLOW_DIGGING", "Mining Fatigue")
            .put("SPEED", "Swiftness")
            .put("UNLUCK", "Bad Luck")
            .build();

    public static String getNicerString(String string) {
        string = string.trim().replace("_", " ");
        return ChatColor.stripColor(WordUtils.capitalizeFully(string));
    }

    public static String prettierEffectName(PotionType type) {
        if (type.getEffectType() != null && type != PotionType.TURTLE_MASTER) {
            String effectType = type.getEffectType().getName();
            return effectNames.getOrDefault(effectType, StringUtils.getNicerString(effectType));
        } else {
            if (type.name().equals("WATER")){
                return "Water Bottle";
            }
            return effectNames.getOrDefault(type.name(), StringUtils.getNicerString(type.name()));
        }
    }

    public static String colorToHex(PotionMeta meta, PotionType potionType) {
        int rgb;
        String buf;
        String hex;
        try {
            if (meta.hasColor()) {
                rgb = meta.getColor().asRGB();
            } else {
                rgb = PotionUtils.getPotionBaseColor(potionType).getRGB();
            }
            buf = Integer.toHexString(rgb);
            hex = buf.substring(buf.length()-6);
        } catch (Exception e) {
            buf = String.valueOf(PotionUtils.WATER_COLOR);
            hex = buf.substring(buf.length()-6);
            e.printStackTrace();
        }
        return hex;
    }

    public static String itemName(ItemStack itemStack) {
        String item = StringUtils.getNicerString(itemStack.getType().toString());
        if (PotionUtils.isPotion(itemStack)) {
            PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
            assert meta != null;
            PotionType potionType = meta.getBasePotionData().getType();
            if (itemStack.getType() == Material.TIPPED_ARROW) {
                if (potionType.getEffectType() != null) {
                    item = "Arrow of " + StringUtils.getNicerString(StringUtils.prettierEffectName(potionType));
                } else {
                    if (potionType.equals(PotionType.UNCRAFTABLE)) {
                        item = "Uncraftable Tipped Arrow";
                    } else {
                        item = "Tipped Arrow";
                    }
                }
            } else {
                if (potionType.getEffectType() != null)
                    item = StringUtils.getNicerString(itemStack.getType().toString()) + " of " + StringUtils.getNicerString(StringUtils.prettierEffectName(potionType));
                else {
                    item = StringUtils.prettierEffectName(potionType) + " " + StringUtils.getNicerString(itemStack.getType().toString());
                }
            }
        }
        return item;
    }
}
