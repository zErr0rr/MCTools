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

public class KickCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public KickCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("kick").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.kick")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.kick)", true);
    }
    if (args.length == 0) {
      return Utils.sendMsg(sender, "&cUzycie: /kick <gracz> <powod>", true);
    }
    Player o = Bukkit.getPlayer(args[0]);
    if (o == null) {
      return Utils.sendMsg(sender, "&cGracz o podanym nicku jest offline!", true);
    }
    String reason = "Administrator ma zawsze racje!";
    if (args.length > 1) {
      reason = StringUtils.join(args, " ", 1, args.length);
    }
    o.kickPlayer(Utils.fixColor(" &c zostales wyrzucony przez" + sender.getName() + "&c powod" + reason));
    for (Player player : Bukkit.getOnlinePlayers()) {
      Utils.sendMsg(player, "&c Gracz " + o.getName() + " &c Zostal Wyrzucony przez " + sender.getName() + "&c powod " + reason + "&c!", true);
    }
    return true;
  }
}
