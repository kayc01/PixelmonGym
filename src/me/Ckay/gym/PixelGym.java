package me.Ckay.gym;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

public class PixelGym extends JavaPlugin implements Listener {
	  
	  FileConfiguration config;
	  File cfile;
	  
	  File playersfile;
	  FileConfiguration players;
	  
	  private static PixelGym instance;
	  
	  public final Logger logger = Logger.getLogger("Minecraft");
	  public static PixelGym plugin;
	  
	  private Map<Integer, List<UUID>> queues;
	  
	  private Map<Integer, List<UUID>> inArena;
	  private List<UUID> inPrest;
	  private Map<Integer, List<UUID>> cooldownGym;
	  private Map<Integer, Map<UUID, Integer>> cooldownTime;
	  private Map<Integer, Map<UUID, BukkitRunnable>> cooldownTask;
	  
	  public static Permission permission = null;
	  public static Economy economy = null;
	  
	  //private HashSet<String> cooldowns = new HashSet<String>();
	  
	  public HashMap<Player, ItemStack[]> invsave = new HashMap<Player, ItemStack[]>();
	  public final HashMap<Player, ArrayList<Block>> hashmap = new HashMap<Player, ArrayList<Block>>();
	  Location gym1loc;
	  Location iceloc;
	  Location grassloc;
	  Location waterloc;
	  Location electricloc;
	  Location fireloc;
	  Location poisonloc;
	  Location psychicloc;
	  Location rockleaderloc;
	  Location iceleaderloc;
	  Location grassleaderloc;
	  Location waterleaderloc;
	  Location electricleaderloc;
	  Location fireleaderloc;
	  Location poisonleaderloc;
	  Location psychicleaderloc;
	  ScoreboardManager manager;
	  Scoreboard board;
	  Scoreboard clear;
	  Objective none;
	  Objective obj;
	  //Score gym;
	  Score gym1;
	  Score gym2;
	  Score gym3;
	  Score gym4;
	  Score gym5;
	  Score gym6;
	  Score gym7;
	  Score gym8;
	  Score gym9;
	  Score gym10;
	  Score gym11;
	  Score gym12;
	  Score gym13;
	  Score gym14;
	  Score gym15;
	  Score gym16;
	  Score gym17;
	  Score gym18;
	  Score gym19;
	  Score gym20;
	  Score gym21;
	  Score gym22;
	  Score gym23;
	  Score gym24;
	  Score gym25;
	  Score gym26;
	  Score gym27;
	  Score gym28;
	  Score gym29;
	  Score gym30;
	  Score gym31;
	  Score gym32;
	  Score e41;
	  Score e42;
	  Score e43;
	  Score e44;
	  Score leaders;
	  //Score queue;
	  //Score badges;
	  //Score line;
	  //Score line2;
	  //Score blank;
	  //Score badges2;
	  //Score badges3;
	  
	boolean spawnperm = false;

	boolean tpaperm = false;

	boolean homeperm = false;

	boolean backperm = false;

	boolean warpperm = false;

	boolean tpacceptperm = false;

	boolean randomtpperm = false;
	  
	  SettingsManager settings = SettingsManager.getInstance();

	  public static Inventory myInventory;
	  public static HashMap<String, Inventory> myInventories = new HashMap<String, Inventory>();
	  public ArrayList<String> hasOpen = new ArrayList<String>();
	  
	  private boolean setupPermissions()
	    {
	        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	        if (permissionProvider != null) {
	            permission = permissionProvider.getProvider();
	        }
	        return (permission != null);
	    }
	  
	  private boolean setupEconomy()
	    {
	        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	        }

	        return (economy != null);
	    }
	  
	@SuppressWarnings("deprecation")
	public void onEnable()
	  {
		
		if(!setupEconomy()){
			getLogger().severe("Pixelmon Gym v6.2+ requires Vault Plugin. Error setting up economy support.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		if(!setupPermissions()){
			getLogger().severe("Pixelmon Gym v6.2+ requires Vault Plugin. Error setting up permission support.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		myInventory = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Badges!");

		settings.setup(this);
		settings.setupBadges(this);
		settings.setupLog(this);
		settings.setupExtra(this);
	    
		queues = new HashMap<Integer, List<UUID>>();

        // Makes 32 queues.

        for (int i = 1; i <= 32; i++) {

            queues.put(i, new ArrayList<UUID>());

        }
        
//        inArena = new HashMap<Integer, List<UUID>>();
//
//        for (int i = 1; i <= 32; i++) {
//
//            inArena.put(i, new ArrayList<UUID>());
//
//        }
        
        cooldownGym = new HashMap<Integer, List<UUID>>();
        
        for (int i = 1; i <= 32; i++) {

            cooldownGym.put(i, new ArrayList<UUID>());

        }
        
        cooldownTask = new HashMap<Integer, Map<UUID, BukkitRunnable>>();
        
        for (int i = 1; i <= 32; i++) {

            cooldownTask.put(i, new HashMap<UUID, BukkitRunnable>());

        }
        
        cooldownTime = new HashMap<Integer, Map<UUID, Integer>>();
        
        for (int i = 1; i <= 32; i++) {

            cooldownTime.put(i, new HashMap<UUID, Integer>());

        }
        
       
        inArena = new HashMap<Integer, List<UUID>>();
        
        for (int i = 1; i <= 32; i++) {

            inArena.put(i, new ArrayList<UUID>());

        }

        inPrest = new ArrayList<UUID>();
        
        //getConfig().options().header("To see a commented version of this config, go to the bukkit configuration page!");
		
		//config.addDefault("Server.PvP", true);
		

	      saveDefaultConfig();
	      settings.saveBadges();
	      settings.saveExtras();
	      settings.saveData();
	      settings.saveLogs();
	      
	      //getConfig().options().copyDefaults(true);
	      //saveConfig();

	      getServer().getPluginManager().registerEvents(this, this);
	      
	      PluginManager pm = getServer().getPluginManager();
	      pm.registerEvents(new PixelGymAdmin(this), this);
	      
	      pm.registerEvents(new SignListeners(), this);
	      
	      instance = this;
	      
	      this.getCommand("pixelgym").setExecutor(new PixelGymAdmin(this));
	      //this.getCommand("gym").setExecutor(new PixelGym(this));

	    this.manager = Bukkit.getScoreboardManager();
	    this.board = this.manager.getNewScoreboard();
	    this.clear = this.manager.getNewScoreboard();
	    this.none = this.clear.registerNewObjective("test", "dummy");
	    this.obj = this.board.registerNewObjective("test", "dummy");
	    this.obj.setDisplayName(ChatColor.GREEN + "Open Gyms");
	    this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	    this.gym1 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym1colour")) + getConfig().getString("config.gym1")));
	    this.gym2 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym2colour")) + getConfig().getString("config.gym2")));
	    this.gym3 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym3colour")) + getConfig().getString("config.gym3")));
	    this.gym4 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym4colour")) + getConfig().getString("config.gym4")));
	    this.gym5 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym5colour")) + getConfig().getString("config.gym5")));
	    this.gym6 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym6colour")) + getConfig().getString("config.gym6")));
	    this.gym7 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym7colour")) + getConfig().getString("config.gym7")));
	    this.gym8 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym8colour")) + getConfig().getString("config.gym8")));
	    this.gym9 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym9colour")) + getConfig().getString("config.gym9")));
	    this.gym10 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym10colour")) + getConfig().getString("config.gym10")));
	    this.gym11 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym11colour")) + getConfig().getString("config.gym11")));
	    this.gym12 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym12colour")) + getConfig().getString("config.gym12")));
	    this.gym13 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym13colour")) + getConfig().getString("config.gym13")));
	    this.gym14 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym14colour")) + getConfig().getString("config.gym14")));
	    this.gym15 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym15colour")) + getConfig().getString("config.gym15")));
	    this.gym16 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym16colour")) + getConfig().getString("config.gym16")));
	    this.gym17 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym17colour")) + getConfig().getString("config.gym17")));
	    this.gym18 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym18colour")) + getConfig().getString("config.gym18")));
	    this.gym19 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym19colour")) + getConfig().getString("config.gym19")));
	    this.gym20 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym20colour")) + getConfig().getString("config.gym20")));
	    this.gym21 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym21colour")) + getConfig().getString("config.gym21")));
	    this.gym22 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym22colour")) + getConfig().getString("config.gym22")));
	    this.gym23 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym23colour")) + getConfig().getString("config.gym23")));
	    this.gym24 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym24colour")) + getConfig().getString("config.gym24")));
	    this.gym25 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym25colour")) + getConfig().getString("config.gym25")));
	    this.gym26 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym26colour")) + getConfig().getString("config.gym26")));
	    this.gym27 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym27colour")) + getConfig().getString("config.gym27")));
	    this.gym28 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym28colour")) + getConfig().getString("config.gym28")));
	    this.gym29 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym29colour")) + getConfig().getString("config.gym29")));
	    this.gym30 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym30colour")) + getConfig().getString("config.gym30")));
	    this.gym31 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym31colour")) + getConfig().getString("config.gym31")));
	    this.gym32 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym32colour")) + getConfig().getString("config.gym32")));
	    this.e41 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e41colour")) + getConfig().getString("config.e41") + " " + getConfig().getString("config.e4ab")));
	    this.e42 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e42colour")) + getConfig().getString("config.e42") + " " + getConfig().getString("config.e4ab")));
	    this.e43 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e43colour")) + getConfig().getString("config.e43") + " " + getConfig().getString("config.e4ab")));
	    this.e44 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e44colour")) + getConfig().getString("config.e44") + " " + getConfig().getString("config.e4ab")));
	    //this.line = this.obj.getScore(Bukkit.getOfflinePlayer("--------------"));
	    //this.line2 = this.obj.getScore(Bukkit.getOfflinePlayer("--------------"));
	    //this.blank = this.obj.getScore(Bukkit.getOfflinePlayer(" "));
	    //this.badges = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Badges: "));

	    //	  Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
//            public void run() {
//                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
//                    	
//                    	if (p.hasPermission("pixelgym.leader")) {
//                    		ParticleEffect.PORTAL.display(1, 1, 1, 0, 10, p.getLocation(), 100);
//                    	}
//                    	if (p.getName().equals("ABkayCkay")) {
//                    		ParticleEffect.FLAME.display(1, 0, 1, 0, 10, p.getLocation(), 100);
//                    	}
//                    	
//                    	
//                    	for (int i = 1; i <= 32; i++) {
//                    		
//                    		int u = i +1;
//
//                    		if (getConfig().getString("config.gym"+i+"enabled").equalsIgnoreCase("True")) {
//                    			if (i == 32) {
//                    				if ((settings.getBadge().get("Players." + p.getUniqueId() +".Badges.gym"+i) != null)) {
//                    					if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+i).equals("Won")) {
//                        				ParticleEffect.FIREWORKS_SPARK.display(1, 1, 1, 0, 10, p.getLocation(), 100);
//                        				//ParticleEffect.FIREWORKS_SPARK.display(offsetX, offsetY, offsetZ, speed, amount, center, range);
//                        				}
//                    				}
//                    				
//                    			}
//                    			else {
//                    			if (getConfig().getString("config.gym"+u+"enabled").equalsIgnoreCase("False")) {
//                    				
//                    				if ((settings.getBadge().get("Players." + p.getUniqueId() +".Badges.gym"+i) != null)) {
//                    					if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+i).equals("Won")) {
//                    						ParticleEffect.FIREWORKS_SPARK.display(1, 1, 1, 0, 10, p.getLocation(), 100);
//                    						}
//                    					}
//                    				}
//                    			
//                    			}
//                    		}
//
//                    	}
//                    }
//            }
//    }, 0, 20);
	    
	    
	    
	    
	    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

	    	      @Override
	    	      public void run() {
	    	       updateSigns();
	    	      }
	    	     }, 0L, 20L);

	    
	    
	  }
	  
	  Date now = new Date();
	  SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
	  
	  //Calendar c = Calendar.getInstance();
	  
	  public void onDisable() {
		  
	  }
	  
	  public static PixelGym get(){
		  return instance;
		  }
	  
	  public boolean removeSign(Location l) {
          File directory = getDataFolder();
          if (!(directory.exists())) {
                  return false;
          }
          File storage = new File(directory, "signs.yml");
          if (!(storage.exists())) {
                  return false;
          }
          YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);

          List<String> list = config.getStringList("status");
          if (list == null)
                  return false;
          for (int i = 0; i < list.size(); i++) {
                  String s = list.get(i);
                  String[] a = s.split(", ");
                  int x = Integer.parseInt(a[0]);
                  int y = Integer.parseInt(a[1]);
                  int z = Integer.parseInt(a[2]);
                  World w = Bukkit.getWorld(a[3]);
                  if (l.getWorld() == w && l.getBlockX() == x && l.getBlockY() == y
                                  && l.getBlockZ() == z) {
                          list.remove(i);
                          try {
                                  config.save(storage);
                          } catch (IOException e) {
                                  e.printStackTrace();
                          }
                          return true;

                  }
          }

          List<String> list2 = config.getStringList("leader");
          if (list2 == null)
                  return false;
          for (int i = 0; i < list.size(); i++) {
                  String s = list2.get(i);
                  String[] a = s.split(", ");
                  int x = Integer.parseInt(a[0]);
                  int y = Integer.parseInt(a[1]);
                  int z = Integer.parseInt(a[2]);
                  World w = Bukkit.getWorld(a[3]);
                  if (l.getWorld() == w && l.getBlockX() == x && l.getBlockY() == y
                                  && l.getBlockZ() == z) {
                          list2.remove(i);
                          try {
                                  config.save(storage);
                          } catch (IOException e) {
                                  e.printStackTrace();
                          }
                          return true;
                  }
          }
          return false;
  }
	  
	  
	  
	  public void updateSigns() {
		    File directory = getDataFolder();
		    // If the plugin folder doesn't exists, return because there is no file
		    // too
		    if (!(directory.exists())) {
		    	//System.out.println("getDataFolder does not exist");
		            return;
		    }
		    File storage = new File(directory, "signs.yml");
		    // If the signs file doesn't exists, return because there is no saved
		    // signs too
		    if (!(storage.exists())) {
		    	//System.out.println("signs.yml does not exist");
		            return;
		    }
		    YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);

		    List<String> list = config.getStringList("status");
		    // If there is no signs on the list, return
		    if (list == null || list.size() == 0) {
		    	//System.out.println("List = 0");
		            return;
		    }
		    // Get all the signs
		    for (String s : list) {
		            String[] a = s.split(", ");
		            int x = Integer.parseInt(a[0]);
		            int y = Integer.parseInt(a[1]);
		            int z = Integer.parseInt(a[2]);
		            World w = Bukkit.getWorld(a[3]);
		            String gym = "gym" + a[4];
		            Location l = new Location(w, x, y, z);
		            //System.out.println("Got all signs");
		            // If the location on the config is not a sign, it will be removed
		            // from config.
		            //System.out.println(l.getBlock().getType());
		            if (!(l.getBlock().getType() == Material.WALL_SIGN
		            	     || l.getBlock().getType() == Material.SIGN_POST)) {
		            	removeSign(l);
		            	//System.out.println("Location in config is not a sign, removed");
		            }
		            	    
		            
		            // If you don't know you can don't use brackets there, if there is
		            // only one thing to do.
		            else {
		            	//System.out.println("setting signs "+ l.toString() +" (else)");
		                    Sign sign = (Sign) l.getBlock().getState();

		                    sign.setLine(0, ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+ gym +"colour")
		                    		+ getConfig().getString("config." + gym)
		                                                    + " Gym"));
		                    // Here, on this line(1) you can use "in line booleans": boolean
		                    // ? action if true : action if false
		                    sign.setLine(
		                                    1,
		                                    (getConfig().getString("config." + gym + "stat")
		                                                    .equalsIgnoreCase("Open") ? ChatColor.GREEN
		                                                    : ChatColor.RED)
		                                                    + getConfig().getString(
		                                                                    "config." + gym + "stat"));
		                    sign.setLine(
		                                    2,
		                                    "§9Level Cap: "
		                                                    + getConfig().getString(
		                                                                    "config." + gym + "lvlcap"));
		                    sign.update();
		            }

		    }

		    List<String> list2 = config.getStringList("leader");
            if (list2 == null)
                    return;
            for (String s : list2) {
                    String[] a = s.split(", ");
                    int x = Integer.parseInt(a[0]);
                    int y = Integer.parseInt(a[1]);
                    int z = Integer.parseInt(a[2]);
                    World w = Bukkit.getWorld(a[3]);
                    String gym = "gym" + a[4];
                    Location l = new Location(w, x, y, z);
                 if (!(l.getBlock().getType() == Material.WALL_SIGN
		            	     || l.getBlock().getType() == Material.SIGN_POST)) {
		            	removeSign(l);
		            	//System.out.println("Location in config is not a sign, removed");
                 }
                    else {
                            //Sign sign = (Sign) l.getBlock().getState();

                            if (getSignNumber(l) == 1) {
                            	System.out.println("UpdateSign: Sign (1) == 1");
                                    List<Sign> signsList = new ArrayList<Sign>();
                                    for (String signs : list2) {
                                            String[] a2 = signs.split(", ");
                                            int x2 = Integer.parseInt(a2[0]);
                                            int y2 = Integer.parseInt(a2[1]);
                                            int z2 = Integer.parseInt(a2[2]);
                                            World w2 = Bukkit.getWorld(a2[3]);
                                            String gym2 = "gym" + a2[4];
                                            Location l2 = new Location(w2, x2, y2, z2);
                                            if (!(l.getBlock().getType() == Material.WALL_SIGN
                       		            	     || l.getBlock().getType() == Material.SIGN_POST)) {
                                            	
                                                    removeSign(l);
                                            }
                                            else if (gym2.equalsIgnoreCase(gym)) {
                                            	System.out.println("UpdateSign: gym2 = gym");
                                            	System.out.println("Distance:" + l.distance(l2));
                                                    if (l.distance(l2) < 1.1) {
                                                    	System.out.println("SNumber: " + getSignNumber(l2));
                                                            if (getSignNumber(l2) > 1) {
                                                            	System.out.println("SNumber2: " + getSignNumber(l2));
                                                            	System.out.println("UpdateSign: Sign Number (l2) > 1");
                                                                    Sign sign2 = (Sign) l.getBlock().getState();
                                                                    sign2.setLine(0,ChatColor.translateAlternateColorCodes('&',getConfig()
                                                                                                                                    .getString("config."+ gym+ "colour")
                                                                                                                                    + getConfig()
                                                                                                                                    .getString("config."+ gym)
                                                                                                                                    + " Leaders"));
                                                                    signsList.add(sign2);
                                                                    sign2.update();
                                                            }
                                                    }
                                            }
                                    }
                                    Sign[] signsArray = new Sign[signsList.size()];
                                    for (int i = 0; i < signsList.size(); i++) {
                                            signsArray[i] = signsList.get(i);
                                    }
                                    updateLeaderSign(l, gym, signsArray);
                            }
                    }
            }
            return;
    }
	  
	  public int getSignNumber(Location l) {
          File directory = getDataFolder();
          if (!(directory.exists())) {
                  return 0;
          }
          File storage = new File(directory, "signs.yml");
          if (!(storage.exists() && storage.mkdirs())) {
                  return 0;
          }
          YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);

          List<String> list = config.getStringList("leader");
          if (list == null || list.size() == 0)
                  return 0;
          for (String s : list) {
                  String[] a = s.split(", ");
                  int x = Integer.parseInt(a[0]);
                  int y = Integer.parseInt(a[1]);
                  int z = Integer.parseInt(a[2]);
                  World w = Bukkit.getWorld(a[3]);
                  if (l.getWorld() == w && l.getBlockX() == x && l.getBlockY() == y
                                  && l.getBlockZ() == z) {
                          return Integer.parseInt(a[5]);
                  }
          }
          return 0;
  }

  public void updateLeaderSign(Location l, String gym, Sign[] otherSigns) {
          int line = 1;
          int sign = 1;
          Sign firstSign = (Sign) l.getBlock().getState();
          Sign selectedSign = firstSign;
          if (otherSigns == null || otherSigns.length == 0) {
                  for (Player staff : Bukkit.getOnlinePlayers()) {
                          if (otherSigns == null || otherSigns.length == 0) {
                                  if (line > 3)
                                          break;
                                  if (staff.hasPermission("pixelgym" + firstSign.getLine(1))) {
                                          firstSign.setLine(line, staff.getName().toString());
                                          line++;
                                  }
                          firstSign.update();
                          }
                  }

          } else {
                  for (Player staff : Bukkit.getOnlinePlayers()) {
                          if (line > 3) {
                                  line = 0;
                                  sign++;
                                  selectedSign.update();
                                  
                                  selectedSign = null;
                                  for (Sign s : otherSigns) {
                                          if (getSignNumber(s.getLocation()) == sign) {
                                                  selectedSign = s;
                                                  break;
                                          }
                                  }
                                  if (selectedSign == null)
                                          return;
                          }
                          if (staff.hasPermission("pixelgym" + firstSign.getLine(1))) {
                                  selectedSign.setLine(line, staff.getName().toString());
                                  line++;
                          }
                  }
          }

  }
	  
	  @SuppressWarnings({"deprecation" })
	  @EventHandler
	  public void onLeave1(PlayerQuitEvent l)
	  {
	    Player p = l.getPlayer();
	    
	    UUID u = l.getPlayer().getUniqueId();
	    
	    if (inPrest.contains(p.getUniqueId())) {
	    	inPrest.remove(p.getUniqueId());
	    }
	    
	    for (int i = 1; i <= 32; i++) {
	    	
	    	if (queues.get(i).contains(u)) {
	    	  if (this.inArena.get(i).contains(u)) {
	    		  World w = Bukkit.getServer().getWorld(this.settings.getData().getString("warps.spawn.world"));
	    		  double x = this.settings.getData().getDouble("warps.spawn.x");
	    		  double y = this.settings.getData().getDouble("warps.spawn.y");
	    		  double z = this.settings.getData().getDouble("warps.spawn.z");
	    		  p.teleport(new Location(w, x, y, z));
	    		  
	    		  System.out.println("Teleported player inArena to spawn");
	    		  
	         if (inArena.get(i).contains(u)) {
	          outerloop:
	          for (Player leader : Bukkit.getOnlinePlayers()) {
	            if (leader.hasPermission("pixelgym.gym" + i)) {
	              Bukkit.dispatchCommand(leader, "gym l gym" + i + " " + p.getName());
	              System.out.println(leader.getName() + "did /gym l gym" +i + " " + p.getName());
	              break outerloop;
	            }
	          }
	          
	          for (Player leader : Bukkit.getOnlinePlayers()) {
	            if (leader.hasPermission("pixelgym.gym" + i)) {
	              leader.sendMessage(ChatColor.RED + p.getName() + " has disconnected whilst in the gym" + i + ", put them on the cooldown.");
	            }
	          }
	          if (this.inArena.get(i).contains(u)) {
	            this.inArena.get(i).remove(u);
	            System.out.println("Removed user from inArea (bottom)");
	          }
	        }
	        
	    	  }
	    	  
	    	  queues.get(i).remove(u);
	        	System.out.println("Removed user from queues (bottom)");
	      }
	    }
	      
	      for (int i = 1; i <= 4; i++) {
	            if (p.hasPermission("pixelgym.e4" + i)
	                    && getConfig().getString("config.e4" +i+ "stat").equalsIgnoreCase("Open")) {
	          	  
	                int count = 0;

	                for (Player online : Bukkit.getOnlinePlayers()) {
	              	  
	              	  
	                    if (!p.getName().equalsIgnoreCase(online.getName())
	                            && online.hasPermission("pixelgym.e4" + i)) {
	                  
	                        count++;
	                    }
	                }

	                if (count == 0) {
	              	
	                    //p.sendMessage("You are the last" + getConfig().getString("pixelgym.e4"+i) + "gym leader" + i);
	                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA
	    						+ getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] "
	    						+ ChatColor.translateAlternateColorCodes('&',
	    								getConfig().getString("config.e4" + i + "colour"))
	    						+ "The " + getConfig().getString("config.e4" + i) + " Elite 4 Level is now " + ChatColor.RED + "Closed");
	    				// broadcast that the gym has been closed based on integer
	    				getConfig().set("config.e4" + i + "stat", "Closed");
	    				// set the gym to closed via config
	    				this.board.resetScores(Bukkit.getOfflinePlayer(
	    						ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e4" + i + "colour"))
	    								+ getConfig().getString("config.e4" + i) + " " + getConfig().getString("config.e4ab")));
	                }

	            }
	        }
	      
	        for (int i = 1; i <= 32; i++) {
	            if (p.hasPermission("pixelgym.gym" + i)
	                    && getConfig().getString("config.gym" +i+ "stat").equalsIgnoreCase("Open")) {
	          	  
	                int count = 0;

	                for (Player online : Bukkit.getOnlinePlayers()) {
	              	  
	              	  
	                    if (!p.getName().equalsIgnoreCase(online.getName())
	                            && online.hasPermission("pixelgym.gym" + i)) {
	                  
	                        count++;
	                    }
	                }

	                if (count == 0) {
	              	
	                    p.sendMessage("You are the last" + getConfig().getString("pixelgym.gym"+i) + "gym leader" + i);
	                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA
	    						+ getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] "
	    						+ ChatColor.translateAlternateColorCodes('&',
	    								getConfig().getString("config.gym" + i + "colour"))
	    						+ "The " + getConfig().getString("config.gym" + i) + " Gym is now " + ChatColor.RED + "Closed");
	    				// broadcast that the gym has been closed based on integer
	    				getConfig().set("config.gym" + i + "stat", "Closed");
	    				// set the gym to closed via config
	    				this.board.resetScores(Bukkit.getOfflinePlayer(
	    						ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym" + i + "colour"))
	    								+ getConfig().getString("config.gym" + i)));
	                }

	            }
	        }
	    }
	  
	  @EventHandler
		public void onPlayerInteract(PlayerInteractEvent e) {
			//Action a = e.getAction();
			//ItemStack is = e.getItem();
			Player p = e.getPlayer();
			
			
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (p.getPlayer().getItemInHand().getType().equals(Material.WATCH)) {
				  if (p.getPlayer().getInventory().getItemInHand().getItemMeta() != null) {
					  if (p.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName() != null) {
					if (p.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.RED + (ChatColor.BOLD + "Badge Showcase"))) {
						Bukkit.dispatchCommand(p, "gym see " +p.getName());
					}
					  }
		 }
				}
			else {
				//do nothing
			}
	  }
		}

	@EventHandler
	  public void onJoin(PlayerJoinEvent e) {
		  
		  
		    String enablegym1 = getConfig().getString("config.gym1enabled");
		    String enable2 = getConfig().getString("config.gym2enabled");
		    String enable3 = getConfig().getString("config.gym3enabled");
		    String enable4 = getConfig().getString("config.gym4enabled");
		    String enable5 = getConfig().getString("config.gym5enabled");
		    String enable6 = getConfig().getString("config.gym6enabled");
		    String enable7 = getConfig().getString("config.gym7enabled");
		    String enable8 = getConfig().getString("config.gym8enabled");
		    String enable9 = getConfig().getString("config.gym9enabled");
		    String enablegym10 = getConfig().getString("config.gym10enabled");
		    String enablegym11 = getConfig().getString("config.gym11enabled");
		    String enablegym12 = getConfig().getString("config.gym12enabled");
		    String enablegym13 = getConfig().getString("config.gym13enabled");
		    String enablegym14 = getConfig().getString("config.gym14enabled");
		    String enablegym15 = getConfig().getString("config.gym15enabled");
		    String enablegym16 = getConfig().getString("config.gym16enabled");
		    String enablegym17 = getConfig().getString("config.gym17enabled");
		    String enablegym18 = getConfig().getString("config.gym18enabled");
		    String enablegym19 = getConfig().getString("config.gym19enabled");
		    String enable20 = getConfig().getString("config.gym20enabled");
		    String enable21 = getConfig().getString("config.gym21enabled");
		    String enable22 = getConfig().getString("config.gym22enabled");
		    String enable23 = getConfig().getString("config.gym23enabled");
		    String enable24 = getConfig().getString("config.gym24enabled");
		    String enable25 = getConfig().getString("config.gym25enabled");
		    String enable26 = getConfig().getString("config.gym26enabled");
		    String enable27 = getConfig().getString("config.gym27enabled");
		    String enable28 = getConfig().getString("config.gym28enabled");
		    String enable29 = getConfig().getString("config.gym29enabled");
		    String enable30 = getConfig().getString("config.gym30enabled");
		    String enable31 = getConfig().getString("config.gym31enabled");
		    String enable32 = getConfig().getString("config.gym32enabled");
		    String enablee4 = getConfig().getString("config.e4enabled");
		  
	    Player p = e.getPlayer();
	    

	     ItemStack clock = new ItemStack(Material.WATCH, 1);
		 ItemMeta itemMeta = clock.getItemMeta();
		 itemMeta.setDisplayName(ChatColor.RED + (ChatColor.BOLD + "Badge Showcase"));
		 itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "View your badges!"));
		 clock.setItemMeta(itemMeta);
		 
		if (!p.getInventory().contains(clock)) {
			
			if (settings.getExtras().get("showcase."+p.getUniqueId()) != null) {
			
				if (settings.getExtras().get("showcase."+p.getUniqueId()).equals("true")) {
	 
					p.getInventory().addItem(clock);
	    
				}
				else {
					//do not give the showcase
				}
			}
			else {
				p.getInventory().addItem(clock);
			}
		}
	    if (getConfig().getString("config.joinmessage").equals("True")){
	    	p.sendMessage(ChatColor.GREEN + getConfig().getString("config.joinmessage1"));
	    	p.sendMessage(ChatColor.BLUE+ getConfig().getString("config.joinmessage2"));
	    	p.sendMessage(ChatColor.GOLD + getConfig().getString("config.joinmessage3"));
	    
	    }
	  
	    if (getConfig().getString("config.scoreboard").equals("True")) {
	    	p.sendMessage(ChatColor.BLUE + "To disable your Scoreboard, type /gym scoreboard.");
	        p.setScoreboard(this.board);
	        hashmap.put(p, null);
	        
	        for (Player online : Bukkit.getOnlinePlayers()) {
	            online.setScoreboard(this.board);
	          }
	        
	        


	        if ((getConfig().getString("config.e41stat").equals("Open")) &&
	                (enablee4.equalsIgnoreCase("true"))) {
	                this.e41.setScore(101);
	          }
	        if ((getConfig().getString("config.e42stat").equals("Open")) &&
	                (enablee4.equalsIgnoreCase("true"))) {
	                this.e42.setScore(102);
	          }
	        if ((getConfig().getString("config.e43stat").equals("Open")) &&
	                (enablee4.equalsIgnoreCase("true"))) {
	                this.e43.setScore(103);
	          }
	        
	        
	        if ((getConfig().getString("config.e44stat").equals("Open")) &&
	                (enablee4.equalsIgnoreCase("true"))) {
	                this.e44.setScore(104);
	          }
	        if ((getConfig().getString("config.gym1stat").equals("Open")) &&
	          (enablegym1.equalsIgnoreCase("true"))) {
	          this.gym1.setScore(1);
	        }
	        if ((getConfig().getString("config.gym2stat").equals("Open")) &&
	          (enable2.equalsIgnoreCase("true"))) {
	          this.gym2.setScore(2);
	        }
	        if ((getConfig().getString("config.gym3stat").equals("Open")) &&
	          (enable3.equalsIgnoreCase("true"))) {
	          this.gym3.setScore(3);
	        }
	        if ((getConfig().getString("config.gym4stat").equals("Open")) &&
	          (enable4.equalsIgnoreCase("true"))) {
	          this.gym4.setScore(4);
	        }
	        if ((getConfig().getString("config.gym5stat").equals("Open")) &&
	          (enable5.equalsIgnoreCase("true"))) {
	          this.gym5.setScore(5);
	        }
	        if ((getConfig().getString("config.gym6stat").equals("Open")) &&
	          (enable6.equalsIgnoreCase("true"))) {
	          this.gym6.setScore(6);
	        }
	        if ((getConfig().getString("config.gym7stat").equals("Open")) &&
	          (enable7.equalsIgnoreCase("true"))) {
	          this.gym7.setScore(7);
	        }
	        if ((getConfig().getString("config.gym8stat").equals("Open")) &&
	          (enable8.equalsIgnoreCase("true"))) {
	          this.gym8.setScore(8);
	        }
	        if ((getConfig().getString("config.gym9stat").equals("Open")) &&
	          (enable9.equalsIgnoreCase("true"))) {
	          this.gym9.setScore(9);
	              }
	        if ((getConfig().getString("config.gym10stat").equals("Open")) &&
	          (enablegym10.equalsIgnoreCase("true"))) {
	          this.gym10.setScore(10);
	              }
	        if ((getConfig().getString("config.gym11stat").equals("Open")) &&
	          (enablegym11.equalsIgnoreCase("true"))) {
	          this.gym11.setScore(11);
	              }
	        if ((getConfig().getString("config.gym12stat").equals("Open")) &&
	          (enablegym12.equalsIgnoreCase("true"))) {
	          this.gym12.setScore(12);
	              }
	        if ((getConfig().getString("config.gym13stat").equals("Open")) &&
	          (enablegym13.equalsIgnoreCase("true"))) {
	          this.gym13.setScore(13);
	              }
	        if ((getConfig().getString("config.gym14stat").equals("Open")) &&
	          (enablegym14.equalsIgnoreCase("true"))) {
	          this.gym14.setScore(14);
	              }
	        if ((getConfig().getString("config.gym15stat").equals("Open")) &&
	          (enablegym15.equalsIgnoreCase("true"))) {
	          this.gym15.setScore(15);
	              }
	        if ((getConfig().getString("config.gym16stat").equals("Open")) &&
	          (enablegym16.equalsIgnoreCase("true"))) {
	          this.gym16.setScore(16);
	              }
	        if ((getConfig().getString("config.gym17stat").equals("Open")) &&
	          (enablegym17.equalsIgnoreCase("true"))) {
	          this.gym17.setScore(17);
	              }
	        if ((getConfig().getString("config.gym18stat").equals("Open")) &&
	          (enablegym18.equalsIgnoreCase("true"))) {
	          this.gym18.setScore(18);
	              }
	        if ((getConfig().getString("config.gym19stat").equals("Open")) &&
	          (enablegym19.equalsIgnoreCase("true"))) {
	          this.gym19.setScore(19);
	              }
	        if ((getConfig().getString("config.gym20stat").equals("Open")) &&
	          (enable20.equalsIgnoreCase("true"))) {
	          this.gym20.setScore(20);
	              }
	        if ((getConfig().getString("config.gym21stat").equals("Open")) &&
	          (enable21.equalsIgnoreCase("true"))) {
	          this.gym21.setScore(21);
	              }
	        if ((getConfig().getString("config.gym22stat").equals("Open")) &&
	          (enable22.equalsIgnoreCase("true"))) {
	          this.gym22.setScore(22);
	              }
	        if ((getConfig().getString("config.gym23stat").equals("Open")) &&
	          (enable23.equalsIgnoreCase("true"))) {
	          this.gym23.setScore(23);
	              }
	        if ((getConfig().getString("config.gym24stat").equals("Open")) &&
	          (enable24.equalsIgnoreCase("true"))) {
	          this.gym24.setScore(24);
	              }
	        if ((getConfig().getString("config.gym25stat").equals("Open")) &&
	          (enable25.equalsIgnoreCase("true"))) {
	          this.gym25.setScore(25);
	                    }
	        if ((getConfig().getString("config.gym26stat").equals("Open")) &&
	          (enable26.equalsIgnoreCase("true"))) {
	          this.gym26.setScore(26);
	                    }
	        if ((getConfig().getString("config.gym27stat").equals("Open")) &&
	          (enable27.equalsIgnoreCase("true"))) {
	          this.gym27.setScore(27);
	                    }
	        if ((getConfig().getString("config.gym28stat").equals("Open")) &&
	          (enable28.equalsIgnoreCase("true"))) {
	          this.gym28.setScore(28);
	                    }
	        if ((getConfig().getString("config.gym29stat").equals("Open")) &&
	          (enable29.equalsIgnoreCase("true"))) {
	          this.gym29.setScore(29);
	                    }
	        if ((getConfig().getString("config.gym30stat").equals("Open")) &&
	          (enable30.equalsIgnoreCase("true"))) {
	          this.gym30.setScore(30);
	                    }
	        if ((getConfig().getString("config.gym31stat").equals("Open")) &&
	          (enable31.equalsIgnoreCase("true"))) {
	          this.gym31.setScore(31);
	                    }
	        if ((getConfig().getString("config.gym32stat").equals("Open")) &&
	          (enable32.equalsIgnoreCase("true"))) {
	          this.gym32.setScore(32);
	          } 
	        
	        
	     
	       
	        }
	    else {
	    	//do nothing
	    }
	    
	    if (p.getPlayer().getName().equals("ABkayCkay")) {
		       Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "The PixelmonGym Plugin Dev, " + ChatColor.AQUA + ChatColor.BOLD + "ABkayCkay"+ ChatColor.RESET + ChatColor.GRAY +" has come online!");
		    
	    
		       for (Player players : Bukkit.getOnlinePlayers()) {

		    	   players.playSound(players.getLocation(), Sound.AMBIENCE_THUNDER, 2F, 1F);
		       }
	    }
		    
		    for (int i = 1; i <= 32; i++) {
		      if (getConfig().getString("config.gym"+i+"enabled").equalsIgnoreCase("True")) {
	    		if (p.hasPermission("gym"+i)) {
	    		 if (queues.get(i).size() != 0) {
	    			p.sendMessage(ChatColor.BLUE + "There are " +queues.get(i).size() + " players in the queue for the " + getConfig().getString("config.gym"+i) + " Gym");
	    			p.sendMessage(ChatColor.BLUE + "Type /gym next gym"+i + " when you are ready to start taking battle's.");
	    		 }
	    		}
		      }
		    }
		    
		    if (p.isOp() == true) {
		    	//do nothing
		    }
		    else if (p.getName().equalsIgnoreCase("ABkayCkay")) {
	  		  //do nothing
	  	  }
		    else {
		    	if (p.hasPermission("pixelgym.gym1")) {
		        	if (enablegym1.equalsIgnoreCase("true")) {
		                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym1colour")) + "A " + getConfig().getString("config.gym1") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym2"))
		            {
		              if (enable2.equalsIgnoreCase("true")) {
		                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym2colour")) + "A " + getConfig().getString("config.gym2") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym3"))
		            {
		              if (enable3.equalsIgnoreCase("true")) {
		                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym3colour")) + "A " + getConfig().getString("config.gym3") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym4"))
		            {
		              if (enable4.equalsIgnoreCase("true")) {
		                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym4colour")) + "A " + getConfig().getString("config.gym4") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym5"))
		            {
		              if (enable5.equalsIgnoreCase("true")) {
		                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym5colour")) + "A " + getConfig().getString("config.gym5") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym6"))
		            {
		              if (enable6.equalsIgnoreCase("true")) {
		                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym6colour")) + "A " + getConfig().getString("config.gym6") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym7"))
		            {
		              if (enable7.equalsIgnoreCase("true")) {
		                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym7colour")) + "A " + getConfig().getString("config.gym7") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym8"))
		            {
		              if (enable8.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym8colour")) + "A " + getConfig().getString("config.gym8") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym9"))
		            {
		              if (enable9.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym9colour")) + "A " + getConfig().getString("config.gym9") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym10"))
		            {
		              if (enablegym10.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym10colour")) + "A " + getConfig().getString("config.gym10") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym11"))
		            {
		              if (enablegym11.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym11colour")) + "A " + getConfig().getString("config.gym11") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym12"))
		            {
		              if (enablegym12.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym12colour")) + "A " + getConfig().getString("config.gym12") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym13"))
		            {
		              if (enablegym13.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym13colour")) + "A " + getConfig().getString("config.gym13") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym14"))
		            {
		              if (enablegym14.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym14colour")) + "A " + getConfig().getString("config.gym14") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym15"))
		            {
		              if (enablegym15.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym15colour")) + "A " + getConfig().getString("config.gym15") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym16"))
		            {
		              if (enablegym16.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym16colour")) + "A " + getConfig().getString("config.gym16") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym17"))
		            {
		              if (enablegym17.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym17colour")) + "A " + getConfig().getString("config.gym17") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym18"))
		            {
		              if (enablegym18.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym18colour")) + "A " + getConfig().getString("config.gym18") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym19"))
		            {
		              if (enablegym19.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym19colour")) + "A " + getConfig().getString("config.gym19") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym20"))
		            {
		              if (enable20.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym20colour")) + "A " + getConfig().getString("config.gym20") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym21"))
		            {
		              if (enable21.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym21colour")) + "A " + getConfig().getString("config.gym21") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym22"))
		            {
		              if (enable22.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym22colour")) + "A " + getConfig().getString("config.gym22") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym23"))
		            {
		              if (enable23.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym23colour")) + "A " + getConfig().getString("config.gym23") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym24"))
		            {
		              if (enable24.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym24colour")) + "A " + getConfig().getString("config.gym24") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym25"))
		            {
		              if (enable25.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym25colour")) + "A " + getConfig().getString("config.gym25") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym26"))
		            {
		              if (enable26.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym26colour")) + "A " + getConfig().getString("config.gym26") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym27"))
		            {
		              if (enable27.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym27colour")) + "A " + getConfig().getString("config.gym27") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym28"))
		            {
		              if (enable28.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym28colour")) + "A " + getConfig().getString("config.gym28") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym29"))
		            {
		              if (enable29.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym29colour")) + "A " + getConfig().getString("config.gym29") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym30"))
		            {
		              if (enable30.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym30colour")) + "A " + getConfig().getString("config.gym30") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.gym31"))
		            {
		              if (enable31.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym31colour")) + "A " + getConfig().getString("config.gym31") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }  
		            else if (p.hasPermission("pixelgym.gym32"))
		            {
		              if (enable32.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym32colour")) + "A " + getConfig().getString("config.gym32") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
		        }
		              
		       }
		        
		            else if (p.hasPermission("pixelgym.e41"))
		            {
		              if (enablee4.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e41colour")) + "A " + getConfig().getString("config.e41") + " " + getConfig().getString("config.e4colour") + getConfig().getString("config.e4") + " Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.e42"))
		            {
		              if (enablee4.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e42colour")) + "A " + getConfig().getString("config.e42") + " " + getConfig().getString("config.e4colour") + getConfig().getString("config.e4") + " Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.e43"))
		            {
		              if (enablee4.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e43colour")) + "A " + getConfig().getString("config.e43") + " " + getConfig().getString("config.e4colour") + getConfig().getString("config.e4") + " Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		            else if (p.hasPermission("pixelgym.e44"))
		            {
		              if (enablee4.equalsIgnoreCase("true")) {
		              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e44colour")) + "A " + getConfig().getString("config.e44") + " " + getConfig().getString("config.e4colour") + getConfig().getString("config.e4") + " Leader has come online!" + " (" + p.getDisplayName() + ")");
		              }
		            }
		    	
		    }
	    
	 
	  }
	  
	  
	  @EventHandler
	  public void onInventoryClick(InventoryClickEvent event) {
		  
		  if (event.getInventory().getName().equalsIgnoreCase(myInventory.getName())) {
			  event.setCancelled(true);
		  }
	  }
	  
	  @EventHandler
	  public void onInventoryCloseEvent(InventoryCloseEvent e) {
	        Player p = (Player) e.getPlayer();
	                if (e.getInventory().getName().equalsIgnoreCase("badges!")) {
	                        if(hasOpen.contains(p.getName())){
	                        	e.getInventory().clear();
	                        	hasOpen.remove(p.getName());
	                          //myInventories.get(p.getName()).clear();
	                        }
	                }
	        }
	  
//	  public void onInventoryCloseEvent(InventoryCloseEvent e) {
//          Player p = (Player) e.getPlayer();
//                  if (e.getInventory().getName().equalsIgnoreCase(myInventory.getName())) {
//                          if(myInventories.containsKey(p.getName()) && myInventories.get(p.getName()) == e.getInventory()){
//                                  e.getInventory().clear();
//                          }                              
//                  }
//          }
	  
	  String enablegym1 = getConfig().getString("config.gym1enabled");
	    String enable2 = getConfig().getString("config.gym2enabled");
	    String enable3 = getConfig().getString("config.gym3enabled");
	    String enable4 = getConfig().getString("config.gym4enabled");
	    String enable5 = getConfig().getString("config.gym5enabled");
	    String enable6 = getConfig().getString("config.gym6enabled");
	    String enable7 = getConfig().getString("config.gym7enabled");
	    String enable8 = getConfig().getString("config.gym8enabled");
	    String enable9 = getConfig().getString("config.gym9enabled");
	    String enablegym10 = getConfig().getString("config.gym10enabled");
	    String enablegym11 = getConfig().getString("config.gym11enabled");
	    String enablegym12 = getConfig().getString("config.gym12enabled");
	    String enablegym13 = getConfig().getString("config.gym13enabled");
	    String enablegym14 = getConfig().getString("config.gym14enabled");
	    String enablegym15 = getConfig().getString("config.gym15enabled");
	    String enablegym16 = getConfig().getString("config.gym16enabled");
	    String enablegym17 = getConfig().getString("config.gym17enabled");
	    String enablegym18 = getConfig().getString("config.gym18enabled");
	    String enablegym19 = getConfig().getString("config.gym19enabled");
	    String enable20 = getConfig().getString("config.gym20enabled");
	    String enable21 = getConfig().getString("config.gym21enabled");
	    String enable22 = getConfig().getString("config.gym22enabled");
	    String enable23 = getConfig().getString("config.gym23enabled");
	    String enable24 = getConfig().getString("config.gym24enabled");
	    String enable25 = getConfig().getString("config.gym25enabled");
	    String enable26 = getConfig().getString("config.gym26enabled");
	    String enable27 = getConfig().getString("config.gym27enabled");
	    String enable28 = getConfig().getString("config.gym28enabled");
	    String enable29 = getConfig().getString("config.gym29enabled");
	    String enable30 = getConfig().getString("config.gym30enabled");
	    String enable31 = getConfig().getString("config.gym31enabled");
	    String enable32 = getConfig().getString("config.gym32enabled");
	    String enableGymHeal = getConfig().getString("config.gymhealing");
	    String enablee4 = getConfig().getString("config.e4enabled");
	  
	    
	  @SuppressWarnings("deprecation")
	  public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		  Player p = (Player)sender;
		  
		  if (commandLable.equalsIgnoreCase("gym")) {
			  if (args.length == 0) {
				  
				  int time = Integer.parseInt(getConfig().getString("config.cooldowntime"));
				  
				  if (!p.hasPermission("pixelgym.leader")) {
			          p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
			          p.sendMessage("");
			          p.sendMessage(ChatColor.GREEN + "/gym list" + ChatColor.DARK_GREEN + " - Get a list of the gyms and there status.");
			          p.sendMessage(ChatColor.GREEN + "/gym leaders" + ChatColor.DARK_GREEN + " - Get a list of the online gym leaders.");
			          p.sendMessage(ChatColor.GREEN + "/gym rules <gym#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified Gym.");
			          p.sendMessage(ChatColor.GREEN + "/gym join <gym#>" + ChatColor.DARK_GREEN + " - Join the queue for the gym you want. Example: /gym join gym1");
			          p.sendMessage(ChatColor.GREEN + "/gym <leave | quit> <gym#>" + ChatColor.DARK_GREEN + " - Quits the gym queue of the specified gym. Example: /gym leave gym1.");
			          p.sendMessage(ChatColor.GREEN + "/gym <check | position> <gym#>" + ChatColor.DARK_GREEN + " - Check your position in a queue. Example: /gym check gym1");
			          p.sendMessage(ChatColor.GREEN + "/gym see [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player.");
			          p.sendMessage(ChatColor.GREEN + "/gym scoreboard" + ChatColor.DARK_GREEN + " - Toggle ScoreBoard.");
			          p.sendMessage("");
			          p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
			        } 
				  else if (p.hasPermission("pixelgym.leader")) {
			          p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
			          p.sendMessage("");
			          p.sendMessage(ChatColor.GREEN + "/gym list" + ChatColor.DARK_GREEN + " - Get a list of the gyms and there status.");
			          p.sendMessage(ChatColor.GREEN + "/gym leaders" + ChatColor.DARK_GREEN + " - Get a list of the online gym leaders.");
			          p.sendMessage(ChatColor.GREEN + "/gym scoreboard" + ChatColor.DARK_GREEN + " - Toggle ScoreBoard.");
			          p.sendMessage(ChatColor.GREEN + "/gym rules <gym#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified Gym.");
			          p.sendMessage(ChatColor.GREEN + "/gym <check | position> <gym#>" + ChatColor.DARK_GREEN + " - Check your position in a queue. Example: /gym check gym1");
			          p.sendMessage(ChatColor.GREEN + "/gym join <gym#>" + ChatColor.DARK_GREEN + " - Join the queue for the gym you want. Example: /gym join gym1");
			          p.sendMessage(ChatColor.GREEN + "/gym <see | check> [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player. | = or, you can type see or check.");
			          p.sendMessage(ChatColor.GREEN + "/gym next <gym#>" + ChatColor.DARK_GREEN + " - Grabs the first person of the specified gym queue and teleports them to the gym. (It also displays the gym rules for them in chat, so you don't need to)");
			          p.sendMessage(ChatColor.GREEN + "/gym remove <gym#>" + ChatColor.DARK_GREEN + " - Remove's the first person of the specified gym queue (If someone has disconnected and does not relog after a while)");
			          p.sendMessage(ChatColor.GREEN + "/gym giveTM <gym#> [Player]" + ChatColor.DARK_GREEN + " - Give's the TM to the player if they had there inventory full when winning the gym!");
			          p.sendMessage(ChatColor.GREEN + "/gym <sq | skip | skipq> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Skips the players cooldown of the specified gym, most commonly used if they disconnect mid battle and the automatic cooldown apply's");
			          p.sendMessage(ChatColor.GREEN + "/gym <winner | win | w> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a winner, giving them the badge for the next gym!");
			          p.sendMessage(ChatColor.GREEN + "/gym <lost | lose | l> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a loser, teleporting them out of the gym and giving them a " + time + " minute cooldown!");
			          p.sendMessage(ChatColor.GREEN + "/gym <leave | quit> <gym#>" + ChatColor.DARK_GREEN + " - Quits the gym queue of the specified gym. Example: /gym leave gym1.");
			          p.sendMessage(ChatColor.GREEN + "/gym sendrules <gym#> (Username)" + ChatColor.DARK_GREEN + " - Force shows the specifed gym's rules to the player specifed.");
			          p.sendMessage(ChatColor.GREEN + "/gym open <gym#>" + ChatColor.DARK_GREEN + " - Open a particular gym.");
			          p.sendMessage(ChatColor.GREEN + "/gym close <gym#>" + ChatColor.DARK_GREEN + " - Close a particular gym.");
			          p.sendMessage(ChatColor.GREEN + "/gym heal" + ChatColor.DARK_GREEN + " - Heals your pokemon.");
			          p.sendMessage(ChatColor.GREEN + "/gym quit" + ChatColor.DARK_GREEN + " - Force quits the gym battle.");
			          p.sendMessage("");
			          p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
			        } 
				  else if (p.hasPermission("pixelgym.admin")) {
			        	p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
			            p.sendMessage("");
			        	p.sendMessage(ChatColor.GREEN + "/gym list" + ChatColor.DARK_GREEN + " - Get a list of the gyms and there status.");
			            p.sendMessage(ChatColor.GREEN + "/gym leaders" + ChatColor.DARK_GREEN + " - Get a list of the online gym leaders.");
			            p.sendMessage(ChatColor.GREEN + "/gym scoreboard" + ChatColor.DARK_GREEN + " - Toggle ScoreBoard.");
			            p.sendMessage(ChatColor.GREEN + "/gym rules <gym#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified Gym.");
			            p.sendMessage(ChatColor.GREEN + "/gym <check | position> <gym#>" + ChatColor.DARK_GREEN + " - Check your position in a queue. Example: /gym check gym1");
			            p.sendMessage(ChatColor.GREEN + "/gym join <gym#>" + ChatColor.DARK_GREEN + " - Join the queue for the gym you want. Example: /gym join gym1");
				          p.sendMessage(ChatColor.GREEN + "/gym <see | check> [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player. | = or, you can type see or check.");
				          p.sendMessage(ChatColor.GREEN + "/gym next <gym#>" + ChatColor.DARK_GREEN + " - Grabs the first person of the specified gym queue and teleports them to the gym. (It also displays the gym rules for them in chat, so you don't need to)");
				          p.sendMessage(ChatColor.GREEN + "/gym remove <gym#>" + ChatColor.DARK_GREEN + " - Remove's the first person of the specified gym queue (If someone has disconnected and does not relog after a while)");
				          p.sendMessage(ChatColor.GREEN + "/gym giveTM <gym#> [Player]" + ChatColor.DARK_GREEN + " - Give's the TM to the player if they had there inventory full when winning the gym!");
				          p.sendMessage(ChatColor.GREEN + "/gym <winner | win | w> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a winner, giving them the badge for the next gym!");
				          p.sendMessage(ChatColor.GREEN + "/gym <lost | lose | l> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a loser, teleporting them out of the gym and giving them a " + time + " minute cooldown!");
				          p.sendMessage(ChatColor.GREEN + "/gym givebadge <gym#> [player]" + ChatColor.DARK_GREEN + " - Need to give badges quickly? Then use this command to give player's there badge's, avoiding the cooldown and having to be in a queue.");
				          p.sendMessage(ChatColor.GREEN + "/gym delbadge <gym#> [player]" + ChatColor.DARK_GREEN + " - Need to delete badges quickly? Then use this command to remove player's badge's, avoiding the cooldown and having to be in a queue.");
				          p.sendMessage(ChatColor.GREEN + "/gym addTM <gym#>" + ChatColor.DARK_GREEN + " - Adds the item in hand as a TM to the specified gym for the winners to randomly win!");
				          p.sendMessage(ChatColor.GREEN + "/gym <leave | quit> <gym#>" + ChatColor.DARK_GREEN + " - Quits the gym queue of the specified gym. Example: /gym leave gym1.");
				        p.sendMessage(ChatColor.GREEN + "/gym setwarp <gym#>" + ChatColor.DARK_GREEN + " - Used for the queue system, set a warp that is only a number. E.G: /gym setwarp 1 in the gym1 challanger spot.");
				        p.sendMessage(ChatColor.GREEN + "/gym delwarp <gym#>" + ChatColor.DARK_GREEN + " - Used for the queue system, delete a warp that you no longer need. E.G: /gym delwarp 1 to remove the gym1 teleport.");
				        p.sendMessage(ChatColor.GREEN + "/gym closeall" + ChatColor.DARK_GREEN + " - Closes all Gym's.");
			            p.sendMessage(ChatColor.GREEN + "/gym warp [warp name]" + ChatColor.DARK_GREEN + " - Warp to a gym warp! (For testing teleport locations of the queue system).");
			            p.sendMessage(ChatColor.GREEN + "/pixelgym" + ChatColor.DARK_GREEN + " - More admin commands");
			        }
				  
				  int req = getConfig().getInt("config.prestige_req");
				  
				  if ((settings.getBadge().get("Players." + p.getUniqueId() +".Badges.gym"+req) != null)) {
            			if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+req).equals("Won")) {
            				p.sendMessage(ChatColor.GOLD + "----- Prestige -----");
            				p.sendMessage(ChatColor.GOLD + "/gym prestige - Prestige to reset your gym badges and receive rewards!");
            			}
            		}
				  
				  for (int i = 1; i <= 32; i++) {
              		
              		//int u = i +1;

//              		if (getConfig().getString("config.gym"+i+"enabled").equalsIgnoreCase("True")) {
//              			if (i == 32) {
//              				if ((settings.getBadge().get("Players." + p.getUniqueId() +".Badges.gym"+i) != null)) {
//              					if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+i).equals("Won")) {
//              						p.sendMessage(ChatColor.GOLD + "----- Prestige -----");
//              						p.sendMessage(ChatColor.GOLD + "/gym prestige - Prestige to reset your gym badges and receive rewards!");
//                  				//ParticleEffect.FIREWORKS_SPARK.display(offsetX, offsetY, offsetZ, speed, amount, center, range);
//                  				}
//              				}
//              				
//              			}
//              			else {
//              			if (getConfig().getString("config.gym"+u+"enabled").equalsIgnoreCase("False")) {
//              				
//              				if ((settings.getBadge().get("Players." + p.getUniqueId() +".Badges.gym"+i) != null)) {
//              					if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+i).equals("Won")) {
//              						p.sendMessage(ChatColor.GOLD + "----- Prestige -----");
//              						p.sendMessage(ChatColor.GOLD + "/gym prestige - Prestige to reset your gym badges and receive rewards!");
//              						}
//              					}
//              				}
//              			
//              			}
//              		}

              	}
			  }
			  
			  else if (args.length == 1) {
				  
				  
				  
				  if (args[0].equalsIgnoreCase("scoreboard") && getConfig().getString("config.scoreboard").equals("True")) {
			          if (this.hashmap.containsKey(p)) {
			            p.sendMessage(ChatColor.GRAY + "Scoreboard - " + ChatColor.RED + "Disabled");
			            p.setScoreboard(this.clear);
			            this.hashmap.remove(p);
			          } else {
			            p.setScoreboard(this.board);
			            this.hashmap.put(p, null);
			            p.sendMessage(ChatColor.GRAY + "Scoreboard - " + ChatColor.GREEN + "Enabled");
			            if ((getConfig().getString("config.gym1stat").equals("Open")) &&
			                    (enablegym1.equalsIgnoreCase("true"))) {
			                    this.gym1.setScore(1);
			                  }
			                  if ((getConfig().getString("config.gym2stat").equals("Open")) &&
			                    (enable2.equalsIgnoreCase("true"))) {
			                    this.gym2.setScore(2);
			                  }
			                  if ((getConfig().getString("config.gym3stat").equals("Open")) &&
			                    (enable3.equalsIgnoreCase("true"))) {
			                    this.gym3.setScore(3);
			                  }
			                  if ((getConfig().getString("config.gym4stat").equals("Open")) &&
			                    (enable4.equalsIgnoreCase("true"))) {
			                    this.gym4.setScore(4);
			                  }
			                  if ((getConfig().getString("config.gym5stat").equals("Open")) &&
			                    (enable5.equalsIgnoreCase("true"))) {
			                    this.gym5.setScore(5);
			                  }
			                  if ((getConfig().getString("config.gym6stat").equals("Open")) &&
			                    (enable6.equalsIgnoreCase("true"))) {
			                    this.gym6.setScore(6);
			                  }
			                  if ((getConfig().getString("config.gym7stat").equals("Open")) &&
			                    (enable7.equalsIgnoreCase("true"))) {
			                    this.gym7.setScore(7);
			                  }
			                  if ((getConfig().getString("config.gym8stat").equals("Open")) &&
			                    (enable8.equalsIgnoreCase("true"))) {
			                    this.gym8.setScore(8);
			                  }
			                  if ((getConfig().getString("config.gym9stat").equals("Open")) &&
			                    (enable9.equalsIgnoreCase("true"))) {
			                    this.gym9.setScore(9);
			                        }
			                  if ((getConfig().getString("config.gym10stat").equals("Open")) &&
			                    (enablegym10.equalsIgnoreCase("true"))) {
			                    this.gym10.setScore(10);
			                        }
			                  if ((getConfig().getString("config.gym11stat").equals("Open")) &&
			                    (enablegym11.equalsIgnoreCase("true"))) {
			                    this.gym11.setScore(11);
			                        }
			                  if ((getConfig().getString("config.gym12stat").equals("Open")) &&
			                    (enablegym12.equalsIgnoreCase("true"))) {
			                    this.gym12.setScore(12);
			                        }
			                  if ((getConfig().getString("config.gym13stat").equals("Open")) &&
			                    (enablegym13.equalsIgnoreCase("true"))) {
			                    this.gym13.setScore(13);
			                        }
			                  if ((getConfig().getString("config.gym14stat").equals("Open")) &&
			                    (enablegym14.equalsIgnoreCase("true"))) {
			                    this.gym14.setScore(14);
			                        }
			                  if ((getConfig().getString("config.gym15stat").equals("Open")) &&
			                    (enablegym15.equalsIgnoreCase("true"))) {
			                    this.gym15.setScore(15);
			                        }
			                  if ((getConfig().getString("config.gym16stat").equals("Open")) &&
			                    (enablegym16.equalsIgnoreCase("true"))) {
			                    this.gym16.setScore(16);
			                        }
			                  if ((getConfig().getString("config.gym17stat").equals("Open")) &&
			                    (enablegym17.equalsIgnoreCase("true"))) {
			                    this.gym17.setScore(17);
			                        }
			                  if ((getConfig().getString("config.gym18stat").equals("Open")) &&
			                    (enablegym18.equalsIgnoreCase("true"))) {
			                    this.gym18.setScore(18);
			                        }
			                  if ((getConfig().getString("config.gym19stat").equals("Open")) &&
			                    (enablegym19.equalsIgnoreCase("true"))) {
			                    this.gym19.setScore(19);
			                        }
			                  if ((getConfig().getString("config.gym20stat").equals("Open")) &&
			                    (enable20.equalsIgnoreCase("true"))) {
			                    this.gym20.setScore(20);
			                        }
			                  if ((getConfig().getString("config.gym21stat").equals("Open")) &&
			                    (enable21.equalsIgnoreCase("true"))) {
			                    this.gym21.setScore(21);
			                        }
			                  if ((getConfig().getString("config.gym22stat").equals("Open")) &&
			                    (enable22.equalsIgnoreCase("true"))) {
			                    this.gym22.setScore(22);
			                        }
			                  if ((getConfig().getString("config.gym23stat").equals("Open")) &&
			                    (enable23.equalsIgnoreCase("true"))) {
			                    this.gym23.setScore(23);
			                        }
			                  if ((getConfig().getString("config.gym24stat").equals("Open")) &&
			                    (enable24.equalsIgnoreCase("true"))) {
			                    this.gym24.setScore(24);
			                        }
			                  if ((getConfig().getString("config.gym25stat").equals("Open")) &&
			                    (enable25.equalsIgnoreCase("true"))) {
			                    this.gym25.setScore(25);
			                              }
			                  if ((getConfig().getString("config.gym26stat").equals("Open")) &&
			                    (enable26.equalsIgnoreCase("true"))) {
			                    this.gym26.setScore(26);
			                              }
			                  if ((getConfig().getString("config.gym27stat").equals("Open")) &&
			                    (enable27.equalsIgnoreCase("true"))) {
			                    this.gym27.setScore(27);
			                              }
			                  if ((getConfig().getString("config.gym28stat").equals("Open")) &&
			                    (enable28.equalsIgnoreCase("true"))) {
			                    this.gym28.setScore(28);
			                              }
			                  if ((getConfig().getString("config.gym29stat").equals("Open")) &&
			                    (enable29.equalsIgnoreCase("true"))) {
			                    this.gym29.setScore(29);
			                              }
			                  if ((getConfig().getString("config.gym30stat").equals("Open")) &&
			                    (enable30.equalsIgnoreCase("true"))) {
			                    this.gym30.setScore(30);
			                              }
			                  if ((getConfig().getString("config.gym31stat").equals("Open")) &&
			                    (enable31.equalsIgnoreCase("true"))) {
			                    this.gym31.setScore(31);
			                              }
			                  if ((getConfig().getString("config.gym32stat").equals("Open")) &&
			                    (enable32.equalsIgnoreCase("true"))) {
			                    this.gym32.setScore(32);
			            }
			      
			                  if ((getConfig().getString("config.e41stat").equals("Open")) &&
			                          (enablee4.equalsIgnoreCase("true"))) {
			                          this.e41.setScore(101);
			                  }
			                  if ((getConfig().getString("config.e42stat").equals("Open")) &&
			                          (enablee4.equalsIgnoreCase("true"))) {
			                          this.e42.setScore(102);
			                  }
			                  if ((getConfig().getString("config.e43stat").equals("Open")) &&
			                          (enablee4.equalsIgnoreCase("true"))) {
			                          this.e43.setScore(103);
			                  }
			                  if ((getConfig().getString("config.e44stat").equals("Open")) &&
			                          (enablee4.equalsIgnoreCase("true"))) {
			                          this.e44.setScore(104);
			                  }
			             }
			          }
			        
				  else if (args[0].equalsIgnoreCase("leaders")) {
					  p.sendMessage(ChatColor.GOLD + "----- Online Gym Leaders -----");
			          p.sendMessage("");
			          for (Player staff : Bukkit.getOnlinePlayers()) {
			        	  if (staff.isOp() == true) {
			        	    	//do nothing
			        	    }
			        	  else if (!(p.canSee(staff))) {
			        		  //do nothing
			        	  }
			        	  else if (staff.getName().equalsIgnoreCase("ABkayCkay")) {
			        		  //do nothing
			        	  }
			        	    else {
			        	    	for (int i = 1; i <= 32; i++) {
			        	    		if (staff.hasPermission("pixelgym.gym"+i)) {
			        	    			if (getConfig().getString("config.gym"+i+"enabled").equalsIgnoreCase("true")) {
			        	    			    p.sendMessage(ChatColor.GREEN + staff.getName() + ChatColor.BLACK + " - " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+i+"colour")) + ChatColor.BOLD + getConfig().getString("config.gym"+i) + " Gym");
			        	    			}
			        	    		}
			        	    	}
			        	    	
			        	    }
			          }
				  }
				  else if (args[0].equalsIgnoreCase("prestige")) {
					  
					  int req = getConfig().getInt("config.prestige_req");
					  
					  if (!(inPrest.contains(p.getUniqueId()))) {
		 						if ((settings.getBadge().get("Players." + p.getUniqueId() +".Badges.gym"+req) != null)) {
			            			if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+req).equals("Won")) {
			            				
			            				inPrest.add(p.getUniqueId());
						  
			            				//int fee = settings.getExtras().getInt("prestige_pay");
						  
			            				p.sendMessage(ChatColor.RED + "You are about to reset your gym badges! This will delete all previous gym badges and give you money. Also so you can re-do the gym's.");
			            				p.sendMessage(ChatColor.GREEN + "Are you sure you want to prestige? There is no going back. Type '/gym confirm' if you are sure. And '/gym deny' if you no longer want to prestige!");
			            			}
			            			else {
			            				p.sendMessage(ChatColor.RED + "You have not won Gym"+req+" Badge!");
			            			}
		 						}
		 						else {
		 							p.sendMessage(ChatColor.RED + "You have never challenged Gym"+req+"!");
		 						}
						  
					  }
					  else {
						  p.sendMessage(ChatColor.RED + "You are already in the list to confirm or deny prestiging. Type '/gym confirm' if you are sure. And '/gym deny' if you no longer want to prestige!");
					  }
					  
	  }
				  else if (args[0].equalsIgnoreCase("confirm")) {
					  
					  int req = getConfig().getInt("config.prestige_req");
					  
					  if (inPrest.contains(p.getUniqueId())) {
					  
					  
	 						if ((settings.getBadge().get("Players." + p.getUniqueId() +".Badges.gym"+req) != null)) {
		            			if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+req).equals("Won")) {
		            				for (int i = 1; i <= 32; i++) {
		            					settings.getBadge().set("Players." + p.getUniqueId() + ".Badges.gym" + i, null);
		            					}
		            				
		            				int fee = getConfig().getInt("config.prestige_pay");
		            				System.out.println(fee);
		            				economy.depositPlayer(p, fee);
		            				
		            				
		            				p.sendMessage(ChatColor.GOLD + "You have been payed $" +fee+ "! You may now re-do all the gym's!");
		            				
		            				}
		            			else {
		            				//gym8 is not won
		            				}
		            			}
	 						else {
	 							//gym8 does not exist in there
	 						}
	 						
	 						p.sendMessage(ChatColor.GREEN + "Deleted all badges you have!");
		 					 settings.saveBadges();
		 					 inPrest.remove(p.getUniqueId());
	 				 }
	 					 
					  }
				 
				  
				  else if (args[0].equalsIgnoreCase("deny")) {
					  if (inPrest.contains(p.getUniqueId())) {
					  
					  inPrest.remove(p.getUniqueId());
					  p.sendMessage(ChatColor.GREEN + "You have succefully denied prestiging. Your Badges will not be reset.");
					  }
					  else {
						  p.sendMessage(ChatColor.RED + "You have not done the prestige command! Type '/gym prestige' to get started.");
					  }
				  }
				  
				  else if (args[0].equals("list")) {
			          p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
			          p.sendMessage("");
			          for (int i = 1; i <= 32; i++) {
			        	  if (getConfig().getString("config.gym"+i+"enabled").equalsIgnoreCase("true")) {
			        		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+i+"colour")) + ChatColor.BOLD + getConfig().getString("config.gym"+i) + " Gym is: " + ChatColor.DARK_GREEN + getConfig().getString("config.gym"+i+"stat") + ChatColor.BLUE + " - " + "Level Cap = " + getConfig().getString("config.gym"+i+"lvlcap"));
			        	  }
			          }
				  }
				  
				    else if ((args[0].equals("open")) && (p.hasPermission("pixelgym.leader"))) {
				      p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym to open!");
			          p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym open <gym#>");
			        } 
				    else if ((args[0].equals("sendrules")) && (p.hasPermission("pixelgym.leader"))) {
				    	p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym to send rules about, as well as a player to send the rules too.");  
				    	p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym sendrules <gym#> (username)");
				        } 
			        else if ((args[0].equals("close")) && (p.hasPermission("pixelgym.leader"))) {
			        	p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym to close!");
			          p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym close <gym#>");
			        } 
			        else if ((args[0].equals("rules"))) {
			        	p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym that you want to read the rules of!");
			          p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym rules <gym#>");
			        } 
			        else if ((args[0].equals("join"))) {
			        	p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym queue that you want to join!");
			          p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym join <gym#>");
			        } 
			        else if ((args[0].equals("leave"))) {
			        	p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym queue that you want to leave!");
			          p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym leave <gym#>");
			        }
			        else if (args[0].equalsIgnoreCase("check") || args[0].equalsIgnoreCase("position") ) {
			        	p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym queue position that you want to check!");
				          p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym position <gym#>");
			        }
			        else if ((args[0].equals("next"))) {
			        	p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym to grab the first player of a queue for!");
			          p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym next <gym#>");
			        }
			        else if ((args[0].equals("heal")) && (p.hasPermission("pixelgym.leader")) && (enableGymHeal.equalsIgnoreCase("true"))) {
			          Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + p.getName().toString());
			          p.sendMessage("Your pixelmon have been healed!");
			        } 
			        else if ((args[0].equals("heal")) && (p.hasPermission("pixelgym.leader")) && (!enableGymHeal.equalsIgnoreCase("true"))) {
			    		p.sendMessage(ChatColor.RED + "Gym/E4 Leader healing disabled in the plugin config");
			    	}
				  
			        else if ((args[0].equals("quit")) && (p.hasPermission("pixelgym.leader"))) {
		              Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "endbattle");
		              p.sendMessage("You have successfully quit the battle!");
		    }  
				
			        else if ((args[0].equalsIgnoreCase("closeall")) && (p.hasPermission("pixelgym.admin"))) {
			            p.sendMessage(ChatColor.AQUA + "All gyms are now closed.");
			            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "All gyms are now closed.");
			            getConfig().set("config.gym1stat", "Closed");
			            getConfig().set("config.gym2stat", "Closed");
			            getConfig().set("config.gym3stat", "Closed");
			            getConfig().set("config.gym4stat", "Closed");
			            getConfig().set("config.gym5stat", "Closed");
			            getConfig().set("config.gym6stat", "Closed");
			            getConfig().set("config.gym7stat", "Closed");
			            getConfig().set("config.gym8stat", "Closed");
			            getConfig().set("config.gym9stat", "Closed");
			            getConfig().set("config.gym10stat", "Closed");
			            getConfig().set("config.gym11stat", "Closed");
			            getConfig().set("config.gym12stat", "Closed");
			            getConfig().set("config.gym13stat", "Closed");
			            getConfig().set("config.gym14stat", "Closed");
			            getConfig().set("config.gym15stat", "Closed");
			            getConfig().set("config.gym16stat", "Closed");
			            getConfig().set("config.gym17stat", "Closed");
			            getConfig().set("config.gym18stat", "Closed");
			            getConfig().set("config.gym19stat", "Closed");
			            getConfig().set("config.gym20stat", "Closed");
			            getConfig().set("config.gym21stat", "Closed");
			            getConfig().set("config.gym22stat", "Closed");
			            getConfig().set("config.gym23stat", "Closed");
			            getConfig().set("config.gym24stat", "Closed");
			            getConfig().set("config.gym25stat", "Closed");
			            getConfig().set("config.gym26stat", "Closed");
			            getConfig().set("config.gym27stat", "Closed");
			            getConfig().set("config.gym28stat", "Closed");
			            getConfig().set("config.gym29stat", "Closed");
			            getConfig().set("config.gym30stat", "Closed");
			            getConfig().set("config.gym31stat", "Closed");
			            getConfig().set("config.gym32stat", "Closed");

			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym1colour")) + getConfig().getString("config.gym1")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym2colour")) + getConfig().getString("config.gym2")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym3colour")) + getConfig().getString("config.gym3")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym4colour")) + getConfig().getString("config.gym4")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym5colour")) + getConfig().getString("config.gym5")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym6colour")) + getConfig().getString("config.gym6")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym7colour")) + getConfig().getString("config.gym7")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym8colour")) + getConfig().getString("config.gym8")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym9colour")) + getConfig().getString("config.gym9")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym10colour")) + getConfig().getString("config.gym10")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym11colour")) + getConfig().getString("config.gym11")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym12colour")) + getConfig().getString("config.gym12")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym13colour")) + getConfig().getString("config.gym13")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym14colour")) + getConfig().getString("config.gym14")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym15colour")) + getConfig().getString("config.gym15")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym16colour")) + getConfig().getString("config.gym16")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym17colour")) + getConfig().getString("config.gym17")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym18colour")) + getConfig().getString("config.gym18")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym19colour")) + getConfig().getString("config.gym19")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym20colour")) + getConfig().getString("config.gym20")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym21colour")) + getConfig().getString("config.gym21")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym22colour")) + getConfig().getString("config.gym22")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym23colour")) + getConfig().getString("config.gym23")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym24colour")) + getConfig().getString("config.gym24")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym25colour")) + getConfig().getString("config.gym25")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym26colour")) + getConfig().getString("config.gym26")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym27colour")) + getConfig().getString("config.gym27")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym28colour")) + getConfig().getString("config.gym28")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym29colour")) + getConfig().getString("config.gym29")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym30colour")) + getConfig().getString("config.gym30")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym31colour")) + getConfig().getString("config.gym31")));
			            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym32colour")) + getConfig().getString("config.gym32")));
			          }
				  
			  }
			  
			  else if (args.length == 2) {
				  
				  if (args[0].equalsIgnoreCase("admincheck")) {
		        		if (Bukkit.getPlayer(args[1]) != null) {
		        			//Player playerTarget = Bukkit.getPlayer(args[2]);
		        			
		        			UUID uuid = Bukkit.getPlayer(args[1]).getUniqueId();
		        			
		        			Player playerTarget = Bukkit.getPlayer(uuid);
		        			
		        			for (int i = 1; i <= 32; i++) {
		        				if (queues.get(i).contains(uuid)) {
		        					p.sendMessage(ChatColor.GREEN + playerTarget.getName() + " is in gym" +i + " queue.");
		        				}
		        				if (inArena.get(i).contains(uuid)) {
		        					p.sendMessage(ChatColor.GREEN + playerTarget.getName() + " is in gym" +i + " inArena.");
		        				}
		        				if (cooldownGym.get(i).contains(uuid)) {
		        					p.sendMessage(ChatColor.GREEN + playerTarget.getName() + " is in gym" +i + " Cooldown.");
		        				}
		        			}
		        			
		        			//p.sendMessage(ChatColor.GREEN + " " + queues);
		        			p.sendMessage(ChatColor.GOLD + " " + uuid);
		        			
		        		}
		        	}
	              
	          if (args[0].equalsIgnoreCase("join")) {
	        	  
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
	              
	              int i = gym -1;
	              //int n = gym +1;
	              
      			
      			
	              
	              //final Player o = (Player) sender;
	              final UUID o = p.getUniqueId();

	        		if (!(cooldownTime.get(gym).containsKey(o))) {

	        	if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+gym) != null) {
	        		if (settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+gym).equals("Won")) {
	        		       p.sendMessage(ChatColor.RED + "You have already completed this gym! You may not do it again!");
	        		 	}
	        			
	        		}

	        		else {
	            	if(gym == 1) {
	            		if (queues.get(gym).contains(p.getUniqueId())) {

	            			p.sendMessage(ChatColor.RED + "You are already in this queue, please wait to be teleported.");
	            		
	            	   }

	            	
	            		else {

	            		  queues.get(gym).add(p.getUniqueId());
	            		  
	            		  
	            		  //UUID queuesUUID = queues.get(gym).get(pUUID);
	            			//Player queuesPlayer = Bukkit.getPlayer(queuesUUID);
	            		  
	            		  
	            		  p.sendMessage(ChatColor.GREEN + "Added to queue: " + ChatColor.YELLOW + ChatColor.BOLD + gym + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym1colour") + ChatColor.BOLD + getConfig().getString("config.gym1") + ChatColor.BLACK + ")"));
		                  p.sendMessage(ChatColor.GOLD + "You are in position " + queues.get(gym).size() + " for the "+ getConfig().getString("config.gym1") + " Gym");
		                  p.sendMessage(ChatColor.GREEN + "Notified gym leaders of gym1" + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym1colour") + ChatColor.BOLD + getConfig().getString("config.gym1") + ChatColor.BLACK + ")" + ChatColor.GREEN + " that you are waiting to be battled!"));
	            	
		                  for (Player leader : Bukkit.getOnlinePlayers()) {
			                  
			                  if (leader.hasPermission("pixelgym."+args[1])) {
			                	  leader.sendMessage(ChatColor.BLUE + "A challenger has joined queue " + ChatColor.YELLOW + ChatColor.BOLD + gym + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour") + ChatColor.BOLD + getConfig().getString("config."+args[1]) + ChatColor.BLACK + ")" + ChatColor.GOLD + " (" + p.getName() + ")"));
			                	  leader.sendMessage(ChatColor.BLUE + "Type /gym next " +args[1] + " to teleport them to your gym.");
			                	  
			                    }
			                  
			                 
			                   
			                 }
	            	    }
	            		
	            	}
	            	else {


	            	
	            if ((settings.getBadge().get("Players." + p.getUniqueId() +".Badges.gym"+i) != null)) {
	            	
	               if ((settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym"+i).equals("Won"))) {
	            	  
	            	   if (queues.get(gym).contains(p.getUniqueId())) {

	            			p.sendMessage(ChatColor.RED + "You are already in this queue, please wait to be teleported.");
	            			return true;
	            		
	            	   }

	            		else {

	            		//if not, then add player to the queue. 
	            		
	            	  queues.get(gym).add(p.getUniqueId());
	            	  
	            	  
	                  p.sendMessage(ChatColor.GREEN + "Added to queue: " + ChatColor.YELLOW + ChatColor.BOLD + gym + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour") + ChatColor.BOLD + getConfig().getString("config."+args[1]) + ChatColor.BLACK + ")"));
	                  p.sendMessage(ChatColor.GOLD + "You are in position " + queues.get(gym).size() + " for the "+ getConfig().getString("config."+args[1]) + " Gym");
	                  p.sendMessage(ChatColor.GREEN + "Notified gym leaders of gym"+gym+  ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour") + ChatColor.BOLD + getConfig().getString("config."+args[1]) + ChatColor.BLACK + ")" + ChatColor.GREEN + " that you are waiting to be battled!"));
	                  
	                  
	                  for (Player leader : Bukkit.getOnlinePlayers()) {
	                  
	                  if (leader.hasPermission("pixelgym."+args[1])) {
	                	  leader.sendMessage(ChatColor.BLUE + "A challenger has joined queue " + ChatColor.YELLOW + ChatColor.BOLD + gym + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour") + ChatColor.BOLD + getConfig().getString("config."+args[1]) + ChatColor.BLACK + ")" + ChatColor.GOLD + " (" + p.getName() + ")"));
	                	  leader.sendMessage(ChatColor.BLUE + "Type /gym next " +args[1] + " to teleport them to your gym.");
	                	  
	                    }
	                  
	                  
	                   
	                 }
	            		
	            		}  
	               
	            	}
	               else {
	            	   p.sendMessage(ChatColor.RED + "You cannot join this queue as you have not won the previous badge!");
	               }
	            }
	            else {
	            	p.sendMessage(ChatColor.RED + "You cannot join the gym queue for gym"+gym + " because you do not have the previous badge. (gym"+i+")");
	            }
	            	}
	        	   
	        	}

	          }
	        		else {
	        			p.sendMessage(ChatColor.RED + "You have to wait " + cooldownTime.get(gym).get(o) + " minutes before you can try gym"+gym + " again.");
	        		}
	       }
	          
	          	
		  

			  
	              else if (args[0].equalsIgnoreCase("check") || args[0].equalsIgnoreCase("position") ) {
	            	  
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
		              
		              Player playerFirst = null;
		              
		              //Player online = (Player) Bukkit.getOnlinePlayers();
		              
		              if (queues.get(gym).size() > 0) {
		            	
		              UUID uuid = queues.get(gym).get(0);

                      playerFirst = Bukkit.getPlayer(uuid);
                      
                      System.out.println(playerFirst);
                      System.out.println(queues.get(gym));
                      System.out.println(queues);
                      
                      int iu = queues.get(gym).indexOf(p.getUniqueId()) +1;
	            	  
	            	  if (queues.get(gym).contains(p.getUniqueId())) {
	            		  p.sendMessage(ChatColor.GOLD + "You are currently in position " + iu + " for the " + getConfig().getString("config."+args[1]) + " Gym");
	            		  //p.sendMessage(ChatColor.YELLOW + "If you are in position '0', this means you are ready to be chosen and are at the front of the queue.");
	            		  p.sendMessage(ChatColor.GOLD + "The queue size for the " + getConfig().getString("config."+args[1]) + " Gym is " + queues.get(gym).size());
	            	  }
	            	  if (p.hasPermission("pixelgym."+args[1]) && (!(queues.get(gym).contains(p.getUniqueId())))) {
	            		  p.sendMessage(ChatColor.GOLD + "The queue size for the " + getConfig().getString("config."+args[1]) + " Gym is " + queues.get(gym).size());
	            		  p.sendMessage(ChatColor.GOLD + "The first player of the queue is: (" + playerFirst.getName() + ")");
	            	  			}
	            	  else if (queues.get(gym).contains(p.getUniqueId()) && p.hasPermission("pixelgym."+args[1])) {
	            		  p.sendMessage(ChatColor.GOLD + "The queue size for the " + getConfig().getString("config."+args[1]) + " Gym is " + queues.get(gym).size());
	            		  p.sendMessage(ChatColor.GOLD + "The first player of the queue is: (" + playerFirst.getName() + ")");
	            	  			 
	            	  }
	            	  else {
	            		  p.sendMessage(ChatColor.RED + "You are not in queue " +args[1]);
	            
	            	  		}
		              	}
		              else {
		              p.sendMessage(ChatColor.RED + "There is no one in this queue!");  
		              }
		              
	              }
	              
	              else if (args[0].equalsIgnoreCase("leave") || (args[0].equalsIgnoreCase("quit"))) {
	            	  
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
		              
	            	  
	            	  if(queues.get(gym).contains(p.getUniqueId())) {
	            		  if (!(inArena.get(gym).contains(p.getUniqueId()))) {
	            		  //if player is in the gym queue, remove them
	            		  queues.get(gym).remove(p.getUniqueId());
	            		  
	            		  	World w = Bukkit.getServer().getWorld(settings.getData().getString("warps.spawn.world"));
			                double x = settings.getData().getDouble("warps.spawn.x");
			                double y = settings.getData().getDouble("warps.spawn.y");
			                double z = settings.getData().getDouble("warps.spawn.z");
			                p.teleport(new Location(w, x, y, z));
	            		  
	            		  p.sendMessage(ChatColor.GREEN + "You have successfully been removed from the gym" +gym + " queue!" + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour") + ChatColor.BOLD + getConfig().getString("config."+args[1]) + ChatColor.BLACK + ")"));
	            		}
	            		  else {
	            			  p.sendMessage(ChatColor.RED + "You cannot leave the queue as you are in the area!");
	            		  }
	              }
	            	  else {
	            		  //if player is not in a queue, tell them they are not.
	            		  p.sendMessage(ChatColor.RED + "You are not in queue " +gym);
	            	  }
	            		
	            		}
	   
	              else if (args[0].equalsIgnoreCase("remove")) {
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
		              	
		              
		              if (p.hasPermission("pixelgym.leader")) {
		            		if (p.hasPermission("pixelgym."+args[1]) || p.hasPermission("pixelgym.admin")) {
		            			
		            			UUID removeUUID = queues.get(gym).get(0);
		            			
		            			Player toRemove = Bukkit.getPlayer(removeUUID);
		            			
		            			
		            			//if (queues.get(online.getUniqueId()) != null) {
		            				
		            			//}

		            			if ((settings.getData().get("warps.spawn") != null)) {
		            				queues.get(gym).remove(0);
		            				
		            				if(inArena.get(gym).contains(removeUUID)) {
		            					inArena.get(gym).remove(removeUUID);
		            				}
		            				
		    					    World w = Bukkit.getServer().getWorld(settings.getData().getString("warps.spawn.world"));
					                double x = settings.getData().getDouble("warps.spawn.x");
					                double y = settings.getData().getDouble("warps.spawn.y");
					                double z = settings.getData().getDouble("warps.spawn.z");
					                toRemove.teleport(new Location(w, x, y, z));
					                toRemove.sendMessage(ChatColor.GREEN + "You have been removed from the queue by a gym leader! (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+gym+"colour")+ ChatColor.BOLD + getConfig().getString("config.gym"+gym) +ChatColor.GREEN+  ") You can re-enter the queue, but make sure you are not Afk and co-operate with the gym leader!"));
				    				
					                p.sendMessage(ChatColor.GREEN + "Successfully telported " +toRemove.getName() + " out of your gym!");
					                p.sendMessage(ChatColor.GREEN + "You are now ready for your next battle, type: /gym next gym"+gym);
				    			}
				    			else {
				    				p.sendMessage(ChatColor.RED + "Warp point 'spawn' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym.");
				    			}
		            			
		            			
		            		  }
		            		}
	              }
	          
	              else if (args[0].equalsIgnoreCase("next")) {
	            	  
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
		              
		              
		              
	            	  int add = gym +1;
	            	if (p.hasPermission("pixelgym.leader")) {
	            		if (p.hasPermission("pixelgym."+args[1])) {
	            			
	            	  Player playerToTeleport = null;
	            	  
	                  while (playerToTeleport == null) {

	                      if (queues.get(gym).size() > 0) {
	                    	  if (settings.getData().getConfigurationSection("warps." + args[1]) == null) {
	        	                    p.sendMessage(ChatColor.RED + "Warp " + args[1] + " does not exist!");
	        	                    return true;
	        	            }
	                    	  
//	                    	  if(queues.get(gym).get(0) == null) {
//	                    		  p.sendMessage(ChatColor.RED + "Player is offline, you can remove them from the queue with /gym remove gym" +gym);
//	                    		  return true;
//	                    	  }
	                    	  
	                          UUID uuid = queues.get(gym).get(0);

	                          //queues.get(gym).remove(0);

	                          playerToTeleport = Bukkit.getPlayer(uuid);
	                          
	                          System.out.println(playerToTeleport);
	                          System.out.println(uuid);
	                          
	                          p.sendMessage(ChatColor.GREEN + "Getting first player from queue " + ChatColor.GOLD + " (" + playerToTeleport.getName() + ")" + ChatColor.YELLOW + ChatColor.BOLD +gym + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+gym+"colour") + ChatColor.BOLD + getConfig().getString("config.gym"+gym) + ChatColor.BLACK + ")"));
                              p.sendMessage(ChatColor.BLUE + "Make sure you type " + ChatColor.RED+ "/gym lost gym"+gym + " (player) "+ChatColor.BLUE + "or " +ChatColor.GREEN + "/gym win gym"+gym +" (player)" + ChatColor.BLUE + " after they have won or lost the battle. (They need it to join gym"+add+ " queue (If they won.)) And it teleports them out of your gym!");
                              
	                      } else {

	                          p.sendMessage(ChatColor.RED + "There are currently no people in the queue " + ChatColor.YELLOW + ChatColor.BOLD + gym + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+gym+"colour")+ ChatColor.BOLD + getConfig().getString("config.gym"+gym) + ChatColor.BLACK + ")"));

	                          return true;
	                      }
	                  
	              }   	
	                    
	                if (getConfig().getString("config.gymfee").equalsIgnoreCase("True")) {
	                    	
	                    	int fee = getConfig().getInt("config.gymfee");
	                    	
	                  if (economy.getBalance(playerToTeleport) >= fee) {
	                    Bukkit.dispatchCommand(playerToTeleport, "pay " +p.getName()+ " " + getConfig().getString("config.gymfeeamount"));
	                    
	                    World w = Bukkit.getServer().getWorld(settings.getData().getString("warps." + args[1] + ".world"));
	                    
	                    EconomyResponse TookMoney = economy.withdrawPlayer(playerToTeleport, fee);
	                    EconomyResponse sentMoney = economy.depositPlayer(p, fee);
	                    
	                    if(TookMoney.transactionSuccess() && sentMoney.transactionSuccess()) {
	                    	//playerToTeleport.sendMessage(ChatColor.GREEN + "Gave " + p.getName() + " " +fee );
	                    	//p.sendMessage(ChatColor.GREEN + "Recieved " + fee + " from " + playerToTeleport.getName());
	                    }
	                    
		                double x = settings.getData().getDouble("warps." + args[1] + ".x");
		                double y = settings.getData().getDouble("warps." + args[1] + ".y");
		                double z = settings.getData().getDouble("warps." + args[1] + ".z");
		                playerToTeleport.teleport(new Location(w, x, y, z));
		                playerToTeleport.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.YELLOW + ChatColor.BOLD + args[1] + "!");
		                playerToTeleport.sendMessage(ChatColor.GREEN + "Welcome to " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour") + ChatColor.BOLD + getConfig().getString("config."+args[1]) + " Gym!"));
		                playerToTeleport.sendMessage(ChatColor.GOLD + "----- "+getConfig().getString("config.gym"+gym)+" Gym Rules -----");
		                playerToTeleport.sendMessage("");
		                playerToTeleport.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config.gym"+gym+"rule1"));
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config.gym"+gym+"rule2"));
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config.gym"+gym+"rule3"));
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config.gym"+gym+"rule4"));
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config.gym"+gym+"rule5"));
		             	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + playerToTeleport.getName().toString());
		             	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + p.getName().toString());
				        playerToTeleport.sendMessage(ChatColor.GREEN + "Your pixelmon have been healed!");
				        
				        inArena.get(gym).add(playerToTeleport.getUniqueId());
				        
//				        if (permission.has(playerToTeleport, "essentials.spawn")) {
//				        	System.out.println("player has essentials.spawn");
//				        	permission.playerRemove(playerToTeleport, "essentials.spawn");
//				        	spawnperm = true;
//				        }
//				
//				        if (permission.has(playerToTeleport, "essentials.tpa")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.tpa");
//				        	tpaperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "essentials.home")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.home");
//				        	homeperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "essentials.back")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.back");
//				        	backperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "essentials.warp")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.warp");
//				        	warpperm = true;
//				        
//				        }
//				        if (permission.has(playerToTeleport, "essentials.tpaccept")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.tpaccept");
//				        	tpacceptperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "randomtp.tp")) {
//				        	permission.playerRemove(playerToTeleport, "randomtp.tp");
//				        	randomtpperm = true;
//				        }
				        
	                    //playerToTeleport.sendMessage(ChatColor.GOLD + "Sending " +getConfig().getString("config.gymfeeamount") + " to " +p.getDisplayName());
	                    		}
	                    	else {
	                    		playerToTeleport.sendMessage(ChatColor.RED + "You do not have enough money to face the gym!");
	                    		queues.get(gym).remove(0);
	                    		p.sendMessage(ChatColor.RED + "First player did not have enough money, type /gym next gym"+gym + " to get the next player.");
	                    		return true;
	                    	}
	                    }
	                    else {
	                    
	                    
	                    World w = Bukkit.getServer().getWorld(settings.getData().getString("warps." + args[1] + ".world"));
		                double x = settings.getData().getDouble("warps." + args[1] + ".x");
		                double y = settings.getData().getDouble("warps." + args[1] + ".y");
		                double z = settings.getData().getDouble("warps." + args[1] + ".z");
		                playerToTeleport.teleport(new Location(w, x, y, z));
		                playerToTeleport.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.YELLOW + ChatColor.BOLD + args[1] + "!");
		                playerToTeleport.sendMessage(ChatColor.GREEN + "Welcome to " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour") + ChatColor.BOLD + getConfig().getString("config."+args[1]) + " Gym!"));
		                playerToTeleport.sendMessage(ChatColor.GOLD + "----- "+getConfig().getString("config.gym"+gym)+" Gym Rules -----");
		                playerToTeleport.sendMessage("");
		                playerToTeleport.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config.gym"+gym+"rule1"));
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config.gym"+gym+"rule2"));
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config.gym"+gym+"rule3"));
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config.gym"+gym+"rule4"));
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config.gym"+gym+"rule5"));
		             	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + playerToTeleport.getName().toString());
		             	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + p.getName().toString());
		             	playerToTeleport.sendMessage(ChatColor.GREEN + "Your pixelmon have been healed!");
		             	
		             	inArena.get(gym).add(playerToTeleport.getUniqueId());
				        
//				        if (permission.has(playerToTeleport, "essentials.spawn")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.spawn");
//				        	spawnperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "essentials.tpa")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.tpa");
//				        	tpaperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "essentials.home")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.home");
//				        	homeperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "essentials.back")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.back");
//				        	backperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "essentials.warp")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.warp");
//				        	warpperm = true;
//				        
//				        }
//				        if (permission.has(playerToTeleport, "essentials.tpaccept")) {
//				        	permission.playerRemove(playerToTeleport, "essentials.tpaccept");
//				        	tpacceptperm = true;
//				        }
//				        if (permission.has(playerToTeleport, "randomtp.tp")) {
//				        	permission.playerRemove(playerToTeleport, "randomtp.tp");
//				        	randomtpperm = true;
//				        }
				        
		             	//inArena.get(gym).add(playerToTeleport.getUniqueId());
	              
	                  //playerToTeleport.teleport(location);
	                      }
	            		}
	            		else {
	            			p.sendMessage(ChatColor.RED + "You do not have permission to open gym"+gym+"!");
	            		}
	            		
	           }
	            	else {
	            	p.sendMessage(ChatColor.RED + "You are not a gym leader, you do not have permission to do this command!");
	            	}
	            	
	              }    
	          
	          if (args[0].equalsIgnoreCase("showcase")) {
	        	  if (args[1].equalsIgnoreCase("false")) {
	        		  	settings.getExtras().set("showcase."+p.getUniqueId(), "false");
  						settings.saveExtras();
	        		 
	        	  }
	        	  if (args[1].equalsIgnoreCase("true")) {
	        		  	settings.getExtras().set("showcase."+p.getUniqueId(), "true");
						settings.saveExtras();
	        		 
	        	  }
	          }
                  
			        	

				if ((args[0].equalsIgnoreCase("see") || args[0].equalsIgnoreCase("s"))) {
					

						if (Bukkit.getPlayer(args[1]) != null) {
							Player playerBadges = Bukkit.getPlayer(args[1]);
							
							Inventory myInventory_ = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Badges!");

							for (int i = 1; i <= 32; i++) {
								
								int u = i -1;
								
							if ((settings.getBadge().get("Players." + playerBadges.getUniqueId() + ".Badges.gym"+i) != null)) {
								if (settings.getBadge().getString("Players." + playerBadges.getUniqueId() + ".Badges.gym"+i)
										.equalsIgnoreCase("Won")) {
									if (getConfig().getString("config.gym"+i+"badge") != null) {
										
										//myInventory_.setItem(u, new ItemStack(Material.getMaterial(getConfig().getInt("config.gym"+i+"badge"))));
										
										ItemStack badge = new ItemStack(getConfig().getInt("config.gym"+i+"badge"));
										ItemMeta itemMeta = badge.getItemMeta();
										itemMeta.setDisplayName(ChatColor.RED + (ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+i+"colour")+ ChatColor.BOLD + getConfig().getString("config.gym"+i) + " Badge!")));										
										
										if ((settings.getLogs().get("Challengers." + playerBadges.getUniqueId() + ".gym"+i) != null)) {
										itemMeta.setLore(Arrays.asList(ChatColor.GOLD + "Date/Time Won: " + ChatColor.GREEN + settings.getLogs().getString("Challengers." + playerBadges.getUniqueId() + "." + "gym"+i + ".Date/Time Won"), ChatColor.GOLD + "Gym Leader: " + ChatColor.GREEN + settings.getLogs().getString("Challengers." + playerBadges.getUniqueId() + "." + "gym"+i + ".Gym Leader")));
										badge.setItemMeta(itemMeta);
										myInventory_.setItem(u, badge);
										}
										else {
											itemMeta.setLore(Arrays.asList(ChatColor.RED + "Gym badge info un-known! (Badge won before feature implemented!)"));
											badge.setItemMeta(itemMeta);
											myInventory_.setItem(u, badge);
											//myInventory_.setItem(u, new ItemStack(Material.getMaterial(getConfig().getInt("config.gym"+i+"badge"))));
										}
									}
										
								}
							}
							
							
						}
							
							 if(!hasOpen.contains(p.getName())) {
                                 hasOpen.add(p.getName());
                         }
							 
							//myInventories.put(playerBadges.getName(), myInventory_);
                            p.openInventory(myInventory_);
						    p.sendMessage(ChatColor.GREEN + "Opening " + playerBadges.getName() + "'s badge showcase!");
					 }
					
					

				}
			        	
			        
			       
				  
				  if ((args[0].equalsIgnoreCase("sendrules"))) {
					  if (getConfig().getString("config."+args[1]) != (null)) {
						  p.sendMessage(ChatColor.RED + "You need to specify a player that you want to send the rules to!");
						  p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym sendrules <gym#> (username)");
					  	}
					 
					  else {
						  p.sendMessage(ChatColor.RED + "There are only 32 gym's, please use a valid gym!");
					  
					  }
				  }
				  
				  if ((args[0].equalsIgnoreCase("rules"))) {
						  
					  if (getConfig().getString("config."+args[1]) != (null)) {
							  
						  if (getConfig().getString("config."+args[1]+"enabled").equalsIgnoreCase("true")) {
							  p.sendMessage(ChatColor.GOLD + "----- "+getConfig().getString("config."+args[1])+" Gym Rules -----");
				              p.sendMessage("");
				              p.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config."+args[1]+"rule1"));
				              p.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config."+args[1]+"rule2"));
				              p.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config."+args[1]+"rule3"));
				              p.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config."+args[1]+"rule4"));
				              p.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config."+args[1]+"rule5"));
						   }
						  }
					  else {
						  p.sendMessage(ChatColor.RED + "The gym " +args[1]+ " does not exist!");
						  p.sendMessage(ChatColor.RED + "Try /gym rules gym1");
					  }
				   }
				if ((args[0].equalsIgnoreCase("open"))) {
				  if (p.hasPermission("pixelgym.leader") || p.hasPermission("pixelgym.admin")) {
					  if (getConfig().getString("config."+args[1]) != (null)) {
					  if (p.hasPermission("pixelgym."+args[1])  || p.hasPermission("pixelgym.admin")) {
							  if (args[1].equalsIgnoreCase("e41") || (args[1].equalsIgnoreCase("e42") || (args[1].equalsIgnoreCase("e43") || (args[1].equalsIgnoreCase("e44"))))) {
								  p.sendMessage(ChatColor.RED + "To open the Elite 4, type /e4 open e4#. Not /gym open e4#");
							  }
							  else {
								  if (getConfig().getString("config."+args[1]+"stat").equals("Open")) {
					        		  p.sendMessage(ChatColor.RED + "The "+ getConfig().getString("config."+args[1])+ " Gym is already open! ");
							  }
								  else if (getConfig().getString("config."+args[1]+"stat").equals("Closed")) {
									  if (getConfig().getString("config."+args[1]+"enabled").equalsIgnoreCase("True"))	  {
							        		Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour")) + "The " + getConfig().getString("config."+args[1]) + " Gym is now " + ChatColor.GREEN + "Open");
							        		getConfig().set("config."+args[1]+"stat", "Open");
							        		p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
							        		//reloadConfig();
							        		if (getConfig().getString("config.scoreboard").equals("True")) {
							                  if (args[1].equalsIgnoreCase("gym1")) {
							        			this.gym1.setScore(1);
							        		}
							                  else if (args[1].equalsIgnoreCase("gym2")) {
							          			this.gym2.setScore(2);
							          		}
							                  else if (args[1].equalsIgnoreCase("gym3")) {
							            			this.gym3.setScore(3);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym4")) {
							            			this.gym4.setScore(4);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym5")) {
							            			this.gym5.setScore(5);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym6")) {
							            			this.gym6.setScore(6);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym7")) {
							            			this.gym7.setScore(7);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym8")) {
							            			this.gym8.setScore(8);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym9")) {
							            			this.gym9.setScore(9);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym10")) {
							            			this.gym10.setScore(10);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym11")) {
							            			this.gym11.setScore(11);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym12")) {
							            			this.gym12.setScore(12);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym13")) {
							            			this.gym13.setScore(13);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym14")) {
							            			this.gym14.setScore(14);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym15")) {
							            			this.gym15.setScore(15);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym16")) {
							            			this.gym16.setScore(16);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym17")) {
							            			this.gym17.setScore(17);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym18")) {
							            			this.gym18.setScore(18);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym19")) {
							            			this.gym19.setScore(19);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym20")) {
							            			this.gym20.setScore(20);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym21")) {
							            			this.gym21.setScore(21);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym22")) {
							            			this.gym22.setScore(22);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym23")) {
							            			this.gym23.setScore(23);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym24")) {
							            			this.gym24.setScore(24);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym25")) {
							            			this.gym25.setScore(25);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym26")) {
							            			this.gym26.setScore(26);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym27")) {
							            			this.gym27.setScore(27);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym28")) {
							            			this.gym28.setScore(28);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym29")) {
							            			this.gym29.setScore(29);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym30")) {
							            			this.gym30.setScore(30);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym31")) {
							            			this.gym31.setScore(31);
							            		}
							                  else if (args[1].equalsIgnoreCase("gym32")) {
							            			this.gym32.setScore(32);
							            		}
							        		}
									  }
									  
									  else if (getConfig().getString("config."+args[1]+"enabled").equalsIgnoreCase("False"))	  {
						        		   p.sendMessage(ChatColor.RED + "This gym, " +args[1]+ " is disabled in the config. Please open an enabled gym.");
						        	   }
								  }
							  }
						  }
				
					  
					  else {
						  p.sendMessage(ChatColor.RED + "You do not have permission to open "+args[1]);
					 }
				}	
					  else {
						  p.sendMessage(ChatColor.RED + "The gym " +args[1]+ " does not exist!");
						  p.sendMessage(ChatColor.RED + "Try /gym open gym1");
					  }
			}
				  else {
					  p.sendMessage(ChatColor.RED + "You do not have permission to open a gym!");
				  }
			}
				
				else if ((args[0].equalsIgnoreCase("close"))) {
					if (p.hasPermission("pixelgym.leader")  || p.hasPermission("pixelgym.admin")) {
						if (getConfig().getString("config."+args[1]) != (null)) {

						if (p.hasPermission("pixelgym."+args[1])  || p.hasPermission("pixelgym.admin")) {
							
								if (args[1].equalsIgnoreCase("e41") || (args[1].equalsIgnoreCase("e42") || (args[1].equalsIgnoreCase("e43") || (args[1].equalsIgnoreCase("e44"))))) {
									  p.sendMessage(ChatColor.RED + "To close the Elite 4, type /e4 close e4#. Not /gym close e4#");
								  }
								  else {
									  if (getConfig().getString("config."+args[1]+"enabled").equalsIgnoreCase("True"))	  {
										  if (getConfig().getString("config."+args[1]+"stat").equals("Closed")) {
								        		 p.sendMessage(ChatColor.RED + "The " + getConfig().getString("config."+args[1]) + " gym is already Closed!");
								        	 }
								        	 else if (getConfig().getString("config."+args[1]+"stat").equals("Open")) {
								        		 Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour")) + "The " + getConfig().getString("config."+args[1]) + " Gym is now " + ChatColor.RED + "Closed");
								         		getConfig().set("config."+args[1]+"stat", "Closed");
								         		p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
								         		this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour")) + getConfig().getString("config."+args[1])));
								        	 }
	
									  }
									  else {
										  p.sendMessage(ChatColor.RED + "The " +args[1]+ " gym is not enabled in the config, please close an enabled gym.");
									  }
								  }
						
						  }
						else {
							p.sendMessage(ChatColor.RED + "You do not have permission to close "+args[1]);
						}
						  }
						else {
							p.sendMessage(ChatColor.RED + "The gym " +args[1]+ " does not exist!");
							  p.sendMessage(ChatColor.RED + "Try /gym close gym1");
						}
					  }
					
					else {
						p.sendMessage(ChatColor.RED + "You do not have permission to close a gym!");
					}
				  
				 }
		  
				
				if ((args[0].equalsIgnoreCase("setwarp"))) {
				 if (p.hasPermission("pixelgym.admin")) {
					if (settings.getData().get("warps." +args[1]) != (null)) {
						p.sendMessage(ChatColor.RED + args[1] + " warp already exists. If you want to overwrite it, do /gym delwarp "+args[1] + ". And then re-set the new warp.");
					}
					else {
	        	 	
	        		settings.getData().set("warps." + args[1] + ".world", p.getLocation().getWorld().getName());
	                settings.getData().set("warps." + args[1] + ".x", p.getLocation().getX());
	                settings.getData().set("warps." + args[1] + ".y", p.getLocation().getY());
	                settings.getData().set("warps." + args[1] + ".z", p.getLocation().getZ());
	                settings.saveData();
	                p.sendMessage(ChatColor.GREEN + "Set warp " + args[1] + "!");
					}	
	        	  }
				 else {
					 p.sendMessage(ChatColor.RED + "You do not have permission to set a warp!");
				 }
				}
	        	
	        	if ((args[0].equalsIgnoreCase("delwarp"))) {
	        	  if (p.hasPermission("pixelgym.admin")) {
	        		if (settings.getData().getConfigurationSection("warps." + args[1]) == null) {
	                    p.sendMessage(ChatColor.RED + "Warp " + args[1] + " does not exist!");
	                    return true;
	            }
	        		settings.getData().set("warps." + args[1], null);
	                settings.saveData();
	                p.sendMessage(ChatColor.GREEN + "Removed warp " + args[1] + "!");
	        	  }
	        	  else {
						 p.sendMessage(ChatColor.RED + "You do not have permission to delete a warp!");
					 }
	        	}
	        	
	        	if ((args[0].equalsIgnoreCase("warp"))) {
	        	  if (p.hasPermission("pixelgym.admin") || p.hasPermission("pixelgym." +args[1])) {
	        		if (settings.getData().getConfigurationSection("warps." + args[1]) == null) {
	                    p.sendMessage(ChatColor.RED + "Warp " + args[1] + " does not exist!");
	                    return true;
	            }
	        		World w = Bukkit.getServer().getWorld(settings.getData().getString("warps." + args[1] + ".world"));
	                double x = settings.getData().getDouble("warps." + args[1] + ".x");
	                double y = settings.getData().getDouble("warps." + args[1] + ".y");
	                double z = settings.getData().getDouble("warps." + args[1] + ".z");
	                p.teleport(new Location(w, x, y, z));
	                p.sendMessage(ChatColor.GREEN + "Teleported to " + args[1] + "!");
	        		
	        	  }
	        	  else {
	        		  p.sendMessage(ChatColor.RED + "You do not have permission to warp to a gym!");
	        	  }
	        	}
	        	
	        	if (args[0].equalsIgnoreCase("addtm")) {
	        		
	        		int tM = p.getItemInHand().getTypeId();
	        		
	        		if (p.hasPermission("pixelgym.admin")) {
	        			
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
			              
			              //outerloop:
	        			
	        			for (int i = 1; i <= 100; i++) {
	        				
	        				int u = i -1;
	        				//int e = i +1;
	        				
	        				if (i == 100) {
//	        					if (settings.getExtras().get("tms.gym"+gym+"TM."+u) != null) {
//	        						if (settings.getExtras().get("tms.gym"+gym+"TM."+i) == null) {
//	        							settings.getExtras().set("tms.gym"+gym+"TM."+i, tM);
//	        							settings.saveExtras();
//	        							System.out.println("i = 100, set tM as i");
//	        							System.out.println(tM);
//	        							System.out.println(settings.getExtras().getInt("tms.gym"+gym+"TM."+i));
//	        						}
//	        						else {
//	        							//do nothing
//	        						}
//	        					}
	        				}
	        				if (i == 1) {
	        					if (settings.getExtras().get("tms.gym"+gym+"TM."+i) == null) {
	        						settings.getExtras().set("tms.gym"+gym+"TM."+i, tM);
	        						settings.saveExtras();
	        						System.out.println("i = 1, set tM as i");
	        						System.out.println(tM);
	        						System.out.println(settings.getExtras().getInt("tms.gym"+gym+"TM."+i));
	        						System.out.println(settings.getExtras().getString("tms.gym"+gym+"TM."+i));
	        						System.out.println(settings.getExtras().get("tms.gym"+gym+"TM."+i));
	        						break;
	        						
	        					}
	        					else {
	        						//do nothing
	        					}
	        				}
	        				else {
	        					
	        					if (settings.getExtras().get("tms.gym"+gym+"TM."+i) == null) {
	        						if (settings.getExtras().get("tms.gym"+gym+"TM."+u) != null) {
	        							settings.getExtras().set("tms.gym"+gym+"TM."+i, tM);
	        							settings.saveExtras();
	        							System.out.println("i is idk, u exists, set tM as i");
	        							break;

	        						}
	        					}
	        					else {
	        						//do nothing

	        					}
	        					
	        				}
	        				
		        			
	        			}
			              p.sendMessage(ChatColor.GREEN + "Added item in hand as a TM for gym"); 
	        			
	        		}
	        	}
	        
			  }
			  
			  
			  
			  
			  else if (args.length == 3) {
				  
				  
	              
			    	 
			    	  if ((args[0].equalsIgnoreCase("sendrules"))) {
			    		if(Bukkit.getPlayer(args[2]) != null) {
		        		  Player playerTarget = Bukkit.getPlayer(args[2]);
			    		  
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
			    		  
			    			if (getConfig().getString("config."+args[1]) != (null)) {
			    				if (p.hasPermission("pixelgym."+args[1])) {
			    					p.sendMessage(ChatColor.GOLD + "Sent " + getConfig().getString("config."+args[1]) + " Gym Rules to " + playerTarget.getName().toString());
			    					playerTarget.sendMessage(ChatColor.GOLD + playerTarget.getName().toString() + ", Make sure you read the " + getConfig().getString("config."+args[1]) + " Gym rules propperly before facing the Gym!");
			    					playerTarget.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config."+args[1]+"rule1"));
			    					playerTarget.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config."+args[1]+"rule2"));
			    					playerTarget.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config."+args[1]+"rule3"));
			    					playerTarget.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config."+args[1]+"rule4"));
			    					playerTarget.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config."+args[1]+"rule5"));
			    	  		}
			    				else {
			    					p.sendMessage(ChatColor.RED + "You are not gym leader of this gym!");
			    				}
			    			}
			    			
			    			else {
			    				  p.sendMessage(ChatColor.RED + "The gym " +args[1]+ " does not exist!");
								  p.sendMessage(ChatColor.RED + "Try /gym sendrules gym1 (player)");
			    			}
			    		
			    		
			    	  }
			    		else {
			    			p.sendMessage(ChatColor.RED + "You need to specify a player to send the rules to. Example: /gym sendrules gym1 (player)");
			    		}
			    	  
			      }
			    	 
			    	 
			    	 
			    	  if (args[0].equalsIgnoreCase("giveTM")) {

			    		  if(Bukkit.getPlayer(args[2]) != null) {
			    			  Player playerToGive = Bukkit.getPlayer(args[2]);
			    			  
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
				              
				              if (p.hasPermission("pixelgym.gym"+gym) || p.hasPermission("pixelgym.admin")) {
				            	  Random random = new Random();
			        				
			        				int randomNum = random.nextInt(5 - 1) + 1;
			        			if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum) != null) {
			        				ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum));
			        				
			        				if (playerToGive.getInventory().firstEmpty() != -1) {
			        					playerToGive.getInventory().addItem(tM);
			        				}
			        				else {
			        					playerToGive.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
			        					p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
			        				}
			        			}
			        			else {
			        				int randomNum2 = random.nextInt(30 - 1) + 1;
		        					if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum2) != null) {
		        						ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum2));
		        						if (playerToGive.getInventory().firstEmpty() != -1) {
		        							playerToGive.getInventory().addItem(tM);
		        						}
		        						else {
		        							playerToGive.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
		        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
		        						}
		        					}
		        					else {
		        						int randomNum3 = random.nextInt(10 - 1) + 1;
			        					if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum3) != null) {
			        						ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum3));
			        						if (playerToGive.getInventory().firstEmpty() != -1) {
			        							playerToGive.getInventory().addItem(tM);
			        						}
			        						else {
			        							playerToGive.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
			        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
			        						}
			        					}
			        					else {
			        						int randomNum4 = random.nextInt(2 - 1) + 1;
				        					if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum4) != null) {
				        						ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum4));
				        						if (playerToGive.getInventory().firstEmpty() != -1) {
				        							playerToGive.getInventory().addItem(tM);
				        						}
				        						else {
				        							playerToGive.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
				        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
				        						}
				        					}
				        					else {
				        						int randomNum5 = 1;
					        					if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum5) != null) {
					        						ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum5));
					        						if (playerToGive.getInventory().firstEmpty() != -1) {
					        							playerToGive.getInventory().addItem(tM);
					        						}
					        						else {
					        							playerToGive.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
					        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
					        						}
					        					}
					        					else {
					        						
					        					}
				        					}
			        					}
		        					
		        					}
			        			}
				              }
			    			  
			    		  }
			    	  }
			    		 
			    		 if ((args[0].equalsIgnoreCase("winner") || args[0].equalsIgnoreCase("win")  || args[0].equalsIgnoreCase("w"))) {
			    			
			    			 if(Bukkit.getPlayer(args[2]) != null) {
				        		  Player playerWinner = Bukkit.getPlayer(args[2]);

			    			 
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
			    			 
				              int u = gym +1;
				              
				              Calendar c = Calendar.getInstance();
				              
			    			if (p.hasPermission("pixelgym."+args[1]) || p.hasPermission("pixelgym.admin")) {
			    				
			    				final Player o = (Player) playerWinner;
			    				UUID po = playerWinner.getUniqueId();
			    				
			    				if (queues.get(gym).contains(o.getUniqueId())) {	
			    				  //UUID uuid = p.getUniqueId();
			    				
			    				  //UUID uuid = queues.get(gym).get(0);
			    				  //Player playerToTeleport = Bukkit.getPlayer(uuid);
			    			if ((settings.getData().get("warps.spawn") != null)) {
			    				World w = Bukkit.getServer().getWorld(settings.getData().getString("warps.spawn.world"));
				                double x = settings.getData().getDouble("warps.spawn.x");
				                double y = settings.getData().getDouble("warps.spawn.y");
				                double z = settings.getData().getDouble("warps.spawn.z");
				                playerWinner.teleport(new Location(w, x, y, z));
				                playerWinner.sendMessage(ChatColor.GREEN + "Teleported out of " + ChatColor.YELLOW + ChatColor.BOLD + "gym"+gym + "!");
				                playerWinner.sendMessage(ChatColor.GREEN + "Congrats, you won the gym"+gym + " badge! (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+gym+"colour")+ ChatColor.BOLD + getConfig().getString("config.gym"+gym)+ChatColor.GREEN+ ") " + "Now you can join the gym"+u+ " queue with /gym join gym"+u+". ("+ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+u+"colour")+ ChatColor.BOLD + getConfig().getString("config.gym"+u)+ ChatColor.GREEN + ")")));
				                
				                inArena.get(gym).remove(p.getUniqueId());
				                
				                Random random = new Random();
		        				
		        				int randomNum = random.nextInt(100 - 1) + 1;
		        				
		        				if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum) != null) {
		        					ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum));
		        						if (playerWinner.getInventory().firstEmpty() != -1) {
		        							playerWinner.getInventory().addItem(tM);
		        						}
		        						else {
		        							playerWinner.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
		        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
		        						}
		        					}
		        				else {
		        						int randomNum2 = random.nextInt(30 - 1) + 1;
		        					if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum2) != null) {
		        						ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum2));
		        						if (playerWinner.getInventory().firstEmpty() != -1) {
		        							playerWinner.getInventory().addItem(tM);
		        						}
		        						else {
		        							playerWinner.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
		        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
		        						}
		        					}
		        					else {
		        						int randomNum3 = random.nextInt(10 - 1) + 1;
			        					if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum3) != null) {
			        						ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum3));
			        						if (playerWinner.getInventory().firstEmpty() != -1) {
			        							playerWinner.getInventory().addItem(tM);
			        						}
			        						else {
			        							playerWinner.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
			        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
			        						}
			        					}
			        					else {
			        						int randomNum4 = random.nextInt(2 - 1) + 1;
				        					if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum4) != null) {
				        						ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum4));
				        						if (playerWinner.getInventory().firstEmpty() != -1) {
				        							playerWinner.getInventory().addItem(tM);
				        						}
				        						else {
				        							playerWinner.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
				        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
				        						}
				        					}
				        					else {
				        						int randomNum5 = 1;
				        						//int randomNum5 = random.nextInt(1 - 1) + 1;
					        					if (settings.getExtras().get("tms.gym"+gym+"TM."+randomNum5) != null) {
					        						ItemStack tM = new ItemStack(settings.getExtras().getInt("tms.gym"+gym+"TM."+randomNum5));
					        						if (playerWinner.getInventory().firstEmpty() != -1) {
					        							playerWinner.getInventory().addItem(tM);
					        						}
					        						else {
					        							playerWinner.sendMessage(ChatColor.RED + "Your inventory was full, you did not get given the TM! Tell the gym leader you did not get the TM and empty a space in your inventory now!");
					        							p.sendMessage(ChatColor.RED + "Player did not recieve the TM, there inventory was full. Type /gym giveTM gym"+gym + " (player) when they have an open spot in there inventory");
					        						}
					        					}
					        					else {
					        						
					        					}
				        					}
			        					}
		        					
		        					}
		        				}

				                p.sendMessage(ChatColor.GREEN + "Successfully telported " +playerWinner.getName() + " out of your gym!");
				                p.sendMessage(ChatColor.GREEN + "You are now ready for your next battle, type: /gym next gym"+gym);
			    			}
			    			else {
			    				p.sendMessage(ChatColor.RED + "Warp point 'spawn' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym.");
			    			}

			    			    //inArena.get(gym).remove(o.getUniqueId());
			    				queues.get(gym).remove(po);
			    				
			    				settings.getLogs().set("Leaders." + p.getName() + "." + args[1] + ".[" + c.getTime() + "]", playerWinner.getName()+ " - Won");
			    				settings.getLogs().set("Challengers." + playerWinner.getUniqueId() + "." + args[1] + ".Date/Time Won", "[" + c.getTime() + "]");
			    				settings.getLogs().set("Challengers." + playerWinner.getUniqueId() + "." + args[1] + ".Gym Leader", p.getName());
			    				
//			    				settings.getLogs().set("Leaders." + p.getName() + "." + args[1] + ".[" +format.format(now) + "]", playerWinner.getName()+ " - Won");
//			    				settings.getLogs().set("Challengers." + playerWinner.getUniqueId() + "." + args[1] + ".Date/Time Won", "[" +format.format(now) + "]");
//			    				settings.getLogs().set("Challengers." + playerWinner.getUniqueId() + "." + args[1] + ".Gym Leader", p.getName());
			    				settings.saveLogs();
			    				
			    				

			    				if (getConfig().getString("config.gymsound").equalsIgnoreCase("true")) {
			    					for (Player playersOnline : Bukkit.getOnlinePlayers()) {
			    					
			    				playersOnline.playSound(playersOnline.getLocation(), Sound.FIREWORK_LARGE_BLAST, 30F, 1F);	
			    				playersOnline.playSound(playersOnline.getLocation(), Sound.FIREWORK_TWINKLE, 30F, 1F);
			    				
			    					}
			    				}
			    				    	
                           
			    				for (int i = 1; i <= 32; i++) {
			    				 if (args[1].equalsIgnoreCase("gym"+i)) {
			    					 settings.getBadge().set("Players." + playerWinner.getUniqueId() + ".Badges." +args[1], "Won");
			    					 settings.saveBadges();
			    				 }
			    				}
			    					 Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + playerWinner.getName() + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config."+args[1]+"colour")) +  " has won the " + getConfig().getString("config."+args[1]) + " Gym Badge!");
			    					 //settings.getBadge().set("Players." + playerWinner.getName() + ".Badges", playerWinner.getInventory().getContents());
			    					 
			    				
			    					 
			    				}
		                           else {
		                        	   p.sendMessage(ChatColor.RED + "Player must be in the queue to win or lose!");
		                           }
			    			}
			    			else {
			    				p.sendMessage(ChatColor.RED + "You do not have permission to set winner's of "+args[1]);
			    				
			    			 	}
			    			 }
			    		 
			    		 }
			    		 
//			    		 else if (args[0].equalsIgnoreCase("logs")) {
//			    			 int gym;
//								
//							 
//							  String gymArg = args[1].replace("gym", "");
//							  
//				              try {
//
//				                  // Set the gym variable to the number in arguement 1
//
//				                  gym = Integer.parseInt(gymArg);
//
//				              } catch (NumberFormatException nfe) {
//
//				                  p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
//
//				                  // Number was not a number
//
//				                  return true;
//
//				              }
//
//				              if (gym < 1 || gym > 32) {
//
//				                  // Number was not between 1 and 32
//
//				                  return true;
//
//				              }
//				              
//				              if(Bukkit.getPlayer(args[2]) != null) {
//				        		  Player playerLogs = Bukkit.getPlayer(args[2]);
//				        		  if (p.hasPermission("pixelgym."+args[1]) || p.hasPermission("pixelgym.admin")) {
//				        			  p.sendMessage(ChatColor.GREEN + "This Months Logs for " + playerLogs.getName() + " in the " + args[1] + " Gym!" );
//				        			  p.sendMessage(ChatColor.GOLD + settings.getLogs().getString("Leaders." + playerLogs.getName() + "." + args[1]));
//				        		  }
//				        		  else {
//				        			  p.sendMessage(ChatColor.RED + "You do not have permisson to check this Gym Leaders Logs ("+playerLogs.getName()+")");
//				        		  }
//				              }
//				              
//			    		 }
			    		 
			    		 else if (args[0].equalsIgnoreCase("givebadge")) {
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
				              for (Player playerGive : Bukkit.getOnlinePlayers()) {
				            	  if (playerGive.getName().equalsIgnoreCase(args[2])) {
					    				 if (p.hasPermission("pixelgym.admin")) {
					    					 for (int i = 1; i <= 32; i++) {
							    				 if (args[1].equalsIgnoreCase("gym"+i)) {
							    					 settings.getBadge().set("Players." + playerGive.getUniqueId() + ".Badges." +args[1], "Won");
							    					 settings.saveBadges();
							    					 
							    			
							    					 p.sendMessage(ChatColor.GREEN + "Gave " + playerGive.getName() + " the gym"+gym + " badge!");
							    				 }
							    				}
					    				 			}
					    				 else {
					    					 p.hasPermission("You do not have permission to give gym badges!");
					    				 }
					    				 }
				              }
				              
				              
				    		 }
			    		 
			    		 else if (args[0].equalsIgnoreCase("delbadge")) {
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
				              for (Player playerTake : Bukkit.getOnlinePlayers()) {
				            	  if (playerTake.getName().equalsIgnoreCase(args[2])) {
					    				 if (p.hasPermission("pixelgym.admin")) {
					    					if (settings.getBadge().get("Players." + playerTake.getUniqueId() + ".Badges." +args[1]) != null) {
					    					 for (int i = 1; i <= 32; i++) {
							    				 if (args[1].equalsIgnoreCase("gym"+i)) {
							    					 //settings.getData().set("warps." + args[1], null);
							    					 settings.getBadge().set("Players." + playerTake.getUniqueId() + ".Badges." +args[1], null);
							    					 settings.saveBadges();
							    					 
							    					 
							    					 p.sendMessage(ChatColor.GREEN + "Deleted the gym"+gym + " badge from " + playerTake.getName());
							    				 }
							    				}
					    					}
					    					else {
			    		                    p.sendMessage(ChatColor.RED + playerTake.getName() + " does not have the gym, " + args[1]);
					    					}
					    				 			}
					    				 else {
					    					 p.hasPermission("You do not have permission to give gym badges!");
					    				 }
					    				 }
				              }
				              
				              
				    		 }
			    		 
			    		 else if ((args[0].equalsIgnoreCase("sq")) || (args[0].equalsIgnoreCase("skipq")) || (args[0].equalsIgnoreCase("skip")))
			    	        {
			    	          
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
			    	          
			    	          if (Bukkit.getPlayer(args[2]) != null)
			    	          {
			    	            Player playerSkip = Bukkit.getPlayer(args[2]);
			    	            if ((p.hasPermission("pixelgym." + args[1])) || (p.hasPermission("pixelgym.admin")))
			    	            {
			    	              UUID po = playerSkip.getUniqueId();
			    	              
			    	              String gymArg3 = args[1].replace("gym", "");
			    	              int gym3 = Integer.parseInt(gymArg3);
			    	              
			    	              cooldownTime.get(gym3).remove(po);
			    	              cooldownTask.get(gym3).remove(po);
			    	              cooldownGym.get(gym3).remove(po);
			    	              
			    	              p.sendMessage(ChatColor.GREEN + "Removed " + playerSkip.getName() + "'s cooldown for gym" + gym3);
			    	              playerSkip.sendMessage(ChatColor.GREEN + "Removed your cooldown for gym" + gym3);
			    	            }
			    	          }
			    	        }
			    		 
			    		 else if (args[0].equalsIgnoreCase("lost") || (args[0].equalsIgnoreCase("l") || (args[0].equalsIgnoreCase("lose")))) {
			    			 
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
				            
				              if(Bukkit.getPlayer(args[2]) != null) {
				        		  Player playerLost = Bukkit.getPlayer(args[2]);
				             
			    				 if (p.hasPermission("pixelgym."+args[1]) || p.hasPermission("pixelgym.admin")) {
			    					 
			    					 final UUID po = playerLost.getUniqueId();
			    					 int time = Integer.parseInt(getConfig().getString("config.cooldowntime"));
					    				
					    		if (queues.get(gym).contains(po)) {
			    					 
			    					 //UUID uuid = p.getUniqueId();
			    					 
			    					 //UUID uuid = queues.get(gym).get(0);
			    					 //layer playerToTeleport = Bukkit.getPlayer(uuid);
					    			if ((settings.getData().get("warps.spawn") != null)) {
			    					    World w = Bukkit.getServer().getWorld(settings.getData().getString("warps.spawn.world"));
						                double x = settings.getData().getDouble("warps.spawn.x");
						                double y = settings.getData().getDouble("warps.spawn.y");
						                double z = settings.getData().getDouble("warps.spawn.z");
						                playerLost.teleport(new Location(w, x, y, z));
						                playerLost.sendMessage(ChatColor.GREEN + "Teleported out of " + ChatColor.YELLOW + ChatColor.BOLD + "gym"+gym + "!");
						                playerLost.sendMessage(ChatColor.GREEN + "Unlucky! you lost that gym battle! (" + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.gym"+gym+"colour")+ ChatColor.BOLD + getConfig().getString("config.gym"+gym) +ChatColor.GREEN+  ") Not to worry! You can challenge the gym again in " + time + " minutes! type: /gym join gym"+gym + " When the cooldown has finished."));
						                playerLost.sendMessage(ChatColor.BLUE + "To check how long you have left on your cooldown, type: /gym join gym"+gym);
					    				
						                p.sendMessage(ChatColor.GREEN + "Successfully telported " +playerLost.getName() + " out of your gym!");
						                p.sendMessage(ChatColor.GREEN + "You are now ready for your next battle, type: /gym next gym"+gym);
					    			}
					    			else {
					    				p.sendMessage(ChatColor.RED + "Warp point 'spawn' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym.");
					    			}
						                
						                final String gymArg3 = args[1].replace("gym", "");
						                final int gym3 = Integer.parseInt(gymArg3);
    
					    				queues.get(gym).remove(po);
					    				inArena.get(gym).remove(po);
					    				
//					    				if (spawnperm = true) {
//					    					permission.playerAdd(playerLost, "essentials.spawn");
//					    				}
//					    				if (tpaperm = true) {
//					    					permission.playerAdd(playerLost, "essentials.tpa");
//					    				}
//					    				if (homeperm = true) {
//					    					permission.playerAdd(playerLost, "essentials.home");
//					    				}
//					    				if (backperm = true) {
//					    					permission.playerAdd(playerLost, "essentials.back");
//					    				}
//					    				if (warpperm = true) {
//					    					permission.playerAdd(playerLost, "essentials.warp");
//					    				}
//					    				if (tpacceptperm = true) {
//					    					permission.playerAdd(playerLost, "essentials.tpaccept");
//					    				}
//					    				if (randomtpperm = true) {
//					    					permission.playerAdd(playerLost, "randomtp.tp");
//					    				}
					    				
					    				settings.getLogs().set("Leaders." + p.getName() + "." + args[1] + ".[" +format.format(now) + "]", playerLost.getName()+ " - Lost");
					    				settings.saveLogs();
					    				
					    				//int time = Integer.parseInt(getConfig().getString("config.cooldowntime"));
					    				
					    				cooldownTime.get(gym3).put(po, time);
					    				//System.out.println("Time = "+time);
					    				//cooldownTime.get(gym3).put(po, 60);
					    				cooldownGym.get(gym).add(po);
					    				//inArena.get(gym).remove(po);
					    				cooldownTask.get(gym).put(po, new BukkitRunnable() {
					    					
											@Override
											public void run() {
												
											if (cooldownGym.get(gym3).contains(po)) {
												
												cooldownTime.get(gym3).put(po, cooldownTime.get(gym3).get(po) - 1);
												if (cooldownTime.get(gym3).get(po) == 0) {
													cooldownTime.get(gym3).remove(po);
													cooldownTask.get(gym3).remove(po);
													cooldownGym.get(gym3).remove(po);
													cancel();

												}
												
											}
											else {
												cancel();
											}
										}
					    					
					    				});
					    				
					    				cooldownTask.get(gym).get(po).runTaskTimer(this, 20, 1200);
					    				
						                }
						                else {
						                	p.sendMessage(ChatColor.RED + "Player must be in the queue to win or lose!");
						                }
			    				 }
			    				 else {
			    					 p.sendMessage(ChatColor.RED + "You do not have permission to set losers of "+args[1]);
			    				 }
			    			 
			    			 
			    		 }
			    		 }
			    		}
			    	
			    	 
			  }

//ELITE 4 SECTION

    
   else if (commandLable.equalsIgnoreCase("e4") && (enablee4.equalsIgnoreCase("true"))) {
    if (args.length == 0) {
      if (!p.hasPermission("pixelgym.e4leader")) {
        p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
        p.sendMessage("");
        p.sendMessage(ChatColor.GREEN + "/e4 list" + ChatColor.DARK_GREEN + " - Get a list of the E4 Level's and there status.");
        p.sendMessage(ChatColor.GREEN + "/e4 leaders" + ChatColor.DARK_GREEN + " - Get a list of the online E4 leaders.");
        p.sendMessage(ChatColor.GREEN + "/e4 rules <e4#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified E4 Level.");
        p.sendMessage(ChatColor.GREEN + "/gym scoreboard" + ChatColor.DARK_GREEN + " - Toggle ScoreBoard for e4 and Gym.");
        p.sendMessage("");
        p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
      } 
      else if (p.hasPermission("pixelgym.e4leader")) {
        p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
        p.sendMessage("");
        p.sendMessage(ChatColor.GREEN + "/e4 list" + ChatColor.DARK_GREEN + " - Get a list of the E4 level's and there status.");
        p.sendMessage(ChatColor.GREEN + "/e4 leaders" + ChatColor.DARK_GREEN + " - Get a list of the online E4 leaders.");
        p.sendMessage(ChatColor.GREEN + "/e4 rules <e4#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified E4 Level.");
        p.sendMessage(ChatColor.GREEN + "/e4 open <e4#>" + ChatColor.DARK_GREEN + " - Open a particular E4 level (e41, e42, e43 or e44).");
        p.sendMessage(ChatColor.GREEN + "/e4 close <e4#>" + ChatColor.DARK_GREEN + " - Close a particular E4 level (e41, e42, e43 or e44).");
        p.sendMessage(ChatColor.GREEN + "/e4 heal" + ChatColor.DARK_GREEN + " - Heals your pokemon.");
        //p.sendMessage(ChatColor.GREEN + "/e4 quit (username)" + ChatColor.DARK_GREEN + " - Force quit a gym battle. In (Username) put either yours or the challengers IGN!");
        p.sendMessage(ChatColor.GREEN + "/e4 sendrules <e4#> (username)" + ChatColor.DARK_GREEN + " - Force quit a e4 battle. In (Username) put either yours or the challengers IGN!");
        p.sendMessage("");
        p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
      } 
      else if (p.hasPermission("pixelgym.admin")) {
          p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
          p.sendMessage("");
          p.sendMessage(ChatColor.GREEN + "/e4 list" + ChatColor.DARK_GREEN + " - Get a list of the E4 Level's and there status.");
          p.sendMessage(ChatColor.GREEN + "/e4 leaders" + ChatColor.DARK_GREEN + " - Get a list of the online E4 leaders.");
          p.sendMessage(ChatColor.GREEN + "/e4 rules <e4#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified E4 Level.");
          p.sendMessage(ChatColor.GREEN + "/e4 closeall" + ChatColor.DARK_GREEN + " - Closes all Elite 4 Level's.");
          p.sendMessage("");
          p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
        } 

    }
    
    else if (args.length == 1) {
    	// ELITE 4 ONLINE LEADERS
    	if (args[0].equals("leaders")) {
            p.sendMessage(ChatColor.GOLD + "----- Online E4 Leaders -----");
            p.sendMessage("");
            for (Player e4staff : Bukkit.getOnlinePlayers()) {
          	  if (e4staff.hasPermission("pixelgym.e41"))
                {
                if (enablee4.equalsIgnoreCase("true")) {
                  p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e41colour")) + e4staff.getName() + " - " + getConfig().getString("config.e41"));
                  }
                }
          	  if (e4staff.hasPermission("pixelgym.e42"))
              {
                if (enablee4.equalsIgnoreCase("true")) {
                  p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e42colour")) + e4staff.getName() + " - " + getConfig().getString("config.e42"));
                  }
                 }
          	 if (e4staff.hasPermission("pixelgym.e43"))
             {
               if (enablee4.equalsIgnoreCase("true")) {
                 p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e43colour")) + e4staff.getName() + " - " + getConfig().getString("config.e43"));
                 }
                }
          	 if (e4staff.hasPermission("pixelgym.e44"))
             {
               if (enablee4.equalsIgnoreCase("true")) {
                 p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e44colour")) + e4staff.getName() + " - " + getConfig().getString("config.e44"));
                 }
                }
             }
          }
    	
    	//ELITE 4 OPEN ELITE LEVEL'S
    	
    	else if (args[0].equals("list")) {
            p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
            p.sendMessage("");
            if (enablee4.equalsIgnoreCase("true")) {
  
    			p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e41colour")) + getConfig().getString("config.e41") + "  Elite 4 is: " + ChatColor.BLUE + getConfig().getString("config.e41stat"));
    			p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e42colour")) + getConfig().getString("config.e42") + "  Elite 4 is: " + ChatColor.BLUE + getConfig().getString("config.e42stat"));
    			p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e43colour")) + getConfig().getString("config.e43") + "  Elite 4 is: " + ChatColor.BLUE + getConfig().getString("config.e43stat"));
    			p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e44colour")) + getConfig().getString("config.e44") + "  Elite 4 is: " + ChatColor.BLUE + getConfig().getString("config.e41stat"));
        }
    	
    }
    
    	else if ((args[0].equals("open")) && (p.hasPermission("pixelgym.e4leader"))) {
            p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/e4 open <e4#>");
          } 
    	else if ((args[0].equals("close")) && (p.hasPermission("pixelgym.e4leader"))) {
            p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/e4 close <e4#>");
          } 
    	else if ((args[0].equals("rules"))) {
            p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/e4 rules <e4#>");
          } 
    	else if ((args[0].equals("heal")) && (p.hasPermission("pixelgym.e4leader")) && (enableGymHeal.equalsIgnoreCase("true"))) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + p.getName().toString());
            p.sendMessage("Your pixelmon have been healed!");
          } 
    	else if ((args[0].equals("heal")) && (p.hasPermission("pixelgym.e4leader")) && (!enableGymHeal.equalsIgnoreCase("true"))) {
    		p.sendMessage(ChatColor.RED + "Gym/E4 Leader healing disabled in the plugin config");
    	}
    	
    	else if ((args[0].equals("quit")) && (p.hasPermission("pixelgym.e4leader"))) {
    	for (Player playerTarget : Bukkit.getOnlinePlayers()) {
    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "endbattle " + playerTarget.getName().toString());
              p.sendMessage(ChatColor.GREEN + "You have successfully quit the battle!");
    }
    	}
    	
    	else if ((args[0].equalsIgnoreCase("closeall")) && (p.hasPermission("pixelgym.admin"))) {
            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "All " + getConfig().getString("config.e4") + " Level's are now closed.");
            
            getConfig().set("config.e41stat", "Closed");
            getConfig().set("config.e42stat", "Closed");
            getConfig().set("config.e43stat", "Closed");
            getConfig().set("config.e44stat", "Closed");

            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e41colour")) + getConfig().getString("config.e41") + " " + getConfig().getString("config.e4ab")));
            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e42colour")) + getConfig().getString("config.e42") + " " + getConfig().getString("config.e4ab")));
            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e43colour")) + getConfig().getString("config.e43") + " " + getConfig().getString("config.e4ab")));
            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e44colour")) + getConfig().getString("config.e44") + " " + getConfig().getString("config.e4ab")));
          }
    }
    
    	else if (args.length == 2) {
      	  
      	  if ((args[0].equals("rules")) && (args[1].equals("e41")))
            {
              if (enablee4.equalsIgnoreCase("true")) {
                      p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                  p.sendMessage("");
                p.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config.e41rule1"));
              p.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config.e41rule2"));
              p.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config.e41rule3"));
              p.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config.e41rule4"));
              p.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config.e41rule5"));
              }
            }
            else if ((args[0].equals("rules")) && (args[1].equals("e42")))
            {
              if (enablee4.equalsIgnoreCase("true")) {
                      p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                  p.sendMessage("");
                p.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config.e42rule1"));
              p.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config.e42rule2"));
              p.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config.e42rule3"));
              p.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config.e42rule4"));
              p.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config.e42rule5"));
            }
          }
            else if ((args[0].equals("rules")) && (args[1].equals("e43")))
            {
              if (enablee4.equalsIgnoreCase("true")) {
                      p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                  p.sendMessage("");
                p.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config.e43rule1"));
              p.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config.e43rule2"));
              p.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config.e43rule3"));
              p.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config.e43rule4"));
              p.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config.e43rule5"));
            }
          }
            else if ((args[0].equals("rules")) && (args[1].equals("e44")))
            {
              if (enablee4.equalsIgnoreCase("true")) {
                      p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                  p.sendMessage("");
                p.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config.e44rule1"));
              p.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config.e44rule2"));
              p.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config.e44rule3"));
              p.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config.e44rule4"));
              p.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config.e44rule5"));
            }
          }
      	  
      	if (p.hasPermission("pixelgym.e4leader")) {
        	if ((args[0].equalsIgnoreCase("open")) && (args[1].equalsIgnoreCase("e41")) && (p.hasPermission("pixelgym.e41")) && (enablee4.equalsIgnoreCase("true")))
            {
              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e41colour")) + "The " + getConfig().getString("config.e41") + " " + getConfig().getString("config.e4") + " is now " + ChatColor.GREEN + "Open");
              getConfig().set("config.e41stat", "Open");
              p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
             if (getConfig().getString("config.scoreboard").equals("True")) {
              this.e41.setScore(101);
             }
            }
            
        	if ((args[0].equalsIgnoreCase("open")) && (args[1].equalsIgnoreCase("e42")) && (p.hasPermission("pixelgym.e42")) && (enablee4.equalsIgnoreCase("true")))
            {
              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e42colour")) + "The " + getConfig().getString("config.e42") + " " + getConfig().getString("config.e4") + " is now " + ChatColor.GREEN + "Open");
              getConfig().set("config.e42stat", "Open");
              p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
             if (getConfig().getString("config.scoreboard").equals("True")) {
              this.e42.setScore(102);
             }
            }
        	
        	if ((args[0].equalsIgnoreCase("open")) && (args[1].equalsIgnoreCase("e43")) && (p.hasPermission("pixelgym.e43")) && (enablee4.equalsIgnoreCase("true")))
            {
              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e43colour")) + "The " + getConfig().getString("config.e43") + " " + getConfig().getString("config.e4") + " is now " + ChatColor.GREEN + "Open");
              getConfig().set("config.e43stat", "Open");
              p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
             if (getConfig().getString("config.scoreboard").equals("True")) {
              this.e43.setScore(103);
             }
            }
        	
        	if ((args[0].equalsIgnoreCase("open")) && (args[1].equalsIgnoreCase("e44")) && (p.hasPermission("pixelgym.e44")) && (enablee4.equalsIgnoreCase("true")))
            {
              Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e44colour")) + "The " + getConfig().getString("config.e44") + " " + getConfig().getString("config.e4") + " is now " + ChatColor.GREEN + "Open");
              getConfig().set("config.e44stat", "Open");
              p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
             if (getConfig().getString("config.scoreboard").equals("True")) {
              this.e44.setScore(104);
            } 
             
            }
        	
        	else if ((args[0].equalsIgnoreCase("close")) && (args[1].equalsIgnoreCase("e41")) && (p.hasPermission("pixelgym.e41")) && (enablee4.equalsIgnoreCase("true"))) {
                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e41colour")) + "The " + getConfig().getString("config.e41") + " " + getConfig().getString("config.e4") + " is now " + ChatColor.RED + "Closed");
                getConfig().set("config.e41stat", "Closed");
                p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e41colour")) + getConfig().getString("config.e41") + " " + getConfig().getString("config.e4ab")));
            }

        	else if ((args[0].equalsIgnoreCase("close")) && (args[1].equalsIgnoreCase("e42")) && (p.hasPermission("pixelgym.e42")) && (enablee4.equalsIgnoreCase("true"))) {
                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e42colour")) + "The " + getConfig().getString("config.e42") + " " + getConfig().getString("config.e4") + " is now " + ChatColor.RED + "Closed");
                getConfig().set("config.e42stat", "Closed");
                p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e42colour")) + getConfig().getString("config.e42") + " " + getConfig().getString("config.e4ab")));
            }
        	
        	else if ((args[0].equalsIgnoreCase("close")) && (args[1].equalsIgnoreCase("e43")) && (p.hasPermission("pixelgym.e43")) && (enablee4.equalsIgnoreCase("true"))) {
                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e43colour")) + "The " + getConfig().getString("config.e43") + " " + getConfig().getString("config.e4") + " is now " + ChatColor.RED + "Closed");
                getConfig().set("config.e43stat", "Closed");
                p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e43colour")) + getConfig().getString("config.e43") + " " + getConfig().getString("config.e4ab")));
            }
        	
        	else if ((args[0].equalsIgnoreCase("close")) && (args[1].equalsIgnoreCase("e44")) && (p.hasPermission("pixelgym.e44")) && (enablee4.equalsIgnoreCase("true"))) {
                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e44colour")) + "The " + getConfig().getString("config.e44") + " " + getConfig().getString("config.e4") + " is now " + ChatColor.RED + "Closed");
                getConfig().set("config.e44stat", "Closed");
                p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("config.e44colour")) + getConfig().getString("config.e44") + " " + getConfig().getString("config.e4ab")));
        	}
        	
      	}
        	
  }
    	else if (args.length == 3) {
       	 for (Player playerTarget : Bukkit.getServer().getOnlinePlayers()) {
       	  if ((args[0].equalsIgnoreCase("sendrules")) && (playerTarget.getName().equalsIgnoreCase(args[2]) && (p.hasPermission("pixelgym."+args[1])))) {
       		  p.sendMessage(ChatColor.GOLD + "Sent " + getConfig().getString("config."+args[1]) + " Elite 4 Rules to " + playerTarget.getName().toString());
       		  playerTarget.sendMessage(ChatColor.GOLD + playerTarget.getName().toString() + ", Make sure you read the " + getConfig().getString("config."+args[1]) + " Elite 4 rules propperly before facing the Gym!");
       		  playerTarget.sendMessage(ChatColor.GREEN + "1) " + getConfig().getString("config."+args[1]+"rule1"));
       		  playerTarget.sendMessage(ChatColor.GREEN + "2) " + getConfig().getString("config."+args[1]+"rule2"));
       		  playerTarget.sendMessage(ChatColor.GREEN + "3) " + getConfig().getString("config."+args[1]+"rule3"));
       		  playerTarget.sendMessage(ChatColor.GREEN + "4) " + getConfig().getString("config."+args[1]+"rule4"));
       		  playerTarget.sendMessage(ChatColor.GREEN + "5) " + getConfig().getString("config."+args[1]+"rule5"));
       	  }
         }
       
       }
   }
    

	
	return false;
   }


}

 
  
        

