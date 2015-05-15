package me.migsect.LevelingTools;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utilities
{
	/*
	 *  @param The unformated header of the mapping. Mapping will be assumed to have a following":"
	 *  @param The itemstack that would have the mapping.
	 *  @return The Strin returned from the mapping.  If the header is not found the return will be 'null'  Returned value will be unformatted.
	 */
	public static String getMapping(String header, ItemStack item)
	{
		ItemMeta im = item.getItemMeta();
		if(im == null) return null;
		List<String> lore = im.getLore();
		if(lore == null) return null;
		for(String l : lore) // Stripping the lines of color.
		{
			l = ChatColor.stripColor(l);
			if(l.startsWith(header + ":")) // Checking for the header
			{
				l = l.replace(header + ":", "").replace(" ", ""); // Should remove the header.
				return l;
			}
		}
		return null;
	}
	/*
	 *  @param The key of the mapping. Discluding the ":"
	 *  @param The item of which the mapping is gonna be set.
	 *  @param The value the mapping should be set to
	 *  @return Returns nothing
	 */
	public static void setMapping(String header, ItemStack item, String set_to)
	{
		ItemMeta im = item.getItemMeta();
		List<String> lore = im.getLore();
		for(int i = 0; i < lore.size(); i++) // Stripping the lines of color.
		{
			lore.set(i, ChatColor.stripColor(lore.get(i)));
			if(lore.get(i).startsWith(header + ":")) // Checking for the header
			{
				lore.set(i, Utilities.colorEncoding("&7" + header + ": " + set_to));
			}
		}
		im.setLore(lore);
		item.setItemMeta(im);
	}
	/*
	 *  @param An itemstack that would have experience on it
	 *  @return If the item has an experience tag, it will return a number 0+, else it will return -1
	 */
	public static int getExperience(ItemStack item)
	{
		String mapping = Utilities.getMapping("Exp", item);
		LevelingTools.logger.info("" + mapping);
		if(mapping == null) return -1;
		int ret_val = 0;
		try
		{
			ret_val = Integer.parseInt(mapping);
		}
		catch(NumberFormatException e)
		{
			return 0; // Because what else could it be?
		}
		return ret_val;
	}
	/*  @param The item where the experience is getting set.
	 *  @param The amount of experience it is being set to.
	 *  @return nothing
	 */
	public static void setExperience(ItemStack item, int experience)
	{
		Utilities.setMapping("Exp", item, "" + experience);
	}
	/*  @param the item where the experience is being added to.
	 *  @param The amount of experience to add.
	 *  @return nothing
	 */
	public static void addExperience(ItemStack item, int experience)
	{
		Utilities.setExperience(item, Utilities.getExperience(item) + experience);
	}
	
	/*  @param the item we are checking
	 *  @return If the the item is levelable or not.
	 */
	public static boolean isLevelable(ItemStack item)
	{
	  return Utilities.getExperience(item) >= 0;
	}

	/*
	 *  @param the string that had encodings
	 *  @return the encoded string
	 */
	static public String colorEncoding(String string)
	{
		String newString = string;
		
		newString = newString.replace("&0", "" + ChatColor.BLACK);
		newString = newString.replace("&1", "" + ChatColor.DARK_BLUE);
		newString = newString.replace("&2", "" + ChatColor.DARK_GREEN);
		newString = newString.replace("&3", "" + ChatColor.DARK_AQUA);
		newString = newString.replace("&4", "" + ChatColor.DARK_RED);
		newString = newString.replace("&5", "" + ChatColor.DARK_PURPLE);
		newString = newString.replace("&6", "" + ChatColor.GOLD);
		newString = newString.replace("&7", "" + ChatColor.GRAY);
		newString = newString.replace("&8", "" + ChatColor.DARK_GRAY);
		newString = newString.replace("&9", "" + ChatColor.BLUE);
		newString = newString.replace("&a", "" + ChatColor.GREEN);
		newString = newString.replace("&b", "" + ChatColor.AQUA);
		newString = newString.replace("&c", "" + ChatColor.RED);
		newString = newString.replace("&d", "" + ChatColor.LIGHT_PURPLE);
		newString = newString.replace("&e", "" + ChatColor.YELLOW);
		newString = newString.replace("&f", "" + ChatColor.WHITE);
		
		newString = newString.replace("&k", "" + ChatColor.MAGIC);
		newString = newString.replace("&l", "" + ChatColor.BOLD);
		newString = newString.replace("&m", "" + ChatColor.STRIKETHROUGH);
		newString = newString.replace("&n", "" + ChatColor.UNDERLINE);
		newString = newString.replace("&o", "" + ChatColor.ITALIC);
		newString = newString.replace("&p", "" + ChatColor.RESET);
		
		return newString;
	}
	/*  @param block
	 *  @return string of its coords
	 */
	public static String getCoord(Block block)
	{
		return "x" + block.getX() + " y" + block.getY() + " z" + block.getZ();
	}
}
