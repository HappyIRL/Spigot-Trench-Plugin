package de.happyirl.trench;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;


public class TrenchingItems 
{
	
	public ItemStack pick3x3;
	public ItemStack pick5x5;
	public ItemStack pick7x7;
	public ItemStack shovel3x3;
	public ItemStack shovel5x5;
	public ItemStack shovel7x7;
	public List<ItemStack> trenchItems;
	
	public TrenchingItems()
	{
		trenchItems = new ArrayList<ItemStack>();
		pick3x3 = createNMS("trench", "3x3", TrenchType.PICK);
		pick5x5 = createNMS("trench", "5x5", TrenchType.PICK);
		pick7x7 = createNMS("trench", "7x7",  TrenchType.PICK);
		shovel3x3 = createNMS("trench", "3x3", TrenchType.SHOVEL);
		shovel5x5 = createNMS("trench", "5x5", TrenchType.SHOVEL);
		shovel7x7 = createNMS("trench", "7x7", TrenchType.SHOVEL);
		
		
	}
	
	public enum TrenchAmount
	{
		T3X3,
		T5X5,
		T7X7
	}
	public enum TrenchType
	{
		PICK,
		SHOVEL
	}
	
	private ItemStack createNMS(String tag, String data, TrenchType trenchType)
	{
		ItemStack item;
		if(trenchType == TrenchType.PICK)
			item = new ItemStack(Material.DIAMOND_PICKAXE);
		else
			item = new ItemStack(Material.DIAMOND_SPADE);
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		itemCompound.set(tag, new NBTTagString(data));
		nmsItem.setTag(itemCompound);
		ItemStack copy = CraftItemStack.asBukkitCopy(nmsItem);
		addItemToList(copy);
		return copy;
	}
	public ItemStack getTrenchItem(TrenchAmount amount, TrenchType type)
	{
		
		switch(amount)
		{
			case T3X3:
				if(type == TrenchType.PICK)
					return pick3x3;
				else
					return shovel3x3;
			case T5X5:
				if(type == TrenchType.PICK)
					return pick5x5;
				else
					return shovel5x5;
			case T7X7:
				if(type == TrenchType.PICK)
					return pick7x7;
				else
					return shovel7x7;
			default:
				return null;
		}
	}
	private void addItemToList(ItemStack item)
	{
		trenchItems.add(item);
	}
}
