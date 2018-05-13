package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.PluginManager;

public class WitherListener
  implements Listener
{
  public ToolsPlugin plugin;
  
  public WitherListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler
  public void onCreatureSpawn(CreatureSpawnEvent event)
  {
    if (!event.getEntityType().equals(EntityType.WITHER)) {
      return;
    }
    event.setCancelled(true);
  }
}
