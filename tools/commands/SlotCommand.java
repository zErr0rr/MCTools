package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;

public class SlotCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public SlotCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("slot").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.slots")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.slots)", true);
    }
    if (args.length != 1) {
      return Utils.sendMsg(sender, " §c Prawidlowe Uzycie &c" + cmd.getUsage(), true);
    }
    if (!Utils.isInteger(args[0])) {
      return Utils.sendMsg(sender, " §c  &cPodana wartosc nie jest liczba!", true);
    }
    int slots = Integer.parseInt(args[0]);
    
    this.plugin.slots = slots;
    this.plugin.getConfig().set("config.slot-manager.slots", Integer.valueOf(slots));
    this.plugin.saveConfig();
    return Utils.sendMsg(sender, " §c Pomyslnie ustawiono liczbe slotow na &c" + slots + "&c!", true);
  }
}
