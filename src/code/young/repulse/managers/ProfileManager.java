package code.young.repulse.managers;

import code.young.repulse.Repulse;
import code.young.repulse.objects.CurrentKit;
import code.young.repulse.objects.Kit;
import code.young.repulse.objects.Profile;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class ProfileManager {

    private Repulse main = Repulse.getInstance();
    private HashSet<Profile> loadedProfiles = new HashSet<Profile>();
    private DBCollection pCollection = main.getProfileCollection();

    public void loadProfiles() {
        DBCursor dbc = pCollection.find();

        Bukkit.getLogger().log(Level.INFO, "[Repulse]: Loading " + dbc.count() + " profiles.");

        while (dbc.hasNext()) {
            BasicDBObject dbo = (BasicDBObject) dbc.next();

            UUID id = UUID.fromString(dbo.getString("id"));
            String name = dbo.getString("name");
            int kills = dbo.getInt("kills");
            int deaths = dbo.getInt("deaths");
            int protIIElo = dbo.getInt("protIIElo");
            int noEnchElo = dbo.getInt("noEnchElo");
            int topks = dbo.getInt("topks");
            int currentks = dbo.getInt("currentks");
            boolean acceptMessages = dbo.getBoolean("acceptMessages");
            boolean acceptDuels = dbo.getBoolean("acceptDuels");
            boolean playerVisiblity = dbo.getBoolean("playerVisiblity");
            boolean customSounds = dbo.getBoolean("customSounds");
            boolean acceptTeams = dbo.getBoolean("acceptTeams");
            Kit MchcfKit1;
            Kit MchcfKit2;
            Kit MchcfKit3;
            Kit MchcfKit4;
            Kit MchcfKit5;
            Kit noEnchantmentKit1;
            Kit noEnchantmentKit2;
            Kit noEnchantmentKit3;
            Kit noEnchantmentKit4;
            Kit noEnchantmentKit5;

            if (dbo.get("MchcfKit1") != null) {
                MchcfKit1 = new Gson().fromJson(dbo.getString("MchcfKit1"), Kit.class);
            } else {
                MchcfKit1 = null;
            }
            if (dbo.get("MchcfKit2") != null) {
                MchcfKit2 = new Gson().fromJson(dbo.getString("MchcfKit2"), Kit.class);
            } else {
                MchcfKit2 = null;
            }
            if (dbo.get("MchcfKit3") != null) {
                MchcfKit3 = new Gson().fromJson(dbo.getString("MchcfKit3"), Kit.class);
            } else {
                MchcfKit3 = null;
            }
            if (dbo.get("MchcfKit4") != null) {
                MchcfKit4 = new Gson().fromJson(dbo.getString("MchcfKit4"), Kit.class);
            } else {
                MchcfKit4 = null;
            }
            if (dbo.get("MchcfKit5") != null) {
                MchcfKit5 = new Gson().fromJson(dbo.getString("MchcfKit5"), Kit.class);
            } else {
                MchcfKit5 = null;
            }

            if (dbo.get("noEnchantmentKit1") != null) {
                noEnchantmentKit1 = new Gson().fromJson(dbo.getString("noEnchantmentKit1"), Kit.class);
            } else {
                noEnchantmentKit1 = null;
            }
            if (dbo.get("noEnchantmentKit2") != null) {
                noEnchantmentKit2 = new Gson().fromJson(dbo.getString("noEnchantmentKit2"), Kit.class);
            } else {
                noEnchantmentKit2 = null;
            }
            if (dbo.get("noEnchantmentKit3") != null) {
                noEnchantmentKit3 = new Gson().fromJson(dbo.getString("noEnchantmentKit3"), Kit.class);
            } else {
                noEnchantmentKit3 = null;
            }
            if (dbo.get("noEnchantmentKit4") != null) {
                noEnchantmentKit4 = new Gson().fromJson(dbo.getString("noEnchantmentKit4"), Kit.class);
            } else {
                noEnchantmentKit4 = null;
            }
            if (dbo.get("noEnchantmentKit5") != null) {
                noEnchantmentKit5 = new Gson().fromJson(dbo.getString("noEnchantmentKit5"), Kit.class);
            } else {
                noEnchantmentKit5 = null;
            }

            Profile prof = new Profile(id, name, kills, deaths, protIIElo, noEnchElo, topks, currentks, acceptMessages, acceptDuels, playerVisiblity, customSounds, acceptTeams, null, MchcfKit1, MchcfKit2, MchcfKit3, MchcfKit4, MchcfKit5, noEnchantmentKit1, noEnchantmentKit2, noEnchantmentKit3, noEnchantmentKit4, noEnchantmentKit5);

            loadedProfiles.add(prof);
        }

        Bukkit.getLogger().log(Level.INFO, "[Repulse]: Successfully loaded " + dbc.count() + " profiles.");
    }

    public void saveProfiles() {
        Bukkit.getLogger().log(Level.INFO, "[Repulse]: Saving " + getLoadedProfiles().size() + " profiles.");

        for (Profile prof : getLoadedProfiles()) {
            if (prof.hasCurrentKit()) {
                prof.saveKit(prof.getCurrentKit());
            }
            DBCursor dbc = pCollection.find(new BasicDBObject("id", prof.getUniqueId().toString()));
            BasicDBObject dbo = new BasicDBObject("id", prof.getUniqueId().toString());
            dbo.put("name", prof.getName());
            dbo.put("kills", prof.getKills());
            dbo.put("deaths", prof.getDeaths());
            dbo.put("protIIElo", prof.getProtIIElo());
            dbo.put("noEnchElo", prof.getNoEnchElo());
            dbo.put("topks", prof.getTopKS());
            dbo.put("currentks", prof.getCurrentKS());
            dbo.put("acceptMessages", prof.hasMessagesDisabled());
            dbo.put("acceptDuels", prof.isAcceptingDuels());
            dbo.put("playerVisibility", prof.hasVisibilityToggled());
            dbo.put("customSounds", prof.hasCustomSoundsToggled());
            dbo.put("acceptTeams", prof.isAcceptingTeams());

            if (prof.getMchcfKit(1) != null) {
                dbo.put("MchcfKit1", new Gson().toJson(prof.getMchcfKit(1)));
            }
            if (prof.getMchcfKit(2) != null) {
                dbo.put("MchcfKit2", new Gson().toJson(prof.getMchcfKit(2)));
            }
            if (prof.getMchcfKit(3) != null) {
                dbo.put("MchcfKit3", new Gson().toJson(prof.getMchcfKit(3)));
            }
            if (prof.getMchcfKit(4) != null) {
                dbo.put("MchcfKit4", new Gson().toJson(prof.getMchcfKit(4)));
            }
            if (prof.getMchcfKit(5) != null) {
                dbo.put("MchcfKit5", new Gson().toJson(prof.getMchcfKit(5)));
            }
            if (prof.getNoEnchantmentKit(1) != null) {
                dbo.put("noEnchantmentKit1", new Gson().toJson(prof.getNoEnchantmentKit(1)));
            }
            if (prof.getNoEnchantmentKit(2) != null) {
                dbo.put("noEnchantmentKit2", new Gson().toJson(prof.getNoEnchantmentKit(2)));
            }
            if (prof.getNoEnchantmentKit(3) != null) {
                dbo.put("noEnchantmentKit3", new Gson().toJson(prof.getNoEnchantmentKit(3)));
            }
            if (prof.getNoEnchantmentKit(4) != null) {
                dbo.put("noEnchantmentKit4", new Gson().toJson(prof.getNoEnchantmentKit(4)));
            }
            if (prof.getNoEnchantmentKit(5) != null) {
                dbo.put("noEnchantmentKit5", new Gson().toJson(prof.getNoEnchantmentKit(5)));
            }
            if (dbc.hasNext()) {
                pCollection.update(dbc.getQuery(), dbo);
            } else {
                pCollection.insert(dbo);
            }
        }

        Bukkit.getLogger().log(Level.INFO, "[Repulse]: Successfully saved " + getLoadedProfiles().size() + " profiles.");
    }

    public void createProfile(Player p) {
        if (!hasProfile(p.getUniqueId())) {
            Profile prof = new Profile(p.getUniqueId(), p.getName(), 0, 0, 1000, 1000, 0, 0, true, true, true, true, true, null, null, null, null, null, null, null, null, null, null, null);

            BasicDBObject dbo = new BasicDBObject("id", prof.getUniqueId().toString());
            dbo.put("name", prof.getName());
            dbo.put("kills", prof.getKills());
            dbo.put("deaths", prof.getDeaths());
            dbo.put("protIIElo", prof.getProtIIElo());
            dbo.put("noEnchElo", prof.getNoEnchElo());
            dbo.put("topks", prof.getTopKS());
            dbo.put("currentks", prof.getCurrentKS());
            dbo.put("acceptMessages", prof.hasMessagesDisabled());
            dbo.put("acceptDuels", prof.isAcceptingDuels());
            dbo.put("playerVisibility", prof.hasVisibilityToggled());
            dbo.put("customSounds", prof.hasCustomSoundsToggled());
            dbo.put("acceptTeams", prof.isAcceptingTeams());
            if (prof.getMchcfKit(1) != null) {
                dbo.put("MchcfKit1", new Gson().toJson(prof.getMchcfKit(1)));
            }
            if (prof.getMchcfKit(2) != null) {
                dbo.put("MchcfKit2", new Gson().toJson(prof.getMchcfKit(2)));
            }
            if (prof.getMchcfKit(3) != null) {
                dbo.put("MchcfKit3", new Gson().toJson(prof.getMchcfKit(3)));
            }
            if (prof.getMchcfKit(4) != null) {
                dbo.put("MchcfKit4", new Gson().toJson(prof.getMchcfKit(4)));
            }
            if (prof.getMchcfKit(5) != null) {
                dbo.put("MchcfKit5", new Gson().toJson(prof.getMchcfKit(5)));
            }
            if (prof.getNoEnchantmentKit(1) != null) {
                dbo.put("noEnchantmentKit1", new Gson().toJson(prof.getNoEnchantmentKit(1)));
            }
            if (prof.getNoEnchantmentKit(2) != null) {
                dbo.put("noEnchantmentKit2", new Gson().toJson(prof.getNoEnchantmentKit(2)));
            }
            if (prof.getNoEnchantmentKit(3) != null) {
                dbo.put("noEnchantmentKit3", new Gson().toJson(prof.getNoEnchantmentKit(3)));
            }
            if (prof.getNoEnchantmentKit(4) != null) {
                dbo.put("noEnchantmentKit4", new Gson().toJson(prof.getNoEnchantmentKit(4)));
            }
            if (prof.getNoEnchantmentKit(5) != null) {
                dbo.put("noEnchantmentKit5", new Gson().toJson(prof.getNoEnchantmentKit(5)));
            }

            pCollection.insert(dbo);

            getLoadedProfiles().add(prof);
        }
    }

    public void reloadProfile(final Profile prof) {
        saveProfile(prof);

        DBCursor dbc = pCollection.find(new BasicDBObject("id", prof.getUniqueId().toString()));

        Profile newProf = null;

        if (dbc.hasNext()) {
            BasicDBObject dbo = (BasicDBObject) dbc.next();

            UUID id = UUID.fromString(dbo.getString("id"));
            String name = dbo.getString("name");
            int kills = dbo.getInt("kills");
            int deaths = dbo.getInt("deaths");
            int protIIElo = dbo.getInt("protIIElo");
            int noEnchElo = dbo.getInt("noEnchElo");
            int topks = dbo.getInt("topks");
            int currentks = dbo.getInt("currentks");
            CurrentKit currentKit = prof.getCurrentKit();
            boolean acceptMessages = dbo.getBoolean("acceptMessages");
            boolean acceptDuels = dbo.getBoolean("acceptDuels");
            boolean playerVisiblity = dbo.getBoolean("playerVisiblity");
            boolean customSounds = dbo.getBoolean("customSounds");
            boolean acceptTeams = dbo.getBoolean("acceptTeams");
            Kit MchcfKit1;
            Kit MchcfKit2;
            Kit MchcfKit3;
            Kit MchcfKit4;
            Kit MchcfKit5;
            Kit noEnchantmentKit1;
            Kit noEnchantmentKit2;
            Kit noEnchantmentKit3;
            Kit noEnchantmentKit4;
            Kit noEnchantmentKit5;

            if (dbo.get("MchcfKit1") != null) {
                MchcfKit1 = new Gson().fromJson(dbo.getString("MchcfKit1"), Kit.class);
            } else {
                MchcfKit1 = null;
            }
            if (dbo.get("MchcfKit2") != null) {
                MchcfKit2 = new Gson().fromJson(dbo.getString("MchcfKit2"), Kit.class);
            } else {
                MchcfKit2 = null;
            }
            if (dbo.get("MchcfKit3") != null) {
                MchcfKit3 = new Gson().fromJson(dbo.getString("MchcfKit3"), Kit.class);
            } else {
                MchcfKit3 = null;
            }
            if (dbo.get("MchcfKit4") != null) {
                MchcfKit4 = new Gson().fromJson(dbo.getString("MchcfKit4"), Kit.class);
            } else {
                MchcfKit4 = null;
            }
            if (dbo.get("MchcfKit5") != null) {
                MchcfKit5 = new Gson().fromJson(dbo.getString("MchcfKit5"), Kit.class);
            } else {
                MchcfKit5 = null;
            }

            if (dbo.get("noEnchantmentKit1") != null) {
                noEnchantmentKit1 = new Gson().fromJson(dbo.getString("noEnchantmentKit1"), Kit.class);
            } else {
                noEnchantmentKit1 = null;
            }
            if (dbo.get("noEnchantmentKit2") != null) {
                noEnchantmentKit2 = new Gson().fromJson(dbo.getString("noEnchantmentKit2"), Kit.class);
            } else {
                noEnchantmentKit2 = null;
            }
            if (dbo.get("noEnchantmentKit3") != null) {
                noEnchantmentKit3 = new Gson().fromJson(dbo.getString("noEnchantmentKit3"), Kit.class);
            } else {
                noEnchantmentKit3 = null;
            }
            if (dbo.get("noEnchantmentKit4") != null) {
                noEnchantmentKit4 = new Gson().fromJson(dbo.getString("noEnchantmentKit4"), Kit.class);
            } else {
                noEnchantmentKit4 = null;
            }
            if (dbo.get("noEnchantmentKit5") != null) {
                noEnchantmentKit5 = new Gson().fromJson(dbo.getString("noEnchantmentKit5"), Kit.class);
            } else {
                noEnchantmentKit5 = null;
            }

            newProf = new Profile(id, name, kills, deaths, protIIElo, noEnchElo, topks, currentks, acceptMessages, acceptDuels, playerVisiblity, customSounds, acceptTeams, currentKit, MchcfKit1, MchcfKit2, MchcfKit3, MchcfKit4, MchcfKit5, noEnchantmentKit1, noEnchantmentKit2, noEnchantmentKit3, noEnchantmentKit4, noEnchantmentKit5);
        }

        getLoadedProfiles().add(newProf);
        getLoadedProfiles().remove(prof);
    }

    public void saveProfile(Profile prof) {
        DBCursor dbc = pCollection.find(new BasicDBObject("id", prof.getUniqueId().toString()));

        BasicDBObject dbo = new BasicDBObject("id", prof.getUniqueId().toString());
        dbo.put("name", prof.getName());
        dbo.put("kills", prof.getKills());
        dbo.put("deaths", prof.getDeaths());
        dbo.put("protIIElo", prof.getProtIIElo());
        dbo.put("noEnchElo", prof.getNoEnchElo());
        dbo.put("topks", prof.getTopKS());
        dbo.put("currentks", prof.getCurrentKS());
        dbo.put("acceptMessages", prof.hasMessagesDisabled());
        dbo.put("acceptDuels", prof.isAcceptingDuels());
        dbo.put("playerVisibility", prof.hasVisibilityToggled());
        dbo.put("customSounds", prof.hasCustomSoundsToggled());
        dbo.put("acceptTeams", prof.isAcceptingTeams());
        if (prof.getMchcfKit(1) != null) {
            dbo.put("MchcfKit1", new Gson().toJson(prof.getMchcfKit(1)));
        }
        if (prof.getMchcfKit(2) != null) {
            dbo.put("MchcfKit2", new Gson().toJson(prof.getMchcfKit(2)));
        }
        if (prof.getMchcfKit(3) != null) {
            dbo.put("MchcfKit3", new Gson().toJson(prof.getMchcfKit(3)));
        }
        if (prof.getMchcfKit(4) != null) {
            dbo.put("MchcfKit4", new Gson().toJson(prof.getMchcfKit(4)));
        }
        if (prof.getMchcfKit(5) != null) {
            dbo.put("MchcfKit5", new Gson().toJson(prof.getMchcfKit(5)));
        }
        if (prof.getNoEnchantmentKit(1) != null) {
            dbo.put("noEnchantmentKit1", new Gson().toJson(prof.getNoEnchantmentKit(1)));
        }
        if (prof.getNoEnchantmentKit(2) != null) {
            dbo.put("noEnchantmentKit2", new Gson().toJson(prof.getNoEnchantmentKit(2)));
        }
        if (prof.getNoEnchantmentKit(3) != null) {
            dbo.put("noEnchantmentKit3", new Gson().toJson(prof.getNoEnchantmentKit(3)));
        }
        if (prof.getNoEnchantmentKit(4) != null) {
            dbo.put("noEnchantmentKit4", new Gson().toJson(prof.getNoEnchantmentKit(4)));
        }
        if (prof.getNoEnchantmentKit(5) != null) {
            dbo.put("noEnchantmentKit5", new Gson().toJson(prof.getNoEnchantmentKit(5)));
        }

        if (dbc.hasNext()) {
            pCollection.update(dbc.getQuery(), dbo);
        } else {
            pCollection.insert(dbo);
        }
    }

    public Boolean hasProfile(UUID id) {
        for (Profile prof : getLoadedProfiles()) {
            if (prof.getUniqueId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Profile getProfile(UUID id) {
        for (Profile prof : getLoadedProfiles()) {
            if (prof.getUniqueId().equals(id)) {
                return prof;
            }
        }
        return null;
    }

    public HashSet<Profile> getLoadedProfiles() {
        return loadedProfiles;
    }
}
