package code.young.repulse.listeners;

import code.BreakMC.commons.util.PlayerUtility;
import code.young.repulse.Repulse;
import code.young.repulse.enums.ArenaType;
import code.young.repulse.enums.KitType;
import code.young.repulse.enums.QueueType;
import code.young.repulse.managers.*;
import code.young.repulse.objects.CurrentKit;
import code.young.repulse.objects.Kit;
import code.young.repulse.objects.Profile;
import code.young.repulse.objects.Team;
import code.breakmc.repulse.utils.ItemUtill;
import code.young.repulse.utils.MessageManager;
import code.young.inputgui.api.InputGui;
import code.young.inputgui.api.InputGuiAPI;
import code.young.inputgui.api.InputPlayer;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

/*
 * Created by Calvin on 2/17/2015.
 *
 */

public class InventoryEvents implements Listener {

    private Repulse main = Repulse.getInstance();
    private InventoryManager im = main.getInventoryManager();
    private ProfileManager pm = main.getProfileManager();
    private KitManager km = main.getKitManager();
    private FFAManager ffam = main.getFFAManager();
    private SpawnManager sm = main.getSpawnManager();
    private QueueManager qm = main.getQueueManager();
    private InputGuiAPI iapi = main.getInputGUIAPI();
    private TeamManager tm = main.getTeamManager();

    public InventoryEvents() {
        new BukkitRunnable() {
            public void run() {
                for (Player p : PlayerUtility.getOnlinePlayers()) {
                    for (ItemStack i : p.getInventory().getContents()) {
                        if (i != null) {
                            if (i.getType() == Material.INK_SACK) {
                                if (i.getDurability() == (short) 1) {
                                    if (i.getItemMeta().getDisplayName().equals("&cSearching.")) {
                                        ItemMeta meta = i.getItemMeta();
                                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cSearching.."));
                                        i.setItemMeta(meta);
                                    } else if (i.getItemMeta().getDisplayName().equals("&cSearching..")) {
                                        ItemMeta meta = i.getItemMeta();
                                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cSearching..."));
                                        i.setItemMeta(meta);
                                    } else if (i.getItemMeta().getDisplayName().equals("&cSearching...")) {
                                        ItemMeta meta = i.getItemMeta();
                                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cSearching"));
                                        i.setItemMeta(meta);
                                    } else if (i.getItemMeta().getDisplayName().equals("&cSearching")) {
                                        ItemMeta meta = i.getItemMeta();
                                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cSearching."));
                                        i.setItemMeta(meta);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(main, 0, 20);
    }


    @EventHandler
    public void kitCreation(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().contains("No Enchantment") || e.getInventory().getName().contains("Protection II")) {
            if (item != null) {
                if (item.getType() == Material.POTION && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    e.setCancelled(true);
                    for (int i = 0; i < 50; i++) {
                        p.getInventory().addItem(ItemUtill.createItem(Material.POTION, (short) 16421, 1, "&cInstant Health II"));
                    }
                } else if (item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS) {
                    if (item.getType() == Material.DIAMOND_HELMET) {
                        if (p.getInventory().getHelmet() == null) {
                            e.setCancelled(true);
                            p.getInventory().setHelmet(item);
                            p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                            p.updateInventory();
                        }
                    }
                    if (item.getType() == Material.DIAMOND_CHESTPLATE) {
                        if (p.getInventory().getChestplate() == null) {
                            e.setCancelled(true);
                            p.getInventory().setChestplate(item);
                            p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                            p.updateInventory();
                        }
                    }
                    if (item.getType() == Material.DIAMOND_LEGGINGS) {
                        if (p.getInventory().getLeggings() == null) {
                            e.setCancelled(true);
                            p.getInventory().setLeggings(item);
                            p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                            p.updateInventory();
                        }
                    }
                    if (item.getType() == Material.DIAMOND_BOOTS) {
                        if (p.getInventory().getBoots() == null) {
                            e.setCancelled(true);
                            p.getInventory().setBoots(item);
                            p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
                            p.updateInventory();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void kitSavingLoading(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().contains("Save Protection II")) {
            e.setCancelled(true);
            if (item != null) {
                if (e.getSlot() == 0) {
                    km.saveMchcfKit(p, 1);
                } else if (e.getSlot() == 2) {
                    km.saveMchcfKit(p, 2);
                } else if (e.getSlot() == 4) {
                    km.saveMchcfKit(p, 3);
                } else if (e.getSlot() == 6) {
                    km.saveMchcfKit(p, 4);
                } else if (e.getSlot() == 8) {
                    km.saveMchcfKit(p, 5);
                }
            }
        }
        if (e.getInventory().getName().contains("Save No Enchantment")) {
            e.setCancelled(true);
            if (item != null) {
                if (e.getSlot() == 0) {
                    km.saveNoEnchantmentKit(p, 1);
                } else if (e.getSlot() == 2) {
                    km.saveNoEnchantmentKit(p, 2);
                } else if (e.getSlot() == 4) {
                    km.saveNoEnchantmentKit(p, 3);
                } else if (e.getSlot() == 6) {
                    km.saveNoEnchantmentKit(p, 4);
                } else if (e.getSlot() == 8) {
                    km.saveNoEnchantmentKit(p, 5);
                }
            }
        }
        if (e.getInventory().getName().contains("Load Protection II")) {
            e.setCancelled(true);
            if (item != null) {
                if (item.getType() == Material.STAINED_GLASS_PANE) {
                    if (item.getDurability() == 3) {
                        Profile prof = pm.getProfile(p.getUniqueId());
                        if (ffam.get.contains(p.getUniqueId())) {
                            ffam.getProtIIPickedKitList().remove(p.getUniqueId());
                        }
                        if (e.getSlot() == 0) {
                            km.loadMchcfKit(p, 1);
                            Kit k = prof.getMchcfKit(1);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 1, KitType.Mchcf, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        } else if (e.getSlot() == 2) {
                            km.loadMchcfKit(p, 2);
                            Kit k = prof.getMchcfKit(2);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 2, KitType.Mchcf, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        } else if (e.getSlot() == 4) {
                            km.loadMchcfKit(p, 3);
                            Kit k = prof.getMchcfKit(3);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 3, KitType.Mchcf, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        } else if (e.getSlot() == 6) {
                            km.loadMchcfKit(p, 4);
                            Kit k = prof.getMchcfKit(4);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 4, KitType.Mchcf, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        } else if (e.getSlot() == 8) {
                            km.loadMchcfKit(p, 5);
                            Kit k = prof.getMchcfKit(5);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 5, KitType.Mchcf, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        }
                        p.closeInventory();
                    }
                }
            }
        }
        if (e.getInventory().getName().contains("Load No Enchantment")) {
            e.setCancelled(true);
            if (item != null) {
                if (item.getType() == Material.STAINED_GLASS_PANE) {
                    if (item.getDurability() == 3) {
                        Profile prof = pm.getProfile(p.getUniqueId());
                        if (ffam.getNoEnchPickedKitList().contains(p.getUniqueId())) {
                            ffam.getNoEnchPickedKitList().remove(p.getUniqueId());
                        }
                        if (e.getSlot() == 0) {
                            km.loadNoEnchantmentKit(p, 1);
                            Kit k = prof.getNoEnchantmentKit(1);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 1, KitType.NOENCHANTMENT, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        } else if (e.getSlot() == 2) {
                            km.loadNoEnchantmentKit(p, 2);
                            Kit k = prof.getNoEnchantmentKit(2);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 2, KitType.NOENCHANTMENT, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        } else if (e.getSlot() == 4) {
                            km.loadNoEnchantmentKit(p, 3);
                            Kit k = prof.getNoEnchantmentKit(3);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 3, KitType.NOENCHANTMENT, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        } else if (e.getSlot() == 6) {
                            km.loadNoEnchantmentKit(p, 4);
                            Kit k = prof.getNoEnchantmentKit(4);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 4, KitType.NOENCHANTMENT, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        } else if (e.getSlot() == 8) {
                            km.loadNoEnchantmentKit(p, 5);
                            Kit k = prof.getNoEnchantmentKit(5);
                            CurrentKit ck = new CurrentKit(prof.getUniqueId(), k.getName(), 5, KitType.NOENCHANTMENT, k.getKills(), k.getDeaths(), k.getUses());
                            prof.setCurrentKit(ck);
                            ck.setUses(ck.getUses() + 1);
                            prof.saveKit(ck);
                        }
                        p.closeInventory();
                    }
                }
            }
        }
    }

    @EventHandler
    public void kitConfigure(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().contains("Config Protection II")) {
            e.setCancelled(true);
            if (item != null) {
                if (item.getType() == Material.STAINED_GLASS_PANE) {
                    Profile prof = pm.getProfile(p.getUniqueId());
                    //Kit 1
                    if (e.getSlot() == 0) {
                        km.showPreview(p, prof.getMchcfKit(1));
                    }
                    if (e.getSlot() == 9) {
                        km.loadMchcfKit(p, 1);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 18) {
                        km.saveMchcfKit(p, 1);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 27) {
                        prof.setMchcfKit(null, 1);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                    //Kit 2
                    if (e.getSlot() == 2) {
                        km.showPreview(p, prof.getMchcfKit(2));
                    }
                    if (e.getSlot() == 11) {
                        km.loadMchcfKit(p, 2);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 20) {
                        km.saveMchcfKit(p, 2);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 29) {
                        prof.setMchcfKit(null, 2);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                    //Kit 3
                    if (e.getSlot() == 4) {
                        km.showPreview(p, prof.getMchcfKit(3));
                    }
                    if (e.getSlot() == 13) {
                        km.loadMchcfKit(p, 3);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 22) {
                        km.saveMchcfKit(p, 3);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 31) {
                        prof.setMchcfKit(null, 3);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                    //Kit 4
                    if (e.getSlot() == 6) {
                        km.showPreview(p, prof.getMchcfKit(4));
                    }
                    if (e.getSlot() == 15) {
                        km.loadMchcfKit(p, 4);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 24) {
                        km.saveMchcfKit(p, 4);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 33) {
                        prof.setMchcfKit(null, 4);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                    //Kit 5
                    if (e.getSlot() == 8) {
                        km.showPreview(p, prof.getMchcfKit(5));
                    }
                    if (e.getSlot() == 17) {
                        km.loadMchcfKit(p, 5);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 28) {
                        km.saveMchcfKit(p, 5);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 35) {
                        prof.setMchcfKit(null, 5);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                }
            }
        } else if (e.getInventory().getName().contains("Config No Enchantment")) {
            e.setCancelled(true);
            if (item != null) {
                if (item.getType() == Material.STAINED_GLASS_PANE) {
                    Profile prof = pm.getProfile(p.getUniqueId());
                    //Kit 1
                    if (e.getSlot() == 0) {
                        km.showPreview(p, prof.getNoEnchantmentKit(1));
                    }
                    if (e.getSlot() == 9) {
                        km.loadNoEnchantmentKit(p, 1);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 18) {
                        km.saveNoEnchantmentKit(p, 1);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 27) {
                        prof.setNoEnchantmentKit(null, 1);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                    //Kit 2
                    if (e.getSlot() == 2) {
                        km.showPreview(p, prof.getNoEnchantmentKit(2));
                    }
                    if (e.getSlot() == 11) {
                        km.loadNoEnchantmentKit(p, 2);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 20) {
                        km.saveNoEnchantmentKit(p, 2);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 29) {
                        prof.setNoEnchantmentKit(null, 2);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                    //Kit 3
                    if (e.getSlot() == 4) {
                        km.showPreview(p, prof.getNoEnchantmentKit(3));
                    }
                    if (e.getSlot() == 13) {
                        km.loadNoEnchantmentKit(p, 3);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 22) {
                        km.saveNoEnchantmentKit(p, 3);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 31) {
                        prof.setNoEnchantmentKit(null, 3);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                    //Kit 4
                    if (e.getSlot() == 6) {
                        km.showPreview(p, prof.getNoEnchantmentKit(4));
                    }
                    if (e.getSlot() == 15) {
                        km.loadNoEnchantmentKit(p, 4);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 24) {
                        km.saveNoEnchantmentKit(p, 4);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 33) {
                        prof.setNoEnchantmentKit(null, 4);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                    //Kit 5
                    if (e.getSlot() == 8) {
                        km.showPreview(p, prof.getNoEnchantmentKit(5));
                    }
                    if (e.getSlot() == 17) {
                        km.loadNoEnchantmentKit(p, 5);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 28) {
                        km.saveNoEnchantmentKit(p, 5);
                        p.closeInventory();
                    }
                    if (e.getSlot() == 35) {
                        prof.setNoEnchantmentKit(null, 5);
                        pm.reloadProfile(prof);
                        p.closeInventory();
                    }
                }
            }
        }
    }

    @EventHandler
    public void settings(InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        Profile prof = pm.getProfile(p.getUniqueId());
        if (e.getInventory().getName().contains("Settings")) {
            e.setCancelled(true);
            if (item != null) {
                if (item.getType() == Material.DIAMOND_SWORD) {
                    prof.setAcceptingDuels(false);
                    e.setCurrentItem(ItemUtill.createItem(Material.WOOD_SWORD, 1, "&b&lAccept Duels&7&l: &c&lNO"));
                } else if (item.getType() == Material.WOOD_SWORD) {
                    prof.setAcceptingDuels(true);
                    e.setCurrentItem(ItemUtill.createItem(Material.DIAMOND_SWORD, 1, "&b&lAccept Duels&7&l: &a&lYES"));
                } else if (item.getType() == Material.CARROT_ITEM) {
                    prof.setVisibilityToggled(false);
                    e.setCurrentItem(ItemUtill.createItem(Material.GOLDEN_CARROT, 1, "&b&lPlayer Visibility&7&l: &c&lOFF"));
                } else if (item.getType() == Material.GOLDEN_CARROT) {
                    prof.setVisibilityToggled(true);
                    e.setCurrentItem(ItemUtill.createItem(Material.CARROT_ITEM, 1, "&b&lPlayer Visibility&7&l: &a&lON"));
                } else if (item.getType() == Material.PAPER) {
                    prof.setAcceptingMessages(false);
                    e.setCurrentItem(ItemUtill.createItem(Material.EMPTY_MAP, 1, "&b&lReceive Message&7&l: &c&lNO"));
                } else if (item.getType() == Material.EMPTY_MAP) {
                    prof.setAcceptingMessages(true);
                    e.setCurrentItem(ItemUtill.createItem(Material.PAPER, 1, "&b&lReceive Message&7&l: &a&lYES"));
                } else if (item.getType() == Material.GREEN_RECORD) {
                    prof.setCustomSoundsToggled(false);
                    e.setCurrentItem(ItemUtill.createItem(Material.RECORD_4, 1, "&b&lCustom Sounds&7&l: &c&lOFF"));
                } else if (item.getType() == Material.RECORD_4) {
                    prof.setCustomSoundsToggled(true);
                    e.setCurrentItem(ItemUtill.createItem(Material.GREEN_RECORD, 1, "&b&lCustom Sounds&7&l: &A&lON"));
                }
            }
        }
    }

    @EventHandler
    public void queue(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().contains("Ranked")) {
            e.setCancelled(true);
            if (item != null) {
                if (item.getType() == Material.DIAMOND_CHESTPLATE) {
                    if (item.getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                        qm.setMchcfQueue(p, QueueType.RANKED, true);
                    } else {
                        qm.setNoEnchantmentQueue(p, QueueType.RANKED, true);
                    }
                }
            }
            p.closeInventory();
        }
        if (e.getInventory().getName().contains("Unranked")) {
            e.setCancelled(true);
            if (item != null) {
                if (item.getType() == Material.DIAMOND_CHESTPLATE) {
                    if (item.getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                        qm.setMchcfQueue(p, QueueType.UNRANKED, true);
                    } else {
                        qm.setNoEnchantmentQueue(p, QueueType.UNRANKED, true);
                    }
                }
            }
            p.closeInventory();
        }
    }

    @EventHandler
    public void preview(InventoryClickEvent e) {
        if (e.getInventory().getName().contains("Preview")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void rename(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        Profile prof = pm.getProfile(p.getUniqueId());
        if (e.getInventory().getName().contains("Rename Protection II")) {
            e.setCancelled(true);
            if (item.getDurability() == 10) {
                if (e.getSlot() == 0) {
                    final Kit k = prof.getMchcfKit(1);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
                if (e.getSlot() == 2) {
                    final Kit k = prof.getMchcfKit(2);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
                if (e.getSlot() == 4) {
                    final Kit k = prof.getMchcfKit(3);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
                if (e.getSlot() == 6) {
                    final Kit k = prof.getMchcfKit(4);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
                if (e.getSlot() == 8) {
                    final Kit k = prof.getMchcfKit(5);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
            }
        }
        if (e.getInventory().getName().contains("Rename No Enchantment")) {
            e.setCancelled(true);
            if (item.getDurability() == 10) {
                if (e.getSlot() == 0) {
                    final Kit k = prof.getNoEnchantmentKit(1);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
                if (e.getSlot() == 2) {
                    final Kit k = prof.getNoEnchantmentKit(2);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
                if (e.getSlot() == 4) {
                    final Kit k = prof.getNoEnchantmentKit(3);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
                if (e.getSlot() == 6) {
                    final Kit k = prof.getNoEnchantmentKit(4);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
                if (e.getSlot() == 8) {
                    final Kit k = prof.getNoEnchantmentKit(5);
                    InputPlayer iplayer = iapi.getPlayer(p);

                    iplayer.openGui(new InputGui() {
                        public String getDefaultText() {
                            return ChatColor.YELLOW + "" + ChatColor.BOLD + k.getName();
                        }

                        public void onConfirm(InputPlayer player, String input) {
                            k.setName(ChatColor.stripColor(input));
                        }

                        public void onCancel(final InputPlayer player) {
                            new BukkitRunnable() {
                                public void run() {
                                    player.getPlayer().closeInventory();
                                }
                            }.runTaskAsynchronously(main);
                        }
                    });
                }
            }
        }
    }

    @EventHandler
    public void queue(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().name().contains("RIGHT")) {
            if (e.hasItem()) {
                ItemStack item = e.getItem();
                if (item != null) {
                    if (item.getType() == Material.DOUBLE_PLANT) {
                        im.showSettingsMenu(p);
                    }
                    if (item.getType() == Material.SKULL_ITEM) {
                        e.setCancelled(true);
                        MessageManager.sendMessage(p, "&cComing Extremely Soon. (90% Complete)");
//                        tm.createTeam(p.getUniqueId());
                    }
                    if (item.getType() == Material.INK_SACK) {
                        if (item.getDurability() == 8) {
                            if (qm.getProtIIRankedQueue().contains(p.getUniqueId()) || qm.getNoEnchantmentRankedQueue().contains(p.getUniqueId())) {
                                MessageManager.sendMessage(p, "&cYou must first leave the ranked queue to look for an unranked match.");
                                return;
                            }
                            im.showUnrankedMenu(p);
                        }
                        if (item.getDurability() == 10) {
                            if (qm.getProtIIUnrankedQueue().contains(p.getUniqueId()) || qm.getNoEnchantmentUnrankedQueue().contains(p.getUniqueId())) {
                                MessageManager.sendMessage(p, "&cYou must first leave the unranked queue to look for an unranked match.");
                                return;
                            }
                            im.showRankedMenu(p);
                        }
                        if (item.getDurability() == 1) {
                            if (item.getItemMeta().getDisplayName().contains("Searching")) {
                                if (qm.getProtIIUnrankedQueue().contains(p.getUniqueId())) {
                                    qm.setMchcfQueue(p, QueueType.UNRANKED, false);
                                }
                                if (qm.getProtIIRankedQueue().contains(p.getUniqueId())) {
                                    qm.setMchcfQueue(p, QueueType.RANKED, false);
                                }
                                if (qm.getNoEnchantmentUnrankedQueue().contains(p.getUniqueId())) {
                                    qm.setNoEnchantmentQueue(p, QueueType.UNRANKED, false);
                                }
                                if (qm.getNoEnchantmentRankedQueue().contains(p.getUniqueId())) {
                                    qm.setNoEnchantmentQueue(p, QueueType.RANKED, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void team(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction().name().contains("RIGHT")) {
            if (e.hasItem()) {
                ItemStack item = e.getItem();
                if (!(sm.isInSpawn(p))) {
                    return;
                }

                if (item.getType() == Material.BOW) {
                    e.setCancelled(true);
                    return;
                }

                if (tm.hasTeam(p.getUniqueId())) {
                    final Team t = tm.getTeam(p.getUniqueId());

                    if (t.isOwner(p.getUniqueId())) {
                        if (item.getType() == Material.INK_SACK) {
                            Inventory kick = Bukkit.createInventory(p, 18, ChatColor.translateAlternateColorCodes('&', "&c&lKick a Player"));
                            if (t.getMembers().size() == 0) {
                                MessageManager.sendMessage(p, "&c&lThere are no players in your team!");
                                return;
                            }
                            for (UUID team : t.getMembers()) {
                                Profile tp = pm.getProfile(team);
                                kick.addItem(ItemUtill.createItem(Material.SKULL_ITEM, (short) 3, 1, "&c" + tp.getName()));
                            }
                            p.openInventory(kick);
                        }
                        if (item.getType() == Material.REDSTONE) {
                            e.setCancelled(true);
                            tm.disbandTeam(p.getUniqueId());
                        }
                        if (item.getType() == Material.DIAMOND_SWORD) {
                            Inventory duel = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&b&lDuel a Team"));

                            ArrayList<String> mlist = new ArrayList<>();
                            mlist.add(ChatColor.translateAlternateColorCodes('&', "&6&l" + p.getName()));
                            for (UUID members : tm.getTeam(p.getUniqueId()).getMembers()) {
                                Profile mprof = pm.getProfile(members);
                                mlist.add(ChatColor.translateAlternateColorCodes('&', "&6" + mprof.getName()));
                            }

                            ItemStack oitem = ItemUtill.createItem(Material.IRON_CHESTPLATE, (short) 0, 1, "&e&lYour Team &7&l(&e&l" + (t.getMembers().size() + 1) + "&7&l)", mlist);

                            duel.addItem(oitem);

                            for (Team teams : tm.getTeams()) {
                                if (teams != tm.getTeam(p.getUniqueId())) {
                                    Profile owner = pm.getProfile(teams.getOwner());
                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.translateAlternateColorCodes('&', "&a&l" + owner.getName()));

                                    for (UUID tuuid : teams.getMembers()) {
                                        Profile tp = pm.getProfile(tuuid);
                                        lore.add(ChatColor.translateAlternateColorCodes('&', "&a" + tp.getName()));
                                    }

                                    ItemStack titem = ItemUtill.createItem(Material.DIAMOND_CHESTPLATE, (short) 0, 1, "&b&l" + owner.getName() + "'s Team &7&l(&a&l" + (t.getMembers().size() + 1) + "&7&l)", lore);
                                    duel.addItem(titem);
                                }
                            }
                            p.openInventory(duel);
                        }
                        if (item.getType() == Material.EMPTY_MAP) {
                            e.setCancelled(true);
                            Inventory view = Bukkit.createInventory(p, 18, ChatColor.translateAlternateColorCodes('&', "&a&lMembers"));

                            Profile op = pm.getProfile(t.getOwner());
                            view.addItem(ItemUtill.createItem(Material.SKULL_ITEM, (short) 3, 1, "&a&l" + op.getName()));

                            for (UUID team : t.getMembers()) {
                                Profile tp = pm.getProfile(team);
                                view.addItem(ItemUtill.createItem(Material.SKULL_ITEM, (short) 3, 1, "&a" + tp.getName()));
                            }

                            p.openInventory(view);
                        }
                        if (item.getType() == Material.PAPER) {

                            InputPlayer iplayer = iapi.getPlayer(p);

                            iplayer.openGui(new InputGui() {
                                public String getDefaultText() {

                                    return ChatColor.YELLOW + "" + ChatColor.BOLD + "Invite";
                                }

                                public void onConfirm(InputPlayer player, String input) {
                                    Player invitedPlayer = Bukkit.getPlayer(input);

                                    if (invitedPlayer == null) {
                                        MessageManager.sendMessage(p, "&c" + input + " can't be found!");
                                        return;
                                    }

                                    if (tm.hasTeam(invitedPlayer.getUniqueId())) {
                                        MessageManager.sendMessage(player.getPlayer(), "&c" + input + " is already in a team.");
                                        return;
                                    }

                                    t.invitePlayer(invitedPlayer.getUniqueId());
                                }

                                public void onCancel(final InputPlayer player) {
                                    new BukkitRunnable() {
                                        public void run() {
                                            player.getPlayer().closeInventory();
                                        }
                                    }.runTaskAsynchronously(main);
                                }
                            });
                        }
                    } else {
                        if (item.getType() == Material.INK_SACK) {
                            t.leave(p.getUniqueId());
                            sm.setSpawnInventory(p);
                        }
                        if (item.getType() == Material.DIAMOND_SWORD) {
                            Inventory duel = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&b&lDuel a Team"));
                            for (Team teams : tm.getTeams()) {
                                if (teams != tm.getTeam(p.getUniqueId())) {
                                    Profile owner = pm.getProfile(teams.getOwner());
                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.translateAlternateColorCodes('&', "&a&l" + owner.getName()));
                                    for (UUID tuuid : teams.getMembers()) {
                                        Profile tp = pm.getProfile(tuuid);
                                        lore.add(ChatColor.translateAlternateColorCodes('&', "&a" + tp.getName()));
                                    }
                                    ItemStack titem = ItemUtill.createItem(Material.DIAMOND_CHESTPLATE, (short) 0, 1, "&b&l" + owner.getName() + "'s Team &7&l(&a&l" + (t.getMembers().size() + 1) + "&7&l)", lore);
                                    duel.addItem(titem);
                                }
                            }
                            p.openInventory(duel);
                        }

                        if (item.getType() == Material.PAPER) {
                            Inventory view = Bukkit.createInventory(p, 9 * 2, ChatColor.translateAlternateColorCodes('&', "&a&lMembers"));

                            Profile op = pm.getProfile(t.getOwner());
                            view.addItem(ItemUtill.createItem(Material.SKULL_ITEM, (short) 3, 1, "&a&l" + op.getName()));

                            for (UUID team : t.getMembers()) {
                                Profile tp = pm.getProfile(team);
                                view.addItem(ItemUtill.createItem(Material.SKULL_ITEM, (short) 3, 1, "&a" + tp.getName()));
                            }

                            p.openInventory(view);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void teamClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getTitle().contains("Kick")) {//Then explain how this works you fucking dipshit oh fuck
            if (item == null) {
                return;
            }
            if (item.getType() == Material.SKULL_ITEM) {
                if (tm.hasTeam(p.getUniqueId())) {
                    p.closeInventory();
                    Team t = tm.getTeam(p.getUniqueId());

                    if (!t.isOwner(p.getUniqueId())) {
                        MessageManager.sendMessage(p, "&cOnly the owner can kick players.");
                        return;
                    }

                    Profile tprof = null;

                    for (UUID id : t.getMembers()) {
                        if (pm.getProfile(id).getName().equals(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
                            tprof = pm.getProfile(id);
                        }
                    }

                    if (tprof == null) {
                        MessageManager.sendMessage(p, "&cThat is not a valid player.");
                        return;
                    }

                    t.kickPlayer(tprof.getUniqueId());
                } else {
                    p.closeInventory();
                    MessageManager.sendMessage(p, "&cYou are no longer in a team.");
                }
            }
        }
        if (e.getInventory().getTitle().contains("Duel")) {
            if (item.getType() == Material.DIAMOND_CHESTPLATE) {
                p.closeInventory();
                if (tm.hasTeam(p.getUniqueId())) {
                    Team t = tm.getTeam(p.getUniqueId());

                    if (t.isOwner(p.getUniqueId())) {
                        Profile tprof = null;

                        String[] parts = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split("\'");

                        String name = parts[0];

                        for (Profile prof : pm.getLoadedProfiles()) {
                            if (prof.getName().equalsIgnoreCase(name)) {
                                tprof = prof;
                            }
                        }

                        if (tprof == null) {
                            MessageManager.sendMessage(p, "&cThat is no longer a valid player.");
                            return;
                        }

                        if (!tm.hasTeam(tprof.getUniqueId())) {
                            MessageManager.sendMessage(p, "&cThat player is no longer in a team.");
                            return;
                        }

                        Team tt = tm.getTeam(tprof.getUniqueId());

                        Inventory duel = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', "Select a Type"));

                        ItemStack ri = ItemUtill.createItem(Material.DIAMOND_CHESTPLATE, 1, "&b&lUnranked Protection II");
                        ri.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        duel.setItem(2, ri);

                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.translateAlternateColorCodes('&', "&e&l" + tprof.getName()));
                        for (UUID tuuid : tt.getMembers()) {
                            Profile tp = pm.getProfile(tuuid);
                            lore.add(ChatColor.translateAlternateColorCodes('&', "&e" + tp.getName()));
                        }

                        duel.setItem(4, ItemUtill.createItem(Material.IRON_CHESTPLATE, 1, "&6" + tprof.getName() + "'s Team &7&l(&e&l" + (tt.getMembers().size() + 1) + "&7&l)", lore));
                        duel.setItem(6, ItemUtill.createItem(Material.DIAMOND_CHESTPLATE, 1, "&b&lUnranked No Enchantment"));
                        duel.setItem(0, ItemUtill.createItem(Material.STAINED_GLASS_PANE, (short) 7, 1, " "));
                        duel.setItem(1, ItemUtill.createItem(Material.STAINED_GLASS_PANE, (short) 7, 1, " "));
                        duel.setItem(3, ItemUtill.createItem(Material.STAINED_GLASS_PANE, (short) 7, 1, " "));
                        duel.setItem(5, ItemUtill.createItem(Material.STAINED_GLASS_PANE, (short) 7, 1, " "));
                        duel.setItem(7, ItemUtill.createItem(Material.STAINED_GLASS_PANE, (short) 7, 1, " "));
                        duel.setItem(8, ItemUtill.createItem(Material.STAINED_GLASS_PANE, (short) 7, 1, " "));

                        p.openInventory(duel);
                    } else {
                        MessageManager.sendMessage(p, "&cYou must ask your team leader to request a duel.");
                    }
                } else {
                    MessageManager.sendMessage(p, "&cYou are no longer in a team.");
                }
            }
        }
        if (e.getInventory().getName().contains("Type")) {
            if (item.getType() == Material.DIAMOND_CHESTPLATE) {
                p.closeInventory();
                if (tm.hasTeam(p.getUniqueId())) {
                    Team t = tm.getTeam(p.getUniqueId());

                    if (!t.isOwner(p.getUniqueId())) {
                        MessageManager.sendMessage(p, "&cOnly the team leader can request a duel.");
                        return;
                    }

                    if (e.getInventory().getItem(4) != null && e.getInventory().getItem(4).getType() == Material.IRON_CHESTPLATE) {
                        ItemStack titem = e.getInventory().getItem(4);

                        Profile tprof = null;

                        String[] parts = ChatColor.stripColor(titem.getItemMeta().getDisplayName()).split("\'");

                        String name = parts[0];

                        for (Profile prof : pm.getLoadedProfiles()) {
                            if (prof.getName().equalsIgnoreCase(name)) {
                                tprof = prof;
                            }
                        }

                        if (tprof == null) {
                            MessageManager.sendMessage(p, "&cThat is no longer a valid player.");
                            return;
                        }

                        if (!tm.hasTeam(tprof.getUniqueId())) {
                            MessageManager.sendMessage(p, "&cThat player is no longer in a team.");
                            return;
                        }

                        Team tt = tm.getTeam(tprof.getUniqueId());

                        if (item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                            tt.getDuelRequests().put(t, ArenaType.NOENCHANTMENT_TEAMVSTEAM);

                            tprof.sendMessage("&a" + p.getName() + "'s team is requesting a duel!\nKit: &b&lProtection II");

                            FancyMessage fm = new FancyMessage("Click ")
                                    .color(ChatColor.GREEN)
                                    .then("Here")
                                    .color(ChatColor.GREEN)
                                    .style(ChatColor.BOLD, ChatColor.UNDERLINE)
                                    .command("/yes " + p.getName())
                                    .then(" to accept").color(ChatColor.GREEN).then(".").color(ChatColor.GRAY)
                                    .then(" Click ").color(ChatColor.RED)
                                    .then("Here")
                                    .color(ChatColor.RED)
                                    .style(ChatColor.BOLD, ChatColor.UNDERLINE)
                                    .command("/no " + p.getName())
                                    .then(" to deny").color(ChatColor.RED).then(".").color(ChatColor.GRAY);

                            tprof.sendMessage(fm);
                        } else {
                            tt.getDuelRequests().put(t, ArenaType.Mchcf_TEAMVSTEAM);

                            tprof.sendMessage("&a" + p.getName() + "'s team is requesting a duel!\nKit: &b&lNo Enchantment");

                            FancyMessage fm = new FancyMessage("Click ")
                                    .color(ChatColor.GREEN)
                                    .then("Here")
                                    .color(ChatColor.GREEN)
                                    .style(ChatColor.BOLD, ChatColor.UNDERLINE)
                                    .command("/yes " + p.getName())
                                    .then(" to accept").color(ChatColor.GREEN).then(".").color(ChatColor.GRAY)
                                    .then(" Click ").color(ChatColor.RED)
                                    .then("Here")
                                    .color(ChatColor.RED)
                                    .style(ChatColor.BOLD, ChatColor.UNDERLINE)
                                    .command("/no " + p.getName())
                                    .then(" to deny").color(ChatColor.RED).then(".").color(ChatColor.GRAY);

                            tprof.sendMessage(fm);
                        }
                        MessageManager.sendMessage(p, "&aDuel request sent!");
                    }
                } else {
                    MessageManager.sendMessage(p, "&cYou are no longer in a team.");
                }
            }
        }
    }


    @EventHandler
    public void onSpawnbarClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (sm.isInSpawn(p) && !sm.isInKitArea(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItemDrop().getItemStack().clone();
        if (sm.isInSpawn(p) && !sm.isInKitArea(p)) {
            if (item.getType() == Material.INK_SACK || item.getType() == Material.WRITTEN_BOOK || item.getType() == Material.DOUBLE_PLANT || item.getType() == Material.SKULL_ITEM || item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.PAPER || item.getType() == Material.MAP || item.getType() == Material.REDSTONE) {
                item.setAmount(p.getInventory().getItemInHand().getAmount() + 1);
                e.getItemDrop().remove();
                p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
            }
        }
    }
}
