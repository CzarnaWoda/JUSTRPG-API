package pl.justrpg.api.timer;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.justrpg.api.util.Util;

public class TimerUtil
{
    public static void teleport(Player p, final Location location, int delay) {
        if (!p.hasPermission("api.timer.bypass")) {
            Util.sendMsg((CommandSender)p, "&8->> &7Teleport nastapi za &a" + Util.secondsToString(delay) + "&7!");
        }
        TimerManager.addTask(p, new TimerCallback<Player>() {
            @SuppressWarnings("deprecation")
			@Override
            public void success(Player player) {
                player.teleport(location);
                Util.sendMsg((CommandSender)player, Util.getReplacement("&8->> &2%V% &aPrzeteleportowano"));
            	Location loc = player.getLocation();
            	player.getWorld().refreshChunk(loc.getBlockX(), loc.getBlockZ());
            }
            
            @Override
            public void error(Player player) {
                Util.sendMsg((CommandSender)player, "&4Blad: &cTeleport zostal przerwany!");
            }
        }, delay);
    }
}
