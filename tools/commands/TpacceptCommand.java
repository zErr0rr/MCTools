package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.managers.TeleportManager;
import com.tobiasz.tools.utils.Utils;
import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class TpacceptCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public TpacceptCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("tpaccept").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.tpaccept")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.tpaccept)", true);
    }
    if (TeleportManager.getLastSenderByReceiver().get(p) == null) {
      return Utils.sendMsg(p, "&cNie masz oczekujacej prosby o teleportacje!", true);
    }
    Player o = (Player)TeleportManager.getLastSenderByReceiver().get(p);
    if (o == null)
    {
      TeleportManager.denyRequest(o, p);
      return Utils.sendMsg(p, "&cNie masz oczekujacej prosby o teleportacje!", true);
    }
    if ((System.currentTimeMillis() - ((Long)TeleportManager.getLastSenderRequestTime().get(o)).longValue()) / 1000L > 60L)
    {
      TeleportManager.denyRequest(o, p);
      return Utils.sendMsg(p, "&cNie masz oczekujacej prosby o teleportacje!", true);
    }
    TeleportManager.acceptRequest(o, p);
    Utils.sendMsg(o, "&cGracz &c" + p.getName() + " &czaakceptowal twoja prosbe o teleportacje!", true);
    Utils.sendMsg(p, "&cTeleport rozgrzewa sie...", true);
    this.plugin.teleportPlayerWithDelay(o, 10, p.getLocation(), "&cPrzeteleportowano do gracza &c" + p.getName() + "&c!", true, null);
    return Utils.sendMsg(p, "&cZaakceptowales prosbe o teleportacje gracza &c" + o.getName() + "&c!", true);
  }
}
