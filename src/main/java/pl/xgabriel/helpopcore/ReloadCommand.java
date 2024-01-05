package pl.xgabriel.helpopcore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender.hasPermission("hikehelpop.reload")) {
         Main.getInstance().reloadConfig();
         sender.sendMessage("§aPomyślnie przeładowano konfigurację.");
      } else {
         sender.sendMessage("§cNie masz uprawnień do użycia tej komendy.");
      }

      return true;
   }
}
