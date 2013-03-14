package com.vilsol.inventoryswitch;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class InventorySwitch extends JavaPlugin {
	
	private String prefix = "[" + ChatColor.RED + "IS" + ChatColor.WHITE + "] ";
	
	public void loadConfiguration(){
	    this.getConfig().addDefault("Stacks.wool", "35:1,35:1:1,35:1:2,35:1:3,35:1:4,35:1:5,35:1:6,35:1:7,35:1:8");
	    this.getConfig().options().copyDefaults(true);
	    this.saveConfig();
	}
	
	public void onEnable(){		
		loadConfiguration();
		getLogger().info("InventorySwitch by Vilsol Loaded!");
	}
	
	public void onDisable(){
		getLogger().info("InventorySwitch Unloaded!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	    String cmdn = cmd.getName();
		if(cmdn.equalsIgnoreCase("is")){
			if(args.length >= 1){
				String com = args[0];
				if(args.length == 2){
					if(com.equals("get")){
						if(sender.hasPermission("inventoryswitch.set")){
							new InventorySwitchGet(args[1], sender);	
						}
					}
					if(com.equals("set")){
						if(sender.hasPermission("inventoryswitch.set")){
							new InventorySwitchSet(args[1], sender);	
						}
					}
					if(com.equals("del")){
						if(sender.hasPermission("inventoryswitch.del")){
							new InventorySwitchDel(args[1], sender);	
						}
					}
				}else{
					sendHelp(sender);
				}
			}else{
				sendHelp(sender);
			}
			return true;
		}
	    return false;
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
