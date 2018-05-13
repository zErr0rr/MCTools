package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class BroadcastCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public BroadcastCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("broadcast").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.ogloszenie")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &7(tobiasz.tools.ogloszenie)", true);
    }
    if (args.length > 0)
    {
      for (Player online : Bukkit.getOnlinePlayers()) {
        Utils.sendMsg(online, "&c[Ogloszenie] &c" + StringUtils.join(args, " "), false);
      }
      return true;
    }
    return Utils.sendMsg(sender, "&cUzycie: /broadcast <wiadomosc>", true);
  }
}
