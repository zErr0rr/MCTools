package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Ban;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.BanManager;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class UnBanCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public UnBanCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("unban").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.unban")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &7(tobiasz.tools.unban)", true);
    }
    if (args.length < 1) {
      return Utils.sendMsg(sender, "&cUzycie: /unban <gracz>", true);
    }
    User u = UserManager.getUser(args[0]);
    if (u == null) {
      return Utils.sendMsg(sender, "&cGracza o podanym nicku nie ma w bazie danych!", true);
    }
    Ban b = BanManager.getBan(u);
    if (b == null) {
      return Utils.sendMsg(sender, "&cGracza " + u.getLastNick() + " nie ma bana!", true);
    }
    BanManager.deleteBan(b);
    return Utils.sendMsg(Utils.getOnlinePlayers(), "&cGracz &c" + u.getLastNick() + " &czostal odbanowany przez &c" + sender.getName() + "&c!", true);
  }
}
