package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;

public class RespawnListener
  implements Listener
{
  public ToolsPlugin plugin;
  
  public RespawnListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onLogin(PlayerRespawnEvent e)
  {
    e.setRespawnLocation(this.plugin.getSpawn());
  }
}
