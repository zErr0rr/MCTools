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
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class HomeCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public HomeCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("home").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    User u = UserManager.getUser(p);
    if (!p.hasPermission("tobiasz.tools.home")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.home)", true);
    }
    if (!p.hasPermission("tobiasz.tools.bezczasu"))
    {
      Utils.sendMsg(p, "&cTrwa teleportowanie do domu...", true);
      this.plugin.teleportPlayerWithDelay(p, 10, u.getHomeLocation(), "&cPrzeteleportowano do domu!", true, null);
      return true;
    }
    p.teleport(u.getHomeLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
    return Utils.sendMsg(p, "&cPrzeteleportowano do domu!", true);
  }
}
