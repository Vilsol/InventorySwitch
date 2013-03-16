package com.vilsol.inventoryswitch;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class InventorySwitchExists {
	public boolean doesExist(String name, CommandSender sender){
		File SignFile = new File("plugins/InventorySwitch/", "inventories.yml");
		YamlConfiguration SFile = new YamlConfiguration();
		try {
			SFile.load(SignFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		InventorySwitchPublic pb = new InventorySwitchPublic();
		
		boolean set;
		
		if(pb.isPublic()){
			set = SFile.isSet("Stacks.Public." + name);
		}else{
			set = SFile.isSet("Stacks." + sender.getName() + "." + name);
		}
		
		if(set){
			return true;
		}else{
			return false;
		}
	}
}
