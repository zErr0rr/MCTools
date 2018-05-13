package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class GamemodeCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public GamemodeCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("gamemode").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.gamemode")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.gamemode)", true);
    }
    String[] survival = { "0", "s", "survival" };
    String[] creative = { "1", "c", "creative" };
    String[] adventure = { "2", "a", "adventure" };
    if (args.length == 1)
    {
      if (Utils.containsIgnoreCase(survival, args[0]))
      {
        setGamemode(p, GameMode.SURVIVAL, null);
      }
      else if (Utils.containsIgnoreCase(creative, args[0]))
      {
        setGamemode(p, GameMode.CREATIVE, null);
      }
      else
      {
        if (!Utils.containsIgnoreCase(adventure, args[0])) {
          return Utils.sendMsg(p, "&cNiepoprawny tryb gamemode!", true);
        }
        setGamemode(p, GameMode.ADVENTURE, null);
      }
    }
    else
    {
      if (args.length != 2) {
        return Utils.sendMsg(p, "&cUzycie: /gamemode <gracz> <tryb>", true);
      }
      if (!p.hasPermission("tobiasz.tools.gamemode.inni")) {
        return Utils.sendMsg(p, "&cNie masz uprawnien.  &7(tobiasz.tools.gamemode.inni)", true);
      }
      Player o = Bukkit.getPlayer(args[0]);
      if (o == null) {
        return Utils.sendMsg(p, "&cGracz o podanym nicku jest offline!", true);
      }
      if (Utils.containsIgnoreCase(survival, args[1]))
      {
        setGamemode(p, GameMode.SURVIVAL, o);
      }
      else if (Utils.containsIgnoreCase(creative, args[1]))
      {
        setGamemode(p, GameMode.CREATIVE, o);
      }
      else
      {
        if (!Utils.containsIgnoreCase(adventure, args[1])) {
          return Utils.sendMsg(p, "&cNiepoprawny tryb gamemode!", true);
        }
        setGamemode(p, GameMode.ADVENTURE, o);
      }
    }
    return true;
  }
  
  private void setGamemode(Player p, GameMode mode, CommandSender changer)
  {
    User u = UserManager.getUser(p.getUniqueId());
    if (u == null) {
      return;
    }
    p.setGameMode(mode);
    p.setAllowFlight(mode.equals(GameMode.CREATIVE));
    u.setGamemode(mode);
    u.setFly(mode.equals(GameMode.CREATIVE));
    u.setGod(mode.equals(GameMode.CREATIVE));
    if (changer == null)
    {
      Utils.sendMsg(p, "&cTwoj tryb gry zostal zmieniony na &c" + mode.toString().toLowerCase() + "&6!", true);
    }
    else
    {
      String c = changer.getName().equalsIgnoreCase("CONSOLE") ? "konsole" : changer.getName();
      Utils.sendMsg(p, "&cTwoj tryb gry zostal zmieniony na &c" + mode.toString().toLowerCase() + " &cprzez &c" + c + "&c!", true);
      Utils.sendMsg(p, "&cTryb gracza &c" + p.getName() + " &czostal zmieniony na &c" + mode.toString().toLowerCase() + "&c!", true);
    }
  }
}
