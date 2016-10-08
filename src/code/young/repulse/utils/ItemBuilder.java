package code.young.repulse.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Calvin on 5/19/2015.
 */
public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemstack) {
        item = itemstack;
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder addLore(String lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> lores = meta.getLore();

        if (lores == null) {
            lores = new ArrayList<>();
        }

        lores.add(ChatColor.translateAlternateColorCodes('&', lore));
        meta.setLore(lores);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder durability(int durability) {
        item.setDurability((short) durability);
        return this;
    }

    public ItemBuilder data(int data) {
        item.setData(new MaterialData(item.getType(), (byte) data));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment) {
        item.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder material(Material material) {
        item.setType(material);
        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(new ArrayList<String>());
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearEnchantments() {
        for (Enchantment e : item.getEnchantments().keySet()) {
            item.removeEnchantment(e);
        }

        return this;
    }

    public ItemBuilder color(Color color) {
        if (item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_BOOTS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color);
            item.setItemMeta(meta);
            return this;
        } else {
            throw new IllegalArgumentException("You can only apply color to leather armor!");
        }
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        ItemMeta meta = item.getItemMeta();
        if (unbreakable) {
            meta.spigot().setUnbreakable(true);
        } else {
            meta.spigot().setUnbreakable(true);
        }

        return this;
    }

    public ItemStack build() {
        return item;
    }
}
