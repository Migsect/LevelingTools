package me.migsect.LevelingTools;

import java.io.File;
import java.util.logging.Logger;

import me.migsect.LevelingTools.Data.DataManager;
import me.migsect.LevelingTools.Listeners.ListenerBreak;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelingTools extends JavaPlugin
{
	static final public Logger logger = Logger.getLogger("Minecraft"); // Logger is static to allow easy use across plugin;
	static public DataManager data;
	static public boolean debugger;
	
  //Enabling
	public void onEnable()
	{
	  // Startup
		PluginDescriptionFile pdf = this.getDescription();
		// logger.info("Starting" + pdf.getName() + " Version " + pdf.getVersion());
		getConfig().options().copyDefaults(true);
		saveConfig();
		if(!this.getConfig().getBoolean("activated"))
	  {
			LevelingTools.logger.info(" \"activated\" variable is false.  Stopping startup...");
			return;
  	}
		LevelingTools.logger.info(pdf.getName() + " Version " + pdf.getVersion() + " has been enabled.");
		
		debugger = this.getConfig().getBoolean("debug");
		
		// DataManager initilization
		data = new DataManager(this);
		
		// Check to see if the "/ToolTypes/" is empty.  If so, populate it.
		File folder = new File(this.getDataFolder() + "/ToolTypes/");
		if(!folder.exists())
		{
			LevelingTools.logger.info(" Found \"./ToolTypes/ \" to not exist, creating directory.  Place your tool-type configurations in this file.");
			folder.mkdir();
		}
		
		// Data Parsing:
		File[] listed_configs = folder.listFiles();  // Grab all the files to parse.
		for(final File file : listed_configs)
		{
			if(file.isDirectory()) continue;
			LevelingTools.logger.info(" Parsing: " + file.getPath());
			data.parseData(file);
		}
		
		// Event registering
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new ListenerBreak(data), this);
	}
	// Disabling
	public void onDisable()
	{
	  // Server Log Message
		PluginDescriptionFile pdf = this.getDescription();
    LevelingTools.logger.info(pdf.getName() + " has been disabled");
	}
	
	
}
