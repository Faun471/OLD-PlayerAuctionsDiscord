package me.faun.playerauctiondiscord.utils;

import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.awt.Color;

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

    public static String prettierEffectType(PotionType type) {
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
        String buf;
        String hex;
        try {
            if (meta.hasColor()) {
                rgb = meta.getColor().asRGB();
            } else {
                rgb = RandomUtils.getPotionBaseColor(potionType).getRGB();
            }
            buf = Integer.toHexString(rgb);
            hex = buf.substring(buf.length()-6);
        } catch (Exception e) {
            buf = String.valueOf(WATER_COLOR);
            hex = buf.substring(buf.length()-6);
            e.printStackTrace();
        }
        return hex;
    }


}
