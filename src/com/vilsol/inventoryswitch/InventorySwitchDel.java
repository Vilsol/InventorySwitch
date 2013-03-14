package com.vilsol.inventoryswitch;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class InventorySwitchDel {

	private String prefix = "[" + ChatColor.RED + "IS" + ChatColor.WHITE + "] ";
	
	public boolean doesExist(String name){
		File SignFile = new File("plugins/InventorySwitch/", "config.yml");
		YamlConfiguration SFile = new YamlConfiguration();
		try {
			SFile.load(SignFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		if(SFile.isSet("Stacks." + name)){
			return true;
		}else{
			return false;
		}
	}
	
	public InventorySwitchDel(String args, CommandSender sender) {
		if(doesExist(args.toLowerCase())){
			File SignFile = new File("plugins/InventorySwitch/", "config.yml");
			YamlConfiguration SFile = new YamlConfiguration();
			try {
				SFile.load(SignFile);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			SFile.set("Stacks." + args, null);
			
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
