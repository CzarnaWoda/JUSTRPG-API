package pl.justrpg.api.configs;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

@Data
public abstract class ConfigCreator {
	@NonNull private String configName;
	private File configFile = null;
	@NonNull private String configDescription;
	@NonNull private Plugin plugin;
	private FileConfiguration config = null;


	private Plugin getPlugin(){
		return plugin;
	}
	private Object addField(String path, Object o){
		getConfig().addDefault(path, o);
		getConfig().options().copyDefaults(true);
		saveConfig();
		return o;
	}
	public void createSection(String section){
		getConfig().createSection(section);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	public void addToSection(String section,String path, Object o){
		ConfigurationSection sectionc = getConfigurationSection(section);
		if(sectionc== null){
			createSection(section);
		}
		getConfig().getConfigurationSection(section).addDefault(path, o);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	public void addToSection(ConfigurationSection section,String path, Object o){
		section.addDefault(path, o);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	public void setField(String path,Object o){
		Object object = getConfig().get(path);
		if(object == null){
			addField(path, o);
			getConfig().options().copyDefaults(true);
		}
		getConfig().set(path, o);
		saveConfig();
	}
	public void addToListField(String path,String add){
		List<String> list  = getConfig().getStringList(path);
		if(list == null){
			addField(path, Arrays.asList("default"));
		}
		list.add(add);
		setField(path, list);
		saveConfig();
	}
	public void addToListField(String path, int i){
		List<Integer> list = getConfig().getIntegerList(path);
		if(list == null){
			addField(path, Arrays.asList(1));
		}
		list.add(i);
		setField(path, list);
		saveConfig();
	}
	public void removeToListField(String path,String remove){
		List<String> list  = getConfig().getStringList(path);
		if(list == null){
			addField(path, Arrays.asList("default"));
		}
		list.remove(remove);
		setField(path, list);
		saveConfig();
	}
	public ConfigurationSection getConfigurationSection(String section){
		ConfigurationSection sectionc = getConfig().getConfigurationSection(section);
		if(sectionc == null){
			createSection(section);
		}
		return getConfig().getConfigurationSection(section);
	}
	@SuppressWarnings("deprecation")
	public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), configName);
        }
        config = (FileConfiguration)YamlConfiguration.loadConfiguration(configFile);
        final InputStream defConfigStream = getPlugin().getResource(configName);
        if (defConfigStream != null) {
			Reader reader = new InputStreamReader(defConfigStream);
			final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
			config.setDefaults(defConfig);
        }
    }
    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }
    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            getConfig().save(configFile);
        }
        catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }
    public void saveDefaultConfig() {
		if (configFile == null) {
			configFile = new File(plugin.getDataFolder(), configName);
		}
		if (!configFile.exists()) {
			plugin.saveResource(configName, false);
		}
	}
}
