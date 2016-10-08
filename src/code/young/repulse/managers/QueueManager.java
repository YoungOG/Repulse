package code.young.repulse.managers;

import code.young.repulse.Repulse;
import code.young.repulse.enums.ArenaType;
import code.young.repulse.enums.QueueType;
import code.young.repulse.utils.ItemBuilder;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class QueueManager {

    private Repulse main = Repulse.getInstance();
    private ArenaManager am = main.getArenaManager();
    private ProfileManager pm = main.getProfileManager();

    private ArrayList<UUID> protectionIIunrankedQueue = new ArrayList<>();
    private ArrayList<UUID> protectionIIrankedQueue = new ArrayList<>();
    private ArrayList<UUID> noEnchantmentRankedQueue = new ArrayList<>();
    private ArrayList<UUID> noEnchantmentUnrankedQueue = new ArrayList<>();
    private ArrayList<UUID> twovstwoQueueQueue = new ArrayList<>();
    private ArrayList<UUID> teamvsteamQueueQueue = new ArrayList<>();


    public void setProtectionIIQueue(Player p, QueueType type, Boolean isQueued) {
        if (isQueued) {
            if (type == QueueType.UNRANKED) {
                getProtIIUnrankedQueue().add(p.getUniqueId());

                MessageManager.sendMessage(p, "&6Joined unranked &eMCHCF &6queue.\n&6Looking for match...");
                p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);

                p.setItemInHand(new ItemBuilder(Material.INK_SACK).durability(1).name("&cSearching").addLore("&6Right-Click to stop searching for an unranked &eMCHCF &61vs1.").build());

                for (UUID id : getProtIIUnrankedQueue()) {
                    if (p.getUniqueId() != id) {
                        Player match = Bukkit.getPlayer(id);
                        MessageManager.sendMessage(p, "&6You have been matched with &e" + match.getName());
                        MessageManager.sendMessage(match, "&6You have been matched with &e" + p.getName());
                        getProtIIUnrankedQueue().remove(p.getUniqueId());
                        getProtIIUnrankedQueue().remove(match.getUniqueId());
                        removeFromQueues(p);
                        removeFromQueues(match);
                        am.startSingleDuel(p, match, ArenaType.UNRANKED_PROTECTIONII);
                    }
                }
            }

            if (type == QueueType.RANKED) {
                getProtIIRankedQueue().add(p.getUniqueId());

                MessageManager.sendMessage(p, "&6Joined ranked &eMCHCF &6queue.\n&6Looking for match.");
                p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);

                p.setItemInHand(new ItemBuilder(Material.INK_SACK).durability(1).name("&cSearching").addLore("&6Right-Click to stop searching for a ranked &eMCHCF &61vs1.").build());

                for (UUID id : getProtIIRankedQueue()) {
                    if (p.getUniqueId() != id) {
                        Player match = Bukkit.getPlayer(id);
                        MessageManager.sendMessage(p, "&6You have been matched with &e" + match.getName() + " &6? Elo: &e" + pm.getProfile(match.getUniqueId()).getProtIIElo());
                        MessageManager.sendMessage(match, "&6You have been matched with &e" + p.getName() + " &6? Elo: &e" + pm.getProfile(p.getUniqueId()).getProtIIElo());
                        getProtIIRankedQueue().remove(p.getUniqueId());
                        getProtIIRankedQueue().remove(match.getUniqueId());
                        removeFromQueues(p);
                        removeFromQueues(match);
                        am.startSingleDuel(p, match, ArenaType.RANKED_PROTECTIONII);
                    }
                }
            }
        } else {
            if (type == QueueType.UNRANKED) {
                getProtIIUnrankedQueue().remove(p.getUniqueId());
                MessageManager.sendMessage(p, "&cYou have been removed from the unranked MCHCF queue.");
                p.setItemInHand(new ItemBuilder(Material.INK_SACK).durability(8).name("&c&lUnranked Queue").addLore( "&6Right-Click to search for a unranked &eMCHCF &61vs1.").build());
                p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 1.0F, 1.0F);
                p.updateInventory();
            }

            if (type == QueueType.RANKED) {
                getProtIIRankedQueue().remove(p.getUniqueId());
                MessageManager.sendMessage(p, "&cYou have been removed from the ranked MCHCF queue.");
                p.setItemInHand(new ItemBuilder(Material.INK_SACK).durability(10).name("&a&lRanked Queue").addLore( "&6Right-Click to search for a ranked &eMCHCF &61vs1.").build());
                p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 1.0F, 1.0F);
                p.updateInventory();
            }
        }
    }

    public void setNoEnchantmentQueue(Player p, QueueType type, Boolean isQueued) {
        if (isQueued) {
            if (type == QueueType.UNRANKED) {
                getNoEnchantmentUnrankedQueue().add(p.getUniqueId());

                MessageManager.sendMessage(p, "&6Joined unranked &eNo Enchantment &6queue.\n&6Looking for match...");
                p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);

                p.setItemInHand(new ItemBuilder(Material.INK_SACK).durability(1).name("&cSearching").addLore("&6Right-Click to stop searching for an unranked &eNo Enchantment &61vs1.").build());

                for (UUID id : getNoEnchantmentUnrankedQueue()) {
                    if (p.getUniqueId() != id) {
                        Player match = Bukkit.getPlayer(id);
                        MessageManager.sendMessage(p, "&6You have been matched with &e" + match.getName());
                        MessageManager.sendMessage(match, "&6You have been matched with &e" + p.getName());
                        removeFromQueues(p);
                        removeFromQueues(match);
                        am.startSingleDuel(p, match, ArenaType.UNRANKED_NOENCHANTMENT);
                    }
                }
            }

            if (type == QueueType.RANKED) {
                getNoEnchantmentRankedQueue().add(p.getUniqueId());

                MessageManager.sendMessage(p, "&6Joined ranked &eNo Enchantment &6queue.\n&6Looking for match.");
                p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);

                p.setItemInHand(new ItemBuilder(Material.INK_SACK).durability(1).name("&cSearching").addLore("&6Right-Click to stop searching for a ranked &eNo Enchantment &61vs1.").build());

                for (UUID id : getNoEnchantmentRankedQueue()) {
                    if (p.getUniqueId() != id) {
                        Player match = Bukkit.getPlayer(id);
                        MessageManager.sendMessage(p, "&6You have been matched with &e" + match.getName() + " &6\u23A2 Elo: &e" + pm.getProfile(match.getUniqueId()).getNoEnchElo());
                        MessageManager.sendMessage(match, "&6You have been matched with &e" + p.getName() + " &6\u23A2 Elo: &e" + pm.getProfile(p.getUniqueId()).getNoEnchElo());
                        removeFromQueues(p);
                        removeFromQueues(match);
                        am.startSingleDuel(p, match, ArenaType.RANKED_NOENCHANTMENT);
                    }
                }
            }
        } else {
            if (type == QueueType.UNRANKED) {
                getProtIIUnrankedQueue().remove(p.getUniqueId());
                MessageManager.sendMessage(p, "&cYou have been removed from the unranked No Enchantment queue.");
                p.setItemInHand(new ItemBuilder(Material.INK_SACK).durability(8).name("&c&lUnranked Queue").addLore( "&6Right-Click to search for a unranked &eNo Enchantment &61vs1.").build());
                p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 1.0F, 1.0F);
                p.updateInventory();
            }

            if (type == QueueType.RANKED) {
                getProtIIRankedQueue().remove(p.getUniqueId());
                MessageManager.sendMessage(p, "&cYou have been removed from the ranked No Enchantment queue.");
                p.setItemInHand(new ItemBuilder(Material.INK_SACK).durability(10).name("&a&lRanked Queue").addLore( "&6Right-Click to search for a ranked &eNo Enchantment &61vs1.").build());
                p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 1.0F, 1.0F);
                p.updateInventory();
            }
        }
    }

    public void removeFromQueues(Player p) {
        if (getProtIIRankedQueue().contains(p.getUniqueId())) {
            MessageManager.sendMessage(p, "&cYou have been removed from the ranked MCHCF queue.");
            getProtIIRankedQueue().remove(p.getUniqueId());
        }
        if (getProtIIUnrankedQueue().contains(p.getUniqueId())) {
            MessageManager.sendMessage(p, "&cYou have been removed from the unranked MCHCF queue.");
            getProtIIUnrankedQueue().remove(p.getUniqueId());
        }
        if (getNoEnchantmentRankedQueue().contains(p.getUniqueId())) {
            MessageManager.sendMessage(p, "&cYou have been removed from the ranked No Enchantment queue.");
            getNoEnchantmentRankedQueue().remove(p.getUniqueId());
        }
        if (getNoEnchantmentUnrankedQueue().contains(p.getUniqueId())) {
            MessageManager.sendMessage(p, "&cYou have been removed from the unranked No Enchantment queue.");
            getNoEnchantmentUnrankedQueue().remove(p.getUniqueId());
        }
    }

    public List<UUID> getProtIIUnrankedQueue() {
        return protectionIIunrankedQueue;
    }

    public List<UUID> getProtIIRankedQueue() {
        return protectionIIrankedQueue;
    }

    public List<UUID> getNoEnchantmentUnrankedQueue() {
        return noEnchantmentUnrankedQueue;
    }

    public List<UUID> getNoEnchantmentRankedQueue() {
        return noEnchantmentRankedQueue;
    }
}