package code.young.repulse.managers;

import code.young.repulse.Repulse;
import code.young.repulse.objects.Profile;
import code.young.repulse.objects.Team;
import code.young.repulse.utils.ItemBuilder;
import code.young.repulse.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class TeamManager {

    private Repulse main = Repulse.getInstance();
    private ArenaManager am = main.getArenaManager();
    private InventoryManager im = main.getInventoryManager();
    private QueueManager qm = main.getQueueManager();
    private ProfileManager pm = main.getProfileManager();
    private SpawnManager sm = main.getSpawnManager();

    private HashSet<Team> teams = new HashSet<>();

    public void createTeam(final UUID owner) {
        Profile op = pm.getProfile(owner);
        for (Team teams : getTeams()) {
            if (teams.getMembers().contains(owner)) {
                return;
            } else if (teams.getOwner() == owner) {
                return;
            }
        }

        op.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have successfully created a new team."));
        Team t = new Team(owner);
        getTeams().add(t);

        new BukkitRunnable() {
            public void run() {
                setTeamInventory(Bukkit.getPlayer(owner));
            }
        }.runTaskLater(main, 5L);
    }

    public void disbandTeam(UUID owner) {
        Profile po = pm.getProfile(owner);
        po.sendMessage("&cYour team has been disbanded!");

        Team t = getTeam(owner);

        for (UUID team : t.getMembers()) {
            if (Bukkit.getPlayer(team) != null) {
                Player p = Bukkit.getPlayer(team);
                sm.setSpawnInventory(p);
                MessageManager.sendMessage(p, "&cYour team has been disbanded.");
            }
        }

        sm.setSpawnInventory(Bukkit.getPlayer(owner));

        getTeams().remove(t);
    }

    public boolean hasTeam(UUID id) {
        for (Team t : getTeams()) {
            if (t.getOwner().equals(id) || t.getMembers().contains(id)) {
                return true;
            }
        }
        return false;
    }

    public Team getTeam(UUID id) {
        for (Team t : getTeams()) {
            if (t.getOwner().equals(id) || t.getMembers().contains(id)) {
                return t;
            }
        }
        return null;
    }

    public void setTeamInventory(Player p) {
        if (sm.isInSpawn(p)) {
            clearInventory(p);
            managePlayer(p);

            if (hasTeam(p.getUniqueId())) {
                Team t = getTeam(p.getUniqueId());
                if (t.isOwner(p.getUniqueId())) {
                    p.getInventory().setItem(0, new ItemBuilder(Material.INK_SACK).durability(1).name("&c&lKick a Team Member").build());
                    p.getInventory().setItem(2, new ItemBuilder(Material.REDSTONE).name("&c&lDisband Team").build());
                    p.getInventory().setItem(4, new ItemBuilder(Material.DIAMOND_SWORD).name("&b&lDuel a Team").build());
                    p.getInventory().setItem(6, new ItemBuilder(Material.EMPTY_MAP).name("&a&lView Members").build());
                    p.getInventory().setItem(8, new ItemBuilder(Material.PAPER).name("&a&lInvite a Player to your Team").build());
                } else {
                    p.getInventory().setItem(0, new ItemBuilder(Material.INK_SACK).durability(1).name("&c&lLeave Team").build());
                    p.getInventory().setItem(4, new ItemBuilder(Material.DIAMOND_SWORD).name("&b&lRequest A Duel").build());
                    p.getInventory().setItem(8, new ItemBuilder(Material.PAPER).name("&a&lView Members").build());
                }
            }
        }
        p.updateInventory();
    }

    public void clearInventory(Player p) {
        PlayerInventory inv = p.getInventory();

        inv.clear();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);

        p.updateInventory();
    }

    public void managePlayer(Player p) {
        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }

        p.setHealth(20.0);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.setFallDistance(0.0F);
        p.setWalkSpeed(0.2F);
        p.setGameMode(GameMode.SURVIVAL);
        p.setExp(0.0F);
        p.setLevel(0);
    }

    public HashSet<Team> getTeams() {
        return teams;
    }
}
