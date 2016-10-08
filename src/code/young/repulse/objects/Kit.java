package code.young.repulse.objects;

import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class Kit {

    UUID id;
    String inventory;
    String name;
    int kills;
    int deaths;
    int uses;

    public Kit (UUID id, String inventory, String name, int kills, int deaths, int uses) {
        this. id = id;
        this.inventory = inventory;
        this.name = name;
        this.kills = kills;
        this.deaths = deaths;
        this.uses = uses;
    }

    public UUID getUniqueId() {
        return id;
    }

    public String getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
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

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public void setName(String name) {
        this.name = name;
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
