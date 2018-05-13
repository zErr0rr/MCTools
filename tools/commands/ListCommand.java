package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class ListCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public ListCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("list").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.listagraczy")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.listagraczy)", true);
    }
    List<String> arrayOfString = new ArrayList();
    for (Player p : Bukkit.getOnlinePlayers()) {
      arrayOfString.add(p.getName());
    }
    String players = StringUtils.join(arrayOfString.toArray(), ", ");
    Utils.sendMsg(sender, "&cNa serwerze jest aktualnie &c" + Utils.getOnlinePlayers().size() + " &cgraczy na &c" + Bukkit.getMaxPlayers() + " &cmozliwych!", true);
    if (sender.hasPermission("tobiasz.toolk.show.list.player")) {
      Utils.sendMsg(sender, "&cGracze online: &c" + players, true);
    }
    return true;
  }
}
