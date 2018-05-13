package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Ban;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.BanManager;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class BanCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public BanCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("ban").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.ban")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &7(tobiasz.tools.ban)", true);
    }
    if (args.length < 1) {
      return Utils.sendMsg(sender, "&cUzycie: /ban <gracz> <powod>", true);
    }
    User u = UserManager.getUser(args[0]);
    if (u == null) {
      return Utils.sendMsg(sender, "&cGracza o podanym nicku nie ma w bazie danych!", true);
    }
    if (sender.getName().equalsIgnoreCase(u.getLastNick())) {
      return Utils.sendMsg(sender, "&4Nie mozesz zbanowac sam siebie!", true);
    }
    Ban b = BanManager.getBan(u);
    if (b != null) {
      return Utils.sendMsg(sender, "&cGracz " + u.getLastNick() + " ma juz bana!", true);
    }
    String reason = "Administrator ma zawsze racje!";
    if (args.length > 1) {
      reason = StringUtils.join(args, " ", 1, args.length);
    }
    Ban ban = BanManager.createBan(u.getUuid(), reason, sender.getName(), 0L);
    return Utils.sendMsg(Utils.getOnlinePlayers(), "&4Gracz &4" + u.getLastNick() + " &4zostal zbanowany przez &4" + ban.getAdmin() + "&4! Powod: &4" + ban.getReason() + "&4!", true);
  }
}
