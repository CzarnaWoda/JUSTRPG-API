package pl.justrpg.api.configs;

import org.bukkit.configuration.file.FileConfiguration;
import pl.justrpg.api.RPG;

import java.lang.reflect.Field;

public class Config
{
    public static boolean ENABLED;
    public static String DATABASE_MODE;
    public static String DATABASE_TABLEPREFIX;
    public static String DATABASE_MYSQL_HOST;
    public static int DATABASE_MYSQL_PORT;
    public static String DATABASE_MYSQL_USER;
    public static String DATABASE_MYSQL_PASS;
    public static String DATABASE_MYSQL_NAME;
    public static String DATABASE_SQLITE_NAME;
    public static String SOCKET_PASSWORD;
    public static int SOCKET_PORT;
    public static String API_GUI_NAME;
    public static String DATABASE_SERVER_TYPE;
    
    public static void loadConfig() {
        try {
            RPG.getPlugin().saveDefaultConfig();
            FileConfiguration c = RPG.getPlugin().getConfig();
            for (Field f : Config.class.getFields()) {
                if (c.isSet("config." + f.getName().toLowerCase().replace("_", "."))) {
                    f.set(null, c.get("config." + f.getName().toLowerCase().replace("_", ".")));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveConfig() {
        try {
            FileConfiguration c = RPG.getPlugin().getConfig();
            for (Field f : Config.class.getFields()) {
                c.set("config." + f.getName().toLowerCase().replace("_", "."), f.get(null));
            }
            RPG.getPlugin().saveConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void reloadConfig() {
        RPG.getPlugin().reloadConfig();
        loadConfig();
        saveConfig();
    }
    
    static {
        Config.ENABLED = true;
        Config.DATABASE_MODE = "mysql";
        Config.DATABASE_TABLEPREFIX = "chestpvp_";
        Config.DATABASE_MYSQL_HOST = "localhost";
        Config.DATABASE_MYSQL_PORT = 3306;
        Config.DATABASE_MYSQL_USER = "root";
        Config.DATABASE_MYSQL_PASS = "";
        Config.DATABASE_MYSQL_NAME = "minecraft";
        Config.DATABASE_SQLITE_NAME = "minecraft.db";
        Config.DATABASE_SERVER_TYPE = "chestpvp";
        Config.SOCKET_PASSWORD = "ZAQ!2wsx";
        Config.SOCKET_PORT = 1337;
        Config.API_GUI_NAME = "&c&lAPI Manager";
    }
}
