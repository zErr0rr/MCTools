package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Ban;
import com.tobiasz.tools.data.BanIP;
import com.tobiasz.tools.managers.BanIPManager;
import com.tobiasz.tools.managers.BanManager;
import com.tobiasz.tools.utils.Utils;
import java.net.InetAddress;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.PluginManager;

public class LoginListener
  implements Listener
{
  public ToolsPlugin plugin;
  
  public LoginListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onLogin(PlayerLoginEvent e)
  {
    BanIP banIp = BanIPManager.getBanIP(e.getAddress().getHostAddress());
    Ban ban = BanManager.getBan(e.getPlayer().getUniqueId());
    if (banIp != null)
    {
      String kick = " &c Twoje IP jest Zablokowane przez administratora" + banIp.getAdmin() + "&c dnia" + Utils.getDate(banIp.getCreateTime()) + "&c powod" + banIp.getReason() + "&6.";
      if (banIp.getExpireTime() == 0L)
      {
        e.setKickMessage(ChatColor.translateAlternateColorCodes('&', kick));
        e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        return;
      }
      if (banIp.getExpireTime() > 0L)
      {
        if (banIp.getExpireTime() <= System.currentTimeMillis())
        {
          BanIPManager.deleteBan(banIp);
          return;
        }
        kick = String.valueOf(String.valueOf(kick)) + "\n\n &9࿃ &6Wygasa: &a" + Utils.getDate(banIp.getExpireTime()) + "&6!";
        e.setKickMessage(ChatColor.translateAlternateColorCodes('&', kick));
        e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
      }
    }
    if (ban != null)
    {
      String kick = " &c twoje konto jest zbanowane przez" + ban.getAdmin() + " dnia" + Utils.getDate(ban.getCreateTime()) + "&c powod" + ban.getReason() + "&6.";
      if (ban.getExpireTime() == 0L)
      {
        e.setKickMessage(ChatColor.translateAlternateColorCodes('&', kick));
        e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        return;
      }
      if (ban.getExpireTime() > 0L)
      {
        if (ban.getExpireTime() <= System.currentTimeMillis())
        {
          BanIPManager.deleteBan(banIp);
          return;
        }
        kick = String.valueOf(String.valueOf(kick)) + "\n\n &9࿃ &6Wygasa: &a" + Utils.getDate(ban.getExpireTime()) + "&6!";
        e.setKickMessage(ChatColor.translateAlternateColorCodes('&', kick));
        e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
      }
    }
    if (e.getResult().equals(PlayerLoginEvent.Result.KICK_FULL))
    {
      if (!e.getPlayer().hasPermission("dgtools.join.full"))
      {
        e.setKickMessage(Utils.fixColor("&6Serwer jest pelny! Zakup range VIP aby moc wejsc!"));
        return;
      }
      e.allow();
    }
  }
}
