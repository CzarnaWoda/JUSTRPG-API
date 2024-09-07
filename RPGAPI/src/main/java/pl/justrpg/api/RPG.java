package pl.justrpg.api;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.justrpg.api.commands.Command;
import pl.justrpg.api.commands.CommandManager;
import pl.justrpg.api.configs.Config;
import pl.justrpg.api.configs.ConfigCreator;
import pl.justrpg.api.configs.ConfigManager;
import pl.justrpg.api.store.Store;
import pl.justrpg.api.store.StoreMode;
import pl.justrpg.api.store.modes.StoreMySQL;
import pl.justrpg.api.store.modes.StoreSQLITE;
import pl.justrpg.api.util.Logger;

public class RPG extends JavaPlugin {

    @Getter private static RPG plugin;
    @Getter private static PluginManager pluginManager;
    @Getter private static Store store;
    @Override
    public void onEnable(){
        Logger.sendFixFormatedColoredToConsole("RPG enabling.....");
        final long start = System.currentTimeMillis();
        plugin = this;
        pluginManager = getPluginManager();
        Config.reloadConfig();
        Logger.info("Register DataBase Config");
        this.registerDatabase();
        Logger.info("Register Database");
        Logger.sendFixFormatedColoredToConsole("RPG enabled in: &c" + (System.currentTimeMillis() - start) + "&4ms");
    }
    public void onDisable(){
        //bez testu
    }

    public static void registerCommand(Command command){
        CommandManager.register(command);
    }
    public static void registerConfigCreator(ConfigCreator configCreator){
        ConfigManager.register(configCreator);
    }
    public static void registerListener(Plugin plugin, Listener... listeners) {
        if (getPluginManager() == null) {
            pluginManager = Bukkit.getPluginManager();
        }
        for (Listener listener : listeners) {
            getPluginManager().registerEvents(listener, plugin);
        }
    }
    protected boolean registerDatabase() {
        switch (StoreMode.getByName(Config.DATABASE_MODE)) {
            case MYSQL: {
                RPG.store = new StoreMySQL(Config.DATABASE_MYSQL_HOST, Config.DATABASE_MYSQL_PORT, Config.DATABASE_MYSQL_USER, Config.DATABASE_MYSQL_PASS, Config.DATABASE_MYSQL_NAME, Config.DATABASE_TABLEPREFIX);
                break;
            }
            case SQLITE: {
                RPG.store = new StoreSQLITE(Config.DATABASE_SQLITE_NAME, Config.DATABASE_TABLEPREFIX);
                break;
            }
            default: {
                Logger.warning("Value of databse mode is not valid! Using SQLITE as database!");
                RPG.store = new StoreSQLITE(Config.DATABASE_SQLITE_NAME, Config.DATABASE_TABLEPREFIX);
                break;
            }
        }
        boolean conn = RPG.store.connect();
        return conn;
    }
    public static void addMYSQLTable(String tableBuild){
        store.update(true,tableBuild);
    }

}
