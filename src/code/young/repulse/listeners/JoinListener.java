package code.young.repulse.listeners;

import code.young.repulse.Repulse;
import code.young.repulse.managers.ProfileManager;
import code.young.repulse.managers.SpawnManager;
import code.young.repulse.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Myles on 01/03/2015.
 *
 */
public class JoinListener implements Listener  {

    private ProfileManager pm = Repulse.getInstance().getProfileManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        final Player p = e.getPlayer();

        if (!pm.hasProfile(p.getUniqueId())) {
            pm.createProfile(p);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Repulse.getInstance(), new Runnable() {
            public void run() {
                SpawnManager sm = Repulse.getInstance().getSpawnManager();
                sm.setSpawnInventory(p);
                p.teleport(sm.getSpawn());
            }
        }, 3);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        Profile prof = pm.getProfile(p.getUniqueId());
        if (prof.hasCurrentKit()) {
            prof.saveKit(prof.getCurrentKit());
            prof.setCurrentKit(null);
        }
    }
}