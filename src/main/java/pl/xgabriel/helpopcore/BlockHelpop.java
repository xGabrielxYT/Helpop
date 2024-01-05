package pl.xgabriel.helpopcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class BlockHelpop implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("§cTa komenda może być używana tylko przez graczy.");
         return true;
      } else {
         Player player = (Player)sender;
         FileConfiguration config = Main.getInstance().getConfig();
         String permissjablokada = config.getString("helpop.permissionBlockHelpop");
         String permisjamsg = config.getString("messagesHelpop.noPermissionBlock");
         if (!player.hasPermission(permissjablokada)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', permisjamsg));
            return false;
         } else {
            String targetName;
            if (args.length != 1) {
               targetName = config.getString("messagesHelpop.blockUzycie");
               sender.sendMessage(ChatColor.translateAlternateColorCodes('&', targetName));
               return false;
            } else {
               targetName = args[0];
               Player target = Bukkit.getPlayer(targetName);
               if (target == null) {
                  String pof = config.getString("messagesHelpop.blockOffline").replace("{nick}", targetName);
                  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pof));
                  return true;
               } else {
                  ConfigurationSection blockHelpopSection = config.getConfigurationSection("blockhelpop");
                  if (blockHelpopSection == null) {
                     blockHelpopSection = config.createSection("blockhelpop");
                  }

                  String targetUUID = target.getUniqueId().toString();
                  boolean isBlocked = blockHelpopSection.getBoolean(targetUUID, false);
                  String odblokowano;
                  if (isBlocked) {
                     blockHelpopSection.set(targetUUID, (Object)null);
                     odblokowano = config.getString("messagesHelpop.unblocked").replace("{nick}", targetName);
                     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', odblokowano));
                  } else {
                     blockHelpopSection.set(targetUUID, true);
                     odblokowano = config.getString("messagesHelpop.blocked").replace("{nick}", targetName);
                     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', odblokowano));
                  }

                  Main.getInstance().saveConfig();
                  return true;
               }
            }
         }
      }
   }

   public static boolean isBlocked(Player player) {
      FileConfiguration config = Main.getInstance().getConfig();
      ConfigurationSection blockHelpopSection = config.getConfigurationSection("blockhelpop");
      if (blockHelpopSection != null) {
         String playerUUID = player.getUniqueId().toString();
         return blockHelpopSection.getBoolean(playerUUID, false);
      } else {
         if (blockHelpopSection != null) {
            Main.getInstance().saveConfig();
         }

         return false;
      }
   }
}
