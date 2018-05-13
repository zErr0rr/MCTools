package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class SpawnCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public SpawnCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("spawn").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.spawn")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.spawn)", true);
    }
    if (!p.hasPermission("tobiasz.tools.spawn.nodelay"))
    {
      Utils.sendMsg(p, "&cTrwa teleportowanie na spawn...", true);
      this.plugin.teleportPlayerWithDelay(p, 10, this.plugin.getSpawn(), "&cPrzeteleportowano na spawn!", true, null);
      return true;
    }
    p.teleport(this.plugin.getSpawn(), PlayerTeleportEvent.TeleportCause.COMMAND);
    return Utils.sendMsg(p, "&cPrzeteleportowano na spawn!", true);
  }
}
