package code.young.repulse.objects;

import code.young.repulse.Repulse;
import code.young.repulse.enums.ArenaType;
import code.young.repulse.managers.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class Team {

    private Repulse main = Repulse.getInstance();
    private TeamManager tm = main.getTeamManager();
    private ArenaManager am = main.getArenaManager();
    private QueueManager qm = main.getQueueManager();
    private ProfileManager pm = main.getProfileManager();
    private SpawnManager sm = main.getSpawnManager();

    private UUID owner;
    private List<UUID> members;
    private List<UUID> invitations;
    private HashMap<Team, ArenaType> duelrequests;

    public Team(UUID owner) {
        this.owner = owner;
        this.members = new ArrayList<>();
        this.invitations = new ArrayList<>();
        this.duelrequests = new HashMap<>();
    }

    public boolean isOwner(UUID id) {
        return getOwner().equals(id);
    }

    public void leave(UUID id) {
        Profile p1 = pm.getProfile(id);

        getMembers().remove(id);
        p1.sendMessage("&c&lYou have left your team.");

        for (UUID team : members) {
            Profile p2 = pm.getProfile(team);
            p2.sendMessage("&c&l" + p1.getName() + " left your team.");
        }
    }

    public void joinTeam(UUID id) {
        final Profile p1 = pm.getProfile(id);
        Profile op = pm.getProfile(owner);

        if (getInvitations().contains(p1.getUniqueId())) {
            getInvitations().remove(p1.getUniqueId());
        }

        p1.sendMessage("&aYou have joined " + op.getName() + "'s team.");

        new BukkitRunnable() {
            public void run() {
                tm.setTeamInventory(Bukkit.getPlayer(p1.getUniqueId()));
            }
        }.runTaskLater(main, 5L);

        if (!getMembers().contains(p1.getUniqueId())){
            getMembers().add(p1.getUniqueId());
        }

        for (UUID team : getMembers()) {
            Profile tp = pm.getProfile(team);
            tp.sendMessage("&a" + p1.getName() + " has joined your team.");
        }

        op.sendMessage("&a" + p1.getName() + " has joined your team.");
    }

    public void invitePlayer(UUID id) {
        Profile p1 = pm.getProfile(id);
        Profile po = pm.getProfile(owner);

        if (isOwner(id)) {
            po.sendMessage("&cYou can not invite yourself.");
            return;
        }

        if (getMembers().contains(id)) {
            po.sendMessage("&c" + p1.getName() + " is already in your team.");
            return;
        }

        if (getInvitations().contains(id)) {
            po.sendMessage("&cYou have already invited " + p1.getName() + " to join your team.");
            return;
        }

        if (!(sm.isInSpawn(Bukkit.getPlayer(id)))) {
            po.sendMessage("&c" + p1.getName() + " is not at spawn!");
            return;
        }

        getInvitations().add(p1.getUniqueId());
        p1.sendMessage("&aYou have been invited to join " + po.getName() + "'s team!");

      //  FancyMessage fm = new FancyMessage("Type /accept " + po.getName() + " or click ")
          //      .color(ChatColor.GREEN)
         //       .then("Here")
       //         .color(ChatColor.GREEN)
     //           .style(ChatColor.BOLD, ChatColor.UNDERLINE)
       //         .command("/accept " + po.getName())
     //           .then(" to accept.").color(ChatColor.GREEN);
       // p1.sendMessage(fm);

        for (UUID team : getMembers()) {
            Profile p2 = pm.getProfile(team);
            p2.sendMessage("&a" + p1.getName() + " has been invited to your team.");
        }

        po.sendMessage("&aYou have invited " + p1.getName() + " to join your team.");
    }

    public void kickPlayer(UUID id) {
        Profile p1 = pm.getProfile(id);
        Profile po = pm.getProfile(owner);

        if (!getMembers().contains(id)) {
            po.sendMessage("&c" + p1.getName() + " is not on your team.");
            return;
        }

        p1.sendMessage("&cYou have been kicked from " + po.getName() + "'s team!");
        po.sendMessage("&aYou have kicked " + p1.getName() + " from your team.");
        getMembers().remove(p1.getUniqueId());

        for (UUID team : members) {
            Profile p2 = pm.getProfile(team);
            p2.sendMessage("&a" + po.getName() + " has kicked " + p1.getName() + " from the team.");
        }

        Player p = Bukkit.getPlayer(p1.getUniqueId());
        sm.setSpawnInventory(p);
    }

    public boolean inDuel() {
        return false;
    }

    public UUID getOwner() {
        return owner;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public HashMap<Team, ArenaType> getDuelRequests() { return duelrequests; }

    public List<UUID> getInvitations() {
        return invitations;
    }
}