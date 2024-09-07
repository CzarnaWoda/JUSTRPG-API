package pl.justrpg.api.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import pl.justrpg.api.util.Logger;

public class ConfigManager {
	@Getter public static HashMap<String, ConfigCreator> configs;
	@Getter public static List<String> configslist = new ArrayList<String>();
	static{
		configs = new HashMap<String, ConfigCreator>();
	}
	public static void register(ConfigCreator c){
		ConfigCreator cfg = configs.get(c.getConfigName());
		if(cfg == null){
			configs.put(c.getConfigName(), c);
			c.saveDefaultConfig();
			Logger.info("Config " + c.getConfigName() + " was registered");
			configslist.add(c.getConfigName());
		}
	}
	public static ConfigCreator getConfig(String cfgname){
		return configs.get(cfgname);
	}
}
