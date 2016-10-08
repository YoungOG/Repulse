package code.young.repulse.managers;

import code.BreakMC.commons.util.LocationUtility;
import code.young.repulse.Repulse;
import code.young.repulse.enums.ArenaState;
import code.young.repulse.enums.ArenaType;
import code.young.repulse.objects.Arena;
import code.young.repulse.objects.Profile;
import code.young.repulse.utils.EbolaCalc;
import code.young.repulse.utils.MessageManager;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class ArenaManager {

    private Repulse main = Repulse.getInstance();
    private KitManager km = main.getKitManager();
    private InventoryManager im = main.getInventoryManager();
    private ProfileManager pm = main.getProfileManager();
    private FFAManager ffa = main.getFFAManager();
    private QueueManager qm = main.getQueueManager();

    private Random random = new Random();
    private ArrayList<Arena> arenas = new ArrayList<>();
    private ArrayList<UUID> freezed = new ArrayList<>();
    private Multimap<Arena, UUID> team1 = ArrayListMultimap.create();
    private Multimap<Arena, UUID> team2 = ArrayListMultimap.create();
    private Multimap<Arena, UUID> team1Dead = ArrayListMultimap.create();
    private Multimap<Arena, UUID> team2Dead = ArrayListMultimap.create();

    private DBCollection aCollection = Repulse.getInstance().getArenaCollection();

    public void loadArenas() {
        DBCursor dbc = aCollection.find();

        System.out.println("[Repulse]: Preparing to load " + dbc.count() + " Arenas.");

        while (dbc.hasNext()) {
            BasicDBObject dbo = (BasicDBObject) dbc.next();

            String id = dbo.getString("id");
            Location pos1 = LocationUtility.deserializeLocation(dbo.getString("pos1"));
            Location pos2 = LocationUtility.deserializeLocation(dbo.getString("pos2"));
            Location spawn1 = null;
            Location spawn2 = null;

            if (dbo.getString("spawn1") != null) {
                spawn1 = LocationUtility.deserializeLocation(dbo.getString("spawn1"));
            }

            if (dbo.getString("spawn2") != null) {
                spawn2 = LocationUtility.deserializeLocation(dbo.getString("spawn2"));
            }

            Arena a;

            if (spawn1 != null && spawn2 != null) {
                a = new Arena(id, pos1, pos2, spawn1, spawn2);
            } else {
                a = new Arena(id, pos1, pos2);
            }

            System.out.println("[Repulse]: Arena: " + a.getId() + " has been successfully loaded.");

            getArenas().add(a);
        }
    }

    public void saveArena(Arena a) {
        DBCursor dbc = aCollection.find(new BasicDBObject("id", a.getId()));

        BasicDBObject dbo = new BasicDBObject("id", a.getId());
        dbo.put("pos1", LocationUtility.serializeLocation(a.getPos1()));
        dbo.put("pos2", LocationUtility.serializeLocation(a.getPos2()));

        if (a.getSpawn1() != null) {
            dbo.put("spawn1", LocationUtility.serializeLocation(a.getSpawn1()));
        }

        if (a.getSpawn2() != null) {
            dbo.put("spawn2", LocationUtility.serializeLocation(a.getSpawn2()));
        }

        if (dbc.hasNext()) {
            aCollection.update(dbc.getQuery(), dbo);
        } else {
            aCollection.insert(dbo);
        }
    }

    public void removeArena(Arena a) {
        DBCursor dbc = aCollection.find(new BasicDBObject("id", a.getId()));

        BasicDBObject dbo = new BasicDBObject("id", a.getId());

        if (dbc.hasNext()) {
            aCollection.remove(dbo);
        }

        getArenas().remove(a);
    }

    public void startSingleDuel(Player p1, Player p2, ArenaType type) {
        final Arena a = getRandomArena();

        a.setState(ArenaState.STARTED);

        a.setType(type);

        a.addPlayer(p1);
        a.addPlayer(p2);

        teleportPlayer(p1, a.getSpawn1());
        teleportPlayer(p2, a.getSpawn2());

        for (UUID id : a.getPlayers()) {
            final Player all = Bukkit.getPlayer(id);
            main.getSpawnManager().managePlayer(all);
            main.getSpawnManager().clearInventory(all);
            all.setWalkSpeed(0.0F);
            freezed.add(id);
            all.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 4 * 20, 150));
            MessageManager.sendMessage(all, ("&aThe match will begin in 3 seconds. Pot up!"));

            if (a.getType() == ArenaType.RANKED_PROTECTIONII || a.getType() == ArenaType.UNRANKED_PROTECTIONII) {
                new BukkitRunnable() {
                    public void run() {
                        if (km.hasMchcfKit(all)) {
                            if (!ffa.getmchcfPickedKitList().contains(all.getUniqueId())) {
                                ffa.getmchcfPickedKitList().add(all.getUniqueId());
                            }
                            im.showProtectionIILoadMenu(all);
                        } else {
                            MessageManager.sendMessage(all, "&cNo kit found! &eDefault Protection II kit loaded!");
                            km.giveDefaultMchcfKit(all);
                        }
                    }
                }.runTaskLater(main, 5);

                all.setHealth(20.0);
                all.setFoodLevel(20);
            }

            if (a.getType() == ArenaType.RANKED_NOENCHANTMENT || a.getType() == ArenaType.UNRANKED_NOENCHANTMENT) {
                new BukkitRunnable() {
                    public void run() {
                        if (km.hasNoEnchantmentKit(all)) {
                            if (!ffa.getNoEnchPickedKitList().contains(all.getUniqueId())) {
                                ffa.getNoEnchPickedKitList().add(all.getUniqueId());
                            }
                            im.showNoEnchantmentLoadMenu(all);
                        } else {
                            MessageManager.sendMessage(all, "&cNo kit found! &eDefault No Enchantment kit loaded!");
                            km.giveDefaultNoEnchantmentKit(all);
                        }
                    }
                }.runTaskLater(main, 10);

                all.setHealth(20.0);
                all.setFoodLevel(20);
            }
        }

        new BukkitRunnable() {
            public void run() {
                for (UUID id : a.getPlayers()) {
                    Player all = Bukkit.getPlayer(id);
                    MessageManager.sendMessage(all, "&c&l\u2462");
                    all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                }
            }
        }.runTaskLater(main, 20);

        new BukkitRunnable() {
            public void run() {
                for (UUID id : a.getPlayers()) {
                    Player all = Bukkit.getPlayer(id);
                    MessageManager.sendMessage(all, "&6&l\u2461");
                    all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                }
            }
        }.runTaskLater(main, 2 * 20);

        new BukkitRunnable() {
            public void run() {
                for (UUID id : a.getPlayers()) {
                    Player all = Bukkit.getPlayer(id);
                    MessageManager.sendMessage(all, "&e&l\u2460");
                    all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                }
            }
        }.runTaskLater(main, 3 * 20);

        new BukkitRunnable() {
            public void run() {
                for (UUID id : a.getPlayers()) {
                    Player all = Bukkit.getPlayer(id);
                    MessageManager.sendMessage(all, "&a&lGO!");
                    all.playSound(all.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                    all.setWalkSpeed(0.2F);
                    freezed.remove(all.getUniqueId());
                }
            }
        }.runTaskLater(main, 4*20);
    }

    public void endSingleDuel(final Arena a, final UUID winner, UUID looser) {
        if (a.isOver()) {
            Profile wprof = pm.getProfile(winner);
            Profile lprof = pm.getProfile(looser);

            if (a.getType() == ArenaType.UNRANKED_PROTECTIONII || a.getType() == ArenaType.UNRANKED_NOENCHANTMENT) {
                MessageManager.sendMessage(wprof, "&eYou have won the match.");
                MessageManager.sendMessage(lprof, "&eYou have lost the match.");
            }
            if (a.getType() == ArenaType.RANKED_PROTECTIONII) {
                int oldelow = wprof.getProtIIElo();
                int oldelol = lprof.getProtIIElo();
                int[] new_elo = EbolaCalc.getNewRankings(wprof.getProtIIElo(), lprof.getProtIIElo(), true);
                wprof.setProtIIElo(new_elo[0]);
                lprof.setProtIIElo(new_elo[1]);

                MessageManager.sendMessage(wprof, "&eYou have won the match. &a+" + String.valueOf(wprof.getProtIIElo() - oldelow).replace("-", "") + " &6Elo (&e" + wprof.getProtIIElo() + "&6)");
                MessageManager.sendMessage(lprof, "&eYou have lost the match. &c-" + String.valueOf(lprof.getProtIIElo() - oldelol).replace("-", "") + " &6Elo (&e" + lprof.getProtIIElo() + "&6)");
            }
            if (a.getType() == ArenaType.RANKED_NOENCHANTMENT) {
                int oldelow = wprof.getNoEnchElo();
                int oldelol = lprof.getNoEnchElo();
                int[] new_elo = EbolaCalc.getNewRankings(wprof.getNoEnchElo(), lprof.getNoEnchElo(), true);
                wprof.setNoEnchElo(new_elo[0]);
                lprof.setNoEnchElo(new_elo[1]);

                MessageManager.sendMessage(wprof, "&eYou have won the match. &a+" + String.valueOf(wprof.getNoEnchElo() - oldelow).replace("-", "") + " &6Elo (&e" + wprof.getNoEnchElo() + "&6)");
                MessageManager.sendMessage(lprof, "&eYou have lost the match. &c-" + String.valueOf(lprof.getNoEnchElo() - oldelol).replace("-", "") + " &6Elo (&e" + lprof.getNoEnchElo() + "&6)");
            }
//
//            FancyMessage w1 = new FancyMessage("View your inventory. (")
//                    .color(ChatColor.GOLD)
//                    .then("Click Here")
//                    .color(ChatColor.YELLOW)
//                    .command("/results " + wprof.getUniqueId())
//                    .then(")")
//                    .color(ChatColor.GOLD);
//
//            FancyMessage w2 = new FancyMessage("View opponent's inventory. (")
//                    .color(ChatColor.GOLD)
//                    .then("Click Here")
//                    .color(ChatColor.YELLOW)
//                    .command("/results " + lprof.getUniqueId())
//                    .then(")")
//                    .color(ChatColor.GOLD);
//
//            FancyMessage w3 = new FancyMessage("View your inventory. (")
//                    .color(ChatColor.GOLD)
//                    .then("Click Here")
//                    .color(ChatColor.YELLOW)
//                    .command("/results " + lprof.getUniqueId())
//                    .then(")")
//                    .color(ChatColor.GOLD);
//
//            FancyMessage w4 = new FancyMessage("View opponent's inventory. (")
//                    .color(ChatColor.GOLD)
//                    .then("Click Here")
//                    .color(ChatColor.YELLOW)
//                    .command("/results " + wprof.getUniqueId())
//                    .then(")")
//                    .color(ChatColor.GOLD);
//
//            wprof.sendMessage(w1);
//            wprof.sendMessage(w2);
//            lprof.sendMessage(w3);
//            lprof.sendMessage(w4);
        }

        new BukkitRunnable() {
            public void run() {
                a.getPlayers().clear();
                a.setState(ArenaState.AVAILABLE);
                a.setType(ArenaType.NONE);
                a.setOver(false);

                if (Bukkit.getPlayer(winner) != null) {
                    main.getSpawnManager().setSpawnInventory(Bukkit.getPlayer(winner));
                    teleportPlayer(Bukkit.getPlayer(winner), main.getSpawnManager().getSpawn());
                }
            }
        }.runTaskLater(main, 3 * 20);
    }

    public boolean isInDuel(Player p) {
        for (Arena arena : getArenas()) {
            if (arena.getPlayers().contains(p.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public Arena getPlayerArena(Player p) {
        for (Arena arena : getArenas()) {
            List<UUID> players = arena.getPlayers();
            if (players.contains(p.getUniqueId())) {
                return arena;
            }
        }
        return null;
    }

    public void teleportPlayer(Player p, Location l) {
        if (!l.getWorld().getChunkAt(l).isLoaded()) {
            l.getWorld().getChunkAt(l).load();
        }

        p.teleport(l, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public Arena getRandomArena() {
        Arena a = null;
        while (a == null) {
            for (Arena as : getArenas()) {
                if (as.getPlayers().size() > 0 && as.getState() == ArenaState.STARTED) {
                    continue;
                }
                a = as;
            }
        }
        return a;
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    public List<UUID> getFreezed() {
        return freezed;
    }
}
