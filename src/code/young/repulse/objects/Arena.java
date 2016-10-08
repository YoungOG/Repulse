package code.young.repulse.objects;

import code.young.repulse.enums.ArenaState;
import code.young.repulse.enums.ArenaType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class Arena {

    private String id;
    private Location pos1;
    private Location pos2;
    private Location spawn1;
    private Location spawn2;
    private List<UUID> players;
    private ArenaState state;
    private Boolean isOver;
    private ArenaType type;

    public Arena(String id, Location pos1, Location pos2, Location spawn1, Location spawn2) {
        this.id = id;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.players = new ArrayList<>();
        this.state = ArenaState.AVAILABLE;
        this.spawn1 = spawn1;
        this.spawn2 = spawn2;
        this.isOver = false;
        this.type = ArenaType.NONE;
    }

    public Arena(String id, Location pos1, Location pos2) {
        this.id = id;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.players = new ArrayList<>();
        this.state = ArenaState.AVAILABLE;
        this.spawn1 = null;
        this.spawn2 = null;
        this.isOver = false;
        this.type = ArenaType.NONE;
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

    public ArenaState getState() {
        return state;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public void removePlayer(Player p) {
        players.remove(p.getUniqueId());
    }

    public void addPlayer(Player p) {
        players.add(p.getUniqueId());
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public Location getSpawn1() {
        return spawn1;
    }

    public void setSpawn1(Location spawn1) {
        this.spawn1 = spawn1;
    }

    public Location getSpawn2() {
        return spawn2;
    }

    public void setSpawn2(Location spawn2) {
        this.spawn2 = spawn2;
    }

    public Boolean isOver() {
        return isOver;
    }

    public void setOver(Boolean isOver) {
        this.isOver = isOver;
    }

    public ArenaType getType() {
        return type;
    }

    public void setType(ArenaType type) {
        this.type = type;
    }
}
