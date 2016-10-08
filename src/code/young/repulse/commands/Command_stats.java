package code.young.repulse.commands;

import code.BreakMC.commons.command.BaseCommand;
import code.young.repulse.Repulse;
import code.young.repulse.managers.ProfileManager;
import code.young.repulse.objects.Profile;
import code.young.repulse.utils.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Calvin on 6/2/2015.
 */
public class Command_stats extends BaseCommand {

    private Repulse main = Repulse.getInstance();
    private ProfileManager pm = main.getProfileManager();

    public Command_stats() {
        super("stats", "repulse.stats");
        setUsage("/<command>");
        setMinArgs(0);
        setMaxArgs(1);
    }

    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            Profile prof = pm.getProfile(p.getUniqueId());

            MessageManager.sendMessage(p, "&2&m----------&r &a&lStats&r &2&m----------");
            MessageManager.sendMessage(p, "&2Kills&7: &a" + prof.getKills());
            MessageManager.sendMessage(p, "&2Deaths&7: &a" + prof.getDeaths());
            MessageManager.sendMessage(p, "&2KDR&7: &a" + prof.getKDR());
            MessageManager.sendMessage(p, "&2Current KS&7: &a" + prof.getCurrentKS());
            MessageManager.sendMessage(p, "&2Highest KS&7: &a" + prof.getTopKS());
            MessageManager.sendMessage(p, "&2Protection II Elo&7: &a" + prof.getProtIIElo());
            MessageManager.sendMessage(p, "&2No Enchants Elo&7: &a" + prof.getNoEnchElo());
            MessageManager.sendMessage(p, "&2MC-HCF Elo&7: &a1000");
            MessageManager.sendMessage(p, "&21v1 Wins&7: &a0");
            MessageManager.sendMessage(p, "&2&m--------------------------");
        }

        if (args.length == 1) {
            Profile prof = null;

            for (Profile profs : pm.getLoadedProfiles()) {
                if (profs.getName().equalsIgnoreCase(args[0])) {
                    prof = profs;
                }
            }

            if (prof != null) {
                MessageManager.sendMessage(p, "&2&m----------&r &a&l" + prof.getName() + "'s Stats&r &2&m----------");
                MessageManager.sendMessage(p, "&2Kills&7: &a" + prof.getKills());
                MessageManager.sendMessage(p, "&2Deaths&7: &a" + prof.getDeaths());
                MessageManager.sendMessage(p, "&2KDR&7: &a" + prof.getKDR());
                MessageManager.sendMessage(p, "&2Current KS&7: &a" + prof.getCurrentKS());
                MessageManager.sendMessage(p, "&2Highest KS&7: &a" + prof.getTopKS());
                MessageManager.sendMessage(p, "&2Protection II Elo&7: &a" + prof.getProtIIElo());
                MessageManager.sendMessage(p, "&2No Enchants Elo&7: &a" + prof.getNoEnchElo());
                MessageManager.sendMessage(p, "&2MC-HCF Elo&7: &a1000");
                MessageManager.sendMessage(p, "&21v1 Wins&7: &a0");
                MessageManager.sendMessage(p, "&2&m--------------------------");
            } else {
                MessageManager.sendMessage(p, "&cCould not find a player named: " + args[0]);
            }
        }
    }
}
