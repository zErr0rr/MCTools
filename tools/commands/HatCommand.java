package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HatCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public HatCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("hat").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.hat")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.hat)", true);
    }
    ItemStack item = p.getItemInHand();
    if ((item.getType().equals(Material.AIR)) || (!item.getType().isBlock())) {
      return Utils.sendMsg(p, "&cPrzedmiot ktory trzymasz w rece nie jest blokiem!", true);
    }
    p.setItemInHand(p.getInventory().getHelmet());
    p.getInventory().setHelmet(item);
    p.updateInventory();
    return Utils.sendMsg(p, "&cCiesz sie nowym kapeluszem!", true);
  }
}
