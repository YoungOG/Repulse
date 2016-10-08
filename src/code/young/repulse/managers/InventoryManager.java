package code.young.repulse.managers;

import code.young.repulse.Repulse;
import code.young.repulse.objects.Kit;
import code.young.repulse.objects.Profile;
import code.young.repulse.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class InventoryManager {

    private ProfileManager pm = Repulse.getInstance().getProfileManager();

    public void showProtectionIIMenu(Player p) {
        final Inventory protectionIIMenu = Bukkit.createInventory(p, 27, ChatColor.translateAlternateColorCodes('&', "&b&lProtection II"));

        final ItemStack helm = new ItemBuilder(Material.DIAMOND_HELMET).name("&bDiamond Helmet").addLore("&a* Auto Equips *").build();
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        helm.addEnchantment(Enchantment.DURABILITY, 3);

        final ItemStack chest = new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("&bDiamond Chestplate").addLore( "&a* Auto Equips *").build();
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chest.addEnchantment(Enchantment.DURABILITY, 3);

        final ItemStack leg = new ItemBuilder(Material.DIAMOND_LEGGINGS).name("&bDiamond Leggings").addLore("&a* Auto Equips *").build();
        leg.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leg.addEnchantment(Enchantment.DURABILITY, 3);

        final ItemStack boot = new ItemBuilder(Material.DIAMOND_BOOTS).name("&bDiamond Boots").addLore("&a* Auto Equips *").build();
        boot.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boot.addEnchantment(Enchantment.DURABILITY, 3);

        final ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&bDiamond Sword").build();
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);

        final ItemStack bow = new ItemBuilder(Material.BOW).name("&6Bow").build();
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        new BukkitRunnable() {
            public void run() {

                // Armor
                protectionIIMenu.setItem(11, helm);
                protectionIIMenu.setItem(12, chest);
                protectionIIMenu.setItem(13, leg);
                protectionIIMenu.setItem(14, boot);
                protectionIIMenu.setItem(15, sword);
                protectionIIMenu.setItem(20, bow);
                protectionIIMenu.setItem(24, new ItemBuilder(Material.ARROW).name("&7Arrow").build());

                // Potions
                protectionIIMenu.setItem(1, new ItemBuilder(Material.POTION).durability(16421).name("&cInstant Health II").build());
                protectionIIMenu.setItem(7, new ItemBuilder(Material.POTION).durability(16388).name("&2Poison I").build());
                protectionIIMenu.setItem(10, new ItemBuilder(Material.POTION).durability(16421).name("&cInstant Health II").addLore("&a* Fill Inventory *").build());
                protectionIIMenu.setItem(16,  new ItemBuilder(Material.POTION).durability(16426).name("&5Slowness I").build());
                protectionIIMenu.setItem(21, new ItemBuilder(Material.POTION).durability(8226).name("&bSpeed II").build());
                protectionIIMenu.setItem(22,new ItemBuilder(Material.POTION).durability(8259).name("&6Fire Resistance").build());
                protectionIIMenu.setItem(23, new ItemBuilder(Material.POTION).durability(8265).name("&5Strength I").build());

                // Food, Enderpearls, Milk
                protectionIIMenu.setItem(3, new ItemBuilder(Material.BAKED_POTATO).amount(64).name("&7Baked Potato").build());
                protectionIIMenu.setItem(4, new ItemBuilder(Material.COOKED_BEEF).amount(64).name("&7Steak").build());
                protectionIIMenu.setItem(5, new ItemBuilder(Material.GOLDEN_CARROT).amount(64).name("&7Golden Carrot").build());
                protectionIIMenu.setItem(19, new ItemBuilder(Material.ENDER_PEARL).amount(16).name("&3Ender Pearl").build());
                protectionIIMenu.setItem(25, new ItemBuilder(Material.MILK_BUCKET).name("&7Milk").build());
            }
        }.runTaskTimerAsynchronously(Repulse.getInstance(), 0l, 5);

        p.openInventory(protectionIIMenu);
    }

    public void showProtectionIISaveMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&a&lSave Protection II"));

        inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 1").addLore("&eClick to save kit!").build());
        inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 2").addLore("&eClick to save kit!").build());
        inv.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 3").addLore("&eClick to save kit!").build());
        inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 4").addLore("&eClick to save kit!").build());
        inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 5").addLore("&eClick to save kit!").build());

        p.openInventory(inv);
    }

    public void showProtectionIILoadMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&b&lLoad Protection II"));

        Profile prof = pm.getProfile(p.getUniqueId());

        if (prof.getMchcfKit(1) != null) {
            Kit k = prof.getMchcfKit(1);
            inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(2) != null) {
            Kit k = prof.getMchcfKit(2);
            inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(3) != null) {
            Kit k = prof.getMchcfKit(3);
            inv.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(4) != null) {
            Kit k = prof.getMchcfKit(4);
            inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(5) != null) {
            Kit k = prof.getMchcfKit(5);
            inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }

        p.openInventory(inv);
    }

    public void showConfigProtectionIIMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 36, ChatColor.translateAlternateColorCodes('&', "&b&lConfig Protection II"));
        Profile prof = pm.getProfile(p.getUniqueId());

        if (prof.getMchcfKit(1) != null) {
            Kit k = prof.getMchcfKit(1);
            inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(2) != null) {
            Kit k = prof.getMchcfKit(2);
            inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(3) != null) {
            Kit k = prof.getMchcfKit(3);
            inv.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(32, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(4) != null) {
            Kit k = prof.getMchcfKit(4);
            inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(25, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(34, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(5) != null) {
            Kit k = prof.getMchcfKit(5);
            inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(36, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }

        p.openInventory(inv);
    }


    public void showNoEnchantmentMenu(Player p) {
        final Inventory noEnchantsMenu = Bukkit.createInventory(p, 27, ChatColor.translateAlternateColorCodes('&', "&b&lNo Enchantment"));

        new BukkitRunnable() {
            public void run() {


                // Armor
                noEnchantsMenu.setItem(11, new ItemBuilder(Material.DIAMOND_HELMET).name( "&bDiamond Helmet").addLore( "&a* Auto Equips *").build());
                noEnchantsMenu.setItem(12, new ItemBuilder(Material.DIAMOND_CHESTPLATE).name( "&bDiamond Chestplate").addLore("&a* Auto Equips *").build());
                noEnchantsMenu.setItem(13, new ItemBuilder(Material.DIAMOND_LEGGINGS).name( "&bDiamond Leggings").addLore( "&a* Auto Equips *").build());
                noEnchantsMenu.setItem(14, new ItemBuilder(Material.DIAMOND_BOOTS).name( "&bDiamond Boots").addLore( "&a* Auto Equips *").build());
                noEnchantsMenu.setItem(15, new ItemBuilder(Material.DIAMOND_SWORD).name("&bDiamond Sword").build());
                noEnchantsMenu.setItem(20, new ItemBuilder(Material.BOW).name("&6Bow").addEnchantment(Enchantment.ARROW_INFINITE).build());
                noEnchantsMenu.setItem(24, new ItemBuilder(Material.ARROW).name("&7Arrow").build());

                // Potions
                noEnchantsMenu.setItem(1, new ItemBuilder(Material.POTION).durability(16421).name("&cInstant Health II").build());
                noEnchantsMenu.setItem(7, new ItemBuilder(Material.POTION).durability(16388).name("&2Poison I").build());
                noEnchantsMenu.setItem(10, new ItemBuilder(Material.POTION).durability(16421).name("&cInstant Health II").addLore("&a* Fill Inventory *").build());
                noEnchantsMenu.setItem(16,  new ItemBuilder(Material.POTION).durability(16426).name("&5Slowness I").build());
                noEnchantsMenu.setItem(21, new ItemBuilder(Material.POTION).durability(8226).name("&bSpeed II").build());
                noEnchantsMenu.setItem(22,new ItemBuilder(Material.POTION).durability(8259).name("&6Fire Resistance").build());
                noEnchantsMenu.setItem(23, new ItemBuilder(Material.POTION).durability(8265).name("&5Strength I").build());

                // Food, Enderpearls, Milk
                noEnchantsMenu.setItem(3, new ItemBuilder(Material.BAKED_POTATO).amount(64).name("&7Baked Potato").build());
                noEnchantsMenu.setItem(4, new ItemBuilder(Material.COOKED_BEEF).amount(64).name("&7Steak").build());
                noEnchantsMenu.setItem(5, new ItemBuilder(Material.GOLDEN_CARROT).amount(64).name("&7Golden Carrot").build());
                noEnchantsMenu.setItem(19, new ItemBuilder(Material.ENDER_PEARL).amount(16).name("&3Ender Pearl").build());
                noEnchantsMenu.setItem(25, new ItemBuilder(Material.MILK_BUCKET).name("&7Milk").build());
            }
        }.runTaskTimerAsynchronously(Repulse.getInstance(), 0l, 5);

        p.openInventory(noEnchantsMenu);
    }

    public void showNoEnchantmentSaveMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&a&lSave No Enchantment"));

        inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 1").addLore("&eClick to save kit!").build());
        inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 2").addLore("&eClick to save kit!").build());
        inv.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 3").addLore("&eClick to save kit!").build());
        inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 4").addLore("&eClick to save kit!").build());
        inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit 5").addLore("&eClick to save kit!").build());

        p.openInventory(inv);
    }

    public void showNoEnchantmentLoadMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&b&lLoad No Enchantment"));

        Profile prof = pm.getProfile(p.getUniqueId());

        if (prof.getMchcfKit(1) != null) {
            Kit k = prof.getMchcfKit(1);
            inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(2) != null) {
            Kit k = prof.getMchcfKit(2);
            inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(3) != null) {
            Kit k = prof.getMchcfKit(3);
            inv.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(4) != null) {
            Kit k = prof.getMchcfKit(4);
            inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(5) != null) {
            Kit k = prof.getMchcfKit(5);
            inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lLoad &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());
        } else {
            inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }

        p.openInventory(inv);
    }

    public void showConfigNoEnchantmentMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 36, ChatColor.translateAlternateColorCodes('&', "&b&lConfig No Enchantment"));
        Profile prof = pm.getProfile(p.getUniqueId());

        if (prof.getMchcfKit(1) != null) {
            Kit k = prof.getMchcfKit(1);
            inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(2) != null) {
            Kit k = prof.getMchcfKit(2);
            inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(3) != null) {
            Kit k = prof.getMchcfKit(3);
            inv.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(32, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(4) != null) {
            Kit k = prof.getMchcfKit(4);
            inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(25, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(34, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(5) != null) {
            Kit k = prof.getMchcfKit(5);
            inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).addLore(
                    "&6&lPreview &e&l" + ChatColor.stripColor(k.getName())).addLore(
                    "&d&l&m--------------------").addLore(
                    "&6Kills: &e" + k.getKills()).addLore(
                    "&6Deaths: &e" + k.getDeaths()).addLore(
                    "&6Times Used: &e" + k.getUses()).build());

            inv.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(3).name("&b&lLoad Kit").build());

            inv.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(5).name("&a&lSave Kit").build());

            inv.setItem(36, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&lDelete Kit").build());
        } else {
            inv.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }

        p.openInventory(inv);
    }

    public void showUnrankedMenu(Player p) {
        Inventory settingsMenu = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&b&lUnranked"));

        ItemStack ri = new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("&b&lUnranked Protection II Match").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
        settingsMenu.setItem(3, ri);
        settingsMenu.setItem(5, new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("&b&lUnranked No Enchantment Match").build());
        settingsMenu.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());

        p.openInventory(settingsMenu);
    }

    public void showRankedMenu(Player p) {
        Inventory settingsMenu = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&b&lRanked"));

        ItemStack ri = new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("&b&lRanked Protection II Match").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
        ri.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        settingsMenu.setItem(3, ri);
        settingsMenu.setItem(5, new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("&b&lRanked No Enchantment Match").build());
        settingsMenu.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());
        settingsMenu.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").build());

        p.openInventory(settingsMenu);
    }

    public void showProtectionIIRenameMenu(Player p) {
        Inventory renamemenu = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&5&lRename Protection II"));
        Profile prof = pm.getProfile(p.getUniqueId());
        if (prof.getMchcfKit(1) != null) {
            Kit k = prof.getMchcfKit(1);
            renamemenu.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(2) != null) {
            Kit k = prof.getMchcfKit(2);
            renamemenu.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(3) != null) {
            Kit k = prof.getMchcfKit(3);
            renamemenu.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(4) != null) {
            Kit k = prof.getMchcfKit(4);
            renamemenu.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(5) != null) {
            Kit k = prof.getMchcfKit(5);
            renamemenu.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        p.openInventory(renamemenu);
    }

    public void showNoEnchantmentRenameMenu(Player p) {
        Inventory renamemenu = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&5&lRename No Enchantment"));
        Profile prof = pm.getProfile(p.getUniqueId());
        if (prof.getMchcfKit(1) != null) {
            Kit k = prof.getMchcfKit(1);
            renamemenu.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(2) != null) {
            Kit k = prof.getMchcfKit(2);
            renamemenu.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(3) != null) {
            Kit k = prof.getMchcfKit(3);
            renamemenu.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(4) != null) {
            Kit k = prof.getMchcfKit(4);
            renamemenu.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        if (prof.getMchcfKit(5) != null) {
            Kit k = prof.getMchcfKit(5);
            renamemenu.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(10).name("&5&lRename &e&l" + ChatColor.stripColor(k.getName())).build());
        } else {
            renamemenu.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE).durability(14).name("&c&l\u2716 No Kit Saved \u2716").addLore("&eCreate and save a kit first.").build());
        }
        p.openInventory(renamemenu);
    }

    public void showSettingsMenu(Player p) {
        Inventory settingsMenu = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "&b&lSettings"));

        Profile prof = pm.getProfile(p.getUniqueId());

        settingsMenu.setItem(0, new ItemBuilder(prof.isAcceptingDuels() ? Material.DIAMOND_SWORD : Material.WOOD_SWORD).name("&b&lAccept Duels&7&l: " + (prof.isAcceptingDuels() ? "&a&lYES" : "&c&lNO")).build());
        settingsMenu.setItem(2,  new ItemBuilder(prof.hasVisibilityToggled() ? Material.CARROT_ITEM : Material.GOLDEN_CARROT).name( "&b&lPlayer Visibility&7&l: " + (prof.hasVisibilityToggled() ? "&a&lON" : "&c&lOFF")).build());
        settingsMenu.setItem(4,  new ItemBuilder(prof.hasMessagesDisabled() ? Material.PAPER : Material.EMPTY_MAP).name("&b&lReceive Messages&7&l: " + (prof.hasMessagesDisabled() ? "&a&lYES" : "&c&lNO")).build());
        settingsMenu.setItem(6,  new ItemBuilder(prof.hasCustomSoundsToggled() ? Material.GREEN_RECORD : Material.RECORD_4).name("&b&lCustom Sounds&7&l: " + (prof.hasCustomSoundsToggled() ? "&a&lON" : "&c&lOFF")).build());

        p.openInventory(settingsMenu);
    }
}
