package code.young.repulse.listeners;

/*
 * Created by Calvin on 2/17/2015.
 *
 */

import code.BreakMC.commons.util.PlayerUtility;
import code.young.repulse.Repulse;
import code.young.repulse.managers.FFAManager;
import code.young.repulse.managers.QueueManager;
import code.young.repulse.managers.SpawnManager;
import code.young.repulse.managers.TeamManager;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnEvents implements Listener {

    private Repulse main = Repulse.getInstance();
    private SpawnManager sm = main.getSpawnManager();
    private FFAManager ffam = main.getFFAManager();
    private QueueManager qm = main.getQueueManager();
    private TeamManager tm = main.getTeamManager();

    public SpawnEvents() {
        new BukkitRunnable() {
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    w.setThundering(false);
                    w.setStorm(false);
                }
            }
        }.runTaskLater(main, 5*20L);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (sm.isInSpawn(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player v = (Player) e.getEntity();
            if (sm.isInSpawn(v)) {
                e.setCancelled(true);
            }
        }
        if (e.getDamager() instanceof Player) {
            Player d = (Player) e.getDamager();
            if (sm.isInSpawn(d)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (sm.isInSpawn(p) && (sm.isInKitArea(p))) {
            e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().equals(Bukkit.getWorld("world"))) {
            if (sm.isInKitArea(p)) {
                if (!sm.getInKitSelection().contains(p.getUniqueId())) {
                    sm.getInKitSelection().add(p.getUniqueId());
                    MessageManager.sendMessage(p, "&aYou have entered the Kit Creation area.");
                    qm.removeFromQueues(p);
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0, -5);
                    sm.clearInventory(p);
                    for (Player online : PlayerUtility.getOnlinePlayers()) {
                        if (sm.isInKitArea(online)) {
                            p.hidePlayer(online);
                        }
                        if (online != p) {
                            online.hidePlayer(p);
                        }
                    }
                }
            }
            if (!sm.isInKitArea(p)) {
                if (sm.getInKitSelection().contains(p.getUniqueId())) {
                    sm.getInKitSelection().remove(p.getUniqueId());
                    MessageManager.sendMessage(p, "&cYou have left the Kit Creation area.\n&eDon't worry! Your kit will load when you enter a battle.");
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0, -5);
                    if (tm.hasTeam(p.getUniqueId())) {
                        tm.setTeamInventory(p);
                    } else {
                        sm.setSpawnInventory(p);
                    }
                    for (Player online : PlayerUtility.getOnlinePlayers()) {
                        if (!sm.isInKitArea(online)) {
                            p.showPlayer(online);
                            online.showPlayer(p);
                        }
                        if (sm.isInKitArea(online)) {
                            p.hidePlayer(online);
                        }
                    }
                }
            }
        }
        if (sm.isInSpawn(p)) {
            if (p.getLocation().getY() <= 0) {
                p.teleport(sm.getSpawn());
            }
        }
        //TODO: Maybe block if they are in team?
        if (ffam.isInProtectionIIFFAPortal(p.getLocation())) {
            ffam.teleportToProtectionIIFFA(p);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent e) {
        if (sm.isInKitArea(e.getPlayer())) {
            if (e.hasItem()) {
                if (e.getItem() != null) {
                    if (e.getItem().getType() == Material.ENDER_PEARL || e.getItem().getType() == Material.POTION) {
                        e.setCancelled(true);
                        e.getPlayer().updateInventory();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFoodLoss(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (sm.isInSpawn(p)) {
            e.setCancelled(true);
        }
    }
}
