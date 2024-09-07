package pl.justrpg.api.util;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import pl.justrpg.api.RPG;

import java.util.logging.Level;

public class Logger
{
    private static ConsoleCommandSender console = Bukkit.getConsoleSender();
    public static void info(@NotNull String... logs) {
        for (String s : logs) {
            log(Level.INFO, s);
        }
    }

    public static void warning(@NotNull String... logs) {
        for (String s : logs) {
            log(Level.WARNING, s);
        }
    }

    public static void severe(@NotNull String... logs) {
        for (String s : logs) {
            log(Level.SEVERE, s);
        }
    }

    public static void log(Level level, String log) {
        RPG.getPlugin().getLogger().log(level, log);
    }

    public static void exception(@NotNull Throwable cause) {
        cause.printStackTrace();
    }

    public static void fixColorSend(@NotNull String... logs){
        for(String s : logs){
            console.sendMessage(Util.fixColor(s));
        }
    }

    public static void sendFixFormatedColoredToConsole(String... logs){
        for(String s : logs){
            console.sendMessage(Util.fixColor(Util.getReplacement("&8->> " + RPG.getPlugin().getDescription().getName() + " &8* &7" + s)));
        }
    }
}
