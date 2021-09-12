package me.faun.playerauctionsdiscord.utils;

import com.google.common.collect.ImmutableMap;
import de.jeff_media.jefflib.SkullUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionType;

import java.util.Base64;
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

    public static String capitalizeString(String string) {
        return WordUtils.capitalizeFully(string).replace("_", " ");
    }

    public static String prettierEffectName(PotionType type) {
        if (type.getEffectType() != null && type != PotionType.TURTLE_MASTER) {
            String effectType = type.getEffectType().getName();
            return effectNames.getOrDefault(effectType, StringUtils.capitalizeString(effectType));
        } else {
            if (type.name().equals("WATER")){
                return "Water Bottle";
            }
            return effectNames.getOrDefault(type.name(), StringUtils.capitalizeString(type.name()));
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
            PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
            assert meta != null;
            PotionType potionType = meta.getBasePotionData().getType();
            if (itemStack.getType() == Material.TIPPED_ARROW) {
                if (potionType.getEffectType() != null) {
                    item = "Arrow of " + StringUtils.capitalizeString(StringUtils.prettierEffectName(potionType));
                } else {
                    if (potionType.equals(PotionType.UNCRAFTABLE)) {
                        item = "Uncraftable Tipped Arrow";
                    } else {
                        item = "Tipped Arrow";
                    }
                }
            } else {
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
            if (head.hasOwner()) {
                return "https://www.mc-heads.net/head/" + head.getOwningPlayer().getUniqueId() + "/100/.png";
            } else {
                String base64Texture = SkullUtils.getBase64Texture(head);
                String trimmed = base64Texture != null ? base64Texture.replace("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv", "").replace("J9fX0=", "") : null;
                byte[] bytes = Base64.getDecoder().decode(trimmed);
                String url = new String(bytes);
                return "https://www.mc-heads.net/head/" + url + "/100/.png";
            }
        }
        //Unlike cloudinary, imagekit doesn't add random characters at the end of each uploaded image, which made it possible for me to mass upload images
        if (itemStack.getAmount() >= 2) {
            return "https://ik.imagekit.io/pryormc/tr:w-64,h-auto,otf-font.ttf,ot-" + itemStack.getAmount() + ",otc-FFFFFF,ofo-bottom_right/" + itemStack.getType().toString().toLowerCase() + ".png";
        } else {
            return "https://ik.imagekit.io/pryormc/tr:w-64,h-auto,otf-font.ttf,ot-%20,otc-FFFFFF,ofo-bottom_right/" + itemStack.getType().toString().toLowerCase() + ".png";
        }
    }
}