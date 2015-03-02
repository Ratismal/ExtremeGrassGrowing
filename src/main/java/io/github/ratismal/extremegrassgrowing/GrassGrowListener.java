package io.github.ratismal.extremegrassgrowing;

import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

public final class GrassGrowListener implements Listener {

	ExtremeGrassGrowing plugin;
	Economy econ;

	public GrassGrowListener(ExtremeGrassGrowing instance) {
		plugin = instance;
		econ = instance.econ;
	}

	@EventHandler
	public void grassSpread(BlockSpreadEvent event) {

		//Bukkit.broadcastMessage("Grass has spread!");
		Block block = event.getBlock();
		Material blockType = block.getType();
		if (blockType == Material.GRASS || blockType == Material.DIRT) {
			List<String> listOfStrings = plugin.pdata.getStringList("list");			
			for(int x = 0; x <= listOfStrings.size() - 1; x++) {
				String inGame = plugin.pdata.getString("data." + listOfStrings.get(x) + ".inGame");
				//Bukkit.broadcastMessage(listOfStrings.get(x));
				if (inGame.equalsIgnoreCase("true")) {
					//Bukkit.broadcastMessage("Grass has spread... AND A GAME IS ACTIVE!");

					String id = listOfStrings.get(x);

					Location loc = block.getLocation();

					loc.setY(loc.getY() + 1);
					Block block2 = loc.getBlock();

					int r = plugin.pdata.getInt("data." + id + ".radius");
					int xCentre = plugin.pdata.getInt("data." + id + ".xCentre");
					int zCentre = plugin.pdata.getInt("data." + id + ".zCentre");

					if (block2.getType() == Material.SIGN_POST && 
							(loc.getX() >= xCentre - r) && (loc.getX() <= xCentre + r) && 
							(loc.getZ() >= zCentre - r) && (loc.getZ() <= zCentre + r)) {
						//Bukkit.broadcastMessage("Gahhh! Someone has been eliminated!!!");
						Sign sign = (Sign) block2.getState();
						String playerName = sign.getLine(1);
						List<String> playerList = plugin.pdata.getStringList("data." + id + ".players");
						playerList.remove(playerName);
						block2.setType(Material.AIR);
						if (playerList.size() == 1) {
							Player winner = gettPlayer(playerList.get(0));
							econ.depositPlayer(winner, plugin.pdata.getDouble("data." + id + ".pool"));
							Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + winner.getDisplayName() + ChatColor.GOLD + " has won a game of Extreme Grass Growing");
							plugin.pdata.set("data." + id + ".isEnabled", "false");
						}
						else {
						}
						plugin.pdata.set("data." + id + ".players", playerList);
						plugin.saveFiles();
					}
				}
			}
		}

	}



	public Player gettPlayer(String name) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getName().equalsIgnoreCase(name))
				return p;
		}
		return null;
	}

}
