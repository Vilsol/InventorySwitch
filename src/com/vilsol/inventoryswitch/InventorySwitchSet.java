package com.vilsol.inventoryswitch;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventorySwitchSet {

	private String prefix = "[" + ChatColor.RED + "IS" + ChatColor.WHITE + "] ";

	public InventorySwitchSet(String args, CommandSender sender) {
		InventorySwitchExists ex = new InventorySwitchExists();
		if(!ex.doesExist(args.toLowerCase(), sender)){
			
			File SignFile = new File("plugins/InventorySwitch/", "inventories.yml");
			YamlConfiguration SFile = new YamlConfiguration();
			try {
				SFile.load(SignFile);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			Player p = (Player) sender;
			Inventory inv = p.getInventory();
			
			String adding = "";
			boolean first = true;
			int queue = 0;
			
			for(int i = 0; i < 40; i++){
				if(inv.getItem(i) != null){
					if(queue > 0){
						for(int z = 0; z < queue+1; z++){
							adding += "0,";
						}
						queue = 0;
					}else{
						if(!first){
							adding += ",";
						}else{
							first = false;
						}
					}
					
					int itemid = inv.getItem(i).getTypeId();
					int amount = inv.getItem(i).getAmount();
					int damage = inv.getItem(i).getDurability();
					if(damage == 0){
						adding += itemid + ":" + amount;
					}else{
						adding += itemid + ":" + amount + ":" + damage;
					}
				}else{
					queue += 1;
				}
			}
			
			InventorySwitchPublic pb = new InventorySwitchPublic();
			
			if(pb.isPublic()){
				SFile.set("Stacks.Public." + args.toLowerCase(), adding);
			}else{
				SFile.set("Stacks." + sender.getName() + "." + args.toLowerCase(), adding);
			}
			
			
			try {
				SFile.save(SignFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sender.sendMessage(prefix + "Inventory " + args + " saved!");
		}else{
			sender.sendMessage(prefix + "That inventory already exists!");
		}
	}

}
