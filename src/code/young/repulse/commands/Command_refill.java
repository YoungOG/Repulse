package code.young.repulse.commands;

import code.BreakMC.commons.command.BaseCommand;
import code.young.repulse.Repulse;
import code.young.repulse.managers.RefillManager;
import code.young.repulse.objects.Profile;
import code.young.repulse.objects.RefillZone;
import code.young.repulse.utils.MessageManager;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Calvin on 6/2/2015.
 */
public class Command_refill extends BaseCommand {

    private Repulse main = Repulse.getInstance();
    private RefillManager rfm = main.getRefillManager();


    public Command_refill() {
        super("refill", "repulse.refill", "refillzones", "ref", "rf", "rz", "rfz");
        setUsage("/<command>");
        setMinArgs(1);
        setMaxArgs(2);
    }

    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                MessageManager.sendMessage(p, "&6&m--------&r &eRefill Zones &6&m--------");
                for (RefillZone rz : rfm.getRefillZones()) {
                    if (rz.getPlayers().size() > 0) {
                        List<String> names = new ArrayList<String>();
                        for (UUID id : rz.getPlayers()) {
                            Profile prof = main.getProfileManager().getProfile(id);
                            names.add(prof.getName());
                        }
                        MessageManager.sendMessage(p, "&6ID: &e" + rz.getId() + " &6Players: &e" + names.toString().replace("[", "").replace("]", ""));
                    } else {
                        MessageManager.sendMessage(p, "&6ID: &e" + rz.getId() + " &6Players: &enone");
                    }
                }
                MessageManager.sendMessage(p, "&6&m---------------------------");
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                Location pos1;
                Location pos2;
                try {
                    LocalSession session = WorldEdit.getInstance().getSession(p.getName());
                    LocalWorld world = session.getSelectionWorld();

                    Region region = session.getSelection(world);

                    String worldName = region.getWorld().getName();
                    World selWorld = Bukkit.getWorld(worldName);

                    double pos1x = region.getMaximumPoint().getX();
                    double pos1y = region.getMaximumPoint().getY();
                    double pos1z = region.getMaximumPoint().getZ();
                    pos1 = new Location(selWorld, pos1x, pos1y, pos1z);

                    double pos2x = region.getMinimumPoint().getX();
                    double pos2y = region.getMinimumPoint().getY();
                    double pos2z = region.getMinimumPoint().getZ();
                    pos2 = new Location(selWorld, pos2x, pos2y, pos2z);
                } catch (IncompleteRegionException | NullPointerException e) {
                    MessageManager.sendMessage(p, "&cYou must select an area.");
                    return;
                }

                String name = args[1];

                for (RefillZone rzone : rfm.getRefillZones()) {
                    if (rzone.getId().equalsIgnoreCase(name)) {
                        MessageManager.sendMessage(p, "&cThere is already an existing refill zone with that name.");
                        return;
                    }
                }

                RefillZone newRefillZone = new RefillZone(name, pos1, pos2);
                rfm.getRefillZones().add(newRefillZone);
                rfm.saveRefillZone(newRefillZone);

                MessageManager.sendMessage(p, "&6You have created a new refill zone: &e" + args[1]);
            }
            if (args[0].equalsIgnoreCase("remove")) {
                String name = args[1];
                RefillZone rfz = null;

                for (RefillZone rzone : rfm.getRefillZones()) {
                    if (rzone.getId().equalsIgnoreCase(name)) {
                        rfz = rzone;
                    }
                }

                if (rfz == null) {
                    MessageManager.sendMessage(p, "&cThere are no refill zones named: " + args[1]);
                    return;
                }

                rfm.removeRefillZone(rfz);

                MessageManager.sendMessage(p, "&6You have removed the refill zone: &e" + args[1]);
            }
        }
    }
}
