package code.young.repulse.objects;

import code.young.repulse.Repulse;
import code.young.repulse.managers.RefillManager;
import code.young.repulse.utils.Cooldowns;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class RefillZone {

    private Repulse main = Repulse.getInstance();
    private RefillManager rfm = main.getRefillManager();
    private String id;
    private Location pos1;
    private Location pos2;
    private List<UUID> players;

    public RefillZone(String id, Location pos1, Location pos2) {
        this.id = id;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.players = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void startRefill(final Profile prof) {
        players.add(prof.getUniqueId());
        final Player p = Bukkit.getPlayer(prof.getUniqueId());
         new BukkitRunnable() {
            public void run() {
                if (p == null) {
                    players.remove(prof.getUniqueId());
                    cancel();
                    return;
                }

                if (!players.contains(prof.getUniqueId())) {
                    players.remove(prof.getUniqueId());
                    cancel();
                }

                if (!isInside(p.getLocation())) {
                    players.remove(prof.getUniqueId());
                    cancel();
                }

                if (rfm.getRemainingRefills(prof.getUniqueId()) > 0) {
                    rfm.setRemainingRefills(prof.getUniqueId(), rfm.getRemainingRefills(prof.getUniqueId()) - 1);
                    ItemStack pot = new ItemStack(Material.POTION, 1);
                    pot.setDurability((short) 16421);
                    p.getInventory().addItem(pot);
                    MessageManager.sendMessage(prof, "&dRemaining Refills: &c" + (rfm.getRemainingRefills(prof.getUniqueId()) + 1));
                }

                if (rfm.getRemainingRefills(prof.getUniqueId()) == 0) {
                    MessageManager.sendMessage(prof, "&cMaximum Refills reached!");
                    players.remove(prof.getUniqueId());
                    cancel();
                    Cooldowns.setCooldown(prof.getUniqueId(), "RefillCooldown", 60000);
                    new BukkitRunnable() {
                        public void run() {
                            rfm.setRemainingRefills(prof.getUniqueId(), 10);
                        }
                    }.runTaskLater(main, 60*20);
                }
            }
        }.runTaskTimer(main, 0, 20);
    }

    public void stopRefill(UUID id) {
        players.remove(id);
    }

    public Boolean isInside(Location loc) {
        double xMin;
        double xMax;
        double yMin;
        double yMax;
        double zMin;
        double zMax;
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        xMin = Math.min(pos1.getX(), pos2.getX());
        xMax = Math.max(pos1.getX(), pos2.getX());

        yMin = Math.min(pos1.getY(), pos2.getY());
        yMax = Math.max(pos1.getY(), pos2.getY());

        zMin = Math.min(pos1.getZ(), pos2.getZ());
        zMax = Math.max(pos1.getZ(), pos2.getZ());

        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);
    }
}
