package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.PlayerTeleportEvent;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TpCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public TpCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("tp").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.tp")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.tp)", true);
    }
    if (args.length == 1)
    {
      Player o = Bukkit.getPlayer(args[0]);
      if (o == null) {
        return Utils.sendMsg(p, "&cGracz o podanym nicku jest offline!", true);
      }
      p.teleport(o.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
      return Utils.sendMsg(p, "&cPrzeteleportowano do gracza &a" + o.getName() + "&6!", true);
    }
    if (args.length != 2) {
      return Utils.sendMsg(p, "&cUzycie: /tp <gracz> <gracz>", true);
    }
    if (!p.hasPermission("dgtools.tp.others")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &7(tobiasz.tools.tp.others)", true);
    }
    Player o = Bukkit.getPlayer(args[0]);
    Player oo = Bukkit.getPlayer(args[1]);
    if ((o == null) || (oo == null)) {
      return Utils.sendMsg(p, "&cGracz o podanym nicku jest offline!", true);
    }
    o.teleport(oo.getLocation());
    Utils.sendMsg(o, "&cGracz &c" + p.getName() + " &cprzeteleportowal cie do gracza &c" + oo.getName() + "&c!", true);
    return Utils.sendMsg(p, "&cPrzeteleportowano gracza &c" + o.getName() + " &cdo gracza &c" + oo.getName() + "&c!", true);
  }
}
