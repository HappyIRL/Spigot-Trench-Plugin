package de.happyirl.trench;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class TrenchPlugin extends JavaPlugin implements Listener
{
	private String TRENCH_COMMAND = "trench";
	
	TrenchingItems trenchingItems;
	
	public void onEnable()
	{
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		trenchingItems = new TrenchingItems();
		
		getCommand(TRENCH_COMMAND).setExecutor(new TrenchCommand(this, trenchingItems));
	}
	
	@EventHandler
	private void OnBlockBreak(BlockBreakEvent event)
	{
		validateItem(event);
	}
	private void validateItem(BlockBreakEvent event) 
	{
		boolean isPick = false;
		ItemStack itemInHand = event.getPlayer().getItemInHand();
			
			net.minecraft.server.v1_8_R3.ItemStack nmsInHand = CraftItemStack.asNMSCopy(itemInHand);
			if(nmsInHand != null)
			{
				if(nmsInHand.hasTag())
				{
					NBTTagCompound inHandTag = nmsInHand.getTag();
					for(NBTTagCompound trenchItem : trenchingItems.trenchItems)
					{
						if(trenchItem.equals(inHandTag))
						{
							if(itemInHand.getType().equals(Material.DIAMOND_PICKAXE))
								isPick = true;
							new Trench(event, isPick, inHandTag);
							break;
						}
					}
				}
			}
	}
}
