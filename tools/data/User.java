package com.tobiasz.tools.data;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.database.MySQL;
import com.tobiasz.tools.utils.Utils;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class User
{
  private static ToolsPlugin plugin;
  private String firstIP;
  private long firstJoin;
  private boolean fly;
  private GameMode gamemode;
  private boolean god;
  private Location homeLocation;
  private String lastIP;
  private long lastJoin;
  private Location lastLocation;
  private String lastNick;
  private int timePlay;
  private UUID uuid;
  
  public User(Player p)
  {
    this.uuid = p.getUniqueId();
    this.gamemode = GameMode.SURVIVAL;
    this.fly = false;
    this.god = false;
    this.lastLocation = p.getLocation();
    this.homeLocation = p.getLocation();
    this.firstJoin = System.currentTimeMillis();
    this.lastJoin = System.currentTimeMillis();
    this.firstIP = p.getAddress().getHostString();
    this.lastIP = p.getAddress().getHostString();
    this.lastNick = p.getName();
    this.timePlay = 0;
    insert();
  }
  
  public User(ResultSet rs)
    throws SQLException
  {
    this.uuid = UUID.fromString(rs.getString("uuid"));
    this.gamemode = GameMode.getByValue(rs.getInt("gamemode"));
    this.fly = (rs.getInt("fly") == 1);
    this.god = (rs.getInt("god") == 1);
    this.lastLocation = Utils.getLocation(rs.getString("lastWorld"), rs.getInt("lastX"), rs.getInt("lastY"), rs.getInt("lastZ"));
    this.homeLocation = Utils.getLocation(rs.getString("homeWorld"), rs.getInt("homeX"), rs.getInt("homeY"), rs.getInt("homeZ"));
    this.firstJoin = rs.getLong("firstJoin");
    this.lastJoin = rs.getLong("lastJoin");
    this.firstIP = rs.getString("firstIP");
    this.lastIP = rs.getString("lastIP");
    this.lastNick = rs.getString("lastNick");
    this.timePlay = rs.getInt("timePlay");
  }
  
  public static void setTools(ToolsPlugin plugin)
  {
    plugin = plugin;
  }
  
  public void addTimePlay(int paramInt)
  {
    this.timePlay += paramInt;
  }
  
  public void delete()
  {
    plugin.getMysql().updateNow("DELETE FROM `tools_users` WHERE `uuid`='" + getUuid() + "'");
  }
  
  public OfflinePlayer getOfflinePlayer()
  {
    return Bukkit.getOfflinePlayer(this.uuid);
  }
  
  public void insert()
  {
    String query = "INSERT INTO `tools_users` SET `uuid` = '" + getUuid() + "', `gamemode` = '" + getGamemode().getValue() + "', `fly` = '" + (this.fly ? 1 : 0) + "', `god` = '" + (this.god ? 1 : 0) + "', `lastWorld` = '" + getLastLocation().getWorld().getName() + "', `lastX` = '" + getLastLocation().getBlockX() + "', `lastY` = '" + getLastLocation().getBlockY() + "', `lastZ` = '" + getLastLocation().getBlockZ() + "', `homeWorld` = '" + getHomeLocation().getWorld().getName() + "', `homeX` = '" + getHomeLocation().getBlockX() + "', `homeY` = '" + getHomeLocation().getBlockY() + "', `homeZ` = '" + getHomeLocation().getBlockZ() + "', `firstJoin` = '" + getFirstJoin() + "', `lastJoin` = '" + getLastJoin() + "', `firstIP` = '" + getFirstIP() + "', `lastIP` = '" + getLastIP() + "', `lastNick` = '" + getLastNick() + "', `timePlay` = '" + getTimePlay() + "'";
    plugin.getMysql().updateNow(query);
  }
  
  public void update(boolean now)
  {
    String query = "UPDATE `tools_users` SET `gamemode` = '" + getGamemode().getValue() + "', `fly` = '" + (this.fly ? 1 : 0) + "', `god` = '" + (this.god ? 1 : 0) + "', `lastWorld` = '" + getLastLocation().getWorld().getName() + "', `lastX` = '" + getLastLocation().getBlockX() + "', `lastY` = '" + getLastLocation().getBlockY() + "', `lastZ` = '" + getLastLocation().getBlockZ() + "', `homeWorld` = '" + getHomeLocation().getWorld().getName() + "', `homeX` = '" + getHomeLocation().getBlockX() + "', `homeY` = '" + getHomeLocation().getBlockY() + "', `homeZ` = '" + getHomeLocation().getBlockZ() + "', `firstJoin` = '" + getFirstJoin() + "', `lastJoin` = '" + getLastJoin() + "', `firstIP` = '" + getFirstIP() + "', `lastIP` = '" + getLastIP() + "', `lastNick` = '" + getLastNick() + "', `timePlay` = '" + getTimePlay() + "' WHERE `uuid` = '" + getUuid() + "'";
    if (now) {
      plugin.getMysql().updateNow(query);
    } else {
      plugin.getMysql().update(query);
    }
  }
  
  public String getFirstIP()
  {
    return this.firstIP;
  }
  
  public void setFirstIP(String firstIP)
  {
    this.firstIP = firstIP;
  }
  
  public long getFirstJoin()
  {
    return this.firstJoin;
  }
  
  public void setFirstJoin(long firstJoin)
  {
    this.firstJoin = firstJoin;
  }
  
  public boolean isFly()
  {
    return this.fly;
  }
  
  public void setFly(boolean fly)
  {
    this.fly = fly;
  }
  
  public GameMode getGamemode()
  {
    return this.gamemode;
  }
  
  public void setGamemode(GameMode gamemode)
  {
    this.gamemode = gamemode;
  }
  
  public boolean isGod()
  {
    return this.god;
  }
  
  public void setGod(boolean god)
  {
    this.god = god;
  }
  
  public Location getHomeLocation()
  {
    return this.homeLocation;
  }
  
  public void setHomeLocation(Location homeLocation)
  {
    this.homeLocation = homeLocation;
  }
  
  public String getLastIP()
  {
    return this.lastIP;
  }
  
  public void setLastIP(String lastIP)
  {
    this.lastIP = lastIP;
  }
  
  public long getLastJoin()
  {
    return this.lastJoin;
  }
  
  public void setLastJoin(long lastJoin)
  {
    this.lastJoin = lastJoin;
  }
  
  public Location getLastLocation()
  {
    return this.lastLocation;
  }
  
  public void setLastLocation(Location lastLocation)
  {
    this.lastLocation = lastLocation;
  }
  
  public String getLastNick()
  {
    return this.lastNick;
  }
  
  public void setLastNick(String lastNick)
  {
    this.lastNick = lastNick;
  }
  
  public int getTimePlay()
  {
    return this.timePlay;
  }
  
  public void setTimePlay(int timePlay)
  {
    this.timePlay = timePlay;
  }
  
  public UUID getUuid()
  {
    return this.uuid;
  }
  
  public void setUuid(UUID uuid)
  {
    this.uuid = uuid;
  }
}
