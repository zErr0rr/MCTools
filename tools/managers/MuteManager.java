package com.tobiasz.tools.managers;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Mute;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.database.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class MuteManager
{
  private static HashMap<UUID, Mute> mutes = new HashMap();
  private static ToolsPlugin plugin;
  
  public static Mute createMute(UUID uuid, String reason, String admin, long expireTime)
  {
    Mute m = new Mute(uuid, reason, admin, expireTime);
    mutes.put(uuid, m);
    return m;
  }
  
  public static void deleteMute(Mute m)
  {
    m.delete();
    mutes.remove(m.getUuid());
  }
  
  public static Mute getMute(User paramUser)
  {
    return (Mute)mutes.get(paramUser.getUuid());
  }
  
  public static Mute getMute(UUID paramUuid)
  {
    return (Mute)mutes.get(paramUuid);
  }
  
  public static HashMap<UUID, Mute> getMutes()
  {
    return mutes;
  }
  
  public static void setTools(ToolsPlugin plugin)
  {
    plugin = plugin;
  }
  
  public static void setup()
  {
    ResultSet rs = plugin.getMysql().query("SELECT * FROM `tools_mutes`");
    try
    {
      while (rs.next())
      {
        Mute m = new Mute(rs);
        mutes.put(m.getUuid(), m);
      }
      plugin.getLogger().info("Loaded " + mutes.size() + " mutes!");
    }
    catch (SQLException e)
    {
      plugin.getLogger().warning("An error occurred while loading bans!Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
