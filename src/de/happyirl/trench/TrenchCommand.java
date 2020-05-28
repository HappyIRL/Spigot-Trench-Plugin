package de.happyirl.trench;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.happyirl.trench.CustomCommand;
import de.happyirl.trench.TrenchingItems.TrenchAmount;
import de.happyirl.trench.TrenchingItems.TrenchType;

public class TrenchCommand extends CustomCommand
{
	private String type;
	private TrenchingItems trenchingItems;
	
	public TrenchCommand(TrenchPlugin trenchPlugin, TrenchingItems trenchingItems)
	{ 
		//super(trenchPlugin.getConfig());
		this.trenchingItems = trenchingItems;
		permission = "permission.trench";//config.getString("permission.trench");
        playerOnly = true;
        parameters = 2;
	}
	@Override
	public void execute(Player source, String[] args)
	{
		String arg0 = args[0].toLowerCase();
		if (args[1] != null)
		{
			String arg1 = args[1].toLowerCase();
			type = arg0 + " " + arg1;
		}
		else
		{
			type = arg0;
		}
		
		switch(type) 
		{
			case "pick 3x3":
				givePlayerItem(source, TrenchAmount.T3X3, TrenchType.PICK);
				break;
			case "pick 5x5":
				givePlayerItem(source, TrenchAmount.T5X5, TrenchType.PICK);
				break;
			case "pick 7x7":
				givePlayerItem(source, TrenchAmount.T7X7, TrenchType.PICK);
				break;
			case "shovel 3x3":
				givePlayerItem(source, TrenchAmount.T3X3, TrenchType.SHOVEL);
				break;
			case "shovel 5x5":
				givePlayerItem(source, TrenchAmount.T5X5, TrenchType.SHOVEL);
				break;
			case "shovel 7x7":
				givePlayerItem(source, TrenchAmount.T7X7, TrenchType.SHOVEL);
				break;
			case "help":
				
				break;
			default:
				
				break;
		}
	}
	private void givePlayerItem(Player source, TrenchAmount amount, TrenchType type)
	{
		source.getInventory().addItem(trenchingItems.getTrenchItem(amount, type));
	}
}
