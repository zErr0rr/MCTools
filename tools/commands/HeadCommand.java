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
import org.bukkit.inventory.meta.SkullMeta;

public class HeadCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public HeadCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("head").setExecutor(this);
  }
  
  private void giveHead(Player player, String owner)
  {
    ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    SkullMeta meta = (SkullMeta)item.getItemMeta();
    meta.setOwner(owner);
    meta.setDisplayName(Utils.fixColor("&f" + owner));
    item.setItemMeta(meta);
    player.getInventory().addItem(new ItemStack[] { item });
    player.updateInventory();
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.head")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.head)", true);
    }
    if (args.length == 0)
    {
      giveHead(p, p.getName());
      Utils.sendMsg(p, "&cOtrzymales swoja glowe!", true);
    }
    else
    {
      if (args.length != 1) {
        return Utils.sendMsg(p, "&cUzycie: /head <gracz>", true);
      }
      giveHead(p, args[0]);
      Utils.sendMsg(p, "&cOtrzymales glowe gracza &c" + args[0] + "&c!", true);
    }
    return true;
  }
}
