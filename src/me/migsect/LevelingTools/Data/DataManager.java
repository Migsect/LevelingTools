package me.migsect.LevelingTools.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

import me.migsect.LevelingTools.ConfigAccessor;
import me.migsect.LevelingTools.LevelingTools;

public class DataManager
{
	LevelingTools plugin;
	HashMap<String,TypeData> typeData = new HashMap<String,TypeData>();
	
	
	public DataManager(LevelingTools plugin)
	{
		this.plugin = plugin;
	}
	
	public void parseData(File file)
	{
		String file_name = file.getName();
		ConfigAccessor file_config = new ConfigAccessor(plugin, "/ToolTypes/" + file_name);
		TypeData data = new TypeData(file_name, file_config);
		
		parseToolData(data, file_config);
		parseVanillaEnchants(data, file_config);
		parseCustomEnchants(data, file_config);
		
		typeData.put(file_name, data);
	}
	private void parseToolData(TypeData type_data, ConfigAccessor config)
	{
		List<String> keys = new ArrayList<String>();
		keys.addAll(config.getConfig().getConfigurationSection("tools").getKeys(false));
		for(String key : keys)
		{
			ToolData data = new ToolData("tools." + key, key, config);
			type_data.registerToolData(data);
		}
	}
	private void parseVanillaEnchants(TypeData type_data, ConfigAccessor config)
	{
		List<String> keys = new ArrayList<String>();
		keys.addAll(config.getConfig().getConfigurationSection("vanilla-enchantments").getKeys(false));
		for(String key : keys)
		{
			EnchantDataVanilla data = new EnchantDataVanilla("vanilla-enchantments." + key, key, config);
			type_data.registerVanillaEnchant(data);
		}
	}
	private void parseCustomEnchants(TypeData type_data, ConfigAccessor config)
	{
		List<String> keys = new ArrayList<String>();
		keys.addAll(config.getConfig().getConfigurationSection("custom-enchantments").getKeys(false));
		for(String key : keys)
		{
			EnchantDataCustom data = new EnchantDataCustom("custom-enchantments." + key, key, config);
			type_data.registerCustomEnchant(data);
		}
	}
	
	// Helping Commands to make life go easier in other places.
	
	// getExpReward returns the reward for a block.  The passed values
	//   are in their asString variants due to the data within the the "TypeData"
	//   being stored as a string.
	// @param A string of the item's material type.
	// @param A string of the blocks's material type.
	// @return An int as the amount of exp that should be awarded.
	public int getBlockExpReward(String item, String block)
	{
		TypeData data = this.getToolsTypeData(item);
		if(data == null) return 0;
		return data.getBlockReward(block);
	}
	public int getBlockExpReward(Material item, String block){return this.getBlockExpReward(item.name(), block);}
	public int getBlockExpReward(String item, Material block){return this.getBlockExpReward(item, block.name());}
	public int getBlockExpReward(Material item, Material block){return this.getBlockExpReward(item.name(), block.name());}
	
	
	public TypeData getToolsTypeData(String item)
	{
		// if(LevelingTools.debugger) LevelingTools.logger.info("  getToolsTypeData Method:");
		List<String> keys = new ArrayList<String>();
		keys.addAll(typeData.keySet());
		for(String k : keys)
		{
			// if(LevelingTools.debugger) LevelingTools.logger.info("   - Checked " + item + " against " + k);
			TypeData current = typeData.get(k);
			if(!current.containsTool(item)) continue;
			return current;
		}
		return null;
	}

}
