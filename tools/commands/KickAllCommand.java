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

public class KickAllCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public KickAllCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("kickall").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.kickall")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.kickall)", true);
    }
    String reason = Utils.fixColor("&cKICKALL\n\n&c" + StringUtils.join(args, " "));
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (!p.hasPermission("tobiasz.tools.kickall.bypass")) {
        p.kickPlayer(Utils.fixColor(reason));
      }
    }
    return Utils.sendMsg(sender, "&cWyrzucono wszystkich graczy z serwera!", true);
  }
}
