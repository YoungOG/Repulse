package code.young.repulse.listeners;

import code.young.repulse.Repulse;
import code.young.repulse.managers.InventoryManager;
import code.young.repulse.managers.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Myles on 01/03/2015.
 */
public class ItemFrameListener implements Listener {

    private Repulse main = Repulse.getInstance();
    private InventoryManager im = main.getInventoryManager();
    private SpawnManager sm = main.getSpawnManager();

    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity frame = e.getRightClicked();

        Location createProtII = new Location(Bukkit.getWorld("world"), 1.5, 146.5, 9.9375);
        Location createNoEnchant = new Location(Bukkit.getWorld("world"), -1.5, 146.5, 9.9375);
        Location saveProtII = new Location(Bukkit.getWorld("world"), 1.5, 145.5, 9.9375);
        Location saveNoEnchant = new Location(Bukkit.getWorld("world"), -1.5, 145.5, 9.9375);
        Location configProtII = new Location(Bukkit.getWorld("world"), 2.5, 145.5, 8.9375);
        Location configNoEnch = new Location(Bukkit.getWorld("world"), -2.5, 145.5, 8.9375);
        Location clearinv = new Location(Bukkit.getWorld("world"), 0.5, 147.5, 8.9375);
        Location clearinv1 = new Location(Bukkit.getWorld("world"), -0.5, 147.5, 8.9375);
        Location renameProtII = new Location(Bukkit.getWorld("world"), 0.5, 145.5, 9.9375);
        Location renameNoEnch = new Location(Bukkit.getWorld("world"), -0.5, 145.5, 9.9375);
        if (frame instanceof ItemFrame) {
            e.setCancelled(true);
            if (sm.isInKitArea(p)) {
                if (frame.getLocation().equals(createProtII)) {
                    im.showProtectionIIMenu(p);
                } else if (frame.getLocation().equals(createNoEnchant)) {
                    im.showNoEnchantmentMenu(p);
                } else if (frame.getLocation().equals(saveProtII)) {
                    im.showProtectionIISaveMenu(p);
                } else if (frame.getLocation().equals(saveNoEnchant)) {
                    im.showNoEnchantmentSaveMenu(p);
                } else if (frame.getLocation().equals(configProtII)) {
                    im.showConfigProtectionIIMenu(p);
                } else if (frame.getLocation().equals(configNoEnch)) {
                    im.showConfigNoEnchantmentMenu(p);
                } else if (frame.getLocation().equals(clearinv)) {
                    sm.clearInventory(p);
                } else if (frame.getLocation().equals(clearinv1)) {
                    sm.clearInventory(p);
                } else if (frame.getLocation().equals(renameProtII)) {
                    im.showProtectionIIRenameMenu(p);
                }
                else if (frame.getLocation().equals(renameNoEnch)) {
                    im.showNoEnchantmentRenameMenu(p);
                }
            }
        }
    }
}
