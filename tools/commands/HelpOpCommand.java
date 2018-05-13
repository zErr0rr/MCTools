package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import java.util.WeakHashMap;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class HelpOpCommand
  implements CommandExecutor
{
  private final WeakHashMap<Player, Long> lastMessage;
  private ToolsPlugin plugin;
  
  public HelpOpCommand(ToolsPlugin plugin)
  {
    this.lastMessage = new WeakHashMap();
    this.plugin = plugin;
    this.plugin.getCommand("helpop").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.helpop")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.helpop)", true);
    }
    if (args.length <= 0) {
      return Utils.sendMsg(sender, "&cUzycie: /helpop <wiadomosc>", true);
    }
    String message = StringUtils.join(args, " ");
    if (!this.lastMessage.containsKey(p))
    {
      if (!p.hasPermission("tobiasz.tools.helpop.bypass")) {
        this.lastMessage.put(p, Long.valueOf(System.currentTimeMillis() + 60000L));
      }
      Utils.sendMsg(p, "&4[HelpOp] &8" + message, false);
      for (Player o : Utils.getOnlinePlayers()) {
        if (o.hasPermission("tobiasz.tools.helpop.getmessage")) {
          Utils.sendMsg(o, "&4[HelpOp - " + p.getName() + "] &a" + message, false);
        }
      }
      return true;
    }
    if (p.hasPermission("tobiasz.tools.helpop.wiadomosc"))
    {
      this.lastMessage.remove(p);
      return true;
    }
    long time = ((Long)this.lastMessage.get(p)).longValue();
    if (time < System.currentTimeMillis())
    {
      this.lastMessage.remove(p);
      return true;
    }
    long slow = (time - System.currentTimeMillis()) / 1000L;
    if (slow == 0L) {
      return true;
    }
    Utils.sendMsg(p, "&cNa helpop bedziesz mogl napisac dopiero za " + slow + " sekund!", true);
    return true;
  }
}
