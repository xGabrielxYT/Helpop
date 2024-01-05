package pl.xgabriel.helpopcore;

import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.GameMode.*;

public class HelpOP implements CommandExecutor {
   FileConfiguration config = Main.getInstance().getConfig();
   String czas;
   private final Map cooldowns;
   private final int cooldownTime;

   public HelpOP() {
      this.czas = this.config.getString("helpop.cooldown");
      this.cooldowns = new HashMap();
      this.cooldownTime = Integer.parseInt(this.czas);
   }

   String teleportconfig = config.getString("helpop.clickTeleport");
   TextComponent tp = new TextComponent(ChatColor.translateAlternateColorCodes('&',teleportconfig));

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      Main plugin = Main.getInstance();
      if (plugin == null) {
         sender.sendMessage("Wystąpił błąd podczas uzyskiwania instancji pluginu.");
         return true;
      } else if (!(sender instanceof Player)) {
         sender.sendMessage("§cTylko gracze mogą używać tej komendy.");
         return true;
      } else {
         Player player = (Player)sender;
         String message;
         boolean currentAlertSettingBar;
         if (player.hasPermission("hikemc.helpop.admin") && args.length == 1 && args[0].equalsIgnoreCase("alerts")) {
            currentAlertSettingBar = plugin.getConfig().getBoolean("helpop.adminAlerts." + player.getUniqueId().toString(), true);
            plugin.getConfig().set("helpop.adminAlerts." + player.getUniqueId().toString(), !currentAlertSettingBar);
            plugin.getConfig().set("helpop.adminChat." + player.getUniqueId().toString(), !currentAlertSettingBar);
            plugin.getConfig().set("helpop.adminTitle." + player.getUniqueId().toString(), !currentAlertSettingBar);
            plugin.getConfig().set("helpop.adminBar." + player.getUniqueId().toString(), !currentAlertSettingBar);

            plugin.saveConfig();
            message = currentAlertSettingBar ? "§cwyłączone" : "§awłączone";
            sender.sendMessage(ChatColor.GREEN + "Alerty helpopa zostały " + message + ".");
            return true;
         } else if (player.hasPermission("hikemc.helpop.admin.title") && args.length == 1 && args[0].equalsIgnoreCase("title")) {
            currentAlertSettingBar = plugin.getConfig().getBoolean("helpop.adminTitle." + player.getUniqueId().toString(), true);
            plugin.getConfig().set("helpop.adminTitle." + player.getUniqueId().toString(), !currentAlertSettingBar);
            plugin.saveConfig();
            message = currentAlertSettingBar ? "§cwyłączone" : "§awłączone";
            sender.sendMessage(ChatColor.GREEN + "Alerty helpopa title zostały " + message + ".");
            return true;
         } else if (player.hasPermission("hikemc.helpop.admin.chat") && args.length == 1 && args[0].equalsIgnoreCase("chat")) {
            currentAlertSettingBar = plugin.getConfig().getBoolean("helpop.adminChat." + player.getUniqueId().toString(), true);
            plugin.getConfig().set("helpop.adminChat." + player.getUniqueId().toString(), !currentAlertSettingBar);
            plugin.saveConfig();
            message = currentAlertSettingBar ? "§cwyłączone" : "§awłączone";
            sender.sendMessage(ChatColor.GREEN + "Alerty helpopa chat zostały " + message + ".");
            return true;
         } else if (player.hasPermission("hikemc.helpop.admin.bar") && args.length == 1 && args[0].equalsIgnoreCase("bar")) {
            currentAlertSettingBar = plugin.getConfig().getBoolean("helpop.adminBar." + player.getUniqueId().toString(), true);
            plugin.getConfig().set("helpop.adminBar." + player.getUniqueId().toString(), !currentAlertSettingBar);
            plugin.saveConfig();
            message = currentAlertSettingBar ? "§cwyłączone" : "§awłączone";
            sender.sendMessage(ChatColor.GREEN + "Alerty helpopa actionbar zostały " + message + ".");
            return true;
         } else {
            String permisja;
            if (player.hasPermission("hikemc.helpop.admin.info") && args.length == 1 && args[0].equalsIgnoreCase("info")) {
               currentAlertSettingBar = plugin.getConfig().getBoolean("helpop.adminBar." + player.getUniqueId().toString(), true);
               boolean currentAlertSettingTitle = plugin.getConfig().getBoolean("helpop.adminTitle." + player.getUniqueId().toString(), true);
               boolean currentAlertSettingChat = plugin.getConfig().getBoolean("helpop.adminChat." + player.getUniqueId().toString(), true);
               boolean currentAlertSettingAlerts = plugin.getConfig().getBoolean("helpop.adminAlerts." + player.getUniqueId().toString(), true);
               String alertStatusBar = currentAlertSettingBar ? "§2włączone" : "§cwyłączone";
               String alertStatusTitle = currentAlertSettingTitle ? "§2włączone" : "§cwyłączone";
               permisja = currentAlertSettingChat ? "§2włączone" : "§cwyłączone";
               String alertStatusAlerts = currentAlertSettingAlerts ? "§2włączone" : "§cwyłączone";
               sender.sendMessage(ChatColor.GREEN + "Alerty helpopa actionbar są " + alertStatusBar + ".");
               sender.sendMessage(ChatColor.GREEN + "Alerty helpopa tytułu są " + alertStatusTitle + ".");
               sender.sendMessage(ChatColor.GREEN + "Alerty helpopa chat są " + permisja + ".");
               sender.sendMessage(ChatColor.GREEN + "Alerty helpopa są " + alertStatusAlerts + ".");
               return true;
            } else {
               String permbypass;
               if (BlockHelpop.isBlocked(player)) {
                  permbypass = this.config.getString("messagesHelpop.playerBlocked");
                  sender.sendMessage(ChatColor.translateAlternateColorCodes('&', permbypass));
                  return true;
               } else {
                  FileConfiguration config;
                  if (args.length < 1) {
                     config = Main.getInstance().getConfig();
                     message = config.getString("messagesHelpop.poprawneUzycie");
                     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                     return false;
                  } else {
                     String wyslanogracz;
                     if (this.hasCooldown(player)) {
                        permbypass = this.config.getString("helpop.permissionForBypass");
                        if (!player.hasPermission(permbypass)) {
                           long timeLeft = this.getCooldownTimeLeft(player);
                           wyslanogracz = this.config.getString("messagesHelpop.cooldownLeft").replace("{czas}", String.valueOf(timeLeft));
                           sender.sendMessage(ChatColor.translateAlternateColorCodes('&', wyslanogracz));
                           return true;
                        }
                     }

                     this.setCooldown(player);
                     config = Main.getInstance().getConfig();
                     message = String.join(" ", args);
                     String senderName = player.getName();
                     wyslanogracz = config.getString("helpop.playerSend").replace("{wiadomosc}", ChatColor.translateAlternateColorCodes('&', message));
                     if (config.getBoolean("helpop.playerSendMessage")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', wyslanogracz));
                     }

                     Iterator var11 = Bukkit.getOnlinePlayers().iterator();

                     while(var11.hasNext()) {
                        Player recipient = (Player) var11.next();
                        permisja = config.getString("helpop.permission");
                        if (recipient.hasPermission(permisja)) {
                           boolean adminAlertsEnabled = plugin.getConfig().getBoolean("helpop.adminAlerts." + player.getUniqueId().toString(), true);
                           boolean adminAlertsEnabledbar = plugin.getConfig().getBoolean("helpop.adminBar." + player.getUniqueId().toString(), true);
                           boolean adminAlertsEnabledtitle = plugin.getConfig().getBoolean("helpop.adminTitle." + player.getUniqueId().toString(), true);
                           boolean adminAlertsEnabledchat = plugin.getConfig().getBoolean("helpop.adminChat." + player.getUniqueId().toString(), true);
                           String chatMessage;
                           if (config.getBoolean("helpop.title") && adminAlertsEnabled && adminAlertsEnabledtitle) {
                              chatMessage = config.getString("messagesHelpop.title").replace("{nick}", player.getName()).replace("{wiadomosc}", message);
                              String subtitleMessage = config.getString("messagesHelpop.subTitle").replace("{nick}", player.getName()).replace("{wiadomosc}", message);
                              String czasttile = config.getString("messagesHelpop.titleTime");
                              recipient.sendTitle(ChatColor.translateAlternateColorCodes('&', chatMessage), ChatColor.translateAlternateColorCodes('&', subtitleMessage), 1, Integer.parseInt(czasttile), 1);
                           }

                           if (config.getBoolean("helpop.actionbar") && adminAlertsEnabled && adminAlertsEnabledbar) {
                              chatMessage = config.getString("messagesHelpop.actionbar").replace("{nick}", player.getName()).replace("{wiadomosc}", ChatColor.translateAlternateColorCodes('&', message));
                              recipient.sendActionBar(ChatColor.translateAlternateColorCodes('&', chatMessage));
                           }

                           if (config.getBoolean("helpop.chat") && adminAlertsEnabled && adminAlertsEnabledchat) {
                              chatMessage = config.getString("messagesHelpop.chat").replace("{nick}", player.getName()).replace("{wiadomosc}", message);
                              String confighovertp = config.getString("helpop.clickHoverTp")
                                      .replace("{nick}", player.getName());
                              String komendaconfig = config.getString("helpop.clickCommandTP")
                                      .replace("{nick}", player.getName())
                                      .replace("{admin}", recipient.getName());
                              tp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', confighovertp))));
                              tp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, komendaconfig));

                              TextComponent fullMessage = new TextComponent(
                                      TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', chatMessage + " ")));
                              fullMessage.addExtra(tp);

                              if (config.getBoolean("helpop.clickCommand")) {
                                 recipient.spigot().sendMessage(ChatMessageType.CHAT, fullMessage);
                              } else {
                                 recipient.spigot().sendMessage(ChatMessageType.CHAT, fullMessage);
                              }
                           }
                        }
                     }

                     if (config.getBoolean("helpop.webhook")) {
                        try {
                           this.sendWebhookMessageWithEmbed("", "Zgłoszenie gracza " + senderName, "Zgłoszenie gracza: " + senderName + "\n Treść: " + message, 15548997);
                        } catch (Exception var21) {
                           var21.printStackTrace();
                        }
                     }

                     return true;
                  }
               }
            }
         }
      }
   }

   private void sendWebhookMessageWithEmbed(String content, String embedTitle, String embedDescription, int embedColor) throws Exception {
      FileConfiguration config = Main.getInstance().getConfig();
      String link = config.getString("helpop.webhookLink");
      URL url = new URL(link);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);
      embedDescription = embedDescription.replace("\n", "\\n");
      String jsonInputString = "{\"content\": \"" + content + "\",\"embeds\": [{\"title\": \"" + embedTitle + "\",\"description\": \"" + embedDescription + "\",\"color\": " + embedColor + "}]}";
      OutputStream os = connection.getOutputStream();
      Throwable var11 = null;

      try {
         byte[] input = jsonInputString.getBytes("utf-8");
         os.write(input, 0, input.length);
      } catch (Throwable var20) {
         var11 = var20;
         throw var20;
      } finally {
         if (os != null) {
            if (var11 != null) {
               try {
                  os.close();
               } catch (Throwable var19) {
                  var11.addSuppressed(var19);
               }
            } else {
               os.close();
            }
         }

      }

      int responseCode = connection.getResponseCode();
      if (responseCode != 204) {
         throw new RuntimeException("HTTP Response Code: " + responseCode);
      } else {
         connection.disconnect();
      }
   }

   private boolean hasCooldown(Player player) {
      if (this.cooldowns.containsKey(player.getName())) {
         long timeLeft = this.getCooldownTimeLeft(player);
         return timeLeft > 0L;
      } else {
         return false;
      }
   }

   private void setCooldown(Player player) {
      this.cooldowns.put(player.getName(), System.currentTimeMillis() + (long)(this.cooldownTime * 1000));
   }

   private long getCooldownTimeLeft(Player player) {
      long cooldownExpireTime = (Long)this.cooldowns.get(player.getName());
      long currentTime = System.currentTimeMillis();
      return Math.max(0L, (cooldownExpireTime - currentTime) / 1000L);
   }
}
