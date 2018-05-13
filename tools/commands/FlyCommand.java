package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class FlyCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public FlyCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("fly").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.fly")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &7(tobiasz.tools.fly)", true);
    }
    if (args.length == 0)
    {
      User u = UserManager.getUser(p);
      if (u == null) {
        return Utils.sendMsg(p, "&cUzytkownika o podanym nicku nie ma w bazie danych!", true);
      }
      p.setAllowFlight(!p.getAllowFlight());
      u.setFly(!u.isFly());
      return Utils.sendMsg(p, "&c" + (u.isFly() ? "Wlaczono" : "Wylaczono") + " &ctryb latania!", true);
    }
    if (args.length != 1) {
      return Utils.sendMsg(p, "&cUzycie: /fly <nicK>", true);
    }
    if (!p.hasPermission("tobiasz.tools.inni.fly")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &7(tobiasz.tools.inni.fly)", true);
    }
    Player o = Bukkit.getPlayer(args[0]);
    if (o == null) {
      return Utils.sendMsg(p, "&cGracz o podanym nicku jest offline!", true);
    }
    User u2 = UserManager.getUser(p);
    if (u2 == null) {
      return Utils.sendMsg(p, "&cUzytkownika o podanym nicku nie ma w bazie danych!", true);
    }
    o.setAllowFlight(!p.getAllowFlight());
    u2.setFly(!u2.isFly());
    Utils.sendMsg(p, "&c" + (u2.isFly() ? "Wlaczono" : "Wylaczono") + " &ctryb latania dla gracza &c" + o.getName() + "&c!", true);
    return Utils.sendMsg(o, "&c" + (u2.isFly() ? "Wlaczono" : "Wylaczono") + " &ctryb latania!", true);
  }
}
