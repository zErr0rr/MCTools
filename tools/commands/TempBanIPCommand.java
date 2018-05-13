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

public class TempBanIPCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public TempBanIPCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("tempbanip").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.tempbanip")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.tempbanip)", true);
    }
    if (args.length < 1) {
      return Utils.sendMsg(sender, "&cUzycie: /tempbanip <gracz/ip> <czas> <powod>", true);
    }
    User u = UserManager.getUser(args[0]);
    String ip = args[0];
    if (u != null) {
      ip = u.getLastIP();
    }
    if (sender.getName().equalsIgnoreCase(u.getLastNick())) {
      return Utils.sendMsg(sender, "&cNie mozesz zbanowac sam siebie!", true);
    }
    long time = Utils.parseDateDiff(args[1], true);
    BanIP b = BanIPManager.getBanIP(ip);
    if (b != null) {
      return Utils.sendMsg(sender, "&cAdres IP " + ip + " ma juz bana!", true);
    }
    String reason = "Administrator ma zawsze racje!";
    if (args.length > 2) {
      reason = StringUtils.join(args, " ", 2, args.length);
    }
    BanIP ban = BanIPManager.createBan(ip, reason, sender.getName(), time);
    return Utils.sendMsg(Utils.getOnlinePlayers(), "&cAdres IP &c" + ip + " &czostal zbanowany przez &c" + ban.getAdmin() + " &cdo &c" + Utils.getDate(time) + "&c! Powod: &c" + ban.getReason() + "&c!", true);
  }
}
