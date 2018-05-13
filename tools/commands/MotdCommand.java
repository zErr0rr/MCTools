package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;

public class MotdCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public MotdCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("motd").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.motd")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  (tobiasz.tools.motd)", true);
    }
    if (args.length < 1) {
      return Utils.sendMsg(sender, "&cPrawidlowe uzycie: &c" + cmd.getUsage(), true);
    }
    String motd = StringUtils.join(args, " ");
    
    this.plugin.motd = motd;
    this.plugin.getConfig().set("config.slot-manager.motd", motd);
    this.plugin.saveConfig();
    return Utils.sendMsg(sender, " §c &cPomyslnie ustawiono MOTD serwera na &c" + motd + "&c!", true);
  }
}
