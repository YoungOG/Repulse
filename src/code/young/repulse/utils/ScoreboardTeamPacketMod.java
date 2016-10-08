package code.young.repulse.utils;

import code.BreakMC.commons.util.reflection.ReflectionUtils;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Credited to NametagEdit
 * This has been recoded but archived
 */

public class ScoreboardTeamPacketMod {

    private static Method getHandle;
    private static Method sendPacket;
    private static Field playerConnection;
    private static Class<?> packetType;
    private Object packet;

    public ScoreboardTeamPacketMod(String name, String prefix, String suffix, Collection players, int paramInt) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException {
        this.packet = packetType.newInstance();
        this.setField("a", name);
        this.setField("f", Integer.valueOf(paramInt));
        if (paramInt == 0 || paramInt == 2) {
            this.setField("b", name);
            this.setField("c", prefix);
            this.setField("d", suffix);
            this.setField("g", Integer.valueOf(3));
        }

        if (paramInt == 0) {
            this.addAll(players);
        }

    }

    public ScoreboardTeamPacketMod(String name, Collection players, int paramInt) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException {
        this.packet = packetType.newInstance();
        if (paramInt != 3 && paramInt != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        } else {
            if (players == null || ((Collection) players).isEmpty()) {
                players = new ArrayList();
            }

            this.setField("g", Integer.valueOf(3));
            this.setField("a", name);
            this.setField("f", Integer.valueOf(paramInt));
            this.addAll((Collection) players);
        }
    }

    public void sendToPlayer(Player bukkitPlayer) {
        try {
            Object ex = getHandle.invoke(bukkitPlayer, new Object[0]);
            Object connection = playerConnection.get(ex);
            sendPacket.invoke(connection, new Object[]{this.packet});
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void setField(String field, Object value) {
        try {
            Field ex = this.packet.getClass().getDeclaredField(field);
            ex.setAccessible(true);
            ex.set(this.packet, value);
            ex.setAccessible(false);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private void addAll(Collection<?> col) throws NoSuchFieldException, IllegalAccessException {
        Field f = this.packet.getClass().getDeclaredField("e");
        f.setAccessible(true);
        ((Collection) f.get(this.packet)).addAll(col);
    }

    static {
        try {
            packetType = Class.forName(ReflectionUtils.getPacketTeamClasspath());
            Class e = Class.forName(ReflectionUtils.getCraftPlayerClasspath());
            Class typeNMSPlayer = Class.forName(ReflectionUtils.getNMSPlayerClasspath());
            Class typePlayerConnection = Class.forName(ReflectionUtils.getPlayerConnectionClasspath());
            getHandle = e.getMethod("getHandle", new Class[0]);
            playerConnection = typeNMSPlayer.getField("playerConnection");
            sendPacket = typePlayerConnection.getMethod("sendPacket", new Class[]{Class.forName(ReflectionUtils.getPacketClasspath())});
        } catch (Exception var3) {
            System.out.println("Failed to setup reflection for Packet209Mod!");
            var3.printStackTrace();
        }
    }
}