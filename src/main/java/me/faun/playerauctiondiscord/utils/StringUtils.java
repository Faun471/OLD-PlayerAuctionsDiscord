package me.faun.playerauctiondiscord.utils;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class StringUtils {

    public static String capitalizeString(String string) {
        return WordUtils.capitalizeFully(string).replace("_", " ");
    }

    public static String prettierEffectName(PotionType type) {
        if (type.getEffectType() != null) {
            String effectType = type.getEffectType().getName();
            switch (effectType) {
                case "DAMAGE_RESISTANCE":
                    return "Resistance";
                case "DOLPHINS_GRACE":
                    return "Dolphin's Grace";
                case "FAST_DIGGING":
                    return "Haste";
                case "HARM":
                    return "Harming";
                case "HEAL":
                    return "Healing";
                case "INCREASE_DAMAGE":
                    return "Strength";
                case "JUMP":
                    return "Leaping";
                case "SLOW":
                    return "Slowness";
                case "SLOW_DIGGING":
                    return "Mining Fatigue";
                case "SPEED":
                    return "Swiftness";
                case "UNLUCK":
                    return "Bad Luck";
                default:
                    return StringUtils.capitalizeString(effectType);
            }
        } else {
            if (type.name().equals("WATER")) {
                return "Water Bottle";
            }
            return StringUtils.capitalizeString(type.name());
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
        String item = StringUtils.capitalizeString(itemStack.getType().toString());
        if (PotionUtils.isPotion(itemStack)) {
            if (itemStack.getType() == Material.TIPPED_ARROW) {
                PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
                PotionType potionType = meta.getBasePotionData().getType();
                if (potionType.getEffectType() != null) {
                    item = "Arrow of " + StringUtils.capitalizeString(StringUtils.prettierEffectName(potionType));
                } else {
                    if (potionType.equals(PotionType.UNCRAFTABLE)) {
                        item = "Tipped Arrow";
                    } else {
                        item = "Uncraftable Tipped Arrow";
                    }
                }
            } else {
                PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
                PotionType potionType = meta.getBasePotionData().getType();
                if (potionType.getEffectType() != null)
                    item = StringUtils.capitalizeString(itemStack.getType().toString()) + " of " + StringUtils.capitalizeString(StringUtils.prettierEffectName(potionType));
                else {
                    item = StringUtils.prettierEffectName(potionType) + " " + StringUtils.capitalizeString(itemStack.getType().toString());
                }
            }
        }
        return item;
    }

    public static String getLink (ItemStack itemStack) {
        if (PotionUtils.isPotion(itemStack)) {
            PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
            PotionType potionType = meta.getBasePotionData().getType();
            if (itemStack.getType().equals(Material.TIPPED_ARROW)) {
                return "https://res.cloudinary.com/pryormc/image/upload/l_tipped_arrow_head,e_tint:100:" +
                        StringUtils.colorToHex(meta, potionType) + "/h_250/tipped_arrow_base.png";
            }
            else {
                return "https://res.cloudinary.com/pryormc/image/upload/l_potion_overlay,e_tint:100:" + StringUtils.colorToHex(meta, potionType) + "/h_250/" + itemStack.getType() + ".png";
            }
        }
        if (itemStack.getAmount() >= 2) {
            return "https://ik.imagekit.io/pryormc/tr:w-64,h-auto,otf-font.ttf,ot-" + itemStack.getAmount() + ",otc-FFFFFF,ofo-bottom_right/" + itemStack.getType().toString().toLowerCase() + ".png";
        } else {
            return "https://ik.imagekit.io/pryormc/tr:w-64,h-auto,otf-font.ttf,ot- ,otc-FFFFFF,ofo-bottom_right/" + itemStack.getType().toString().toLowerCase() + ".png";
        }
    }
}
