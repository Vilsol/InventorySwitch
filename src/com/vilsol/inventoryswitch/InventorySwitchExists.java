package com.vilsol.inventoryswitch;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class InventorySwitchExists {
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
}
