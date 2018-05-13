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

public class RepairCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public RepairCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("repair").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.repair")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &7(tobiasz.tools.repair)", true);
    }
    if (args.length == 0)
    {
      ItemStack item = p.getItemInHand();
      if ((item.getType().equals(Material.AIR)) || (item.getType().isBlock())) {
        return Utils.sendMsg(p, "&cPrzedmiot ktory trzymasz w rece jest blokiem!", true);
      }
      repair(p, item);
      return Utils.sendMsg(p, "&cPomyslnie naprawiono przedmiot!", true);
    }
    if (args.length != 1) {
      return Utils.sendMsg(p, "&cUzycie: /repair <all/armor>", true);
    }
    if (args[0].equalsIgnoreCase("all"))
    {
      repair(p, p.getInventory().getContents());
      repair(p, p.getInventory().getArmorContents());
      return Utils.sendMsg(p, "&cPomyslnie naprawiono wszystkie przedmioty!", true);
    }
    if (args[0].equalsIgnoreCase("armor"))
    {
      repair(p, p.getInventory().getArmorContents());
      return Utils.sendMsg(p, "&cPomyslnie naprawiono cala zbroje!", true);
    }
    return Utils.sendMsg(p, "&cUzycie: /repair <all/armor>", true);
  }
  
  private void repair(Player player, ItemStack item)
  {
    if (item.getType().equals(Material.AIR)) {
      return;
    }
    item.setDurability((short)0);
    player.updateInventory();
  }
  
  private void repair(Player player, ItemStack[] items)
  {
    for (ItemStack item : items) {
      if ((item != null) && 
        (!item.getType().equals(Material.AIR))) {
        repair(player, item);
      }
    }
  }
}
