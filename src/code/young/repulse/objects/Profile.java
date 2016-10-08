package code.young.repulse.objects;

import code.young.repulse.Repulse;
import code.young.repulse.enums.KitType;
import code.young.repulse.managers.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Calvin on 2/26/2015.
 * Project: Repulse
 *
 */
public class Profile {

    private ProfileManager pm = Repulse.getInstance().getProfileManager();

    UUID id;
    String name;
    int kills;
    int deaths;
    int hcfElo;
    int noEnchElo;
    int topks;
    int currentks;
    CurrentKit currentKit;

    boolean acceptMessages;
    boolean acceptDuels;
    boolean playerVisibility;
    boolean customSounds;
    boolean acceptTeams;

    Kit Mchcf1;
    Kit Mchcf2;
    Kit Mchcf3;
    Kit Mchcf4;
    Kit Mchcf5;

    Kit noEnchantment1;
    Kit noEnchantment2;
    Kit noEnchantment3;
    Kit noEnchantment4;
    Kit noEnchantment5;

    public Profile(
            UUID id,
            String name,
            int kills, int deaths, int hcfElo, int noEnchElo, int topks, int currentks,
            boolean acceptMessages, boolean acceptDuels, boolean playerVisibility, boolean customSounds, boolean acceptTeams,
            CurrentKit currentKit,
            Kit Mchcf1, Kit Mchcf2, Kit Mchcf3, Kit Mchcf4, Kit Mchcf5,
            Kit noEnchantment1, Kit noEnchantment2, Kit noEnchantment3, Kit noEnchantment4, Kit noEnchantment5) {
        this.id = id;
        this.name = name;
        this.kills = kills;
        this.deaths = deaths;
        this.hcfElo = hcfElo;
        this.noEnchElo = noEnchElo;
        this.topks = topks;
        this.currentks = currentks;
        this.currentKit = currentKit;

        this.acceptMessages = acceptMessages;
        this.acceptDuels = acceptDuels;
        this.playerVisibility = playerVisibility;
        this.customSounds = customSounds;
        this.acceptTeams = acceptTeams;

        this.Mchcf1 = Mchcf1;
        this.Mchcf2 = Mchcf2;
        this.Mchcf3 = Mchcf3;
        this.Mchcf4 = Mchcf4;
        this.Mchcf5 = Mchcf5;

        this.noEnchantment1 = noEnchantment1;
        this.noEnchantment2 = noEnchantment2;
        this.noEnchantment3 = noEnchantment3;
        this.noEnchantment4 = noEnchantment4;
        this.noEnchantment5 = noEnchantment5;
    }

    public UUID getUniqueId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getProtIIElo() {
        return hcfElo;
    }

    public void setProtIIElo(int elo) {
        this.hcfElo = elo;
    }

    public int getNoEnchElo() {
        return noEnchElo;
    }

    public void setNoEnchElo(int elo) {
        this.noEnchElo = elo;
    }

    public int getTopKS() {
        return topks;
    }

    public void setTopKS(int topks) {
        this.topks = topks;
    }

    public double getKDR() {
        double kills = getKills();
        double deaths = getDeaths();
        double ratio;

        if (kills == 0.0D && deaths == 0.0D) {
            ratio = 0.0D;
        } else {
            if (kills > 0.0D && deaths == 0.0D) {
                ratio = kills;
            } else {
                if (deaths > 0.0D && kills == 0.0D) {
                    ratio = -deaths;
                } else {
                    ratio = kills / deaths;
                }
            }
        }

        double fratio = Math.round(ratio * 100.0D) / 100.0D;

        return fratio;
    }

    public int getCurrentKS() {
        return currentks;
    }

    public void setCurrentKS(int currentks) {
        this.currentks = currentks;
    }

    public boolean hasMessagesDisabled() {
        return acceptMessages;
    }

    public void setAcceptingMessages(boolean acceptMessages) {
        this.acceptMessages = acceptMessages;
    }

    public boolean isAcceptingDuels() {
        return acceptDuels;
    }

    public void setAcceptingDuels(boolean acceptDuels) {
        this.acceptDuels = acceptDuels;
    }

    public boolean hasVisibilityToggled() {
        return playerVisibility;
    }

    public void setVisibilityToggled(boolean playerVisibility) {
        this.playerVisibility = playerVisibility;
    }

    public boolean hasCustomSoundsToggled() {
        return customSounds;
    }

    public void setCustomSoundsToggled(boolean customSounds) {
        this.customSounds = customSounds;
    }

    public boolean isAcceptingTeams() {
        return acceptTeams;
    }

    public void setAcceptingTeams(boolean acceptTeams) {
        this.acceptTeams = acceptTeams;
    }

    public CurrentKit getCurrentKit() {
        return currentKit;
    }

    public void setCurrentKit(CurrentKit currentKit) {
        this.currentKit = currentKit;
    }

    public boolean hasCurrentKit() {
        return getCurrentKit() != null;
   }

    public void sendMessage(String message) {
        if (Bukkit.getPlayer(getUniqueId()) != null) {
            Player p = Bukkit.getPlayer(getUniqueId());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
   // public void sendMessage(FancyMessage message) {
       // if (Bukkit.getPlayer(getUniqueId()) != null) {
      //      Player p = Bukkit.getPlayer(getUniqueId());
     //       message.send(p);
   //     }
  // }

    public void saveKit(CurrentKit ck) {
        KitType type = ck.getType();
        int slot = ck.getSlot();
        if (type == KitType.PROTECTIONII) {
            if (slot == 1) {
                Kit k = getMchcfKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
                pm.reloadProfile(this);
            } else if (slot == 2) {
                Kit k = getMchcfKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
                pm.reloadProfile(this);
            } else if (slot == 3) {
                Kit k = getMchcfKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
                pm.reloadProfile(this);
            } else if (slot == 4) {
                Kit k = getMchcfKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
                pm.reloadProfile(this);
            } else if (slot == 5) {
                Kit k = getMchcfKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                k.setUses(ck.getUses());
                pm.reloadProfile(this);
            }
        } else {
            if (slot == 1) {
                Kit k = getNoEnchantmentKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                pm.reloadProfile(this);
            } else if (slot == 2) {
                Kit k = getNoEnchantmentKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                pm.reloadProfile(this);
            } else if (slot == 3) {
                Kit k = getNoEnchantmentKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                pm.reloadProfile(this);
            } else if (slot == 4) {
                Kit k = getNoEnchantmentKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                pm.reloadProfile(this);
            } else if (slot == 5) {
                Kit k = getNoEnchantmentKit(slot);
                k.setKills(ck.getKills());
                k.setDeaths(ck.getDeaths());
                pm.reloadProfile(this);
            }
        }
    }

    public Kit getMchcfKit(int slot) {
        if (slot == 1) {
            return Mchcf1;
        } else if (slot == 2) {
            return Mchcf2;
        } else if (slot == 3) {
            return Mchcf3;
        } else if (slot == 4) {
            return Mchcf4;
        } else if (slot == 5) {
            return Mchcf5;
        } else {
            return null;
        }
    }

    public Kit getNoEnchantmentKit(int slot) {
        if (slot == 1) {
            return noEnchantment1;
        } else if (slot == 2) {
            return noEnchantment2;
        } else if (slot == 3) {
            return noEnchantment3;
        } else if (slot == 4) {
            return noEnchantment4;
        } else if (slot == 5) {
            return noEnchantment5;
        } else {
            return null;
        }
    }

    public void setMchcfKit(Kit MchcfKit, int slot) {
        if (slot == 1) {
            this.Mchcf1 = MchcfKit;
        } else if (slot == 2) {
            this.Mchcf2 = MchcfKit;
        } else if (slot == 3) {
            this.Mchcf3 = MchcfKit;
        } else if (slot == 4) {
            this.Mchcf4 = MchcfKit;
        } else if (slot == 5) {
            this.Mchcf5 = MchcfKit;
        }
    }

    public void setNoEnchantmentKit(Kit noEnchantmentKit, int slot) {
        if (slot == 1) {
            this.noEnchantment1 = noEnchantmentKit;
        } else if (slot == 2) {
            this.noEnchantment2 = noEnchantmentKit;
        } else if (slot == 3) {
            this.noEnchantment3 = noEnchantmentKit;
        } else if (slot == 4) {
            this.noEnchantment4 = noEnchantmentKit;
        } else if (slot == 5) {
            this.noEnchantment5 = noEnchantmentKit;
        }
    }
}
