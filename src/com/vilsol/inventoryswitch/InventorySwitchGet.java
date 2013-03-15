package com.vilsol.inventoryswitch;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySwitchGet {
	
	private String prefix = "[" + ChatColor.RED + "IS" + ChatColor.WHITE + "] ";
	
	public InventorySwitchGet(String args, CommandSender sender) {
		InventorySwitchExists ex = new InventorySwitchExists();
		if(ex.doesExist(args.toLowerCase())){
			Player p = (Player) sender;
			Inventory inv = p.getInventory();
			inv.clear();
			
			File SignFile = new File("plugins/InventorySwitch/", "config.yml");
			YamlConfiguration SFile = new YamlConfiguration();
			try {
				SFile.load(SignFile);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			String getting = SFile.getString("Stacks." + args.toLowerCase());
			String[] split = getting.split(",");
			
			for(int i = 0; i < split.length; i++){
				String[] dual = split[i].split(":");
				if(dual.length >= 2){
					if(dual.length == 3){
						inv.setItem(i, new ItemStack(Integer.parseInt(dual[0]), Integer.parseInt(dual[1]), (short) Integer.parseInt(dual[2])));
					}else{
						inv.setItem(i, new ItemStack(Integer.parseInt(dual[0]), Integer.parseInt(dual[1])));
					}
				}
			}
			sender.sendMessage(prefix + "You have been given the " + args + " inventory!");
		}else{
			sender.sendMessage(prefix + "That inventory doesn't exist!");
		}
	}

}
