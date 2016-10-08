package code.young.repulse.commands;

import code.BreakMC.commons.command.BaseCommand;
import code.young.repulse.Repulse;
import code.young.repulse.managers.ArenaManager;
import code.young.repulse.managers.FFAManager;
import code.young.repulse.managers.SpawnManager;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

/**
 * Created by Calvin on 6/2/2015.
 */
public class Command_spawn extends BaseCommand implements Listener {

    private Repulse main = Repulse.getInstance();
    private ArenaManager am = main.getArenaManager();
    private SpawnManager sm = main.getSpawnManager();
    private FFAManager ffam = main.getFFAManager();
    private static HashMap<Player, BukkitTask> playerTeleport = new HashMap<>();

    public Command_spawn() {
        super("spawn", "repulse.spawn");
        setUsage("/<command>");
        setMinArgs(0);
        setMaxArgs(1000);
    }

    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (am.isInDuel(p)) {
            MessageManager.sendMessage(p, "&cYou can not use this command during a duel.");
            return;
        }

        if (ffam.getProtectionIIPlayers().contains(p.getUniqueId()) || ffam.getNoEnchantmentPlayers().contains(p.getUniqueId())) {
            if (playerTeleport.get(p) == null) {
                MessageManager.sendMessage(p, "&aYou will be teleported in 5 seconds. Don't move!");
                teleportPlayerWithDelay(p, 5, sm.getSpawn(), null);
            } else {
                MessageManager.sendMessage(p, "&cYou are already preparing to teleport.");
            }
        }
    }

    public static void teleportPlayerWithDelay(final Player p, long l, final Location loc, final Runnable postTeleport) {
        if (playerTeleport.get(p) != null) {
            playerTeleport.remove(p);
        }

        BukkitTask task = Repulse.getInstance().getServer().getScheduler().runTaskLater(Repulse.getInstance(), new Runnable() {
            public void run() {
                if (p.isOnline()) {
                    p.teleport(loc);
                    MessageManager.sendMessage(p, "&aYou have been teleported to spawn.");
                    Repulse.getInstance().getSpawnManager().setSpawnInventory(p);
                    if (postTeleport != null) {
                        postTeleport.run();
                    }
                }
            }
        }, l * 20L);

        playerTeleport.put(p, task);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ() || from.getWorld() != to.getWorld()) {
            Player p = e.getPlayer();
            if (playerTeleport.get(p) != null) {
                playerTeleport.remove(p).cancel();
                MessageManager.sendMessage(p, "&cTeleportation canceled.");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (playerTeleport.get(e.getPlayer()) != null) {
            playerTeleport.remove(e.getPlayer()).cancel();
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (playerTeleport.get(e.getEntity()) != null) {
            playerTeleport.remove(e.getEntity()).cancel();
        }

        for (Entity ents : e.getEntity().getWorld().getEntities()) {
            if (ents.getType() == EntityType.ENDER_PEARL) {
                EnderPearl pearl = (EnderPearl) ents;
                if (pearl.getShooter() instanceof Player) {
                    Player p = (Player) pearl.getShooter();
                    if (p.equals(pearl.getShooter())) {
                        pearl.remove();
                    }
                }
            }
        }
    }
}
