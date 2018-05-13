package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.plugin.PluginManager;

public class BugListener
  implements Listener
{
  public ToolsPlugin plugin;
  
  public BugListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler(priority=EventPriority.LOW)
  public void onPortal(EntityPortalEvent event)
  {
    if ((event.getEntityType().equals(EntityType.EXPERIENCE_ORB)) || (event.getEntityType().equals(EntityType.DROPPED_ITEM))) {
      event.setCancelled(true);
    }
  }
}
