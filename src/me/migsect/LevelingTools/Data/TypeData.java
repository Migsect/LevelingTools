package me.migsect.LevelingTools.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.migsect.LevelingTools.ConfigAccessor;
import me.migsect.LevelingTools.LevelingTools;

/* Type Data is meant to store the data for each tool type and acts as a second configuration.
 * 
 */
public class TypeData
{
	String file_name;
	
	HashMap<String,EnchantDataVanilla> vanilla = new HashMap<String, EnchantDataVanilla>();
	HashMap<String,EnchantDataCustom> custom = new HashMap<String, EnchantDataCustom>();
	HashMap<String,ToolData> tools = new HashMap<String,ToolData>();
	
	String repair_type;
	String repair_loss_type;
	int repair_material_amount;
	double repair_amount;
	
	HashMap<String, Integer> block_rewards = new HashMap<String, Integer>();
	HashMap<String, Integer> mob_rewards = new HashMap<String, Integer>();
	HashMap<String, Integer> action_rewards = new HashMap<String, Integer>();
	
	public TypeData(String file_name, ConfigAccessor config)
	{
		this.file_name = file_name;
		if(LevelingTools.debugger) LevelingTools.logger.info(" Reading: *");
		
		// For repairing
		if(LevelingTools.debugger) LevelingTools.logger.info("  repairing.type");
		repair_type = config.getConfig().getString("repairing.type");
		if(LevelingTools.debugger) LevelingTools.logger.info("  repairing.loss-type");
		repair_loss_type = config.getConfig().getString("repairing.loss-type");
		if(LevelingTools.debugger) LevelingTools.logger.info("  repairing.material-amount");
		repair_material_amount = config.getConfig().getInt("repairing.material-amount");
		if(LevelingTools.debugger) LevelingTools.logger.info("  repairing.repaired-amount");
		repair_amount = config.getConfig().getDouble("repairing.repaired-amount");
		
		// For exp-rewards
		if(LevelingTools.debugger) LevelingTools.logger.info("  exp-awards.blocks");
		if(config.getConfig().isConfigurationSection("exp-awards.blocks"))
		{
			List<String> keys = new ArrayList<String>();
			keys.addAll(config.getConfig().getConfigurationSection("exp-awards.blocks").getKeys(false));
			for(String k : keys)
			{
				int value = config.getConfig().getInt("exp-awards.blocks." + k);
				block_rewards.put(k, value);
			}
		}
		if(LevelingTools.debugger) LevelingTools.logger.info("  exp-awards.mobs");
		if(config.getConfig().isConfigurationSection("exp-awards.mobs"))
		{
			List<String> keys = new ArrayList<String>();
			keys.addAll(config.getConfig().getConfigurationSection("exp-awards.mobs").getKeys(false));
			for(String k : keys)
			{
				int value = config.getConfig().getInt("exp-awards.mobs." + k);
				mob_rewards.put(k, value);
			}
		}
		if(LevelingTools.debugger) LevelingTools.logger.info("  exp-awards.actions");
		if(config.getConfig().isConfigurationSection("exp-awards.actions"))
		{
			List<String> keys = new ArrayList<String>();
			keys.addAll(config.getConfig().getConfigurationSection("exp-awards.actions").getKeys(false));
			for(String k : keys)
			{
				int value = config.getConfig().getInt("exp-awards.actions." + k);
				action_rewards.put(k, value);
			}
		}
		
		// Done
		if(LevelingTools.debugger) LevelingTools.logger.info(" Completed: *");
	}
	
	public void registerToolData(ToolData data)
	{
		tools.put(data.getRawMaterialName(), data);
	}
	public void registerVanillaEnchant(EnchantDataVanilla data)
	{
		vanilla.put(data.getRawName(), data);
	}
	public void registerCustomEnchant(EnchantDataCustom data)
	{
		custom.put(data.getRawName(), data);
	}
	
	public void setRepairType(String repair_type){this.repair_type = repair_type;}
	public void setRepairLossType(String repair_loss_type){this.repair_loss_type = repair_loss_type;}
	public void setRepairMaterialAmount(int repair_material_amount){this.repair_material_amount = repair_material_amount;}
	public void setRepairAmount(double repair_amount){this.repair_amount = repair_amount;}
	
	public String getRepairType(){return repair_type;}
	public String getRepairLossType(){return repair_loss_type;}
	public int getRepairedMaterialAmount(){return repair_material_amount;}
	public double getRepairAmount(){return repair_amount;}
	
	public ToolData getToolData(String tool){return tools.get(tool);}
	public EnchantDataVanilla getVanillaEnchantData(String enchant){return vanilla.get(enchant);}
	public EnchantDataCustom getCustomEnchantData(String enchant){return custom.get(enchant);}
	
	public int getBlockReward(String material)
	{
		if(!this.block_rewards.containsKey(material)) return 0;
		return this.block_rewards.get(material);
	}
	public int getMobReward(String mob)
	{
		if(!this.mob_rewards.containsKey(mob)) return 0;
		return this.mob_rewards.get(mob);
	}
	public int getActionReward(String action)
	{
		if(!this.block_rewards.containsKey(action)) return 0;
		return this.action_rewards.get(action);
	}
	
	// Helper Functions to make other things go more smoothly
	public boolean containsTool(String tool)
	{
		return tools.containsKey(tool);
	}
}
