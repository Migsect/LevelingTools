package me.migsect.LevelingTools.Listeners;

import me.migsect.LevelingTools.LevelingTools;
import me.migsect.LevelingTools.Utilities;
import me.migsect.LevelingTools.Data.DataManager;
import me.migsect.LevelingTools.Data.TypeData;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerBreak implements Listener
{
	DataManager dm;
	
	public ListenerBreak(DataManager dm)
	{
		this.dm = dm;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(event.isCancelled()) return;
		
		Block block = event.getBlock();
		Player player = event.getPlayer();
		ItemStack item_in_hand = player.getItemInHand();
		
		if(item_in_hand == null) return;
		
		if(LevelingTools.debugger) LevelingTools.logger.info(Utilities.getCoord(block) + " BLOCK-BREAK ON " + block.getType() + " WITH TOOL TYPE " + item_in_hand.getType());
		
		if(!Utilities.isLevelable(item_in_hand)) return;
		// LOGIC WILL NEED TO GO HERE WHEN WE ADD DIFFERENT WOOD TYPES AND WHAT NOT
		// FOR BASIC FUNCTIONALITY HOWEVER WE DO NOT NEED TO DO MUCH :D

		if(LevelingTools.debugger) LevelingTools.logger.info( "  ITEM IN HAND WAS LEVELABLE.  ADDING " + dm.getBlockExpReward(item_in_hand.getType(), block.getType().name()) + " EXPERIENCE");
		Utilities.addExperience(item_in_hand, dm.getBlockExpReward(item_in_hand.getType(), block.getType()));
		player.updateInventory();
		if(LevelingTools.debugger) // Prints all the item's info.
		{
			LevelingTools.logger.info("  Tool Digest:");
			TypeData td = dm.getToolsTypeData(item_in_hand.getType().name());
			if(td == null) 
			{
				LevelingTools.logger.info("   - Found no tooldata for this item.");
				return;
			}
			LevelingTools.logger.info("   - " + block.getType().name() + "'s reward is: " + td.getBlockReward(block.getType().name()));
		}
	}
}
