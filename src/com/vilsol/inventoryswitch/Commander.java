package com.vilsol.inventoryswitch;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commander implements CommandExecutor {

	@SuppressWarnings("unused")
	private InventorySwitch plugin;
	
	public Commander(InventorySwitch inventorySwitch) {
		this.plugin = inventorySwitch;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean alright = false;
		if(args.length >= 1){
		    InventorySwitchSystem sys = new InventorySwitchSystem();
			String com = args[0];
			if(args.length == 2){
				if(com.equals("get")){
					if(sender.hasPermission("inventoryswitch.get") || sender.hasPermission("inventoryswitch.del."+args[1])){
						sys.get(args[1], sender);	
					}
					alright = true;
				}
				if(com.equals("set")){
					if(sender.hasPermission("inventoryswitch.set")){
						sys.set(args[1], sender);	
					}
					alright = true;
				}
				if(com.equals("del")){
					if(sender.hasPermission("inventoryswitch.del") || sender.hasPermission("inventoryswitch.del."+args[1])){
						sys.del(args[1], sender);	
					}
					alright = true;
				}
				
			}
			if(com.equals("list")){
				if(sender.hasPermission("inventoryswitch.list")){
					sys.sendList(sender);
				}
				alright = true;
			}
		}
		if(!alright){
		    InventorySwitchSystem sys = new InventorySwitchSystem();
			sys.sendHelp(sender);
		}
		return true;
	}

}
