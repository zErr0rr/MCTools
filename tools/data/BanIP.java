package com.tobiasz.tools.data;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.database.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BanIP
{
  private static ToolsPlugin plugin;
  private String admin;
  private long createTime;
  private long expireTime;
  private int ID;
  private String ip;
  private String reason;
  
  public BanIP(ResultSet rs)
    throws SQLException
  {
    this.ID = rs.getInt("id");
    this.ip = rs.getString("ip");
    this.reason = rs.getString("reason");
    this.admin = rs.getString("admin");
    this.createTime = rs.getLong("createTime");
    this.expireTime = rs.getLong("expireTime");
  }
  
  public BanIP(String ip, String reason, String admin, long expireTime)
  {
    this.ID = -1;
    this.ip = ip;
    this.reason = reason;
    this.admin = admin;
    this.createTime = System.currentTimeMillis();
    this.expireTime = expireTime;
    insert();
  }
  
  public static void setTools(ToolsPlugin plugin)
  {
    plugin = plugin;
  }
  
  public void delete()
  {
    plugin.getMysql().updateNow("DELETE FROM `tools_ipbans` WHERE `ip` = '" + getIp() + "'");
  }
  
  public void insert()
  {
    String query = "INSERT INTO `tools_ipbans` SET `ip` = '" + getIp() + "', `admin` = '" + getAdmin() + "', `reason` = '" + getReason() + "', `createTime` = '" + getCreateTime() + "', `expireTime` = '" + getExpireTime() + "'";
    plugin.getMysql().updateNow(query);
  }
  
  public void update(boolean paramBoolean)
  {
    String query = "UPDATE `tools_ipbans` SET `admin` = '" + getAdmin() + "', `reason` = '" + getReason() + "', `createTime` = '" + getCreateTime() + "', `expireTime` = '" + getExpireTime() + " WHERE `ip` = '" + getIp() + "'";
    if (paramBoolean) {
      plugin.getMysql().updateNow(query);
    } else {
      plugin.getMysql().update(query);
    }
  }
  
  public String getAdmin()
  {
    return this.admin;
  }
  
  public void setAdmin(String admin)
  {
    this.admin = admin;
  }
  
  public long getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(long createTime)
  {
    this.createTime = createTime;
  }
  
  public long getExpireTime()
  {
    return this.expireTime;
  }
  
  public void setExpireTime(long expireTime)
  {
    this.expireTime = expireTime;
  }
  
  public int getID()
  {
    return this.ID;
  }
  
  public void setID(int ID)
  {
    this.ID = ID;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public String getReason()
  {
    return this.reason;
  }
  
  public void setReason(String reason)
  {
    this.reason = reason;
  }
}
