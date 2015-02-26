package io.github.ratismal.extremegrassgrowing;

import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EGGGame {
	
	
	private Logger log = Logger.getLogger("Minecraft");
	ExtremeGrassGrowing plugin;
	Economy econ;
	
	
	
	public EGGGame(ExtremeGrassGrowing instance) {
		plugin = instance;
		econ = instance.econ;
		}
	
	public void createGame(String id, CommandSender sender, int r) {
		
		log.info("someone is creating a game");
		List<String> listOfStrings = plugin.pdata.getStringList("list");
		//String[] stringArray = listOfStrings.toArray(new String[listOfStrings.size()]);
		
		//check if duplicate
		for(int x = 0; x <= listOfStrings.size() - 1; x++) {
			if (listOfStrings.get(x).equals(id)){
				
			sender.sendMessage(ChatColor.DARK_RED + "There is already a game with that ID!");
			return;
			}
		}
		
		
		Player player = (Player) sender;
		Location loc = player.getLocation();
		loc.setY(loc.getY() - 1);
		String worldName = loc.getWorld().getName();
		int xCentre = loc.getBlockX();
		int yCentre = loc.getBlockY();
		int zCentre = loc.getBlockZ();
		String standHere = plugin.getConfig().getString("configs.block");
		standHere = standHere.replaceAll(" ", "_").toUpperCase();
		if (loc.getBlock().getType() == Material.getMaterial(standHere)) {
			sender.sendMessage("Generating EGG arena");

			//set border
			for(int x = ((r+1) * -1); x <= (r+1); x++) {
	            for(int z = ((r+1) * -1); z <= (r+1); z++) {
	            
	            	// Grab the current block
	                Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z);
	 
	                    b.setType(Material.GOLD_BLOCK);
	                
	            }
	        }
			
			Block diamond0 = loc.getWorld().getBlockAt(loc.getBlockX() + ((r+1) * -1), loc.getBlockY(), loc.getBlockZ());
			diamond0.setType(Material.DIAMOND_BLOCK);
			Block diamond1 = loc.getWorld().getBlockAt(loc.getBlockX() + ((r+1) * 1), loc.getBlockY(), loc.getBlockZ());
			diamond1.setType(Material.DIAMOND_BLOCK);
			Block diamond2 = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() + ((r+1) * -1));
			diamond2.setType(Material.DIAMOND_BLOCK);
			Block diamond3 = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() + ((r+1) * 1));
			diamond3.setType(Material.DIAMOND_BLOCK);
			
			//set dirt
			for(int x = (r * -1); x <= r; x++) {
		            for(int z = (r * -1); z <= r; z++) {
		            
		            	// Grab the current block
		                Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z);
		 
		                    b.setType(Material.DIRT);
		                
		            }
		        }
			
			//set air
			for(int x = ((r+1) * -1); x <= (r+1); x++) {
		        for(int y = 1; y <= 4; y++) {
		            for(int z = ((r+1) * -1); z <= (r+1); z++) {
		                // Grab the current block
		                Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
		 
		                    b.setType(Material.AIR);
		            
		        }
		    }
			}
			
			//List<String> list = Arrays.asList("");
			
			plugin.pdata.set("data." + id, id);
			plugin.pdata.set("data." + id + ".xCentre", xCentre);
			plugin.pdata.set("data." + id + ".yCentre", yCentre);
			plugin.pdata.set("data." + id + ".zCentre", zCentre);
			plugin.pdata.set("data." + id + ".radius", r);
			plugin.pdata.set("data." + id + ".inGame", "no");
			//plugin.pdata.set("data." + id + ".players", list);
			plugin.pdata.set("data." + id + ".pool", 0);
			plugin.pdata.set("data." + id + ".world", worldName);
			List<String> playerList = plugin.pdata.getStringList("list");
			playerList.add(id);
			plugin.pdata.set("list", playerList);
			
			
			plugin.saveFiles();
		}
		
		
		
	}
	
	public void listGames(CommandSender sender) {
		
		List<String> listOfStrings = plugin.pdata.getStringList("list");
		//read list
			for (int x = 0; x <= listOfStrings.size() - 1; x++) {
				if (listOfStrings.get(x) != "") {
					sender.sendMessage(ChatColor.GOLD + "" + (x+1) + ": " + listOfStrings.get(x));
				}
			}
			return;
	}
	
	public void removeGame(CommandSender sender, String id) {
		
		List<String> listOfStrings = plugin.pdata.getStringList("list");
		//read list
		boolean canStart = false;
		
		for(int x = 0; x <= listOfStrings.size() - 1; x++) {
			if (listOfStrings.get(x).equals(id)){
				
			sender.sendMessage(ChatColor.GOLD + "Removing game with id: " + ChatColor.AQUA + id);
			canStart = true;
			}
		}
		if (canStart) {
		
			/*
			for (int x = 0; x <= listOfStrings.size() - 1; x++) {
				Bukkit.broadcastMessage(listOfStrings.get(x));
				if (listOfStrings.get(x) == id) {
					Bukkit.broadcastMessage("Removing " + listOfStrings.get(x));
					listOfStrings.remove(x);
					plugin.pdata.set("list", listOfStrings);
					plugin.saveFiles();
				}
			}
			*/
			
			listOfStrings.remove(id);
			plugin.pdata.set("list", listOfStrings);
			
			int blockX = plugin.pdata.getInt("data." + id + ".xCentre");
			int blockY = plugin.pdata.getInt("data." + id + ".yCentre");
			int blockZ = plugin.pdata.getInt("data." + id + ".zCentre");
			int r = plugin.pdata.getInt("data." + id + ".radius");
			String worldName = plugin.pdata.getString("data." + id + ".world");
			
			World world = Bukkit.getWorld(worldName);
			Location loc = new Location(world, blockX, blockY, blockZ);
			
			for(int x = ((r+1) * -1); x <= (r+1); x++) {
	            for(int z = ((r+1) * -1); z <= (r+1); z++) {
	            //Bukkit.broadcastMessage("(" + (loc.getBlockX() + x) + "," + (loc.getBlockZ() + z) + ")");
	            	// Grab the current block
	            	int xX = blockX + x;
	            	int zZ = blockZ + z;
	            	//Bukkit.broadcastMessage("(" + xX + "," + zZ + ")");
	                Block b = loc.getWorld().getBlockAt(xX, blockY, zZ);
	 
	                    b.setType(Material.AIR);
	                
	            }
	        }
			
			plugin.pdata.set("data." + id, null);
			plugin.saveFiles();
		}
		else {
			sender.sendMessage(ChatColor.DARK_RED + "There's no game with that ID!");
		}
			return;
	}
	
	public void joinGame(CommandSender sender, String id, double bet) {
		
		log.info("someone is joining a game");
		
		List<String> playerList = plugin.pdata.getStringList("data." + id + ".players");
		
		List<String> listOfStrings = plugin.pdata.getStringList("list");
		Player player = (Player) sender;
		if (playerList.contains(player.getName())) {
			sender.sendMessage(ChatColor.DARK_RED + "You are already in this game!");
			return;
		}
		//check if has enough money to make bet
		if (bet > econ.getBalance(player)) {
			sender.sendMessage(ChatColor.DARK_RED + "You don't have enough money for that!");
			return;
		}
		boolean canJoin = false;
		//check if existing game
		
		for(int x = 0; x <= listOfStrings.size() - 1; x++) {
			if (listOfStrings.get(x).equals(id)){
				
			sender.sendMessage(ChatColor.GOLD + "Joining game with id: " + ChatColor.AQUA + id);
			canJoin = true;
			}
		}
		if (canJoin == true) {
			
		
		Location loc = player.getLocation();
		
		int r = plugin.pdata.getInt("data." + id + ".radius");
		int xCentre = plugin.pdata.getInt("data." + id + ".xCentre");
		int zCentre = plugin.pdata.getInt("data." + id + ".zCentre");
		/*
		log.info(xCentre + " " + zCentre + " " + r + " ");
		log.info(loc.getX() + " " + loc.getY() + " " + loc.getZ());
		log.info((xCentre - r) + " " + (xCentre + r) + " " + (zCentre - r) + " " + (zCentre + r));
		*/

		if ((loc.getX() >= xCentre - r) && (loc.getX() <= xCentre + (r+1)) && 
				(loc.getZ() >= zCentre - r) && (loc.getZ() <= zCentre + (r+1))) {
			
			Block entry = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
			/*
			if (entry.getType() == Material.SIGN_POST) {
				sender.sendMessage(ChatColor.RED + "Someone is already there!");
				return;
			}
			*/
			entry.setType(Material.SIGN_POST);
			//BlockState state = entry.getState();
			Sign sign = (Sign) entry.getState();
			sign.setLine(1, player.getName());
			sign.setLine(2, "" + bet);
			sign.update();
			econ.withdrawPlayer(player, bet);
		}
		else {
			sender.sendMessage(ChatColor.DARK_RED + "You are not within the game zone!");
			return;
		}
		
		List<String> list2 = plugin.pdata.getStringList("data." + id + ".players");
		list2.add(player.getName());
		plugin.pdata.set("data." + id + ".players", list2);
		plugin.pdata.set("data." + id + ".pool", plugin.pdata.getDouble("data." + id + ".pool") + bet);
		plugin.saveFiles();
		
		return;
		
		}
		else {
			sender.sendMessage(ChatColor.DARK_RED + "No game available with that ID!");
			canJoin = false;
			return;
		}

	}
	
	public void startGame(CommandSender sender, String id) {
		
		List<String> listOfStrings = plugin.pdata.getStringList("list");
		
		boolean canStart = false;
		
		for(int x = 0; x <= listOfStrings.size() - 1; x++) {
			if (listOfStrings.get(x).equals(id)){
				
			sender.sendMessage(ChatColor.GOLD + "Starting game with id: " + ChatColor.AQUA + id);
			canStart = true;
			}
		}
		if (canStart) {
		
		List<String> playerList = plugin.pdata.getStringList("data." + id + ".players");
		if (playerList.size() < 2) {
			sender.sendMessage(ChatColor.DARK_RED + "Cannot make a game of less than two!");
			return;
		}
		
			
		int blockX = plugin.pdata.getInt("data." + id + ".xCentre");
		int blockY = plugin.pdata.getInt("data." + id + ".yCentre");
		int blockZ = plugin.pdata.getInt("data." + id + ".zCentre");
		int r = plugin.pdata.getInt("data." + id + ".radius");
		String worldName = plugin.pdata.getString("data." + id + ".world");
		
		World world = Bukkit.getWorld(worldName);
		Location loc = new Location(world, blockX, blockY, blockZ);

		Block diamond0 = loc.getWorld().getBlockAt(blockX + ((r+1) * -1), blockY, blockZ);
		diamond0.setType(Material.GRASS);
		Block diamond1 = loc.getWorld().getBlockAt(blockX + ((r+1) * 1), blockY, blockZ);
		diamond1.setType(Material.GRASS);
		Block diamond2 = loc.getWorld().getBlockAt(blockX, blockY, blockZ + ((r+1) * -1));
		diamond2.setType(Material.GRASS);
		Block diamond3 = loc.getWorld().getBlockAt(blockX, blockY, blockZ + ((r+1) * 1));
		diamond3.setType(Material.GRASS);
		
		plugin.pdata.set("data." + id + ".inGame", true);
		plugin.saveFiles();
		return;
		}
		else {
			sender.sendMessage(ChatColor.DARK_RED + "No game available with that ID!");
			return;
		}
	}

}
