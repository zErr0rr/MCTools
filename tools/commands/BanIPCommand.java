package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.BanIP;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.BanIPManager;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class BanIPCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public BanIPCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("banip").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.banip")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.banip)", true);
    }
    if (args.length < 1) {
      return Utils.sendMsg(sender, "&cUzycie: /banip <gracz/ip> <powod>", true);
    }
    User u = UserManager.getUser(args[0]);
    String ip = args[0];
    if (u != null) {
      ip = u.getLastIP();
    }
    if (sender.getName().equalsIgnoreCase(u.getLastNick())) {
      return Utils.sendMsg(sender, "&cNie mozesz zbanowac sam siebie!", true);
    }
    BanIP b = BanIPManager.getBanIP(ip);
    if (b != null) {
      return Utils.sendMsg(sender, "&cAdres IP " + ip + " ma juz bana!", true);
    }
    String reason = "Administrator ma zawsze racje!";
    if (args.length > 1) {
      reason = StringUtils.join(args, " ", 1, args.length);
    }
    BanIP ban = BanIPManager.createBan(ip, reason, sender.getName(), 0L);
    return Utils.sendMsg(Utils.getOnlinePlayers(), "&cAdres IP &c" + ip + " &czostal zbanowany przez &c" + ban.getAdmin() + "&c! Powod: &c" + ban.getReason() + "&c!", true);
  }
}
