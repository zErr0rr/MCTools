package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Mute;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.MuteManager;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class UnMuteCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public UnMuteCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("unmute").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.unmute")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.unmute)", true);
    }
    if (args.length < 1) {
      return Utils.sendMsg(sender, "&cUzycie: /unmute <gracz>", true);
    }
    User u = UserManager.getUser(args[0]);
    if (u == null) {
      return Utils.sendMsg(sender, "&cGracza o podanym nicku nie ma w bazie danych!", true);
    }
    Mute b = MuteManager.getMute(u);
    if (b == null) {
      return Utils.sendMsg(sender, "&cGracza " + u.getLastNick() + " nie ma mute!", true);
    }
    MuteManager.deleteMute(b);
    return Utils.sendMsg(Utils.getOnlinePlayers(), "&cGracz &c" + u.getLastNick() + " &czostal odmutowany przez &c" + sender.getName() + "&c!", true);
  }
}
