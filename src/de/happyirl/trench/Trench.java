package de.happyirl.trench;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class Trench implements Listener
{
	private List<Block> blocks = new ArrayList<Block>();
	
	private Material[] breakablePick = new Material[]{Material.COBBLESTONE, Material.STONE, 
			Material.NETHERRACK, Material.ENDER_STONE, Material.REDSTONE_ORE,
			Material.LAPIS_ORE, Material.GOLD_ORE, Material.IRON_ORE, Material.COAL_ORE, Material.SANDSTONE, Material.RED_SANDSTONE};
	
	private Material[] breakableSpade = new Material[] {Material.DIRT, Material.MYCEL, 
			Material.SNOW_BLOCK, Material.SAND, Material.GRAVEL, Material.GRASS};
	
	private int[] x = new int[] {1,1,1,0,0,-1,-1,-1,
	/*5x5*/							2,2,2,2,2,1,1,0,0,-1,-1,-2,-2,-2,-2,-2,
	/*7x7*/							3,3,3,3,3,3,3,2,2,1,1,0,0,-1,-1,-2,-2,-3,-3,-3,-3,-3,-3,-3};
	
	private int[] y = new int[] {0,0,0,0,0,0,0,0,
	/*5x5*/							0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
	/*7x7*/							0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	private int[] z = new int[] {1,0,-1,1,-1,1,0,-1,
	/*5x5*/							2,1,0,-1,-2,2,-2,2,-2,2,-2,2,1,0,-1,-2,
	/*7x7*/							3,2,1,0,-1,-2,-3,3,-3,3,-3,3,-3,3,-3,3,-3,3,2,1,0,-1,-2,-3};
	
	private boolean isPick = false;
	
	Player player;
	
	public Trench(BlockBreakEvent event, Boolean isPick, NBTTagCompound inHandTag)
	{
		this.isPick = isPick;
		this.player = event.getPlayer();
		determineDirection(event,getTrenchAmount(inHandTag));
	}
	
	private BlockFace getBlockFace(Player player) 
	{
	    List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks((Set<Material>)null, 100);
	    if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding()) return null;
	    Block targetBlock = lastTwoTargetBlocks.get(1);
	    Block adjacentBlock = lastTwoTargetBlocks.get(0);
	    return targetBlock.getFace(adjacentBlock);
	}
	
	
	private void breakBlocks(int[]x , int[]y ,int[]z, int blocksInRadius, Block brokenBlock)
	{
		for(int i = 0; i < blocksInRadius;i++)
		{
			blocks.add(brokenBlock.getRelative(x[i],y[i],z[i]));
		}
		
		Material[] toCheck;
		
		if(isPick)
			toCheck = breakablePick;
		else
			toCheck = breakableSpade;
		
		for(Block block : blocks)
		{
			Material blockMaterial = block.getType();
			
			for(Material material : toCheck)
			{
				if(material.equals(blockMaterial))
				{
					Collection<ItemStack> drops = block.getDrops();
					for(ItemStack item : drops)
					{
						player.getInventory().addItem(item);
					}
					block.setType(Material.AIR);
				}
			}
		}
		blocks.clear();
	}
	
	private int getTrenchAmount(NBTTagCompound tag)
	{
		String amount = tag.getString("trench");
		
		switch(amount)
		{
			case "3x3":
				return 8;
			case "5x5":
				return 24;
			case "7x7":
				return 48;
			default:
				return 0;
		}
	}
	
	private void determineDirection(BlockBreakEvent event, int amount)
	{
		Block brokenBlock = event.getBlock();
		BlockFace blockFace = getBlockFace(player);
		if(blockFace == null)
		{
			return;
		}
		switch(blockFace)
		{
			case NORTH:
				breakBlocks(z,x,y,amount,brokenBlock);
				break;
			case EAST:
				breakBlocks(y,x,z,amount,brokenBlock);
				break;
			case SOUTH:
				breakBlocks(z,x,y,amount,brokenBlock);
				break;
			case WEST:
				breakBlocks(y,x,z,amount,brokenBlock);
				break;
			case UP:
				breakBlocks(x,y,z,amount,brokenBlock);
				break;
			case DOWN:
				breakBlocks(x,y,z,amount,brokenBlock);
				break;
			default:
				break;
		}
	}
}
