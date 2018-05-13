package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;

public class ServerListPingListener
  implements Listener
{
  public ToolsPlugin plugin;
  
  public ServerListPingListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler
  public void onServerPing(ServerListPingEvent event)
  {
    event.setMotd(this.plugin.motd.replace("$n", "/n"));
    event.setMaxPlayers(this.plugin.slots);
  }
}
