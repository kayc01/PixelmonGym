package me.Ckay.gym;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


public class SettingsManager {
	
	private SettingsManager() {}
	
	 static SettingsManager instance = new SettingsManager();
     
     public static SettingsManager getInstance() {
             return instance;
     }
	
	FileConfiguration data;
    File dfile;
    
    FileConfiguration badges;
    File bfile;
    
    FileConfiguration logs;
    File lfile;
    
    FileConfiguration extras;
    File efile;
   
    public void setupBadges(Plugin p) {
    	
        
        if (!p.getDataFolder().exists()) {
                p.getDataFolder().mkdir();
        }
       
        bfile = new File(p.getDataFolder(), "badges.yml");
       
        if (!bfile.exists()) {
                try {
                        bfile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create badges.yml!");
                }
    
    }
        
        badges = YamlConfiguration.loadConfiguration(bfile);
        
  }
    
    public FileConfiguration getBadge() {
        return badges;
}
    
    public void saveBadges() {
        try {
                badges.save(bfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save badges.yml!");
        }
     }
    
    public void reloadBadges() {
        badges = YamlConfiguration.loadConfiguration(bfile);
}
    
    //--------------------------------------
    
    public void setup(Plugin p) {
    	
       
        if (!p.getDataFolder().exists()) {
                p.getDataFolder().mkdir();
        }
       
        dfile = new File(p.getDataFolder(), "data.yml");
       
        if (!dfile.exists()) {
                try {
                        dfile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
                }
    
    }
        
        data = YamlConfiguration.loadConfiguration(dfile);
        
  }
    
    public FileConfiguration getData() {
        return data;
}
    
    public void saveData() {
        try {
                data.save(dfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
        }
     }
    
    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dfile);
}
    
    // ------------------------------
    
public void setupLog(Plugin p) {
    	
        
        if (!p.getDataFolder().exists()) {
                p.getDataFolder().mkdir();
        }
       
        lfile = new File(p.getDataFolder(), "logs.yml");
       
        if (!lfile.exists()) {
                try {
                        lfile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create logs.yml!");
                }
    
    }
        
        logs = YamlConfiguration.loadConfiguration(lfile);
        
  }
    
    public FileConfiguration getLogs() {
        return logs;
}
    
    public void saveLogs() {
        try {
                logs.save(lfile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save logs.yml!");
        }
     }
    
    public void reloadLogs() {
        logs = YamlConfiguration.loadConfiguration(lfile);
}
    
    
//--------------------------------------
    
    public void setupExtra(Plugin p) {
    	
       
        if (!p.getDataFolder().exists()) {
                p.getDataFolder().mkdir();
        }
       
        efile = new File(p.getDataFolder(), "extras.yml");
       
        if (!efile.exists()) {
                try {
                        efile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create extras.yml!");
                }
    
    }
        
        extras = YamlConfiguration.loadConfiguration(efile);
        
  }
    
    public FileConfiguration getExtras() {
        return extras;
}
    
    public void saveExtras() {
        try {
                extras.save(efile);
        }
        catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save extras.yml!");
        }
     }
    
    public void reloadExtras() {
        extras = YamlConfiguration.loadConfiguration(efile);
}
    
    // ------------------------------
    
    
    
}
