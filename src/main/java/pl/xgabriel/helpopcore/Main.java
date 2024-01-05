package pl.xgabriel.helpopcore;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
   public static Main instance;

   public void onEnable() {
      instance = this;
      File dataFolder = this.getDataFolder();
      File configFile = new File(dataFolder, "config.yml");
      if (!configFile.exists()) {
         this.saveResource("config.yml", false);
      }

      FileConfiguration config = this.getConfig();
      this.getCommand("helpop").setExecutor(new HelpOP());
      this.getCommand("helpopreload").setExecutor(new ReloadCommand());
      this.getCommand("blokadahelpop").setExecutor(new BlockHelpop());
   }

   public void onDisable() {
   }

   public static Main getInstance() {
      return instance;
   }
}
