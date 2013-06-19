package com.vilsol.inventoryswitch;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class InventorySwitch extends JavaPlugin {
	
	public void loadConfiguration(){
	    this.getConfig().addDefault("Config.isPublic", false);
	    this.getConfig().options().copyDefaults(true);
	    this.saveConfig();
	}
	
	public void onEnable(){		
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
			getLogger().info("Error Submitting stats!");
		}
		
		loadConfiguration();
		getCommand("is").setExecutor(new Commander(this));
		getLogger().info("InventorySwitch by Vilsol Loaded!");
	}
	
	public void onDisable(){
		getLogger().info("InventorySwitch Unloaded!");
	}

}
