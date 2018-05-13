package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public SetSpawnCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("setspawn").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.setspawn")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.setspawn)", true);
    }
    this.plugin.getConfig().set("spawn.world", p.getLocation().getWorld().getName());
    this.plugin.getConfig().set("spawn.x", Double.valueOf(p.getLocation().getX()));
    this.plugin.getConfig().set("spawn.y", Double.valueOf(p.getLocation().getY()));
    this.plugin.getConfig().set("spawn.z", Double.valueOf(p.getLocation().getZ()));
    this.plugin.getConfig().set("spawn.yaw", Float.valueOf(p.getLocation().getYaw()));
    this.plugin.getConfig().set("spawn.pitch", Float.valueOf(p.getLocation().getPitch()));
    this.plugin.saveConfig();
    return Utils.sendMsg(p, "&cSpawn zostal ustawiony!", true);
  }
}
