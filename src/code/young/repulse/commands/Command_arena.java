package code.young.repulse.commands;

import code.BreakMC.commons.command.BaseCommand;
import code.young.repulse.Repulse;
import code.young.repulse.enums.ArenaState;
import code.young.repulse.managers.ArenaManager;
import code.young.repulse.objects.Arena;
import code.young.repulse.objects.Profile;
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
public class Command_arena extends BaseCommand {

    private Repulse main = Repulse.getInstance();
    private ArenaManager am = main.getArenaManager();

    public Command_arena() {
        super("arena", "repulse.arena", "arenas");
        setUsage("/<command>");
        setMinArgs(1);
        setMaxArgs(2);
    }

    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                MessageManager.sendMessage(p, "&6&m--------&r &eArenas &6&m--------");
                for (Arena a : am.getArenas()) {
                    List<String> players = new ArrayList<>();
                    for (UUID ap : a.getPlayers()) {
                        Profile prof = Repulse.getInstance().getProfileManager().getProfile(ap);
                        players.add(prof.getName());
                    }
                    MessageManager.sendMessage(p, "&e" + a.getId() + " &6Players: &e" + (a.getPlayers().size() > 0 ? players.toString().replace("[", "").replace("]", "") : "&enone") + " &6State: &e" + a.getState() + (a.getState().equals(ArenaState.STARTED) ? " &6Type: &e" + a.getType() + " &6Is Over: &e" + a.isOver() : ""));
                }
                MessageManager.sendMessage(p, "&6&m------------------------");
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

                for (Arena as : am.getArenas()) {
                    if (as.getId().equalsIgnoreCase(name)) {
                        MessageManager.sendMessage(p, "&cThere is already an existing arena with that name.");
                        return;
                    }
                }

                Arena a = new Arena(name, pos1, pos2);
                am.getArenas().add(a);
                am.saveArena(a);

                MessageManager.sendMessage(p, "&6You have created a new arena: &e" + args[1]);
            }
            if (args[0].equalsIgnoreCase("remove")) {
                String name = args[1];
                Arena a = null;

                for (Arena as : am.getArenas()) {
                    if (as.getId().equalsIgnoreCase(name)) {
                        a = as;
                    }
                }

                if (a == null) {
                    MessageManager.sendMessage(p, "&cThere are no arenas named: " + args[1]);
                    return;
                }

                am.removeArena(a);

                MessageManager.sendMessage(p, "&6You have removed the arena: &e" + args[1]);
            }

            if (args[0].equalsIgnoreCase("setspawn")) {
                Location loc = p.getLocation();
                if (Integer.parseInt(args[1]) == 1) {
                    for (Arena a : am.getArenas()) {
                        if (isInRegion(loc, a.getPos1(), a.getPos2())) {
                            a.setSpawn1(loc);
                            MessageManager.sendMessage(p, "&6Set Spawn Point 1 for Arena: &e" + a.getId());
                            am.saveArena(a);
                        }
                    }
                } else if (Integer.parseInt(args[1]) == 2) {
                    for (Arena a : am.getArenas()) {
                        if (isInRegion(loc, a.getPos1(), a.getPos2())) {
                            a.setSpawn2(loc);
                            MessageManager.sendMessage(p, "&6Set Spawn Point 2 2 for Arena: &e" + a.getId());
                            am.saveArena(a);
                        }
                    }
                } else {
                    MessageManager.sendMessage(p, "&cYou can only set Spawn Points 1 or 2");
                }
            }
        }
    }

    public static boolean isInRegion(Location loc, Location loc1, Location loc2) {
        double xMin;
        double xMax;
        double yMin;
        double yMax;
        double zMin;
        double zMax;
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        xMin = Math.min(loc1.getX(), loc2.getX());
        xMax = Math.max(loc1.getX(), loc2.getX());

        yMin = Math.min(loc1.getY(), loc2.getY());
        yMax = Math.max(loc1.getY(), loc2.getY());

        zMin = Math.min(loc1.getZ(), loc2.getZ());
        zMax = Math.max(loc1.getZ(), loc2.getZ());

        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);
    }
}
