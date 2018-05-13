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

public class TempBanCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public TempBanCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("tempban").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.tempban")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.tempban)", true);
    }
    if (args.length < 2) {
      return Utils.sendMsg(sender, "&cUzycie: /tempban <gracz> <czas> <powod>", true);
    }
    User u = UserManager.getUser(args[0]);
    if (u == null) {
      return Utils.sendMsg(sender, "&cGracza o podanym nicku nie ma w bazie danych!", true);
    }
    if (sender.getName().equalsIgnoreCase(u.getLastNick())) {
      return Utils.sendMsg(sender, "&cNie mozesz zbanowac sam siebie!", true);
    }
    long time = Utils.parseDateDiff(args[1], true);
    Ban b = BanManager.getBan(u);
    if (b != null) {
      return Utils.sendMsg(sender, "&cGracz " + u.getLastNick() + " ma juz bana!", true);
    }
    String reason = "Administrator ma zawsze racje!";
    if (args.length > 2) {
      reason = StringUtils.join(args, " ", 2, args.length);
    }
    Ban ban = BanManager.createBan(u.getUuid(), reason, sender.getName(), time);
    return Utils.sendMsg(Utils.getOnlinePlayers(), "&cGracz &c" + u.getLastNick() + " &czostal zbanowany przez &c" + ban.getAdmin() + " &cdo &c" + Utils.getDate(time) + "&c! Powod: &c" + ban.getReason() + "&c!", true);
  }
}
