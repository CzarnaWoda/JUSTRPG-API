package pl.justrpg.api.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Util {

    private static final HashMap<Integer,String> values = new LinkedHashMap<Integer, String>();
    static {
        values.put(31104000, "y");
        values.put(2592000, "msc");
        values.put(86400, "d");
        values.put(3600, "h");
        values.put(60, "min");
        values.put(1, "s");
    }

    @NotNull
    static String fixColor(@NotNull String string){
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    @NotNull
    public static String getReplacement(@NotNull String string){
        return string.replace(">>", "»").replace("<<", "«").replace("*", "•").replace("%V%", "√").replace("%X%", "✗");
    }

    public static boolean sendMsg(@NotNull CommandSender player, String text){
        player.sendMessage(fixColor(getReplacement(text)));
        return true;
    }
    @NotNull
    public static String secondsToString(int seconds) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> e : Util.values.entrySet()) {
            int iDiv = seconds / e.getKey();
            if (iDiv >= 1) {
                int x = (int)Math.floor(iDiv);
                sb.append(x).append(e.getValue()).append(" ");
                seconds -= x * e.getKey();
            }
        }
        return sb.toString();
    }
}
