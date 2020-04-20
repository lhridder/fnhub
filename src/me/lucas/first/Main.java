package me.lucas.first;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public static Main plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public static int currentLine = 0;
	public static int tid = 0;
	public static int running = 1;
	public static long interval = 120;
	
	
	
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getConfig().addDefault("messages","FNhub/hubmessages.txt");
		getConfig().addDefault("lockdown","false");
		getConfig().addDefault("lockreason","");
		getConfig().options().copyDefaults(true);
		saveConfig();
		System.out.println("[FNhub] by TYSluukie is enabled!");
		tid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				try {
					broadcastMessage("plugins/" + getConfig().getString("messages"));
				}	catch (IOException e) {
					
				}
			}
		}, 0, interval * 20);
	}
	
	public static void broadcastMessage(String fileName) throws IOException {
		FileInputStream fs;
		fs = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for(int i = 0; i < currentLine; ++i)
			br.readLine();
		String line = br.readLine();
		br.close();
		line = line.replaceAll("&f", ChatColor.WHITE + "");
	    line = line.replaceAll("&0", ChatColor.BLACK + "");
	    line = line.replaceAll("&1", ChatColor.DARK_BLUE + "");
	    line = line.replaceAll("&2", ChatColor.DARK_GREEN + "");
	    line = line.replaceAll("&3", ChatColor.DARK_AQUA + "");
	    line = line.replaceAll("&4", ChatColor.DARK_RED + "");
	    line = line.replaceAll("&5", ChatColor.DARK_PURPLE + "");
	    line = line.replaceAll("&6", ChatColor.GOLD + "");
	    line = line.replaceAll("&7", ChatColor.GRAY + "");
	    line = line.replaceAll("&8", ChatColor.DARK_GRAY + "");
	    line = line.replaceAll("&9", ChatColor.BLUE + "");
	    line = line.replaceAll("&a", ChatColor.GREEN + "");
	    line = line.replaceAll("&b", ChatColor.AQUA + "");
	    line = line.replaceAll("&c", ChatColor.RED + "");
	    line = line.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
	    line = line.replaceAll("&e", ChatColor.YELLOW + "");
	    line = line.replaceAll("&f", ChatColor.WHITE + "");
	    line = line.replaceAll("&k", ChatColor.MAGIC + "");
	    line = line.replaceAll("&l", ChatColor.BOLD + "");
	    line = line.replaceAll("&m", ChatColor.STRIKETHROUGH + "");
	    line = line.replaceAll("&n", ChatColor.UNDERLINE + "");
	    line = line.replaceAll("&o", ChatColor.ITALIC + "");
	    line = line.replaceAll("&r", ChatColor.RESET + "");
	    line = line.replaceAll("$n", "\n" + "");
		Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[FlameNetwork] " + ChatColor.WHITE + line);
		LineNumberReader lnr = new LineNumberReader(new FileReader(new File(fileName)));
		lnr.skip(Long.MAX_VALUE);
		int lastLine = lnr.getLineNumber();
	    lnr.close();
		if(currentLine + 1 == lastLine + 1) {
			currentLine = 0;
		} else {
			currentLine++;
		}
	}
	

	@Override
	public void onDisable() {
		System.out.println("[FNhub] by TYSluukie is disabled!");
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent p) {
		if(getConfig().getString("lockdown").equals("true")){
			if (p.getPlayer().hasPermission("lockdown.bypass")) {
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + p.getPlayer().getName() + " heeft de lockdown gebypassed!");
			} else {
				p.getPlayer().kickPlayer(ChatColor.DARK_RED + getConfig().getString("lockreason"));
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + p.getPlayer().getName() + " heeft gefaald om langs de lockdown te komen!");
			}
		}
		p.getPlayer().sendMessage(ChatColor.GOLD +"==============" + ChatColor.DARK_RED + "+FlameNetwork+" + ChatColor.GOLD + "===============");
		p.getPlayer().sendMessage("");
		p.getPlayer().sendMessage(ChatColor.BLUE + "   Welkom op " + ChatColor.DARK_RED + "FlameNetwork!");
		p.getPlayer().sendMessage("");
		p.getPlayer().sendMessage(ChatColor.GOLD + "   - Join een server via de portals of je compass!");
		p.getPlayer().sendMessage(ChatColor.GOLD + "   - /help voor meer informatie!");
		p.getPlayer().sendMessage(ChatColor.GOLD + "   - Doe /staffteam om de staff te zien!");
		p.getPlayer().sendMessage("");
		p.getPlayer().sendMessage(ChatColor.GOLD +"==============" + ChatColor.DARK_RED + "+FlameNetwork+" + ChatColor.GOLD + "===============");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("help")) {
			sender.sendMessage(ChatColor.GOLD +"==================" + ChatColor.DARK_RED + "+FlameNetwork+" + ChatColor.GOLD + "===================");
			sender.sendMessage(ChatColor.BLUE + "   Hoofd command's:");
			sender.sendMessage(ChatColor.GOLD + "   - /kitpvp" + ChatColor.AQUA + " Ga naar KitPvP!");
			sender.sendMessage(ChatColor.GOLD + "   - /kingdom" + ChatColor.AQUA + " Ga naar Kingdom!");
			sender.sendMessage(ChatColor.GOLD + "   - /spawn" + ChatColor.AQUA + " Ga terug naar het midden van de hub!");
			sender.sendMessage(ChatColor.GOLD + "   - /staffteam" + ChatColor.AQUA + " Zie onze staff!");
			sender.sendMessage(ChatColor.GOLD +"==================" + ChatColor.DARK_RED + "+FlameNetwork+" + ChatColor.GOLD + "===================");
			} else if(cmd.getName().equalsIgnoreCase("?")) {
				sender.sendMessage(ChatColor.GOLD +"==================" + ChatColor.DARK_RED + "+FlameNetwork+" + ChatColor.GOLD + "====================");
				sender.sendMessage(ChatColor.BLUE + "   Hoofd command's:");
				sender.sendMessage(ChatColor.GOLD + "   - /kitpvp" + ChatColor.AQUA + " Ga naar KitPvP!");
				sender.sendMessage(ChatColor.GOLD + "   - /kingdom" + ChatColor.AQUA + " Ga naar Kingdom!");
				sender.sendMessage(ChatColor.GOLD + "   - /spawn" + ChatColor.AQUA + " Ga terug naar het midden van de hub!");
				sender.sendMessage(ChatColor.GOLD + "   - /staffteam" + ChatColor.AQUA + " Zie onze staff!");
				sender.sendMessage(ChatColor.GOLD +"==================" + ChatColor.DARK_RED + "+FlameNetwork+" + ChatColor.GOLD + "====================");
				} else if(cmd.getName().equalsIgnoreCase("staffteam")) {
					sender.sendMessage(ChatColor.GOLD +"==================" + ChatColor.DARK_RED + "+FlameNetwork+" + ChatColor.GOLD + "====================");
					sender.sendMessage(ChatColor.BLUE + "   Onze staff bestaat uit:");
					sender.sendMessage(ChatColor.GOLD + "   - Owner: " + ChatColor.AQUA + "xLeas");
					sender.sendMessage(ChatColor.GOLD + "   - Co-Owners: " + ChatColor.AQUA + "IzoBoy90 en ItsGwnMikai");
					sender.sendMessage(ChatColor.GOLD + "   - Event-Manager: " + ChatColor.AQUA + "cram46");
					sender.sendMessage(ChatColor.GOLD + "   - Developer: " + ChatColor.AQUA + "TYSluukie");
					sender.sendMessage(ChatColor.GOLD + "   - Mod's: " + ChatColor.AQUA + "Stryzeon en Lucky_Pro_D");
					sender.sendMessage(ChatColor.GOLD +"==================" + ChatColor.DARK_RED + "+FlameNetwork+" + ChatColor.GOLD + "====================");
				} else if(cmd.getName().equalsIgnoreCase("stopbroadcast")) {
					if(running == 1) {
						Bukkit.getServer().getScheduler().cancelTask(tid);
						Player player = (Player) sender;
						player.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + " Broadcast is gestopt!");
						running = 0;
					} else {
						Player player = (Player) sender;
						player.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.RED + " Broadcast is niet actief!");
					}
					} else if (commandLabel.equalsIgnoreCase("startbroadcast")) {
						if(running == 1) {
							Player player = (Player) sender;
							player.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.RED + " Broadcast is al actief!");
						} else {
							Player player = (Player) sender;
							player.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + " Broadcast is gestart!");
							tid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
								public void run() {
									try {
										broadcastMessage("plugins/FNhub/messages.txt");
									}	catch (IOException e) {
										
									}
								}
							}, 0, interval * 20);
							running = 1;
						}
						} else if (commandLabel.equalsIgnoreCase("lock")) {
							if(args.length == 0) {
								if(getConfig().getString("lockdown").equals("false")){
									getConfig().set("lockreason", ChatColor.GOLD + "De server staat momenteel in de lockdown! Kom later terug!");
									getConfig().set("lockdown", "true");
									saveConfig();
									sender.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + " De lockdown is aangezet met rede: " + getConfig().getString("lockreason"));
									Bukkit.getServer().broadcastMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is aangezet met rede: " + getConfig().getString("lockreason"));
									for(Player player : Bukkit.getServer().getOnlinePlayers()) {
										if (player.hasPermission("lockdown.bypass")) {
											player.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is aangezet!");
										} else {
											player.kickPlayer(getConfig().getString("lockreason"));
										}
									}
								} else if(getConfig().getString("lockdown").equals("true")){
									getConfig().set("lockreason", "");
									getConfig().set("lockdown", "false");
									saveConfig();
									Bukkit.getServer().broadcastMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is uitgezet!");
								}
								}
							if(args.length == 1) {
								if(getConfig().getString("lockdown").equals("false")){
									String reason = args[1];
									getConfig().set("lockreason", ChatColor.RED +  reason);
									getConfig().set("lockdown", "true");
									saveConfig();
									sender.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + " De lockdown is aangezet met rede: " + getConfig().getString("lockreason"));
									Bukkit.getServer().broadcastMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is aangezet met rede: " + getConfig().getString("lockreason"));
									for(Player player : Bukkit.getServer().getOnlinePlayers()) {
										if (player.hasPermission("lockdown.bypass")) {
											player.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is aangezet!");
										} else {
											player.kickPlayer(getConfig().getString("lockreason"));
										}
									}
									} else if(getConfig().getString("lockdown").equals("true")){
									getConfig().set("lockreason", "");
									getConfig().set("lockdown", "false");
									saveConfig();
									Bukkit.getServer().broadcastMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is uitgezet!");
								}
							}
							if(args.length == 2) {
								if(getConfig().getString("lockdown").equals("false")){
									String reason1 = args[1];
									String reason2 = args[2];
									getConfig().set("lockreason", ChatColor.RED +  reason1 + " " + reason2);
									getConfig().set("lockdown", "true");
									saveConfig();
									sender.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + " De lockdown is aangezet met rede: " + getConfig().getString("lockreason"));
									Bukkit.getServer().broadcastMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is aangezet met rede: " + getConfig().getString("lockreason"));
									for(Player player : Bukkit.getServer().getOnlinePlayers()) {
										if (player.hasPermission("lockdown.bypass")) {
											player.sendMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is aangezet!");
										} else {
											player.kickPlayer(getConfig().getString("lockreason"));
										}
									}
									} else if(getConfig().getString("lockdown").equals("true")){
									getConfig().set("lockreason", "");
									getConfig().set("lockdown", "false");
									saveConfig();
									Bukkit.getServer().broadcastMessage(ChatColor.RED + "[FlameNetwork]" + ChatColor.GREEN + "De lockdown is uitgezet!");
								}
							}
							if(args.length >= 2) {
								sender.sendMessage(ChatColor.RED + "Gebruik maximaal 2 woorden!");
							}
							} 
		return true;
		}
}


