package com.vilsol.inventoryswitch;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class InventorySwitch extends JavaPlugin {
	
	private String prefix = "[" + ChatColor.RED + "IS" + ChatColor.WHITE + "] ";
	
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
		getLogger().info("InventorySwitch by Vilsol Loaded!");
	}
	
	public void onDisable(){
		getLogger().info("InventorySwitch Unloaded!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		boolean alright = false;
	    String cmdn = cmd.getName();
		if(cmdn.equalsIgnoreCase("is")){
			if(args.length >= 1){
				String com = args[0];
				if(args.length == 2){
					if(com.equals("get")){
						if(sender.hasPermission("inventoryswitch.get") || sender.hasPermission("inventoryswitch.del."+args[1])){
							new InventorySwitchGet(args[1], sender);	
						}
						alright = true;
					}
					if(com.equals("set")){
						if(sender.hasPermission("inventoryswitch.set")){
							new InventorySwitchSet(args[1], sender);	
						}
						alright = true;
					}
					if(com.equals("del")){
						if(sender.hasPermission("inventoryswitch.del") || sender.hasPermission("inventoryswitch.del."+args[1])){
							new InventorySwitchDel(args[1], sender);	
						}
						alright = true;
					}
					
				}
				if(com.equals("list")){
					if(sender.hasPermission("inventoryswitch.list")){
						sendList(sender);
					}
					alright = true;
				}
			}
			if(!alright){
				sendHelp(sender);
			}
			return true;
		}
		return false;
	}

	private void sendList(CommandSender sender) {
		File SignFile = new File("plugins/InventorySwitch/", "inventories.yml");
		YamlConfiguration SFile = new YamlConfiguration();
		try {
			SFile.load(SignFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		Set<String> list = null;
		
		InventorySwitchPublic pb = new InventorySwitchPublic();
		boolean pub = pb.isPublic();
		boolean exist = false;
		
		if(pub){
			exist = SFile.isSet("Stacks.Public");
			if(exist){
				list = SFile.getConfigurationSection("Stacks.Public.").getKeys(false);
			}
		}else{
			exist = SFile.isSet("Stacks."+sender.getName());
			if(exist){
				list = SFile.getConfigurationSection("Stacks."+sender.getName()).getKeys(false);
			}
		}
		
		if(exist){
			if(!list.isEmpty()){
				boolean first = true;
				String total = "";
				for(int i = 0; i < list.size(); i ++){
					if(!first){
						total += ", ";
					}
					first = false;
					total += list.toArray()[i];
				}
				sender.sendMessage(prefix + "Available inventories: " + total);
			}else{
				sender.sendMessage(prefix + "None");
			}
		}else{
			sender.sendMessage(prefix + "None");
		}
	}

	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "------------------- InventorySwitch -------------------");
		sender.sendMessage(prefix + "Available subcommands:");
		sender.sendMessage(prefix + ChatColor.GOLD + "get" + ChatColor.WHITE +": Retrieves an inventory!");
		sender.sendMessage(prefix + ChatColor.GOLD + "set" + ChatColor.WHITE +": Saves an inventory!");
		sender.sendMessage(prefix + ChatColor.GOLD + "del" + ChatColor.WHITE +": Deletes an inventory!");
		sender.sendMessage(prefix + "Example command: /is get wool");
		sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------------");
		
	}
}
