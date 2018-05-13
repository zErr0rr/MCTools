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

public class GodCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public GodCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("god").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (args.length == 1)
    {
      if (!sender.hasPermission("tobiasz.tools.good")) {
        return Utils.sendMsg(sender, "Nie Masz Uprawnien tobiasz.tools.good", true);
      }
      Player p = Bukkit.getPlayer(args[0]);
      if (p == null) {
        return Utils.sendMsg(sender, " Gracz Jest offline!", true);
      }
      User u = UserManager.getUser(p);
      if (u == null) {
        return Utils.sendMsg(sender, " Brak Takiego uzytkownika w bazie danych", true);
      }
      u.setGod(!u.isGod());
      Utils.sendMsg(sender, "&c" + (u.isGod() ? "Wlaczono" : "Wylaczono") + " tryb goda dla gracza &c" + u.getLastNick() + "&c!", true);
      return Utils.sendMsg(p, " " + (u.isGod() ? "Wlaczono" : "Wylaczono") + " tryb goda przez &c" + sender.getName() + "&c!", true);
    }
    User u3 = UserManager.getUser(sender.getName());
    if (u3 == null) {
      return Utils.sendMsg(sender, "  &cUzytkownik nie istnieje w bazie danych!", true);
    }
    u3.setGod(!u3.isGod());
    return Utils.sendMsg(sender, "  " + (u3.isGod() ? "Wlaczono" : "Wylaczono") + " tryb goda!", true);
  }
}
