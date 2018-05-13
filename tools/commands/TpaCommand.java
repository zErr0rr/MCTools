package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.managers.TeleportManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class TpaCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public TpaCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("tpa").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.tpa")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.tpa)", true);
    }
    if (args.length != 1) {
      return Utils.sendMsg(p, "&cUzycie: /tpa <gracz>", true);
    }
    Player o = Bukkit.getPlayer(args[0]);
    if (o == null) {
      return Utils.sendMsg(p, "&cGracz o podanym nicku jest offline!", true);
    }
    if (o.hasPermission("tobiasz.tools.tp.block")) {
      return Utils.sendMsg(p, "&cDo tego gracza nie mozesz sie teleportowac!", true);
    }
    TeleportManager.sentRequest(p, o);
    Utils.sendMsg(o, "&cGracz &c" + p.getName() + " &cchce sie przeteleportowac do ciebie!", true);
    Utils.sendMsg(o, "&cWpisz &c/tpaccept&c, aby zaakceptowac!", true);
    Utils.sendMsg(o, "&cWpisz &c/tpadeny&c, aby odrzucic!", true);
    return Utils.sendMsg(p, "&cWyslano prosbe o teleportacje do &c" + o.getName() + "&c!", true);
  }
}
