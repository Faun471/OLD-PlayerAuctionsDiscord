package me.faun.playerauctiondiscord.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class StringUtils {

    public static String capitalizeString(String string) {
        StringBuilder sb = new StringBuilder(string.replace("_", " ").toLowerCase());
        int i = 0;
        do {
            sb.replace(i, i + 1, sb.substring(i, i + 1).toUpperCase());
            i = sb.indexOf(" ", i) + 1;
        } while (i > 0 && i < sb.length());
        return sb.toString();
    }

    public static String prettierEffectName(PotionType type) {
        try {
            switch (String.valueOf(type)){
                case "AWKWARD":
                    return "Awkward";
                case "UNCRAFTABLE":
                    return "Uncraftable";
                case "THICK":
                    return "T H I C C";
                case "MUNDANE":
                    return "Mundane";
                case "TURTLE_MASTER":
                    return "Turtle Master";
                case "WATER":
                    return "Water Bottle";
                default:
                    return "Unknown";
            }
        } catch (Exception e) {
            switch (type.getEffectType().getName()) {
                case "ABSORPTION":
                    return "Absorption";
                case "BAD_OMEN":
                    return "Bad Omen";
                case "BLINDNESS":
                    return "Blindness";
                case "CONDUIT_POWER":
                    return "Conduit Power";
                case "CONFUSION":
                    return "Confusion";
                case "DAMAGE_RESISTANCE":
                    return "Resistance";
                case "DOLPHINS_GRACE":
                    return "Dolphin's Grace";
                case "FAST_DIGGING":
                    return "Haste";
                case "FIRE_RESISTANCE":
                    return "Fire Resistance";
                case "GLOWING":
                    return "Glowing";
                case "HARM":
                    return "Harming";
                case "HEAL":
                    return "Healing";
                case "HEALTH_BOOST":
                    return "Health Boost";
                case "HERO_OF_THE_VILLAGE":
                    return "Hero of the Village";
                case "HUNGER":
                    return "Hunger";
                case "INCREASE_DAMAGE":
                    return "Strength";
                case "INVISIBILITY":
                    return "Invisibility";
                case "JUMP":
                    return "Leaping";
                case "LEVITATION":
                    return "Levitation";
                case "LUCK":
                    return "Luck";
                case "NIGHT_VISION":
                    return "Night Vision";
                case "POISON":
                    return "Poison";
                case "REGENERATION":
                    return "Regeneration";
                case "SATURATION":
                    return "Saturation";
                case "SLOW":
                    return "Slowness";
                case "SLOW_DIGGING":
                    return "Mining Fatigue";
                case "SLOW_FALLING":
                    return "Slow Falling";
                case "SPEED":
                    return "Swiftness";
                case "UNLUCK":
                    return "Bad Luck";
                case "WATER_BREATHING":
                    return "Water Breathing";
                case "WEAKNESS":
                    return "Weakness";
                case "WITHER":
                    return "Wither";
                default:
                    return "Unknown";
            }
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
            return "https://ik.imagekit.io/pryormc/tr:otf-font.ttf,ot-" + itemStack.getAmount() + ",ots-90,otc-FFFFFF,oy-250,ox-250/" + itemStack.getType().toString().toLowerCase() + ".png?tr=w-128,h-128";
        } else return "https://ik.imagekit.io/pryormc/tr:otf-font.ttf,ot- ,ots-90,otc-FFFFFF,oy-250,ox-250/" + itemStack.getType().toString().toLowerCase() + ".png?tr=w-128,h-128";
    }
}
