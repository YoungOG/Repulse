package code.young.repulse.listeners;

import code.BreakMC.commons.util.PlayerUtility;
import code.young.repulse.Repulse;
import code.young.repulse.managers.ArenaManager;
import code.young.repulse.managers.ProfileManager;
import code.young.repulse.managers.SpawnManager;
import code.young.repulse.utils.Cooldowns;
import code.young.repulse.utils.MessageManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

/*
 * Created by Calvin on 2/17/2015.
 *
 */
public class PlayerEvents implements Listener {

    private Repulse main = Repulse.getInstance();
    private ProfileManager pm = main.getProfileManager();
    private HashMap<UUID, Vector> pearled = new HashMap<>();
    private ArenaManager am = main.getArenaManager();
    private SpawnManager sm = main.getSpawnManager();

    public PlayerEvents() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (Cooldowns.getCooldown(p.getUniqueId(), "EnderPearlCooldown") > 0) {
                        p.setExp(p.getExp() - 0.00328666666F);
                    } else {
                        p.setExp(0F);
                    }
                }
            }
        }.runTaskTimerAsynchronously(main, 1l, 10l);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (Cooldowns.getCooldown(p.getUniqueId(), "EnderPearlCooldown") >= 1000) {
                        p.setLevel((int) Cooldowns.getCooldown(p.getUniqueId(), "EnderPearlCooldown") / 1000);
                    } else {
                        p.setLevel(0);
                    }
                }
            }
        }.runTaskTimerAsynchronously(main, 1l, 10l);
    }

    @EventHandler
    public void onHideJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
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

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e broke their legs"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e forgot to hold their breath"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e fell into the void"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e now looks like fried chicken"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e burnt into a crisp"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e died"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e exploded into a million pieces"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e was killed by dark magic"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e starved to death"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e was raped by lightning"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e tried to swim in lava"));
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (p.getKiller() instanceof Player) {
                Player killer = (Player) p.getKiller();
                if (killer.getItemInHand().getItemMeta().getDisplayName() != null) {
                    e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e was killed by &c" + killer.getName() + "&e using &c" + killer.getItemInHand().getItemMeta().getDisplayName()));
                } else {
                    e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e was killed by &c" + p.getName()));
                }
            }
        } else if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            if (p.getKiller() instanceof Arrow) {
                Arrow arrow = (Arrow) p.getKiller();
                if (arrow.getShooter() instanceof Player) {
                    Player shooter = (Player) arrow.getShooter();
                    if (shooter.getLocation().distance(p.getLocation()) > 50) {
                        e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e was sniped by &c" + shooter.getName()));
                    } else {
                        e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e was shot down by &c" + shooter.getName()));
                    }
                } else {
                    e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e was shot down"));
                }
            }
        } else {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&c" + p.getName() + "&e died"));
        }
     }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer().isOp()) {
            e.setFormat(ChatColor.translateAlternateColorCodes('&', "&4" + e.getPlayer().getName() + "&f: " + e.getMessage()));
        }
        else if (e.getPlayer().hasPermission("practicepots.mod")) {
            e.setFormat(ChatColor.translateAlternateColorCodes('&', "&5" + e.getPlayer().getName() + "&f: " + e.getMessage()));
        }
        else if (e.getPlayer().hasPermission("practicepots.admin")) {
            e.setFormat(ChatColor.translateAlternateColorCodes('&', "&c" + e.getPlayer().getName() + "&f: " + e.getMessage()));
        } else {
            e.setFormat(ChatColor.translateAlternateColorCodes('&', "&7" + e.getPlayer().getName() + "&f: " + e.getMessage()));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().hasPermission("practicepots.build")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.getPlayer().hasPermission("practicepots.build")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPearlClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().name().contains("RIGHT")) {
            if (e.hasItem()) {
                if (e.getItem().getType() == Material.ENDER_PEARL) {
                    if (this.pearled.containsKey(p.getUniqueId())) {
                        this.pearled.remove(p.getUniqueId());
                    }
                    this.pearled.put(p.getUniqueId(), p.getEyeLocation().getDirection());
                }
            }
        }
    }

    @EventHandler
    public void onPearlLand(ProjectileHitEvent e) {
        if (e.getEntity() instanceof EnderPearl) {
            EnderPearl pearl = (EnderPearl) e.getEntity();
            if (pearl.getShooter() instanceof Player) {
                Player p = (Player) pearl.getShooter();
                if (this.pearled.containsKey(p.getUniqueId())) {
                    Location location = getBack(pearl.getLocation(), this.pearled.get(p.getUniqueId()).multiply(-1));
                    location.setDirection(p.getLocation().getDirection());
                    Block b = pearl.getLocation().getBlock();
                    if (b.getFace(b).equals(BlockFace.UP)) {
                        return;
                    }
                    if (location.getBlock().getType() == Material.AIR) {
                        p.teleport(location);
                    }
                } //try that
            }
        }
    }

    public Location getBack(Location loc, Vector direction) {
        for (int i = 0; i <= 1; i++) {
            loc.add(direction).getBlock();
        }
        return loc;
    }
    @EventHandler
    public void onPearl(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.hasItem()) {
            if (e.getItem().getType() == Material.ENDER_PEARL) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (am.getFreezed().contains(p.getUniqueId())) {
                        e.setCancelled(true);
                        return;
                    }
                    if (Repulse.getInstance().getSpawnManager().isInKitArea(p)) {
                        return;
                    }
                    if (!Cooldowns.tryCooldown(p.getUniqueId(), "EnderPearlCooldown", 15000)) {
                        e.setCancelled(true);
                        p.updateInventory();
                        MessageManager.sendMessage(p, "&cYou must wait &e" + (Cooldowns.getCooldown(p.getUniqueId(), "EnderPearlCooldown") / 1000) + " &cseconds before enderpearling!");
                    } else {
                        p.setExp((float) 0.986);
                        p.setLevel(15);
                        for (ItemStack i : p.getInventory().getContents()) {
                            if (i != null) {
                                if (i.getType() == Material.ENDER_PEARL) {
                                    ItemMeta im = i.getItemMeta();
                                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cRemaining Time: 15"));
                                    i.setItemMeta(im);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}