package com.tobiasz.tools.managers;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.BanIP;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.database.MySQL;
import com.tobiasz.tools.utils.Utils;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BanIPManager
{
  private static HashMap<String, BanIP> bansIp = new HashMap();
  private static ToolsPlugin plugin;
  
  public static BanIP createBan(String ip, String reason, String admin, long expireTime)
  {
    BanIP b = new BanIP(ip, reason, admin, expireTime);
    bansIp.put(ip, b);
    String kick = " &9࿃  &6Twoj adres IP zostal zbanowany!\n\n &9࿃ &6Zbanowano przez: &a" + b.getAdmin() + "&6, dnia: &a" + Utils.getDate(b.getCreateTime()) + "&6!\n &9࿃  &6Powod: &a" + b.getReason() + "&6.";
    if (b.getExpireTime() > 0L) {
      kick = String.valueOf(String.valueOf(kick)) + "\n\n &9࿃  &6Wygasa: &a" + Utils.getDate(b.getExpireTime()) + "&6!";
    }
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (ip.equalsIgnoreCase(p.getAddress().getHostString())) {
        p.kickPlayer(Utils.fixColor(kick));
      }
    }
    return b;
  }
  
  public static void deleteBan(BanIP b)
  {
    b.delete();
    bansIp.remove(b.getIp());
  }
  
  public static BanIP getBanIP(String ip)
  {
    return (BanIP)bansIp.get(ip);
  }
  
  public static BanIP getBanIP(User u)
  {
    if (bansIp.containsKey(u.getFirstIP())) {
      return (BanIP)bansIp.get(u.getFirstIP());
    }
    if (bansIp.containsKey(u.getLastIP())) {
      return (BanIP)bansIp.get(u.getLastIP());
    }
    return null;
  }
  
  public static HashMap<String, BanIP> getBansIp()
  {
    return bansIp;
  }
  
  public static void setTools(ToolsPlugin plugin)
  {
    plugin = plugin;
  }
  
  public static void setup()
  {
    ResultSet rs = plugin.getMysql().query("SELECT * FROM `tools_ipbans`");
    try
    {
      while (rs.next())
      {
        BanIP b = new BanIP(rs);
        bansIp.put(b.getIp(), b);
      }
      plugin.getLogger().info("Loaded " + bansIp.size() + " ip-bans!");
    }
    catch (SQLException e)
    {
      plugin.getLogger().warning("An error occurred while loading ip-bans!Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
