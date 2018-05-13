package com.tobiasz.tools.managers;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Ban;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.database.MySQL;
import com.tobiasz.tools.utils.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class BanManager
{
  private static HashMap<UUID, Ban> bans = new HashMap();
  private static ToolsPlugin plugin;
  
  public static Ban createBan(UUID uuid, String reason, String admin, long expireTime)
  {
    Ban b = new Ban(uuid, reason, admin, expireTime);
    bans.put(uuid, b);
    OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
    if (op.isOnline())
    {
      String kick = " &c Twoje Konto Jest Zablokowane przez" + b.getAdmin() + "&c, dnia: &c" + Utils.getDate(b.getCreateTime()) + "&c z powodem" + b.getReason() + "&6.";
      if (b.getExpireTime() > 0L) {
        kick = String.valueOf(String.valueOf(kick)) + "\n\n &9࿃  &6Wygasa: &a" + Utils.getDate(b.getExpireTime()) + "&6!";
      }
      op.getPlayer().kickPlayer(Utils.fixColor(kick));
    }
    return b;
  }
  
  public static void deleteBan(Ban b)
  {
    b.delete();
    bans.remove(b.getUuid());
  }
  
  public static Ban getBan(User paramUser)
  {
    return (Ban)bans.get(paramUser.getUuid());
  }
  
  public static Ban getBan(UUID paramUuid)
  {
    return (Ban)bans.get(paramUuid);
  }
  
  public static HashMap<UUID, Ban> getBans()
  {
    return bans;
  }
  
  public static void setTools(ToolsPlugin plugin)
  {
    plugin = plugin;
  }
  
  public static void setup()
  {
    ResultSet rs = plugin.getMysql().query("SELECT * FROM `tools_bans`");
    try
    {
      while (rs.next())
      {
        Ban b = new Ban(rs);
        bans.put(b.getUuid(), b);
      }
      plugin.getLogger().info("Loaded " + bans.size() + " bans!");
    }
    catch (SQLException e)
    {
      plugin.getLogger().warning("An error occurred while loading bans!Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
