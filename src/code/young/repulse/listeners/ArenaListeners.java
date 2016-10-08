package code.young.repulse.listeners;

import code.young.repulse.Repulse;
import code.young.repulse.managers.ArenaManager;
import code.young.repulse.managers.QueueManager;
import code.young.repulse.managers.SpawnManager;
import code.young.repulse.objects.Arena;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class ArenaListeners implements Listener {

    private Repulse main = Repulse.getInstance();
    private ArenaManager am = main.getArenaManager();
    private QueueManager qm = main.getQueueManager();
    private SpawnManager sm = main.getSpawnManager();

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();
        if (am.getFreezed().contains(p.getUniqueId())) {
            p.setSprinting(false);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        if (am.getFreezed().contains(p.getUniqueId())) {
            if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
                e.setTo(e.getFrom());
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (am.isInDuel(p)) {
            if (e.getItemDrop().getItemStack() != null) {
                if (e.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onRain(WeatherChangeEvent e) {
        e.setCancelled(true);
        e.getWorld().setWeatherDuration(0);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent e) {
        final Player p = e.getEntity();
        Arena a = am.getPlayerArena(p);

        if (am.isInDuel(p)) {
            p.setWalkSpeed(0.2F);

            e.getDrops().clear();

            a.setOver(true);
            a.removePlayer(p);

            am.endSingleDuel(a, a.getPlayers().get(0), p.getUniqueId());

            new BukkitRunnable() {
                public void run() {
                    p.spigot().respawn();
                }
            }.runTaskLater(main, 1);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Arena a = am.getPlayerArena(p);

        if (am.isInDuel(p)) {
            p.teleport(sm.getSpawn());
            p.setWalkSpeed(0.2F);

            a.setOver(true);
            a.removePlayer(p);

            am.endSingleDuel(a, a.getPlayers().get(0), p.getUniqueId());
        }

        qm.removeFromQueues(p);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        if (inv.getName().contains("Results")) {
            e.setCancelled(true);
        }
    }
}
