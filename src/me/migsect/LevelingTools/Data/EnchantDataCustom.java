package me.migsect.LevelingTools.Data;

import me.migsect.LevelingTools.ConfigAccessor;
import me.migsect.LevelingTools.LevelingTools;

public class EnchantDataCustom extends EnchantData
{

	public EnchantDataCustom(String path, String node, ConfigAccessor config)
  {
	  super(path, node, config);
		if(LevelingTools.debugger) LevelingTools.logger.info(" Completed: " + path);
  }
  
}
