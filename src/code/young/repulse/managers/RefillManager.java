package code.young.repulse.managers;

import code.BreakMC.commons.util.LocationUtility;
import code.young.repulse.Repulse;
import code.young.repulse.objects.RefillZone;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class RefillManager {

    private ArrayList<RefillZone> refillZones = new ArrayList<>();
    private HashMap<UUID, Integer> refills = new HashMap<>();
    private DBCollection rCollection = Repulse.getInstance().getRefillCollection();

    public void loadRefillZones() {
        DBCursor dbc = rCollection.find();

        System.out.println("[Repulse]: Preparing to load " + dbc.count() + " Refill Zones.");

        while (dbc.hasNext()) {
            BasicDBObject dbo = (BasicDBObject) dbc.next();

            String id = dbo.getString("id");
            Location pos1 = LocationUtility.deserializeLocation(dbo.getString("pos1"));
            Location pos2 = LocationUtility.deserializeLocation(dbo.getString("pos2"));

            RefillZone rfz = new RefillZone(id, pos1, pos2);

            System.out.println("[Repulse]: RefillZone: " + rfz.getId() + " has been successfully loaded.");

            getRefillZones().add(rfz);
        }
    }

    public void saveRefillZone(RefillZone rfz) {
        DBCursor dbc = rCollection.find(new BasicDBObject("id", rfz.getId()));

        BasicDBObject dbo = new BasicDBObject("id", rfz.getId());
        dbo.put("pos1", LocationUtility.serializeLocation(rfz.getPos1()));
        dbo.put("pos2", LocationUtility.serializeLocation(rfz.getPos2()));

        if (dbc.hasNext()) {
            rCollection.update(dbc.getQuery(), dbo);
        } else {
            rCollection.insert(dbo);
        }
    }

    public void removeRefillZone(RefillZone rfz) {
        DBCursor dbc = rCollection.find(new BasicDBObject("id", rfz.getId()));

        BasicDBObject dbo = new BasicDBObject("id", rfz.getId());

        if (dbc.hasNext()) {
            rCollection.remove(dbo);
        }

        getRefillZones().remove(rfz);
    }

    public boolean isRefilling(Player p) {
        for (RefillZone rz : getRefillZones()) {
            if (rz.getPlayers().contains(p.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public Integer getRemainingRefills(UUID id) {
        return getRefills().get(id);
    }

    public void setRemainingRefills(UUID id, Integer amount) {
        getRefills().put(id, amount);
    }

    public List<RefillZone> getRefillZones() {
        return refillZones;
    }

    public HashMap<UUID, Integer> getRefills() {
        return refills;
    }
}
