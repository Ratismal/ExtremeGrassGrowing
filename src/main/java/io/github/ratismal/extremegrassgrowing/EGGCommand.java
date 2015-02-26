package io.github.ratismal.extremegrassgrowing;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EGGCommand implements CommandExecutor {

	
	ExtremeGrassGrowing plugin;
	EGGGame egg;
	
	public EGGCommand (ExtremeGrassGrowing instance) {
		plugin = instance;
		egg = new EGGGame(instance);
	}
	/*
	public EGGCommand (ExtremeGrassGrowing instance) {
		plugin = instance;
	}
	*/
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("extremegg") && (args.length == 0)) {
			sender.sendMessage(ChatColor.GOLD + "|======[EGG]======|");
			sender.sendMessage(ChatColor.GOLD + "/extremegg - Display this menu");
			sender.sendMessage(ChatColor.GOLD + "/extremegg-create <id> <radius> - Create a game of EGG");
			sender.sendMessage(ChatColor.GOLD + "/extremegg-start <id> - Start the game");
			sender.sendMessage(ChatColor.GOLD + "/extremegg-join <id> <bet> - Join a game");
			sender.sendMessage(ChatColor.GOLD + "/extremegg-list - List games");
			sender.sendMessage(ChatColor.GOLD + "/extremegg-remove <id> - Remove a game");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("extremegg-create") && (args.length == 2)) {
			if (sender instanceof Player) {
			
			egg.createGame(args[0], sender, Integer.parseInt(args[1]));
			
			}
			else {
				sender.sendMessage(ChatColor.DARK_RED + "Only players can use this command!");
			}
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("extremegg-list") && (args.length == 0)) {
			
			egg.listGames(sender);
			return true;
			
		}
		if (cmd.getName().equalsIgnoreCase("extremegg-remove") && (args.length == 1)) {
			
			
			egg.removeGame(sender, args[0]);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("extremegg-join") && (args.length == 2)) {
			if (sender instanceof Player) {
			double bet = Double.parseDouble(args[1]);
			egg.joinGame(sender, args[0], bet);
		}
		else {
			sender.sendMessage(ChatColor.DARK_RED + "Only players can use this command!");
		}
			return true;
		}	
		if (cmd.getName().equalsIgnoreCase("extremegg-start") && (args.length == 1)) {
			
			egg.startGame(sender, args[0]);
			return true;
			
		}
		
		
		return false;
	}
	
}
