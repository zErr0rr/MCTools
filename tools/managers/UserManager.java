package com.tobiasz.tools.managers;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.database.MySQL;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class UserManager
{
  private static ToolsPlugin plugin;
  private static HashMap<UUID, User> users = new HashMap();
  
  public static User createUser(Player p)
  {
    User u = new User(p);
    users.put(p.getUniqueId(), u);
    return u;
  }
  
  public static User getUser(Player paramPlayer)
  {
    return (User)users.get(paramPlayer.getUniqueId());
  }
  
  public static User getUser(String paramString)
  {
    for (Map.Entry<UUID, User> e : users.entrySet()) {
      if (((User)e.getValue()).getLastNick().equalsIgnoreCase(paramString)) {
        return (User)e.getValue();
      }
    }
    return null;
  }
  
  public static User getUser(UUID paramUuid)
  {
    return (User)users.get(paramUuid);
  }
  
  public static HashMap<UUID, User> getUsers()
  {
    return users;
  }
  
  public static Collection<User> getUsers(String paramString)
  {
    Collection<User> col = new ArrayList();
    for (Map.Entry<UUID, User> e : users.entrySet()) {
      if ((((User)e.getValue()).getFirstIP().equalsIgnoreCase(paramString)) || (((User)e.getValue()).getLastIP().equalsIgnoreCase(paramString))) {
        col.add(e.getValue());
      }
    }
    return col;
  }
  
  public static void joinToGame(Player p)
  {
    User u = getUser(p);
    if (u == null)
    {
      createUser(p);
      return;
    }
    u.setLastIP(p.getAddress().getHostString());
    u.setLastJoin(System.currentTimeMillis());
    u.setLastNick(p.getName());
    p.setGameMode(u.getGamemode());
    p.setAllowFlight(u.isFly());
    p.teleport(u.getLastLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
  }
  
  public static void leaveFromGame(Player p)
  {
    User u = getUser(p);
    if (u == null)
    {
      plugin.getLogger().info("Dane uzytkownika '" + p.getName() + "' przepadly!");
      return;
    }
    u.addTimePlay((int)((System.currentTimeMillis() - u.getLastJoin()) / 1000L));
    u.setFly(p.getAllowFlight());
    u.setGamemode(p.getGameMode());
    u.setLastNick(p.getName());
    u.setLastLocation(p.getLocation());
    u.update(true);
  }
  
  public static void setTools(ToolsPlugin plugin)
  {
    plugin = plugin;
  }
  
  public static void setup()
  {
    ResultSet rs = plugin.getMysql().query("SELECT * FROM `tools_users`");
    try
    {
      while (rs.next())
      {
        User u = new User(rs);
        users.put(u.getUuid(), u);
      }
      plugin.getLogger().info("Loaded " + users.size() + " users!");
    }
    catch (SQLException e)
    {
      plugin.getLogger().warning("An error occurred while loading users!Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
