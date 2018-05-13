package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class SetHomeCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public SetHomeCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("sethome").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    User u = UserManager.getUser(p);
    if (!p.hasPermission("tobiasz.tools.sethome")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.sethome)", true);
    }
    u.setHomeLocation(p.getLocation());
    u.update(false);
    return Utils.sendMsg(p, "&cDom zostal ustawiony!", true);
  }
}
