package com.vilsol.inventoryswitch;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class InventorySwitchDel {

	private String prefix = "[" + ChatColor.RED + "IS" + ChatColor.WHITE + "] ";
	
	public InventorySwitchDel(String args, CommandSender sender) {
		InventorySwitchExists ex = new InventorySwitchExists();
		if(ex.doesExist(args.toLowerCase(), sender)){
			File SignFile = new File("plugins/InventorySwitch/", "inventories.yml");
			YamlConfiguration SFile = new YamlConfiguration();
			try {
				SFile.load(SignFile);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			InventorySwitchPublic pb = new InventorySwitchPublic();
			
			if(pb.isPublic()){
				SFile.set("Stacks.Public." + args, null);
			}else{
				SFile.set("Stacks." + sender.getName() + "." + args, null);
			}
			
			try {
				SFile.save(SignFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			sender.sendMessage(prefix + "Inventory " + args + " has been deleted!");
		}else{
			sender.sendMessage(prefix + "That inventory doesn't exist!");
		}
	}
	
}
