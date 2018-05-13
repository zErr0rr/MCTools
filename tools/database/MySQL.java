package com.tobiasz.tools.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MySQL
  implements Database
{
  private final String host;
  private final String name;
  private final String pass;
  private final JavaPlugin plugin;
  private final int port;
  private final String prefix;
  private final LinkedList<String> queries;
  private final String user;
  private Connection conn;
  private Thread t;
  
  public MySQL(JavaPlugin plugin)
  {
    this.plugin = plugin;
    this.queries = new LinkedList();
    this.host = plugin.getConfig().getString("mysql.host");
    this.port = plugin.getConfig().getInt("mysql.port");
    this.user = plugin.getConfig().getString("mysql.username");
    this.pass = plugin.getConfig().getString("mysql.password");
    this.name = plugin.getConfig().getString("mysql.database");
    this.prefix = plugin.getConfig().getString("mysql.prefix");
    connect();
    (this.t = new Thread(this, "MySQL Thread")).start();
  }
  
  public void connect()
  {
    long start = System.currentTimeMillis();
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.name, this.user, this.pass);
      this.plugin.getLogger().info("Nawiazano polaczenie z serwerem MySQL!");
      this.plugin.getLogger().info("Polaczenie trwalo " + (System.currentTimeMillis() - start) + "ms!");
    }
    catch (ClassNotFoundException e)
    {
      this.plugin.getLogger().warning("Nie znaleziono sterownika JDBC!");
      this.plugin.getLogger().warning("Blad: " + e.getMessage());
      e.printStackTrace();
    }
    catch (SQLException e2)
    {
      this.plugin.getLogger().warning("Nie mozna nawiazac polaczenia z serwerem MySQL!");
      this.plugin.getLogger().warning("Blad: " + e2.getMessage());
      e2.printStackTrace();
    }
  }
  
  public void disconnect()
  {
    if (this.conn != null) {
      try
      {
        this.conn.close();
      }
      catch (SQLException e)
      {
        this.plugin.getLogger().warning("Nie mozna zamknac polaczenia z serwerem MySQL!");
        this.plugin.getLogger().warning("Blad: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }
  
  public boolean isConnected()
  {
    try
    {
      return (this.conn.isClosed()) || (this.conn == null);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public ResultSet query(String query)
  {
    try
    {
      return this.conn.createStatement().executeQuery(query.replace("{P}", this.prefix));
    }
    catch (SQLException e)
    {
      this.plugin.getLogger().warning("Wystapil blad z zapytaniem '" + query.replace("{P}", this.prefix) + "'!");
      this.plugin.getLogger().warning("Blad: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }
  
  public void reconnect()
  {
    try
    {
      if (this.conn.isClosed()) {
        connect();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void run()
  {
    try
    {
      for (;;)
      {
        Thread.sleep(30000L);
        if (this.conn.isClosed()) {
          this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.name, this.user, this.pass);
        }
        if (this.queries.size() > 0)
        {
          List<String> list = new ArrayList();
          synchronized (this.queries)
          {
            list.addAll(this.queries);
            this.queries.clear();
          }
          for (String query : list) {
            updateNow(query);
          }
        }
        else
        {
          Statement s = this.conn.createStatement();
          s.executeQuery("DO 1");
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void update(String update)
  {
    synchronized (this.queries)
    {
      this.queries.add(update);
    }
  }
  
  public ResultSet updateNow(String update)
  {
    try
    {
      Statement stmt = this.conn.createStatement();
      stmt.executeUpdate(update.replace("{P}", this.prefix), 1);
      return stmt.getGeneratedKeys();
    }
    catch (SQLException e)
    {
      this.plugin.getLogger().warning("Wystapil blad z zapytaniem '" + update.replace("{P}", this.prefix) + "'!");
      this.plugin.getLogger().warning("Blad: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }
  
  public Connection getConn()
  {
    return this.conn;
  }
  
  public void setConn(Connection conn)
  {
    this.conn = conn;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getPass()
  {
    return this.pass;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public String getPrefix()
  {
    return this.prefix;
  }
  
  public LinkedList<String> getQueries()
  {
    return this.queries;
  }
  
  public Thread getT()
  {
    return this.t;
  }
  
  public void setT(Thread t)
  {
    this.t = t;
  }
  
  public String getUser()
  {
    return this.user;
  }
}
