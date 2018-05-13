package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.BanIP;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.BanIPManager;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class UnBanIPCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public UnBanIPCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("unbanip").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.unbanip")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &7(tobiasz.tools.unbanip)", true);
    }
    if (args.length < 1) {
      return Utils.sendMsg(sender, "&cUzycie: /unbanip <gracz>", true);
    }
    User u = UserManager.getUser(args[0]);
    String ip = args[0];
    if (u != null) {
      ip = u.getLastIP();
    }
    BanIP b = BanIPManager.getBanIP(ip);
    if (b == null) {
      return Utils.sendMsg(sender, "&cAdres IP " + ip + " nie ma bana!", true);
    }
    BanIPManager.deleteBan(b);
    return Utils.sendMsg(Utils.getOnlinePlayers(), "&cAdres IP &c" + ip + " &czostal odbanowany przez &c" + sender.getName() + "&c!", true);
  }
}
