package code.young.repulse.listeners;

import code.young.repulse.Repulse;
import code.young.repulse.managers.RefillManager;
import code.young.repulse.objects.RefillZone;
import code.young.repulse.utils.Cooldowns;
import code.young.repulse.utils.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class RefillEvents implements Listener {

    private Repulse main = Repulse.getInstance();
    private RefillManager rfm = main.getRefillManager();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!rfm.isRefilling(p)) {
            for (RefillZone rfzs : rfm.getRefillZones()) {
                if (rfzs.isInside(p.getLocation())) {
                    if (Cooldowns.getCooldown(p.getUniqueId(), "RefillCooldown") > 0) {
                        if (Cooldowns.tryCooldown(p.getUniqueId(), "MessageCooldown", 1000)) {
                            MessageManager.sendMessage(p, "&cYou can not refill any more potions for " + (Cooldowns.getCooldown(p.getUniqueId(), "RefillCooldown") / 1000) + " seconds!");
                        }
                        return;
                    }
                    if (!rfzs.getPlayers().contains(p.getUniqueId())) {
                        rfzs.startRefill(main.getProfileManager().getProfile(p.getUniqueId()));
                        MessageManager.sendMessage(p, "&c&lEntering Refill Zone!");
                    } else {
                        MessageManager.sendMessage(p, "&cAn error occured. Alert an admin. ID: Player was still in list.");
                    }
                }
            }
        } else {
            for (RefillZone rfzs : rfm.getRefillZones()) {
                if (rfzs.getPlayers().contains(p.getUniqueId())) {
                    if (!rfzs.isInside(p.getLocation())) {
                        rfzs.stopRefill(p.getUniqueId());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!rfm.getRefills().containsKey(p.getUniqueId())) {
            rfm.getRefills().put(p.getUniqueId(), 10);
        }
    }
}
