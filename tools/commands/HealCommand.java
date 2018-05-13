package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class HealCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public HealCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("heal").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.heal")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(dgtools.heal)", true);
    }
    if (args.length == 0)
    {
      p.setHealth(20.0D);
      p.setFoodLevel(20);
      p.setFireTicks(0);
      for (PotionEffect potionEffect : p.getActivePotionEffects()) {
        p.removePotionEffect(potionEffect.getType());
      }
      return Utils.sendMsg(p, "&cZostales uleczony!", true);
    }
    if (args.length != 1) {
      return Utils.sendMsg(p, "&cUzycie: /heal <gracz>", true);
    }
    if (!p.hasPermission("tobiasz.tools.heal.inni")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.heal.inni)", true);
    }
    Player o = Bukkit.getPlayer(args[0]);
    if (o == null) {
      return Utils.sendMsg(p, "&cGracz o podanym nicku jest offline!", true);
    }
    o.setHealth(20.0D);
    o.setFoodLevel(20);
    o.setFireTicks(0);
    for (PotionEffect potionEffect2 : o.getActivePotionEffects()) {
      o.removePotionEffect(potionEffect2.getType());
    }
    Utils.sendMsg(o, "&cZostales uleczony przez &c" + p.getName() + "&c!", true);
    return Utils.sendMsg(p, "&cUleczyles gracza &c" + o.getName() + "&c!", true);
  }
}
