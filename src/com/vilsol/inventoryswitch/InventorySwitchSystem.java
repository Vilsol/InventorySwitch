package com.vilsol.inventoryswitch;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySwitchSystem {
	
	public File SignFile;
	public String prefix = "[" + ChatColor.RED + "IS" + ChatColor.WHITE + "] ";
	public YamlConfiguration SFile;
	
	public InventorySwitchSystem(){
		SignFile =  new File("plugins/InventorySwitch/", "config.yml");
		SFile = new YamlConfiguration();
		try {
			SFile.load(SignFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void saveFile(){
		try {
			SFile.save(SignFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isPublic(){
		return SFile.getBoolean("Config.isPublic");
	}
	
	public boolean doesExist(String name, CommandSender sender){
		
		boolean set;
		
		if(isPublic()){
			set = SFile.isSet("Stacks.Public." + name);
		}else{
			set = SFile.isSet("Stacks." + sender.getName() + "." + name);
		}
		
		return set;
	}
	
	public void del(String args, CommandSender sender) {
		if(doesExist(args.toLowerCase(), sender)){
			
			if(isPublic()){
				SFile.set("Stacks.Public." + args.toLowerCase(), null);
			}else{
				SFile.set("Stacks." + sender.getName() + "." + args.toLowerCase(), null);
			}	
			saveFile();	
			sender.sendMessage(prefix + "Inventory " + args + " has been deleted!");
		}else{
			sender.sendMessage(prefix + "That inventory doesn't exist!");
		}
	}
	
	public void get(String args, CommandSender sender) {
		if(doesExist(args.toLowerCase(), sender)){
			Player p = (Player) sender;
			Inventory inv = p.getInventory();
			inv.clear();
			
			String getting = "";
			
			if(isPublic()){
				getting = SFile.getString("Stacks.Public." + args.toLowerCase());
			}else{
				getting = SFile.getString("Stacks." + sender.getName() + "." + args.toLowerCase());
			}
			
			String[] split = getting.split(",");
			
			for(int i = 0; i < split.length; i++){
				String[] splitter = split[i].split(":");
				if(splitter.length >= 2){
					if(splitter.length >= 3){
						String[] envs = splitter[2].split("\\.");
						if(envs.length >= 2){
							if(envs.length == 2){
								String enchant = envs[0];
								int lebel = Integer.parseInt(envs[1]);
								inv.setItem(i, new ItemStack(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1])));
								inv.getItem(i).addUnsafeEnchantment(Enchantment.getByName(enchant), lebel);
							}else{
								String[] enchants = splitter[2].split("\\|");
								Map<Enchantment, Integer> adde = new HashMap<Enchantment, Integer>();
								for(int sete = 0; sete < enchants.length; sete++){
									String[] encha = enchants[sete].split("\\.");
									String enchant = encha[0];
									int lebel = Integer.parseInt(encha[1]);
									inv.setItem(i, new ItemStack(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1])));
									adde.put(Enchantment.getByName(enchant),lebel);
								}
								inv.getItem(i).addUnsafeEnchantments(adde);
							}
						}else{
							if(splitter.length == 4){
								envs = splitter[3].split("\\.");
								if(envs.length == 2){
									String enchant = envs[0];
									int lebel = Integer.parseInt(envs[1]);
									inv.setItem(i, new ItemStack(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1])));
									inv.getItem(i).addUnsafeEnchantment(Enchantment.getByName(enchant), lebel);
								}else{
									String[] enchants = splitter[2].split("\\|");
									Map<Enchantment, Integer> adde = new HashMap<Enchantment, Integer>();
									for(int sete = 0; sete < enchants.length; sete++){
										String[] encha = enchants[sete].split("\\.");
										String enchant = encha[0];
										int lebel = Integer.parseInt(encha[1]);
										inv.setItem(i, new ItemStack(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1])));
										adde.put(Enchantment.getByName(enchant),lebel);
									}
									inv.getItem(i).addUnsafeEnchantments(adde);
								}
							}else{
								inv.setItem(i, new ItemStack(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]), (short) Integer.parseInt(splitter[2])));
							}
						}
					}else{
						inv.setItem(i, new ItemStack(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1])));
					}
				}
			}
			sender.sendMessage(prefix + "You have been given the " + args + " inventory!");
		}else{
			sender.sendMessage(prefix + "That inventory doesn't exist!");
		}
	}
	
	public void set(String args, CommandSender sender) {
		if(!doesExist(args.toLowerCase(), sender)){
			
			Player p = (Player) sender;
			Inventory inv = p.getInventory();
			
			String adding = "";
			boolean first = true;
			int queue = 0;
			
			for(int i = 0; i < 40; i++){
				if(inv.getItem(i) != null){
					if(queue > 0){
						adding += ",";
						for(int z = 0; z < queue; z++){
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
	
					if(inv.getItem(i).getEnchantments().size() > 0){
						adding += ":";
						int counter = 1;
						for(Entry<Enchantment, Integer> entry : inv.getItem(i).getEnchantments().entrySet()){
							if(counter > 1){
								adding += "|";
							}
							adding += entry.getKey().getName() + "." + entry.getValue();
							counter++;
						}
					}
				}else{
					queue += 1;
				}
			}
			
			if(isPublic()){
				SFile.set("Stacks.Public." + args.toLowerCase(), adding);
			}else{
				SFile.set("Stacks." + sender.getName() + "." + args.toLowerCase(), adding);
			}
			
			saveFile();
			
			sender.sendMessage(prefix + "Inventory " + args + " saved!");
		}else{
			sender.sendMessage(prefix + "That inventory already exists!");
		}
	}
	
	public void sendList(CommandSender sender) {
		File SignFile = new File("plugins/InventorySwitch/", "inventories.yml");
		YamlConfiguration SFile = new YamlConfiguration();
		try {
			SFile.load(SignFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		Set<String> list = null;
		
		InventorySwitchSystem sys = new InventorySwitchSystem();
		boolean pub = sys.isPublic();
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

	public void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "------------------- InventorySwitch -------------------");
		sender.sendMessage(prefix + "Available subcommands:");
		sender.sendMessage(prefix + ChatColor.GOLD + "get" + ChatColor.WHITE +": Retrieves an inventory!");
		sender.sendMessage(prefix + ChatColor.GOLD + "set" + ChatColor.WHITE +": Saves an inventory!");
		sender.sendMessage(prefix + ChatColor.GOLD + "del" + ChatColor.WHITE +": Deletes an inventory!");
		sender.sendMessage(prefix + "Example command: /is get wool");
		sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------------");
	}
	
}
