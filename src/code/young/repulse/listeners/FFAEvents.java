package code.young.repulse.listeners;

import code.young.repulse.Repulse;
import code.young.repulse.managers.FFAManager;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class FFAEvents implements Listener {

    private FFAManager ffam = Repulse.getInstance().getFFAManager();
    private List<UUID> noFall = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (ffam.getProtectionIIPlayers().contains(p.getUniqueId()) && ffam.getProtectionIISpawnProt().contains(p.getUniqueId())) {
            if (!ffam.isInsideProtectionIISpawn(p.getLocation())) {
                if (ffam.getProtectionIISpawnProt().contains(p.getUniqueId())) {
                    ffam.getProtectionIISpawnProt().remove(p.getUniqueId());
                    MessageManager.sendMessage(p, "&cYou no longer have spawn protection.");
                    noFall.add(p.getUniqueId());
                }
            }
        } else {
            if (ffam.getNoEnchantmentPlayers().contains(p.getUniqueId()) && ffam.getNoEnchantmentSpawnProt().contains(p.getUniqueId())) {
                if (!ffam.isInsideNoEnchantmentFFASpawn(p.getLocation())) {
                    if (ffam.getNoEnchantmentSpawnProt().contains(p.getUniqueId())) {
                        ffam.getNoEnchantmentSpawnProt().remove(p.getUniqueId());
                        MessageManager.sendMessage(p, "&cYou no longer have spawn protection.");
                        noFall.add(p.getUniqueId());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (ffam.isInsideProtectionIISpawn(p.getLocation()) || ffam.isInsideNoEnchantmentFFASpawn(p.getLocation())) {
                e.setCancelled(true);
            }
            if (noFall.contains(p.getUniqueId())) {
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                    noFall.remove(p.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (ffam.isInsideProtectionIISpawn(p.getLocation()) || ffam.isInsideNoEnchantmentFFASpawn(p.getLocation())) {
                e.setCancelled(true);
            }
        }
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (ffam.isInsideProtectionIISpawn(p.getLocation()) || ffam.isInsideNoEnchantmentFFASpawn(p.getLocation())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (ffam.isInsideProtectionIISpawn(p.getLocation()) || ffam.isInsideNoEnchantmentFFASpawn(p.getLocation())) {
            if (e.hasItem()) {
                if (e.getItem() != null) {
                    if (e.getItem().getType() == Material.POTION) {
                        if (e.getItem().getDurability() == 16388 || e.getItem().getDurability() == 16426) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPearl(PlayerTeleportEvent e) {
        if (e.getCause() == TeleportCause.ENDER_PEARL) {
            if (ffam.isInsideProtectionIISpawn(e.getTo()) || ffam.isInsideNoEnchantmentFFASpawn(e.getTo())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            ffam.removeFromLists(p);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        ffam.removeFromLists(p);
    }
}
