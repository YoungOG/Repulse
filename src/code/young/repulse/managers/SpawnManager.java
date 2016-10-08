package code.young.repulse.managers;

import code.young.repulse.Repulse;
import code.young.repulse.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class SpawnManager {

    private Repulse main = Repulse.getInstance();
    private TeamManager tm = main.getTeamManager();
    private ProfileManager pm = main.getProfileManager();

    private Location spawn = new Location(Bukkit.getWorld("world"), -0.0, 149.0, -3.0);
    private Location spawnC1 = new Location(Bukkit.getWorld("world"), -128.5, 0.0, 147.5);
    private Location spawnC2 = new Location(Bukkit.getWorld("world"), 120.5, 256.0, -164.5);

    private Location kitAreaC1 = new Location(Bukkit.getWorld("world"), -4.5, 149.0, 2.5);
    private Location kitAreaC2 = new Location(Bukkit.getWorld("world"), 5.5, 144.0, 10.5);

    private ArrayList<UUID> inKitSelection = new ArrayList<UUID>();

    public void setSpawnInventory(Player p) {
        clearInventory(p);
        managePlayer(p);

        ItemStack guide = new ItemBuilder(Material.WRITTEN_BOOK).addLore("&d&lPracticePots Guide").addLore("&cWe STRONGLY recommend reading").addLore("&cthis before you play.").build();
        BookMeta bm = (BookMeta) guide.getItemMeta();

        bm.setAuthor("&6&lPracticePots");

        List<String> pages = Arrays.asList("Coming Soon"); //MY NIGGA USED CHATCOLOR

        bm.setPages(pages);

        guide.setItemMeta(bm);

        p.getInventory().setItem(0, guide);

        p.getInventory().setItem(2, new ItemBuilder(Material.SKULL_ITEM).durability(3).name("&a&lTeams &7&l(&c&lCOMING IN FULL RELEASE&7&l)").build());

        p.getInventory().setItem(4, new ItemBuilder(Material.DOUBLE_PLANT).name("&7&lSettings").addLore("&bChange preferences such").addLore("&bas Player Visibility, Messaging,").addLore("&band more!").build());

        p.getInventory().setItem(7, new ItemBuilder(Material.INK_SACK).durability(8).name("&c&lUnranked Queue").addLore("&bJoin for quick, unranked, PvP fun!").build());
        p.getInventory().setItem(8, new ItemBuilder(Material.INK_SACK).durability(10).name("&c&lRanked Queue").addLore("&bJoin for competitive, ranked, PvP battles!").build());

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

    public Location getSpawn() {
        return spawn;
    }

    public List<UUID> getInKitSelection() {
        return inKitSelection;
    }

    public boolean isInSpawn(Player p) {
        int x1 = Math.min(spawnC1.getBlockX(), spawnC2.getBlockX());
        int z1 = Math.min(spawnC1.getBlockZ(), spawnC2.getBlockZ());
        int x2 = Math.max(spawnC1.getBlockX(), spawnC2.getBlockX());
        int z2 = Math.max(spawnC1.getBlockZ(), spawnC2.getBlockZ());

        return p.getLocation().getX() >= x1 && p.getLocation().getX() <= x2 && p.getLocation().getZ() >= z1 && p.getLocation().getZ() <= z2;
    }

    public boolean isInKitArea(Player p) {
        int x1 = Math.min(kitAreaC1.getBlockX(), kitAreaC2.getBlockX());
        int y1 = Math.min(kitAreaC1.getBlockY(), kitAreaC2.getBlockY());
        int z1 = Math.min(kitAreaC1.getBlockZ(), kitAreaC2.getBlockZ());
        int x2 = Math.max(kitAreaC1.getBlockX(), kitAreaC2.getBlockX());
        int y2 = Math.max(kitAreaC1.getBlockY(), kitAreaC2.getBlockY());
        int z2 = Math.max(kitAreaC1.getBlockZ(), kitAreaC2.getBlockZ());

        return p.getLocation().getX() >= x1 && p.getLocation().getX() <= x2 && p.getLocation().getY() >= y1 && p.getLocation().getY() <= y2 && p.getLocation().getZ() >= z1 && p.getLocation().getZ() <= z2;
    }
}