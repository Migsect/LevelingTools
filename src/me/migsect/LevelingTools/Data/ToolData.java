package me.migsect.LevelingTools.Data;

import me.migsect.LevelingTools.ConfigAccessor;
import me.migsect.LevelingTools.LevelingTools;

import org.bukkit.Material;

public class ToolData
{
	String raw_name;
	Material material;
	double exp_cost_mod;
	double orb_cost_mod;
	double lvl_cost_mod;
  int initial_lvl_cost;
  
  Material repair_item;
  int repair_lvl_cost;
  int repair_orb_cost;
  int repair_exp_loss;
	
	public ToolData(String path, String node, ConfigAccessor config)
	{
		if(LevelingTools.debugger) LevelingTools.logger.info(" Reading: " + path);
		
		this.raw_name = node;
		this.material = Material.getMaterial(node);
		if(material == null)
		{
			LevelingTools.logger.warning("  " + raw_name + " is not a material type!");
			return;
		}
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.exp-cost-mod");
		exp_cost_mod = config.getConfig().getDouble(path + ".exp-cost-mod");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.orb-cost-mod");
		orb_cost_mod = config.getConfig().getDouble(path + ".orb-cost-mod");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.lvl-cost-mod");
		lvl_cost_mod = config.getConfig().getDouble(path + ".lvl-cost-mod");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.initial-lvl-cost");
		initial_lvl_cost = config.getConfig().getInt(path + ".initial-lvl-cost");
		
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.repair.item");
		String item = config.getConfig().getString(path + ".repair.item");
		repair_item = Material.getMaterial(item);
		if(repair_item == null) LevelingTools.logger.warning("  " + item + " is not a material type!");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.repair.lvl-cost");
		repair_lvl_cost = config.getConfig().getInt(path + ".repair.lvl-cost");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.repair.orb-cost");
		repair_orb_cost = config.getConfig().getInt(path + ".repair.orb-cost");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.repair.exp-loss");
		repair_exp_loss = config.getConfig().getInt(path + ".repair.exp-loss");
		
		if(LevelingTools.debugger) LevelingTools.logger.info(" Completed: " + path);
		
	}
	
	public double getExpCostMod(){return exp_cost_mod;}
	public double getOrbCostMod(){return orb_cost_mod;}
	public double getLvlCostMod(){return lvl_cost_mod;}
	public double getInitialLvlCost(){return initial_lvl_cost;}
	public Material getMaterialType(){return material;}
	public String getRawMaterialName(){return raw_name;}
	
	public Material getRepairMaterialType(){return repair_item;}
	public int getRepairLvlCost(){return repair_lvl_cost;}
	public int getRepairOrbCost(){return repair_orb_cost;}
	public int getRepairExpLoss(){return repair_exp_loss;}
}
