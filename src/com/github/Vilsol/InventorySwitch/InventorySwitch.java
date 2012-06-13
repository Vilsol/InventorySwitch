package com.github.Vilsol.InventorySwitch;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class InventorySwitch extends JavaPlugin implements Listener {
    
	public static InventorySwitch plugin;
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public void onEnable(){ 
		this.logger.info("InventorySwitch by Vilsol Version 0.1 Enabled!");
    }
     
    public void onDisable(){ 
    	this.logger.info("InventorySwitch by Vilsol Version 0.1 Disabled!");
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	Player p = (Player) sender;
    	Inventory inv = p.getInventory();
    	if(commandLabel.equalsIgnoreCase("is")){
    		ChatColor RED = ChatColor.RED;
    		ChatColor WHITE = ChatColor.WHITE;
    		ChatColor GOLD = ChatColor.GOLD;
    		ChatColor GREEN = ChatColor.GREEN;
    		if(args.length < 1){
    			p.sendMessage(GREEN + "------------------- InventorySwitch -------------------");
    			p.sendMessage("[" + RED + "IS" + WHITE + "] " + "You must choose one of those sets:");
    			p.sendMessage("[" + RED + "IS" + WHITE + "] " + GOLD + "RS" + WHITE +": Everything necessary for redstone contraptions!");
    			p.sendMessage("[" + RED + "IS" + WHITE + "] " + GOLD + "WL" + WHITE +": This will give you different types of wool!");
    			p.sendMessage("[" + RED + "IS" + WHITE + "] " + "You can also use " + GOLD + "CI" + WHITE +" to clear your inventory!");
    			p.sendMessage("[" + RED + "IS" + WHITE + "] " + "Example command: /is RS");
    			p.sendMessage(GREEN + "-----------------------------------------------------");
    		}else if(args[0].toLowerCase().equals("ci")){
    			if(p.hasPermission("inventoryswitch.ci")){
    				inv.clear();
    				p.sendMessage("[" + RED + "IS" + WHITE + "] " + "Your inventory has been cleared!");
    			}else{
    				p.sendMessage("[" + RED + "IS" + WHITE + "] " + "You dont have permsssion to use this!");
    			}
    		}else if(args[0].toLowerCase().equals("rs")){
    			if(p.hasPermission("inventoryswitch.rs")){
	    			inv.clear();
	    			inv.setItem(0, new ItemStack(331, 64));
	    			inv.setItem(1, new ItemStack(76, 64));
	    			inv.setItem(2, new ItemStack(356, 64));
	    			inv.setItem(3, new ItemStack(123, 64));
	    			inv.setItem(4, new ItemStack(69, 64));
	    			inv.setItem(5, new ItemStack(77, 64));
	    			inv.setItem(6, new ItemStack(33, 64));
	    			inv.setItem(7, new ItemStack(29, 64));
	    			inv.setItem(8, new ItemStack(42, 64));
	    			p.sendMessage("[" + RED + "IS" + WHITE + "] " + "You have been given a redstone set!");
    			}else{
    				p.sendMessage("[" + RED + "IS" + WHITE + "] " + "You dont have permsssion to use this!");
    			}
    		}else if(args[0].toLowerCase().equals("wl")){
    			if(p.hasPermission("inventoryswitch.wl")){
	    			inv.setItem(0, new ItemStack(35, 64));
	    			inv.setItem(1, new ItemStack(35, 64, (short)1));
	    			inv.setItem(2, new ItemStack(35, 64, (short)2));
	    			inv.setItem(3, new ItemStack(35, 64, (short)3));
	    			inv.setItem(4, new ItemStack(35, 64, (short)4));
	    			inv.setItem(5, new ItemStack(35, 64, (short)5));
	    			inv.setItem(6, new ItemStack(35, 64, (short)6));
	    			inv.setItem(7, new ItemStack(35, 64, (short)7));
	    			inv.setItem(8, new ItemStack(35, 64, (short)8));
	    			p.sendMessage("[" + RED + "IS" + WHITE + "] " + "You have been given a wool set!");
    			}else{
    				p.sendMessage("[" + RED + "IS" + WHITE + "] " + "You dont have permsssion to use this!");
    			}
    		}
    	}
    	return false;
    }

}
