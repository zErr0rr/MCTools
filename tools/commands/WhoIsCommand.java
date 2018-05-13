package com.tobiasz.tools.commands;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Ban;
import com.tobiasz.tools.data.Mute;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.BanManager;
import com.tobiasz.tools.managers.MuteManager;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class WhoIsCommand
  implements CommandExecutor
{
  private ToolsPlugin plugin;
  
  public WhoIsCommand(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getCommand("whois").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player p = (Player)sender;
    if (!p.hasPermission("tobiasz.tools.whois")) {
      return Utils.sendMsg(p, "&cNie masz uprawnien.  &c(tobiasz.tools.whois)", true);
    }
    if (args.length != 1) {
      return Utils.sendMsg(p, "&cUzycie: /whois <gracz>", true);
    }
    String nick = args[0];
    User u = UserManager.getUser(nick);
    if (u == null) {
      return Utils.sendMsg(p, "&cGracza o podanym nicku nie ma w bazie danych!", true);
    }
    String online = Utils.fixColor("&cnie");
    if (Bukkit.getPlayer(nick) != null) {
      online = Utils.fixColor("&ctak");
    }
    String op = Utils.fixColor("&cnie");
    OfflinePlayer o = u.getOfflinePlayer();
    if (o.isOp()) {
      op = Utils.fixColor("&ctak");
    }
    String god = Utils.fixColor("&cwylaczony");
    if (u.isGod()) {
      god = Utils.fixColor("&cwlaczony");
    }
    String gamemode = Utils.fixColor("&a" + u.getGamemode().name().toLowerCase());
    String fly = Utils.fixColor("&cwylaczony");
    if (u.isFly()) {
      fly = Utils.fixColor("&cwlaczony");
    }
    String ip = Utils.fixColor("&c" + u.getLastIP());
    String banned = Utils.fixColor("&cnie");
    Ban b = BanManager.getBan(u);
    if (b != null)
    {
      banned = Utils.fixColor("&2" + b.getReason());
      banned = String.valueOf(banned) + " (do " + Utils.getTime(b.getExpireTime()) + ")";
    }
    String mutted = Utils.fixColor("&4nie");
    Mute m = MuteManager.getMute(u);
    if (m != null)
    {
      mutted = Utils.fixColor("&2" + m.getReason());
      mutted = String.valueOf(mutted) + " (do " + Utils.getTime(m.getExpireTime()) + ")";
    }
    Utils.sendMsg(p, "&8### &f" + u.getLastNick() + " &8###", false);
    Utils.sendMsg(p, " &4Online: " + online, false);
    Utils.sendMsg(p, " &4IP: " + ip, false);
    Utils.sendMsg(p, " &4OP: " + op, false);
    Utils.sendMsg(p, " &4Gamemode: " + gamemode, false);
    Utils.sendMsg(p, " &4Tryb latania: " + fly, false);
    Utils.sendMsg(p, " &4Tryb god'a: " + god, false);
    Utils.sendMsg(p, " &4Zbanowany: " + banned, false);
    Utils.sendMsg(p, " &4Wyciszony: " + mutted, false);
    return true;
  }
}
