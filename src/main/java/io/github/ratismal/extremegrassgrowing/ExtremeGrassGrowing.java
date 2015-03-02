package io.github.ratismal.extremegrassgrowing;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ExtremeGrassGrowing extends JavaPlugin {

	private final Logger log = getLogger();
	public Economy econ = null;
	public Map<String, Object> configValues = new HashMap<String, Object>();
	public Map<String, Object> idValues = new HashMap<String, Object>();

	public File gameData = new File(getDataFolder() + "/Data/gameData.yml");
	public FileConfiguration pdata = YamlConfiguration.loadConfiguration(gameData);
	/*
    public ExtremeGrassGrowing(EGGGame instance) {
    	egg = instance;
    }
	 */
	@Override
	public void onEnable() {

		loadFiles();
		//log.info("Running on version " + getDescription().getVersion());
		log.info("Plugin by Ratismal");
		log.info("Check for updates at dev.bukkit.org/bukkit-plugins/extremegrassgrowing/");
		//log.info("Get ready to GROW EXTREME GRASS!");
		PluginManager pm = this.getServer().getPluginManager();

		this.saveDefaultConfig();
		getConfig();    

		if (!setupEconomy() ) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		else {
			log.info("Hooked onto Vault!");
		}

		pm.registerEvents(new GrassGrowListener(this), this);
		log.info("GrassGrowListener enabled");

		getCommand("extremegg").setExecutor(new EGGCommand(this));
		getCommand("extremegg-create").setExecutor(new EGGCommand(this));
		getCommand("extremegg-join").setExecutor(new EGGCommand(this));
		getCommand("extremegg-start").setExecutor(new EGGCommand(this));
		getCommand("extremegg-list").setExecutor(new EGGCommand(this));
		getCommand("extremegg-remove").setExecutor(new EGGCommand(this));
		getCommand("extremegg-leave").setExecutor(new EGGCommand(this));

	}

	@Override
	public void onDisable() {
		saveFiles();
		log.info("[EGG] Disabling plugin. Bye bye!");
	}




	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public void saveFiles() {
		try {
			pdata.save(gameData);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFiles() {
		if (gameData.exists()) {
			try {
				pdata.load(gameData);
			} 
			catch (IOException
					| InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				pdata.save(gameData);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
