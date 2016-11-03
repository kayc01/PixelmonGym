package me.Ckay.gym;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;


public class SignListeners implements Listener {

//private PixelGym PixelGym.get();	

//public SignListeners(PixelGym PixelGym.get()) {
	//this.PixelGym.get() = PixelGym.get();
	//getServer().getPixelGym.get()Manager().registerEvents(this, this);
//}


public boolean setStatusSign(Location l, String gym) {
    File directory = PixelGym.get().getDataFolder();
    if (!directory.exists() && (!directory.mkdirs())) {
            return false;
    }
    File storage = new File(directory, "signs.yml");
    try {
		if (!storage.exists() && (!storage.createNewFile())) {
		        return false;
		}
	} catch (IOException e1) {
		//Auto-generated catch block
		return false;
	}
    YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);

    List<String> list = config.getStringList("status");
    if (list == null)
            list = new ArrayList<String>();
    list.add(l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ()
                    + ", " + l.getWorld().getName() + ", " + gym);

    config.set("status", list);

    try {
            config.save(storage);
    } catch (IOException e) {
            e.printStackTrace();
            return false;
    }
    PixelGym.get().updateSigns();
    return true;
}

public boolean setLeaderSign(Location l, String gym, int number) {
    File directory = PixelGym.get().getDataFolder();
    if (!directory.exists() && (!directory.mkdirs())) {
            return false;
    }
    File storage = new File(directory, "signs.yml");
    try {
		if (!storage.exists() && (!storage.createNewFile())) {
		        return false;
		}
	} catch (IOException e1) {
		//Auto-generated catch block
		return false;
	}
    YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);

    List<String> list = config.getStringList("leader");
    if (list == null)
            list = new ArrayList<String>();
    list.add(l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ()
                    + ", " + l.getWorld().getName() + ", " + gym +", " +number);

    config.set("leader", list);

    try {
            config.save(storage);
    } catch (IOException e) {
            e.printStackTrace();
            return false;
    }
    PixelGym.get().updateSigns();
    return true;
}

//@EventHandler
//public void onBlockBreak(BlockBreakEvent e) {
//	Location l = e.getBlock().getLocation();
//	if (e.getBlock().getType() == Material.WALL_SIGN
//		            	     || e.getBlock().getType() == Material.SIGN_POST) {
//		PixelGym.get().removeSign(l);
//		            	    	 
//		            	     }
//}

@EventHandler
public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        //System.out.println("On Sign Change Reached");
        if (e.getLine(0).equalsIgnoreCase("[GymStatus]")
                        && (p.hasPermission("pixelgym.admin"))) {
        	if (e.getLine(1).startsWith("gym")) {
        	    String gym = e.getLine(1).replace("gym", "");
        	    int gymNumber = 0;
        	    try {
        	     gymNumber = Integer.parseInt(gym);
        	    } catch (NumberFormatException ex) {
        	     p.sendMessage("Use \"gym + number\" on line 2!");
        	    }
        	    setStatusSign(e.getBlock().getLocation(),
        	      Integer.toString(gymNumber));
        	   }
        } else if (e.getLine(0).equalsIgnoreCase("[GymLeaders]")
                        && (p.hasPermission("pixelgym.admin"))) {
        	 setLeaderSign(e.getBlock().getLocation(), e.getLine(1), 1);
             //System.out.println("Line 0 = [GymLeaders] Reached");
        } else if ((e.getLine(0).startsWith("[GymLeaders") && e.getLine(0)
                        .endsWith("]")) && (p.hasPermission("pixelgym.admin"))) {
            //System.out.println("Line 0 = [GymLeaders .endWith ]");
                try{
                    //System.out.println("Replaced ] to ''");
                	setLeaderSign(e.getBlock().getLocation(), e.getLine(1),
                		      Integer.parseInt(e.getLine(0).replace("[GymLeaders","").replace(
                		        "]", "")));
                }catch(NumberFormatException ex){
                    //System.out.println("User [GymLeaders + number]");
                        p.sendMessage(ChatColor.RED  + "Use [GymLeaders + number] on line 1!");
                }
        }
}

	
	
	//this is an example with updating a sign when clicking it. 
    // the same things need to apply, but just on command from a different class or when a value in the config changes.
	
	}

