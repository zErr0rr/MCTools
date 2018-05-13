package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.managers.UserManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;

public class GodListener
  implements Listener
{
  public ToolsPlugin plugin;
  
  public GodListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler
  public void onDamage(EntityDamageByEntityEvent event)
  {
    if (!(event.getDamager() instanceof Player)) {
      return;
    }
    User user = UserManager.getUser((Player)event.getDamager());
    if (!user.isGod()) {
      return;
    }
    event.setCancelled(true);
  }
  
  @EventHandler
  public void onDamage2(EntityDamageEvent event)
  {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    User user = UserManager.getUser((Player)event.getEntity());
    if (!user.isGod()) {
      return;
    }
    event.setCancelled(true);
  }
}
