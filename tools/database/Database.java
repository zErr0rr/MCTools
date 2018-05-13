package com.tobiasz.tools.database;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract interface Database
  extends Runnable
{
  public abstract Connection getConn();
  
  public abstract void connect();
  
  public abstract void disconnect();
  
  public abstract void reconnect();
  
  public abstract boolean isConnected();
  
  public abstract ResultSet query(String paramString);
  
  public abstract void update(String paramString);
  
  public abstract ResultSet updateNow(String paramString);
}
