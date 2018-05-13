package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.managers.EnchantManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public EnchantCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("enchant").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.enchant")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &7(tobiasz.tools.enchant)", true);
    }
    if ((args.length < 1) || (args.length > 2)) {
      return Utils.sendMsg(p, "&cUzycie: /enchant <nazwa> <poziom>", true);
    }
    ItemStack item = p.getItemInHand();
    String enchantmentName = args[0];
    Enchantment enchant = EnchantManager.get(enchantmentName);
    if (enchant == null) {
      return Utils.sendMsg(p, "&cEnchant o podanej nazwie nie istnieje!", true);
    }
    int level = enchant.getMaxLevel();
    if (args.length == 2) {
      level = Integer.parseInt(args[1]);
    }
    item.addUnsafeEnchantment(enchant, level);
    return Utils.sendMsg(p, "&cEnchant &c" + enchant.getName().toLowerCase().replaceAll("_", " ") + " &c6zostal dodany do przedmiotu w Twojej rece!", true);
  }
}
