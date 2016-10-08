package code.young.repulse.managers;

import code.BreakMC.commons.util.PlayerUtility;
import code.young.repulse.Repulse;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class FFAManager {

    private Repulse main = Repulse.getInstance();
    private InventoryManager im = main.getInventoryManager();
    private KitManager km = main.getKitManager();
    private SpawnManager sm = main.getSpawnManager();
    private TeamManager tm = main.getTeamManager();

    private ArrayList<UUID> protectionIIPickedKit = new ArrayList<>();
    private ArrayList<UUID> protectionIIPickedKit = new ArrayList<>();
    private ArrayList<UUID> protectionIIPlayers = new ArrayList<>();
    private ArrayList<UUID> protectionIISpawnProt = new ArrayList<>();
    private ArrayList<UUID> noEnchantmentPlayers = new ArrayList<>();
    private ArrayList<UUID> noEnchantmentSpawnProt = new ArrayList<>();

    private Location mchcfc1 = new Location(Bukkit.getWorld("world"), -21.5, 120, 5.5);
    private Location mchcfc2 = new Location(Bukkit.getWorld("world"), -4.5, 0, -12.5);
    private Location mchcfSpawnLoc = new Location(Bukkit.getWorld("world"), 1028.5, 153.0, -31.5);
    private Location mchcfSpawnC1 = new Location(Bukkit.getWorld("world"), 1033.5, 256.0, -36.5);
    private Location mchcfSpawnC2 = new Location(Bukkit.getWorld("world"), 1023.5, 150.0, -26.5);
    private Location noench1 = new Location(Bukkit.getWorld("world"), 23.5, 120, -13.5);
    private Location noench2 = new Location(Bukkit.getWorld("world"), 3.5, 0, 6.5);
    private Location noenchSpawnLoc = new Location(Bukkit.getWorld("world"), 1150.5, 155.0, 475.5);
    private Location noenchSpawnC1 = new Location(Bukkit.getWorld("world"), 1155.5, 256.5, 480.5);
    private Location noenchSpawnC2 = new Location(Bukkit.getWorld("world"), 1145.5, 153.5, 470.5);

    public FFAManager() {
        new BukkitRunnable() {
            public void run() {
                for (Player all : PlayerUtility.getOnlinePlayers()) {
                    if (isInProtectionIIFFAPortal(all.getLocation())) {
                        teleportToProtectionIIFFA(all);
                    }
                    else if (isInNoEnchantmentFFAPortal(all.getLocation())) {
                        teleportToNoEnchantmentFFA(all);
                    }
                    if (all.getOpenInventory() != null) {
                        if (getmchcfPickedKitList().contains(all.getUniqueId())) {
                            if (!all.getOpenInventory().getTitle().contains("Load Protection II")) {
                                im.showProtectionIILoadMenu(all);
                            }
                        } else if (getNoEnchPickedKitList().contains(all.getUniqueId())) {
                            if (!all.getOpenInventory().getTitle().contains("Load No Enchantment")) {
                                im.showNoEnchantmentLoadMenu(all);
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(main, 0L, 3L);
    }

    public void teleportToProtectionIIFFA(Player p) {
        if (tm.hasTeam(p.getUniqueId())) {
            p.teleport(sm.getSpawn());
            MessageManager.sendMessage(p, "&cYou can not leave spawn while in a team.");
            return;
        }

        p.teleport(mchcfSpawnLoc);
        sm.clearInventory(p);
        sm.managePlayer(p);

        p.setFallDistance(0.0F);

        MchcfPlayers.add(p.getUniqueId());
        MchcfSpawnProt.add(p.getUniqueId());

        if (km.hasMchcfKit(p)) {
            if (!phasPickedKit.contains(p.getUniqueId())) {
                phasPickedKit.add(p.getUniqueId());
            }
            im.showProtectionIILoadMenu(p);
        } else {
            MessageManager.sendMessage(p, "&cNo kit found! &eDefault Protection II kit loaded!");
            km.giveDefaultMchcfKit(p);
        }
    }

    public void teleportToNoEnchantmentFFA(Player p) {
        if (tm.hasTeam(p.getUniqueId())) {
            p.teleport(sm.getSpawn());
            MessageManager.sendMessage(p, "&cYou can not leave spawn while in a team.");
            return;
        }

        p.teleport(noenchSpawnLoc);
        sm.clearInventory(p);
        sm.managePlayer(p);

        p.setFallDistance(0.0F);

        noEnchantmentPlayers.add(p.getUniqueId());
        noEnchantmentSpawnProt.add(p.getUniqueId());

        if (km.hasNoEnchantmentKit(p)) {
            if (!ehasPickedKit.contains(p.getUniqueId())) {
                ehasPickedKit.add(p.getUniqueId());
            }
            im.showNoEnchantmentLoadMenu(p);
        } else {
            MessageManager.sendMessage(p, "&cNo kit found! &eDefault No Enchantment kit loaded!");
            km.giveDefaultNoEnchantmentKit(p);
        }
    }

    public boolean isInProtectionIIFFAPortal(Location loc) {
        double xMin;
        double xMax;
        double yMin;
        double yMax;
        double zMin;
        double zMax;
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        xMin = Math.min(mchcfc1.getX(), mchcfc2.getX());
        xMax = Math.max(mchcfc1.getX(), mchcfc2.getX());

        yMin = Math.min(mchcfc1.getY(), mchcfc2.getY());
        yMax = Math.max(mchcfc1.getY(), mchcfc2.getY());

        zMin = Math.min(mchcfc1.getZ(), mchcfc2.getZ());
        zMax = Math.max(mchcfc1.getZ(), mchcfc2.getZ());

        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);
    }

    public Boolean isInsideProtectionIISpawn(Location loc) {
        double xMin;
        double xMax;
        double yMin;
        double yMax;
        double zMin;
        double zMax;
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        xMin = Math.min(mchcfSpawnC1.getX(), mchcfSpawnC2.getX());
        xMax = Math.max(mchcfSpawnC1.getX(), mchcfSpawnC2.getX());

        yMin = Math.min(mchcfSpawnC1.getY(), mchcfSpawnC2.getY());
        yMax = Math.max(mchcfSpawnC1.getY(), mchcfSpawnC2.getY());

        zMin = Math.min(mchcfSpawnC1.getZ(), mchcfSpawnC2.getZ());
        zMax = Math.max(mchcfSpawnC1.getZ(), mchcfSpawnC2.getZ());

        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);
    }

    public boolean isInNoEnchantmentFFAPortal(Location loc) {
        double xMin;
        double xMax;
        double yMin;
        double yMax;
        double zMin;
        double zMax;
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        xMin = Math.min(noench1.getX(), noench2.getX());
        xMax = Math.max(noench1.getX(), noench2.getX());

        yMin = Math.min(noench1.getY(), noench2.getY());
        yMax = Math.max(noench1.getY(), noench2.getY());

        zMin = Math.min(noench1.getZ(), noench2.getZ());
        zMax = Math.max(noench1.getZ(), noench2.getZ());

        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);
    }

    public Boolean isInsideNoEnchantmentFFASpawn(Location loc) {
        double xMin;
        double xMax;
        double yMin;
        double yMax;
        double zMin;
        double zMax;
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        xMin = Math.min(noenchSpawnC1.getX(), noenchSpawnC2.getX());
        xMax = Math.max(noenchSpawnC1.getX(), noenchSpawnC2.getX());

        yMin = Math.min(noenchSpawnC1.getY(), noenchSpawnC2.getY());
        yMax = Math.max(noenchSpawnC1.getY(), noenchSpawnC2.getY());

        zMin = Math.min(noenchSpawnC1.getZ(), noenchSpawnC2.getZ());
        zMax = Math.max(noenchSpawnC1.getZ(), noenchSpawnC2.getZ());

        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);
    }

    public void removeFromLists(Player p) {
        if (getMchcfPlayers().contains(p.getUniqueId())) {
            getMchcfPlayers().remove(p.getUniqueId());
        }
        if (getMchcfSpawnProt().contains(p.getUniqueId())) {
            getMchcfSpawnProt().remove(p.getUniqueId());
        }
        if (getNoEnchantmentPlayers().contains(p.getUniqueId())) {
            getNoEnchantmentPlayers().remove(p.getUniqueId());
        }
        if (getNoEnchantmentSpawnProt().contains(p.getUniqueId())) {
            getNoEnchantmentSpawnProt().remove(p.getUniqueId());
        }
    }

    public ArrayList<UUID> getmchcfPickedKitList() {
        return phasPickedKit;
    }

    public ArrayList<UUID> getNoEnchPickedKitList() {
        return ehasPickedKit;
    }

    public ArrayList<UUID> getMchcfPlayers() {
        return MchcfPlayers;
    }

    public ArrayList<UUID> getNoEnchantmentPlayers() {
        return noEnchantmentPlayers;
    }

    public ArrayList<UUID> getMchcfSpawnProt() {
        return MchcfSpawnProt;
    }

    public ArrayList<UUID> getNoEnchantmentSpawnProt() {
        return noEnchantmentSpawnProt;
    }
}
