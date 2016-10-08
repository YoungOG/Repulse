package code.young.repulse.managers;

import code.young.repulse.Repulse;
import code.young.repulse.objects.Kit;
import code.young.repulse.objects.Profile;
import code.young.repulse.utils.ItemBuilder;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class KitManager {

    private ProfileManager pm = Repulse.getInstance().getProfileManager();
    private ArrayList<Material> whitelistedMaterials = new ArrayList<>();

    public KitManager() {
        whitelistedMaterials.add(Material.DIAMOND_HELMET);
        whitelistedMaterials.add(Material.DIAMOND_CHESTPLATE);
        whitelistedMaterials.add(Material.DIAMOND_LEGGINGS);
        whitelistedMaterials.add(Material.DIAMOND_BOOTS);
        whitelistedMaterials.add(Material.DIAMOND_SWORD);
        whitelistedMaterials.add(Material.POTION);
        whitelistedMaterials.add(Material.BAKED_POTATO);
        whitelistedMaterials.add(Material.COOKED_BEEF);
        whitelistedMaterials.add(Material.GOLDEN_CARROT);
        whitelistedMaterials.add(Material.ENDER_PEARL);
        whitelistedMaterials.add(Material.MILK_BUCKET);
        whitelistedMaterials.add(Material.BOW);
        whitelistedMaterials.add(Material.ARROW);
        whitelistedMaterials.add(Material.AIR);
    }

    public void saveMchcfKit(Player p, int slot) {
        boolean hasIllegalItems = false;

        for (int i = 0; i <= p.getInventory().getSize(); i++) {
            ItemStack item = p.getInventory().getItem(i);
            if (item == null) continue;
            if (!whitelistedMaterials.contains(item.getType())) {
                p.getInventory().remove(item);
                hasIllegalItems = true;
            }
            if (item.getType() == Material.POTION) {
                if (item.getDurability() != 16421
                        && item.getDurability() != 16388
                        && item.getDurability() != 16426
                        && item.getDurability() != 8226
                        && item.getDurability() != 8259
                        && item.getDurability() != 8265) {
                    p.getInventory().remove(item);
                    hasIllegalItems = true;
                }
            }
        }

        if (hasIllegalItems)
            MessageManager.sendMessage(p, "&cIllegal Items detected. They have been removed.");

        p.updateInventory();

        Profile prof = pm.getProfile(p.getUniqueId());

        if (slot == 1) {
            Kit ck = null;
            if (prof.getMchcfKit(1) != null) {
                ck = prof.getMchcfKit(1);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 1", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
            }

            prof.setMchcfKit(k, 1);
            pm.reloadProfile(prof);
        } else if (slot == 2) {
            Kit ck = null;
            if (prof.getMchcfKit(2) != null) {
                ck = prof.getMchcfKit(2);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 2", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
            }

            prof.setNoEnchantmentKit(k, 2);
            pm.reloadProfile(prof);
        } else if (slot == 3) {
            Kit ck = null;
            if (prof.getMchcfKit(3) != null) {
                ck = prof.getMchcfKit(3);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 3", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
            }

            prof.setMchcfKit(k, 3);
            pm.reloadProfile(prof);
        } else if (slot == 4) {
            Kit ck = null;
            if (prof.getMchcfKit(4) != null) {
                ck = prof.getMchcfKit(4);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 4", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
            }

            prof.setMchcfKit(k, 4);
            pm.reloadProfile(prof);
        } else if (slot == 5) {
            Kit ck = null;
            if (prof.getMchcfKit(5) != null) {
                ck = prof.getMchcfKit(5);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 1", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
            } else {
                k.setName("Kit 1");
            }

            prof.setMchcfKit(k, 5);
            pm.reloadProfile(prof);
        }

        MessageManager.sendMessage(p, "&bProtection II &akit has been saved.");
        p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0, -5);
        p.closeInventory();
    }

    public void loadMchcfKit(Player p, int slot) {
        Profile prof = pm.getProfile(p.getUniqueId());

        if (slot == 1) {
            Kit k = prof.getMchcfKit(1);

            InventorySerialization.setPlayerInventory(p, k.getInventory());

            p.updateInventory();
        } else if (slot == 2) {
            Kit k = prof.getMchcfKit(2);

            InventorySerialization.setPlayerInventory(p, k.getInventory());
            p.updateInventory();
        } else if (slot == 3) {
            Kit k = prof.getMchcfKit(3);

            InventorySerialization.setPlayerInventory(p, k.getInventory());

            p.updateInventory();
        } else if (slot == 4) {
            Kit k = prof.getMchcfKit(4);

            InventorySerialization.setPlayerInventory(p, k.getInventory());

            p.updateInventory();
        } else if (slot == 5) {
            Kit k = prof.getMchcfKit(5);

            InventorySerialization.setPlayerInventory(p, k.getInventory());

            p.updateInventory();
        }
    }

    public void saveNoEnchantmentKit(Player p, int slot) {
        boolean hasIllegalItems = false;
        boolean hasIllegalEnchantments = false;

        for (int i = 0; i <= p.getInventory().getSize(); i++) {
            ItemStack item = p.getInventory().getItem(i);
            if (item == null) continue;
            if (!whitelistedMaterials.contains(item.getType())) {
                p.getInventory().remove(item);
                hasIllegalItems = true;
            }
            if (item.hasItemMeta()) {
                if (item.getItemMeta().hasEnchants()) {
                    hasIllegalEnchantments = true;
                    for (Enchantment ench : item.getEnchantments().keySet()) {
                        item.removeEnchantment(ench);
                    }
                }
            }
            if (item.getType() == Material.POTION) {
                if (item.getDurability() != 16421
                        && item.getDurability() != 16421
                        && item.getDurability() != 16388
                        && item.getDurability() != 16426
                        && item.getDurability() != 8226
                        && item.getDurability() != 8259) {
                    p.getInventory().remove(item);
                    hasIllegalItems = true;
                }
            }
        }

        if (hasIllegalItems)
            MessageManager.sendMessage(p, "&cIllegal Items detected. They have been removed.");
        if (hasIllegalEnchantments)
            MessageManager.sendMessage(p, "&cIllegal Enchantments detected. They have been removed.");

        p.updateInventory();

        Profile prof = pm.getProfile(p.getUniqueId());

        if (slot == 1) {
            Kit ck = null;
            if (prof.getNoEnchantmentKit(1) != null) {
                ck = prof.getNoEnchantmentKit(1);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 1", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
            }

            prof.setNoEnchantmentKit(k, 1);
            pm.reloadProfile(prof);
        } else if (slot == 2) {
            Kit ck = null;
            if (prof.getNoEnchantmentKit(2) != null) {
                ck = prof.getNoEnchantmentKit(2);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 2", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
            }

            prof.setNoEnchantmentKit(k, 2);
            pm.reloadProfile(prof);
        } else if (slot == 3) {
            Kit ck = null;
            if (prof.getNoEnchantmentKit(3) != null) {
                ck = prof.getNoEnchantmentKit(3);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 3", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
            }

            prof.setNoEnchantmentKit(k, 3);
            pm.reloadProfile(prof);
        } else if (slot == 4) {
            Kit ck = null;
            if (prof.getNoEnchantmentKit(4) != null) {
                ck = prof.getNoEnchantmentKit(4);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 4", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
            }

            prof.setNoEnchantmentKit(k, 4);
            pm.reloadProfile(prof);
        } else if (slot == 5) {
            Kit ck = null;
            if (prof.getNoEnchantmentKit(5) != null) {
                ck = prof.getNoEnchantmentKit(5);
            }

            Kit k = new Kit(p.getUniqueId(), InventorySerialization.serializePlayerInventoryAsString(p.getInventory()), "Kit 1", 0, 0, 0);

            if (ck != null) {
                k.setName(ck.getName());
            } else {
                k.setName("Kit 1");
            }

            prof.setNoEnchantmentKit(k, 5);
            pm.reloadProfile(prof);
        }

        MessageManager.sendMessage(p, "&bNo Enchantment &akit has been saved."); //der we go nigga

        p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0, -5);
        p.closeInventory();
    }

    public void loadNoEnchantmentKit(Player p, int slot) {
        Profile prof = pm.getProfile(p.getUniqueId());

        if (slot == 1) {
            Kit k = prof.getNoEnchantmentKit(1);

            InventorySerialization.setPlayerInventory(p, k.getInventory());

            p.updateInventory();
        } else if (slot == 2) {
            Kit k = prof.getNoEnchantmentKit(2);

            InventorySerialization.setPlayerInventory(p, k.getInventory());
            p.updateInventory();
        } else if (slot == 3) {
            Kit k = prof.getNoEnchantmentKit(3);

            InventorySerialization.setPlayerInventory(p, k.getInventory());

            p.updateInventory();
        } else if (slot == 4) {
            Kit k = prof.getNoEnchantmentKit(4);

            InventorySerialization.setPlayerInventory(p, k.getInventory());

            p.updateInventory();
        } else if (slot == 5) {
            Kit k = prof.getNoEnchantmentKit(5);

            InventorySerialization.setPlayerInventory(p, k.getInventory());

            p.updateInventory();
        }
    }

    public void showPreview(Player p, Kit k) {
        Inventory pinv = Bukkit.createInventory(p, 54, "&5&lKit Preview");

        InventorySerialization.setPlayerInventory(p, k.getInventory());

        pinv.setContents(p.getInventory().getContents());

        if (p.getInventory().getHelmet() != null) {
            pinv.setItem(50, p.getInventory().getHelmet());
        }

        if (p.getInventory().getChestplate() != null) {
            pinv.setItem(51, p.getInventory().getChestplate());
        }

        if (p.getInventory().getLeggings() != null) {
            pinv.setItem(52, p.getInventory().getLeggings());
        }

        if (p.getInventory().getBoots() != null) {
            pinv.setItem(53, p.getInventory().getBoots());
        }

        p.openInventory(pinv);
        p.getInventory().clear();
        p.getInventory().setHelmet(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);
        p.updateInventory();
    }

    public void giveDefaultMchcfKit(Player p) {
        ItemStack helm = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchantment(Enchantment.DURABILITY, 3).build();

        ItemStack chest = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchantment(Enchantment.DURABILITY, 3).build();

        ItemStack legs = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchantment(Enchantment.DURABILITY, 3).build();

        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
                .addEnchantment(Enchantment.DURABILITY, 3).build();

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 2)
                .addEnchantment(Enchantment.FIRE_ASPECT, 1).build();

        ItemStack p1 = new ItemBuilder(Material.POTION).durability(16388).build();

        ItemStack p2 = new ItemBuilder(Material.POTION).durability(16426).build();

        ItemStack p3 = new ItemBuilder(Material.POTION).durability(8226).build();

        ItemStack p4 = new ItemBuilder(Material.POTION).durability(8259).build();

        ItemStack p5 = new ItemBuilder(Material.POTION).durability(8265).build();

        ItemStack p6 = new ItemBuilder(Material.POTION).durability(16421).build();

        p.getInventory().setHelmet(helm);
        p.getInventory().setChestplate(chest);
        p.getInventory().setLeggings(legs);
        p.getInventory().setBoots(boots);

        p.getInventory().setItem(0, sword);
        p.getInventory().setItem(1, p1);
        p.getInventory().setItem(2, p2);
        p.getInventory().setItem(28, p1);
        p.getInventory().setItem(29, p2);
        p.getInventory().setItem(6, p3);
        p.getInventory().setItem(24, p3);
        p.getInventory().setItem(33, p3);
        p.getInventory().setItem(7, p4);
        p.getInventory().setItem(8, p5);
        p.getInventory().setItem(9, new ItemBuilder(Material.COOKED_BEEF).amount(64).build());
        p.getInventory().setItem(17, new ItemBuilder(Material.ENDER_PEARL).amount(16).build());

        for (int i = 0; i < 36; i++) {
            p.getInventory().addItem(p6);
        }

        p.updateInventory();
    }

    public void giveDefaultNoEnchantmentKit(Player p) {
        ItemStack helm = new ItemBuilder(Material.DIAMOND_HELMET).build();

        ItemStack chest = new ItemBuilder(Material.DIAMOND_CHESTPLATE).build();

        ItemStack legs = new ItemBuilder(Material.DIAMOND_LEGGINGS).build();

        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS).build();

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).build();

        ItemStack p1 = new ItemBuilder(Material.POTION).durability(16388).build();

        ItemStack p2 = new ItemBuilder(Material.POTION).durability(16426).build();

        ItemStack p3 = new ItemBuilder(Material.POTION).durability(8226).build();

        ItemStack p4 = new ItemBuilder(Material.POTION).durability(8259).build();

        ItemStack p5 = new ItemBuilder(Material.POTION).durability(16421).build();

        p.getInventory().setHelmet(helm);
        p.getInventory().setChestplate(chest);
        p.getInventory().setLeggings(legs);
        p.getInventory().setBoots(boots);

        p.getInventory().setItem(0, sword);
        p.getInventory().setItem(1, p1);
        p.getInventory().setItem(2, p2);
        p.getInventory().setItem(28, p1);
        p.getInventory().setItem(29, p2);
        p.getInventory().setItem(6, p3);
        p.getInventory().setItem(24, p3);
        p.getInventory().setItem(33, p3);
        p.getInventory().setItem(7, p4);
        p.getInventory().setItem(9, new ItemBuilder(Material.COOKED_BEEF).amount(64).build());
        p.getInventory().setItem(8, new ItemBuilder(Material.ENDER_PEARL).amount(16).build());

        for (int i = 0; i < 36; i++) {
            p.getInventory().addItem(p5);
        }

        p.updateInventory();
    }

    public Boolean hasMchcfKit(Player p) {
        Profile prof = pm.getProfile(p.getUniqueId());
        return prof.getMchcfKit(1) != null || prof.getMchcfKit(2) != null || prof.getMchcfKit(3) != null || prof.getMchcfKit(4) != null || prof.getMchcfKit(5) != null;
    }

    public Boolean hasNoEnchantmentKit(Player p) {
        Profile prof = pm.getProfile(p.getUniqueId());
        return prof.getNoEnchantmentKit(1) != null || prof.getNoEnchantmentKit(2) != null || prof.getNoEnchantmentKit(3) != null || prof.getNoEnchantmentKit(4) != null || prof.getNoEnchantmentKit(5) != null;
    }
}