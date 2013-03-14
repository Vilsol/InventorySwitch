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
		if(ex.doesExist(args.toLowerCase())){
			
			File SignFile = new File("plugins/InventorySwitch/", "config.yml");
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
			
			for(int i = 0; i < 40; i++){
				if(!first){
					adding += ",";
				}else{
					first = false;
				}
				if(inv.getItem(i) != null){
					int itemid = inv.getItem(i).getTypeId();
					int amount = inv.getItem(i).getAmount();
					int damage = inv.getItem(i).getDurability();
					if(damage == 0){
						adding += itemid + ":" + amount;
					}else{
						adding += itemid + ":" + amount + ":" + damage;
					}
				}else{
					adding += "0:0";
				}
			}
			
			SFile.set("Stacks." + args, adding);
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
