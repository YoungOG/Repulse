package code.young.repulse.objects;

import code.young.repulse.enums.KitType;

import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class CurrentKit {

    UUID id;
    String name;
    int slot;
    KitType type;
    int kills;
    int deaths;
    int uses;

    public CurrentKit(UUID id, String name, int slot, KitType type, int kills, int deaths, int uses) {
        this. id = id;
        this.name = name;
        this.slot = slot;
        this.type = type;
        this.kills = kills;
        this.deaths = deaths;
        this.uses = uses;
    }

    public UUID getUniqueId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public KitType getType() {
        return type;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getUses() {
        return uses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setType(KitType type) {
        this.type = type;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }
}
