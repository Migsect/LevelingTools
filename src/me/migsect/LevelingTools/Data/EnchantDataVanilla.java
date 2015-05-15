package me.migsect.LevelingTools.Data;

import org.bukkit.enchantments.Enchantment;

import me.migsect.LevelingTools.ConfigAccessor;
import me.migsect.LevelingTools.LevelingTools;

public class EnchantDataVanilla extends EnchantData
{
	protected Enchantment enchant;
	
	public EnchantDataVanilla(String path, String node, ConfigAccessor config)
  {
	  super(path, node, config);
	  enchant = Enchantment.getByName(raw_name);
	  if(enchant == null)
	  {
	  	LevelingTools.logger.warning("  " + raw_name + " is not a vanilla-enchantment type!");
	  }

	  if(LevelingTools.debugger) LevelingTools.logger.info(" Completed: " + path);
  }
	
	public Enchantment getVanillaEnchant(){return enchant;}
	
}
