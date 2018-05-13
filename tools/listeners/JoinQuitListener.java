package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.managers.UserManager;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

public class JoinQuitListener
  implements Listener
{
  public ToolsPlugin plugin;
  
  public JoinQuitListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onJoin(PlayerJoinEvent e)
  {
    e.setJoinMessage(null);
    UserManager.joinToGame(e.getPlayer());
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onKick(PlayerKickEvent e)
  {
    e.setLeaveMessage(null);
    UserManager.leaveFromGame(e.getPlayer());
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onQuit(PlayerQuitEvent e)
  {
    e.setQuitMessage(null);
    UserManager.leaveFromGame(e.getPlayer());
  }
}
