package code.young.repulse.listeners;

import code.young.repulse.Repulse;
import code.young.repulse.managers.FFAManager;
import code.young.repulse.managers.ProfileManager;
import code.young.repulse.managers.SpawnManager;
import code.young.repulse.objects.CurrentKit;
import code.young.repulse.objects.Profile;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class DeathListener implements Listener {

    private Repulse main = Repulse.getInstance();
    private SpawnManager sm = Repulse.getInstance().getSpawnManager();
    private ProfileManager pm = Repulse.getInstance().getProfileManager();
    private FFAManager ffam = Repulse.getInstance().getFFAManager();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        respawnPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Profile pprof = pm.getProfile(p.getUniqueId());

        pprof.setDeaths(pprof.getDeaths() + 1);

        if (pprof.getCurrentKS() >= 10) {
            if (p.getKiller() != null) {
                MessageManager.broadcastMessage("&6&l" + p.getKiller() + " &ehas ended &6&l" + pprof.getName() + "'s &ekillstreak of &6&l" + pprof.getCurrentKS() + "&e!");
            }
        }

        pprof.setCurrentKS(0);

        if (pprof.hasCurrentKit()) {
            CurrentKit ck = pprof.getCurrentKit();
            ck.setDeaths(ck.getDeaths() + 1);

            pprof.saveKit(ck);
            pprof.setCurrentKit(null);
        }

        if (p.getKiller() != null) {
            Player k = p.getKiller();
            Profile kprof = pm.getProfile(k.getUniqueId());

            kprof.setKills(kprof.getKills() + 1);
            kprof.setCurrentKS(kprof.getCurrentKS() + 1);

            if (kprof.getCurrentKS() > kprof.getTopKS()) {
                kprof.setTopKS(kprof.getCurrentKS());
            }

            if (kprof.getCurrentKS() % 5 == 0) {
                MessageManager.broadcastMessage("&6&l" + kprof.getName() + " &eis on a &6&l" + kprof.getCurrentKS() + " &ekillstreak!");
            }

            if (kprof.hasCurrentKit()) {
                CurrentKit ck = kprof.getCurrentKit();
                ck.setKills(ck.getKills() + 1);

                kprof.saveKit(ck);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        for (ItemStack drops : e.getDrops()) {
            Item item = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
            item.setTicksLived(5 * 20);
        }

        e.getDrops().clear();
    }

    public void respawnPlayer(final Player p) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Repulse.getInstance(), new Runnable() {
            public void run() {
                Repulse.getInstance().getSpawnManager().setSpawnInventory(p);
                p.teleport(Repulse.getInstance().getSpawnManager().getSpawn());
            }
        }, 3L);
    }
}
