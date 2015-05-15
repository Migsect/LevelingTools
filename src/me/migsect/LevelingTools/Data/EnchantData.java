package me.migsect.LevelingTools.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.migsect.LevelingTools.ConfigAccessor;
import me.migsect.LevelingTools.LevelingTools;

import org.bukkit.Material;

public class EnchantData
{
  protected String raw_name;
	
  protected String menu_display_name;
  protected Material menu_display_material;
	protected short menu_display_durability;
	protected int menu_display_amount;
	protected List<String> menu_display_lore;
	
	protected int base_exp_cost;
	protected int base_orb_cost;
	protected int base_lvl_cost;
	
	protected int max_rank;
	protected double per_rank_mod_exp;
	protected double per_rank_mod_orb;
	protected double per_rank_mod_lvl;
	protected int per_rank_add_exp;
	protected int per_rank_add_orb;
	protected int per_rank_add_lvl;
	
	List<String> incompat;
	HashMap<Integer, HashMap<String, Integer>> depend = new HashMap<Integer, HashMap<String, Integer>>();
	
	public EnchantData(String path, String node, ConfigAccessor config)
	{
		if(LevelingTools.debugger) LevelingTools.logger.info(" Reading: " + path);
		this.raw_name = node;
		
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.menu-display.name");
		menu_display_name = config.getConfig().getString(path + ".menu-display.name");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.menu-display.material");
		String material = config.getConfig().getString(path + ".menu-display.material");
		menu_display_material = Material.getMaterial(material);
		if(menu_display_material == null) 
		{
			LevelingTools.logger.warning("  " + material + " is not a material type!");
			menu_display_material = Material.BARRIER;
		}
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.menu-display.durability");
		menu_display_durability = (short) config.getConfig().getInt(path + ".menu-display.durability");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.menu-display.amount");
		menu_display_amount = config.getConfig().getInt(path + ".menu-display.amount");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.menu-display.lore");
		menu_display_lore = config.getConfig().getStringList(path + ".menu-display.lore");
		
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.base-cost.exp");
		base_exp_cost = config.getConfig().getInt(path + ".base-cost.exp");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.base-cost.orb");
		base_orb_cost = config.getConfig().getInt(path + ".base-cost.orb");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.base-cost.lvl");
		base_lvl_cost = config.getConfig().getInt(path + ".base-cost.lvl");
		
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.max-rank");
		max_rank = config.getConfig().getInt(path + ".max_rank");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.per-rank.mod.exp");
		per_rank_mod_exp = config.getConfig().getDouble(path + ".per_rank.mod.exp");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.per-rank.mod.orb");
		per_rank_mod_orb = config.getConfig().getDouble(path + ".per_rank.mod.orb");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.per-rank.mod.lvl");
		per_rank_mod_lvl = config.getConfig().getDouble(path + ".per_rank.mod.lvl");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.per_rank.add.exp");
		per_rank_add_exp = config.getConfig().getInt(path + ".per_rank.add.exp");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.per_rank.add.orb");
		per_rank_add_orb = config.getConfig().getInt(path + ".per_rank.add.orb");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.per_rank.add.lvl");
		per_rank_add_lvl = config.getConfig().getInt(path + ".per_rank.add.lvl");

		if(LevelingTools.debugger) LevelingTools.logger.info("  *.incompatable");
		incompat = config.getConfig().getStringList(path + ".incompatable");
		if(LevelingTools.debugger) LevelingTools.logger.info("  *.dependencies");
		List<String> keys = new ArrayList<String>(); // (these are all "numbers")
		if(config.getConfig().isConfigurationSection(path + ".dependencies"))
		{
			keys.addAll(config.getConfig().getConfigurationSection(path + ".dependencies").getKeys(false));
			List<Integer> number_keys = new ArrayList<Integer>();
			for(String k : keys) // Converting all the keys
			{
				try
				{
					number_keys.add(Integer.parseInt(k));
				}
				catch(NumberFormatException e)
				{
					LevelingTools.logger.warning("  " + k + " in " + path + ".dependencies must be a number!");
				}
			}
			for(int k : number_keys) // Constructing the inner hashmaps
			{
				HashMap<String, Integer> enchant_mapping = new HashMap<String, Integer>();
				List<String> enchant_keys = new ArrayList<String>();
				enchant_keys.addAll(config.getConfig().getConfigurationSection(path + ".dependencies." + k).getKeys(false));
				for(String key : enchant_keys)
				{
					int value = config.getConfig().getInt(path + ".dependencies." + k + key);
					enchant_mapping.put(key, value);
				}
				
				depend.put(k, enchant_mapping);
			}
		}
	}
	
	public String getRawName(){return raw_name;}
	
	public String getMenuDisplayName(){return this.menu_display_name;}
	public Material getMenuDisplayMaterial(){return this.menu_display_material;}
	public short getMenuDisplayDurability(){return this.menu_display_durability;}
	public int getMenuDisiplayAmount(){return this.menu_display_amount;}
	public List<String> getMenuDisplayLore(){return this.menu_display_lore;}
	
	public int getBaseExpCost(){return this.base_exp_cost;}
	public int getBaseOrbCost(){return this.base_orb_cost;}
	public int getBaseLvlCost(){return this.base_lvl_cost;}
	
	public int getMaxRank(){return this.max_rank;}
	public double getPerRankModExp(){return this.per_rank_mod_exp;}
	public double getPerRankModOrb(){return this.per_rank_mod_orb;}
	public double getPerRankModLvl(){return this.per_rank_mod_lvl;}
	public int getPerRankAddExp(){return this.per_rank_add_exp;}
	public int getPerRankAddOrb(){return this.per_rank_add_orb;}
	public int getPerRankAddLvl(){return this.per_rank_add_lvl;}
	
	public List<String> getIncompatabilities(){return incompat;}
	public HashMap<Integer, HashMap<String, Integer>> getDependacies(){return this.depend;}
}
