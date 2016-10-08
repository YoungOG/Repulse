package code.young.repulse.listeners;

import code.young.repulse.Repulse;
import code.young.repulse.managers.TeamManager;
import code.young.repulse.objects.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Calvin on 2/26/2015.
 * Project: PracticePots
 *
 */
public class TeamEvents implements Listener {
    
    private Repulse main = Repulse.getInstance();
    private TeamManager tm = main.getTeamManager();
    
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        
        if (tm.hasTeam(p.getUniqueId())) {
            Team t = tm.getTeam(p.getUniqueId());
            
            if (t.isOwner(p.getUniqueId())) {
                tm.disbandTeam(p.getUniqueId());
            } else {
                t.leave(p.getUniqueId());
            }
        }
    }
}
