package code.young.repulse;

import code.BreakMC.commons.command.Register;
import code.young.repulse.commands.Command_spawn;
import code.young.repulse.listeners.*;
import code.young.repulse.managers.*;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.UnknownHostException;
import java.util.logging.Level;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class Repulse extends JavaPlugin {

    private static Repulse instance;
    private DB db;
    private ProfileManager profileManager;
    private KitManager kitManager;
    private InventoryManager inventoryManager;
    private SpawnManager spawnManager;
    private FFAManager ffaManager;
    private ArenaManager arenaManager;
    private QueueManager queueManager;
    private RefillManager refillManager;
    private TeamManager teamManager;
    private Register commandRegister;

    public void onEnable() {
        instance = this;

        setupDatabase();

        profileManager = new ProfileManager();
        profileManager.loadProfiles();

        kitManager = new KitManager();
        inventoryManager = new InventoryManager();
        spawnManager = new SpawnManager();
        teamManager = new TeamManager();
        ffaManager = new FFAManager();
        arenaManager = new ArenaManager();
        arenaManager.loadArenas();
        refillManager = new RefillManager();
        refillManager.loadRefillZones();
        queueManager = new QueueManager();

        commandRegister = new Register(this);

        getServer().getPluginManager().registerEvents(new ArenaListeners(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
        getServer().getPluginManager().registerEvents(new ItemFrameListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new SpawnEvents(), this);
        getServer().getPluginManager().registerEvents(new StrenghtFix(), this);
        getServer().getPluginManager().registerEvents(new FFAEvents(), this);
        getServer().getPluginManager().registerEvents(new RefillEvents(), this);
        getServer().getPluginManager().registerEvents(new Command_spawn(), this);
        getServer().getPluginManager().registerEvents(new TeamEvents(), this);
    }

    public void onDisable() {
        profileManager.saveProfiles();
    }

    public void setupDatabase() {
        try {
            db = MongoClient.connect(new DBAddress("localhost", "repulse"));
            Bukkit.getLogger().log(Level.INFO, "[Repulse]: Sucessfully connected to MongoDB.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Bukkit.getLogger().log(Level.INFO, "[Repulse]: Failed to connect to MongoDB.");
        }
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }

    public FFAManager getFFAManager() {
        return ffaManager;
    }

    public RefillManager getRefillManager() {
        return refillManager;
    }

    public TeamManager getTeamManager() { return teamManager; }

    public DBCollection getProfileCollection() {
        return db.getCollection("profiles");
    }

    public DBCollection getRefillCollection() {
        return db.getCollection("refills");
    }

    public DBCollection getArenaCollection() {
        return db.getCollection("arenas");
    }

    public static Repulse getInstance() {
        return instance;
    }
}
