package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class SpeedCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public SpeedCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("speed").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.speed")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &7(tobiasz.tools.speed)", true);
    }
    if (args.length == 1)
    {
      if (!ToolsPlugin.isFloat(args[0])) {
        return Utils.sendMsg(p, "&cPodany argument nie jest liczba!", true);
      }
      Float speed = Float.valueOf(Float.parseFloat(args[0]));
      if (speed.floatValue() > 1.0D) {
        return Utils.sendMsg(p, "&cPodano zbyt wysoka liczbe!", true);
      }
      if (setSpeed(p, speed)) {
        return Utils.sendMsg(p, "&cUstawiono predkosc latania na &c" + speed + "&c!", true);
      }
      return Utils.sendMsg(p, "&cUstawiono predkosc chodzenia na &c" + speed + "&c!", true);
    }
    if (args.length != 2) {
      return Utils.sendMsg(p, "&cUzycie: /speed <gracz> <predkosc>", true);
    }
    if (!p.hasPermission("dgtools.speed.others")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.speed.others)", true);
    }
    Player o = Bukkit.getPlayer(args[0]);
    if (o == null) {
      return Utils.sendMsg(p, "&cGracz o podanym nicku jest offline!", true);
    }
    if (!ToolsPlugin.isFloat(args[1])) {
      return Utils.sendMsg(p, "&cPodany argument nie jest liczba!", true);
    }
    Float speed2 = Float.valueOf(Float.parseFloat(args[0]));
    if (speed2.floatValue() > 1.0D) {
      return Utils.sendMsg(p, "&cPodano zbyt wysoka liczbe!", true);
    }
    if (setSpeed(o, speed2))
    {
      Utils.sendMsg(o, "&cUstawiono predkosc latania na &c" + speed2 + "&c!", true);
      return Utils.sendMsg(p, "&cUstawiono predkosc latania &c" + o.getName() + " &cna &c" + speed2 + "&c!", true);
    }
    Utils.sendMsg(o, "&cUstawiono predkosc chodzenia na &c" + speed2 + "&6!", true);
    return Utils.sendMsg(p, "&cUstawiono predkosc chodzenia &c" + o.getName() + " &cna &c" + speed2 + "&c!", true);
  }
  
  boolean setSpeed(Player player, Float speed)
  {
    if (player.isFlying())
    {
      player.setFlySpeed(speed.floatValue());
      return true;
    }
    player.setWalkSpeed(speed.floatValue());
    return false;
  }
}
