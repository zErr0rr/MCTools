package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Mute;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.MuteManager;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class MuteCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public MuteCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("mute").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!sender.hasPermission("tobiasz.tools.mute")) {
      return Utils.sendMsg(sender, "&cNie masz uprawnien.  &c(tobiasz.tools.mute)", true);
    }
    if (args.length < 2) {
      return Utils.sendMsg(sender, "&cUzycie: /mute <gracz> <czas> <powod>", true);
    }
    User u = UserManager.getUser(args[0]);
    if (u == null) {
      return Utils.sendMsg(sender, "&cGracza o podanym nicku nie ma w bazie danych!", true);
    }
    if (sender.getName().equalsIgnoreCase(u.getLastNick())) {
      return Utils.sendMsg(sender, "&cNie mozesz zmutowac sam siebie!", true);
    }
    long time = Utils.parseDateDiff(args[1], true);
    Mute b = MuteManager.getMute(u);
    if (b != null) {
      return Utils.sendMsg(sender, "&cGracz " + u.getLastNick() + " ma juz mute!", true);
    }
    String reason = "Administrator ma zawsze racje!";
    if (args.length > 2) {
      reason = StringUtils.join(args, " ", 2, args.length);
    }
    Mute mute = MuteManager.createMute(u.getUuid(), reason, sender.getName(), time);
    return Utils.sendMsg(Utils.getOnlinePlayers(), "&cGracz " + u.getLastNick() + " czostal zmutowany przez &c" + mute.getAdmin() + " &cdo &c" + Utils.getDate(time) + "&c! Powod: &c" + mute.getReason() + "&c!", true);
  }
}
