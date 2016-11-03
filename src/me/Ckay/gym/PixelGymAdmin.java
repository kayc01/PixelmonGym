package me.Ckay.gym;

//import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class PixelGymAdmin implements CommandExecutor, Listener {
	
	PixelGym plugin;
	
		
	
	public PixelGymAdmin(PixelGym pixelGym){
		this.plugin = pixelGym;
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		
		 Player p = (Player)sender;
		
		if (commandLable.equalsIgnoreCase("pixelgym")) {
		  if (p.hasPermission("pixelgym.admin")) {
	        if (args.length == 0) {
	            if (!p.hasPermission("pixelgym.admin")) {
	              p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
	              p.sendMessage("");
	              p.sendMessage(ChatColor.RED + "You do not haver permission for any commands in this area.");
	              p.sendMessage("");
	              p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
	            } 
	            else if (p.hasPermission("pixelgym.admin")) {
	            	p.sendMessage(ChatColor.GREEN + "/pixelgym reload" + ChatColor.DARK_GREEN + " - Reloads the plugin config.");
	            	//p.sendMessage(ChatColor.GREEN + "/pixelgym saveplayers" + ChatColor.DARK_GREEN + " - Saves the players inventory file.");
	            	p.sendMessage(ChatColor.GREEN + "/pixelgym checkconfig" + ChatColor.DARK_GREEN + " - Checks the currently set config settings.");
	            	p.sendMessage(ChatColor.GREEN + "/pixelgym addleader <Gym#/e4#> (Username)" + ChatColor.DARK_GREEN + " - Add a gym leader to a specific gym or elite 4 level. E.G: /pixelgym addleader Gym1 ABkayCkay");
	            	p.sendMessage(ChatColor.GREEN + "/pixelgym delleader <Gym#/e4#> (Username)" + ChatColor.DARK_GREEN + " - Remove a gym leader from a specific gym or elite 4 level. E.G: /pixelgym delleader Gym1 ABkayCkay");
	            	p.sendMessage(ChatColor.GREEN + "/pixelgym setlevel <gym#> (level)" + ChatColor.DARK_GREEN + " - Sets the level of the specified gym. (Modify's the config)");
	            	p.sendMessage(ChatColor.GREEN + "/pixelgym setrule <gym#> <rule#> (rule)" + ChatColor.DARK_GREEN + " - Sets the rules of the specified gym. (Modify's the config) E.G: /pixelgym setrule gym1 1 No_Potions");
	            	p.sendMessage(ChatColor.GREEN + "/gym closeall" + ChatColor.DARK_GREEN + " - Closes all Gym's.");
	            	p.sendMessage(ChatColor.GREEN + "/e4 closeall" + ChatColor.DARK_GREEN + " - Closes all Elite 4 level's.");
	            	
	            }
	        }
	        else if (args.length == 1) {
	        	//if ((args[0].equals("saveplayers")) && (p.hasPermission("pixelgym.admin"))) {
	        		//plugin.saveplayers();
	        	//}
	        	
	    		if ((args[0].equals("reload")) && (p.hasPermission("pixelgym.admin"))) {
	                plugin.reloadConfig();
	                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + "Config Reloaded!");
	              }
	    		else if ((args[0].equals("checkconfig")) && (p.hasPermission("pixelgym.admin"))) {
	    			p.sendMessage(ChatColor.GOLD + "======= Current PixelGym Config settings =======");
	    			p.sendMessage(" ");
	    			p.sendMessage(ChatColor.GREEN + "Player Join Messages: " + ChatColor.GOLD + plugin.getConfig().getString("config.joinmessage"));
	    			p.sendMessage(ChatColor.GREEN + "Scoreboard Active: " + ChatColor.GOLD + plugin.getConfig().getString("config.scoreboard"));
	    			p.sendMessage(ChatColor.GREEN + "Gym/E4 Leader healing: " + ChatColor.GOLD + plugin.getConfig().getString("config.gymhealing"));
	    			p.sendMessage(ChatColor.GREEN + "Elite 4 Enabled: " + ChatColor.GOLD + plugin.getConfig().getString("config.e4enabled"));
	    			p.sendMessage(ChatColor.GREEN + "Give Leaders Pokemon: " + ChatColor.GOLD + plugin.getConfig().getString("config.giveleaderpokemon"));
	    			
//	    		  if (plugin.getConfig().getString("config.pex").equals("True")) {
//	    			p.sendMessage(ChatColor.GREEN + "Permissions Plugin being used:" + ChatColor.GOLD + " PermissionsEX");
//	    		  }
//	    		  else if (plugin.getConfig().getString("config.groupman").equals("True")) {
//	    			  p.sendMessage(ChatColor.GREEN + "Permissions Plugin being used:" + ChatColor.GOLD + " GroupManager");
//	    		  }
//	    		  else if (plugin.getConfig().getString("config.bperm").equals("True")) {
//	    			  p.sendMessage(ChatColor.GREEN + "Permissions Plugin being used:" + ChatColor.GOLD + " BPermissions");
//	    		  }
	    		 }
	    		  
	    	}
	        
	    
	    	else if (args.length == 3) {

	              
	    			if (args[0].equalsIgnoreCase("addleader")) {
	    					if (p.hasPermission("pixelgym.admin")) {	

	    					if(Bukkit.getPlayer(args[2]) != null) {
	    						
	    						
	    					for (int i = 1; i <= 32; i++) {	
	    						if (args[1].equalsIgnoreCase("gym"+i)) {
				        		  Player playerTarget = Bukkit.getPlayer(args[2]);
				        		  
				        		  String gym = args[1].replace("gym", "");

	    							if (plugin.getConfig().getString("config.enablegroup").equals("True")) {
	    								if (PixelGym.permission.playerInGroup(null, playerTarget, plugin.getConfig().getString("config.globalgroupname"))) {
	    									p.sendMessage(ChatColor.RED + "Player is already in this group, giving them the gym permission node. (pixelgym.gym"+gym+")");
	    									PixelGym.permission.playerAdd(null, playerTarget, "pixelgym.gym"+gym);
	    								}
	    								else {
	    									String gymgroup = plugin.getConfig().getString("config.globalgroupname");
	    									PixelGym.permission.playerAddGroup(null, playerTarget, gymgroup);
	    									PixelGym.permission.playerAdd(null, playerTarget, "pixelgym.gym"+gym);
	    								}
	    							}
	    							else {
	    								
	    								PixelGym.permission.playerAdd(null, playerTarget, "pixelgym.gym"+gym);

	    								if (!PixelGym.permission.playerHas(null, playerTarget, "pixelgym.leader")) {
	    								
	    									PixelGym.permission.playerAdd(null, playerTarget, "pixelgym.leader");
	    								
	    								}
	    								else {
	    									p.sendMessage(ChatColor.RED + "Player already has pixelgym.leader, adding pixelgym.gym"+gym);
	    								}
	    							}
	    							
	    							if (plugin.getConfig().getString("config.giveleaderpokemon").equals("True")) {
	    		      					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config.gym"+gym+"poke1")) + " lvl" + (plugin.getConfig().getString("config.gym"+gym+"poke1lvl")));
	    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config.gym"+gym+"poke2")) + " lvl" + (plugin.getConfig().getString("config.gym"+gym+"poke2lvl")));
	    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config.gym"+gym+"poke3")) + " lvl" + (plugin.getConfig().getString("config.gym"+gym+"poke3lvl")));
	    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config.gym"+gym+"poke4")) + " lvl" + (plugin.getConfig().getString("config.gym"+gym+"poke4lvl")));
	    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config.gym"+gym+"poke5")) + " lvl" + (plugin.getConfig().getString("config.gym"+gym+"poke5lvl")));
	    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config.gym"+gym+"poke6")) + " lvl" + (plugin.getConfig().getString("config.gym"+gym+"poke6lvl")));
	    		      				  }
	    							
	    							playerTarget.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + "You have been set as a " +plugin.getConfig().getString("config."+args[1]) + " Leader!");
	    			      			playerTarget.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + "Try /gym or /e4 to see your new commands!");
	    			      			p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + playerTarget.getName().toString() + " has successfully been added as a " + plugin.getConfig().getString("config."+args[1]) + " leader!");
	    			      		 }
	    					}
	    				}
	    					
	    		              
	    		              if (args[1].equalsIgnoreCase("e41") || args[1].equalsIgnoreCase("e42") || args[1].equalsIgnoreCase("e43")|| args[1].equalsIgnoreCase("e44")) {
	    		            	  
	    		            	  Player playerTarget = Bukkit.getPlayer(args[2]);
	    		            	  
	    		            	  if (args[2].equals(playerTarget.getName())) {
	  	    						
		    							if (plugin.getConfig().getString("config.enablegroup").equals("True")) {
		    								if (PixelGym.permission.playerInGroup(null, playerTarget, plugin.getConfig().getString("config.globale4groupname"))) {
		    									p.sendMessage(ChatColor.RED + "Player is already in this group, giving them the gym permission node. (pixelgym."+args[1]+")");
		    									PixelGym.permission.playerAdd(null, playerTarget, "pixelgym."+args[1]);
		    									}
		    								else {
		    									String e4group = plugin.getConfig().getString("config.globale4groupname");
		    									PixelGym.permission.playerAddGroup(null, playerTarget, e4group);
		    									PixelGym.permission.playerAdd(null, playerTarget, "pixelgym."+args[1]);
		    									}
		    								}
		    							else {
		    								PixelGym.permission.playerAdd(null, playerTarget, "pixelgym."+args[1]);
		    								PixelGym.permission.playerAdd(null, playerTarget, "pixelgym.e4leader");
		    							}
		    							
		    							if (plugin.getConfig().getString("config.giveleaderpokemon").equals("True")) {
		    		      					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config."+args[1]+"poke1")) + " lvl" + (plugin.getConfig().getString("config."+args[1]+"poke1lvl")));
		    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config."+args[1]+"poke2")) + " lvl" + (plugin.getConfig().getString("config."+args[1]+"poke2lvl")));
		    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config."+args[1]+"poke3")) + " lvl" + (plugin.getConfig().getString("config."+args[1]+"poke3lvl")));
		    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config."+args[1]+"poke4")) + " lvl" + (plugin.getConfig().getString("config."+args[1]+"poke4lvl")));
		    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config."+args[1]+"poke5")) + " lvl" + (plugin.getConfig().getString("config."+args[1]+"poke5lvl")));
		    		           			    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + playerTarget.getName().toString() + " " + (plugin.getConfig().getString("config."+args[1]+"poke6")) + " lvl" + (plugin.getConfig().getString("config."+args[1]+"poke6lvl")));
		    		      				  }
		    							
		    							playerTarget.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + "You have been set as a " +plugin.getConfig().getString("config."+args[1]) + " Leader!");
		    			      			playerTarget.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + "Try /gym or /e4 to see your new commands!");
		    			      			p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + playerTarget.getName().toString() + " has successfully been added as a " + plugin.getConfig().getString("config."+args[1]) + " leader!");	
		    						
	    		            	  	}
	    		            	  
	    		              	}
	    		              
	    				  }
	    					else {
	    						p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
	    					}
	    				}
	    			
	    			if (args[0].equalsIgnoreCase("delleader")) {
	    				if (p.hasPermission("pixelgym.admin")) {

	    					Player playerTarget = Bukkit.getPlayer(args[2]);

		    					if (args[2].equals(playerTarget.getName())) {
		    						
		    						
			    	  				
			    					 
			    					  String gym = args[1].replace("gym", "");
			    					  //gym = Integer.parseInt(gymArg);
			    					  
			    		              
		    						
								if (plugin.getConfig().getString("config.enablegroup").equals("True")) {
									if (PixelGym.permission.playerInGroup(null, playerTarget,
											plugin.getConfig().getString("config.globalgroupname"))) {
										
										PixelGym.permission.playerRemove(null, playerTarget, "pixelgym.gym" + gym);
										
										outerloop:
										
										for (int i = 1; i <= 32; i++) {
											if (PixelGym.permission.playerHas(null, playerTarget, "pixelgym.gym"+i)) {
												break outerloop;

											}
											else {
												PixelGym.permission.playerRemoveGroup(null, playerTarget,
														plugin.getConfig().getString("config.globalgroupname"));
											}

										}

									} 
									else {
										p.sendMessage(ChatColor.RED + playerTarget.getName()
												+ " is not the gym leader group! Removing there permission nodes.");
										PixelGym.permission.playerRemove(null, playerTarget, "pixelgym.gym" + gym);
										
										for (int i = 1; i <= 32; i++) {
											if (PixelGym.permission.playerHas(null, playerTarget, "pixelgym.gym"+i)) {
												break;
											}
											else {
												PixelGym.permission.playerRemove(null, playerTarget, "pixelgym.leader");
											}

										}

									}
								}
		    							else {
		    								PixelGym.permission.playerRemove(null, playerTarget, "pixelgym.gym"+gym);
		    								
		    								for (int i = 1; i <= 32; i++) {
												if (PixelGym.permission.playerHas(null, playerTarget, "pixelgym.gym"+i)) {
													break;
												}
												else {
													PixelGym.permission.playerRemove(null, playerTarget, "pixelgym.leader");
												}
												
											}
		    								
		    							}
		    							
		    							playerTarget.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + "You have been removed from being a " +plugin.getConfig().getString("config."+args[1]) + " Leader!");
		    			      			p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + playerTarget.getName().toString() + " has successfully been removed as a " + plugin.getConfig().getString("config."+args[1]) + " leader!");
		    						}
		    					
		    				else {
		    					
		    					
		    				
		    					if (args[1].equalsIgnoreCase("e41") || args[1].equalsIgnoreCase("e42") || args[1].equalsIgnoreCase("e43")|| args[1].equalsIgnoreCase("e44")) {
		    						if (args[2].equals(playerTarget.getName())) {
			    						
		    							if (plugin.getConfig().getString("config.enablegroup").equals("True")) {
		    								if (PixelGym.permission.playerInGroup(null, playerTarget, plugin.getConfig().getString("config.globale4groupname"))) {
		    									PixelGym.permission.playerRemoveGroup(null, playerTarget, plugin.getConfig().getString("config.globale4groupname"));
		    									PixelGym.permission.playerRemove(null, playerTarget, "pixelgym."+args[1]);
		    								}
		    								else {
		    									p.sendMessage(ChatColor.RED + playerTarget.getName() + " is not the e4 leader group! Removing there permission nodes.");
		    									PixelGym.permission.playerRemove(null, playerTarget, "pixelgym."+args[1]);
			    								PixelGym.permission.playerRemove(null, playerTarget, "pixelgym.e4leader");
		    								}
		    							  }
		    							else {
		    								PixelGym.permission.playerRemove(null, playerTarget, "pixelgym."+args[1]);
		    								PixelGym.permission.playerRemove(null, playerTarget, "pixelgym.e4leader");
		    							}
		    							
		    							playerTarget.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + "You have been removed from being a " +plugin.getConfig().getString("config."+args[1]) + " Leader!");
		    			      			p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " +ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("config."+args[1]+"colour")) + playerTarget.getName().toString() + " has successfully been removed as a " + plugin.getConfig().getString("config."+args[1]) + " leader!");
		    						
		    						}
		    					}
		    				}
		    			
		    				}
	    				else {
    						p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
    					}
	    				}
	    			if (args[0].equalsIgnoreCase("setlevel")) {
	    				int gym;
    	  				
   					 
  					  String gymArg = args[1].replace("gym", "");
  					  
  		              try {

  		                  // Set the gym variable to the number in arguement 1

  		                  gym = Integer.parseInt(gymArg);

  		              } catch (NumberFormatException nfe) {

  		                  p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");

  		                  // Number was not a number

  		                  return true;

  		              }

  		              if (gym < 1 || gym > 32) {

  		                  // Number was not between 1 and 32

  		                  return true;

  		              }
  		              
  		              if (args[2] != null) {
  		            	
  		            	  plugin.getConfig().set("config.gym"+gym+"lvlcap", args[2]);
  		            	  plugin.saveDefaultConfig();
  		            	  //plugin.reloadConfig();
  		            	
  		              }
	    				
	    			}
	    			
	    			
	    		
	    			    		
	    	}
	        
	    	else if (args.length == 4) {
	    		if (args[0].equalsIgnoreCase("setrule")) {
	    			
	    			//System.out.println("setrule reached");
    				
	    			int gym;
	  				
					 
					  String gymArg = args[1].replace("gym", "");
					  
		              try {

		                  // Set the gym variable to the number in arguement 1

		                  gym = Integer.parseInt(gymArg);

		              } catch (NumberFormatException nfe) {

		                  p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");

		                  // Number was not a number

		                  return true;

		              }

		              if (gym < 1 || gym > 32) {

		                  // Number was not between 1 and 32

		                  return true;

		              }
		              
		              int rule;
		  				
						 
					  String ruleArg = args[2];
					  
		              try {

		                  // Set the gym variable to the number in arguement 1

		                  rule = Integer.parseInt(ruleArg);

		              } catch (NumberFormatException nfe) {

		                  p.sendMessage(ChatColor.RED + args[2] + " is not an integer!");

		                  // Number was not a number

		                  return true;

		              }
		              
		              if (rule <= 5 && rule >= 1) {
		            	  
		            	  //System.out.println(rule);
		            	  
		            	  String message = args[3].replace("_", " ");
		            	  
		            	  //System.out.println(message);

		            	  plugin.getConfig().set("config.gym"+gym+"rule"+rule, message);
		            	  //plugin.saveConfig();
		            	  plugin.saveDefaultConfig();
		            	  //plugin.reloadConfig();
		              }
    				
    			}
	    	}
		  }
	      		  
	          
	      } 
		
		
		return false;
	}

}
