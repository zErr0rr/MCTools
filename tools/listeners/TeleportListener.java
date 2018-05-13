package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.managers.TeleportManager;
import com.tobiasz.tools.utils.Utils;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;

public class TeleportListener
  implements Listener
{
  public ToolsPlugin plugin;
  
  public TeleportListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void onPlayerDamage(EntityDamageEvent event)
  {
    if ((event.getEntity() instanceof Player))
    {
      Player player = (Player)event.getEntity();
      if (TeleportManager.getPlayerTeleportLocation().get(player) != null)
      {
        ((BukkitTask)TeleportManager.getPlayerTeleportLocation().remove(player)).cancel();
        Utils.sendMsg(player, "&cTeleportacja zostala anulowana!", true);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void onPlayerMove(PlayerMoveEvent event)
  {
    Location from = event.getFrom();
    Location to = event.getTo();
    if ((from.getBlockX() != to.getBlockX()) || (from.getBlockY() != to.getBlockY()) || (from.getBlockZ() != to.getBlockZ()) || (from.getWorld() != to.getWorld()))
    {
      Player player = event.getPlayer();
      if (TeleportManager.getPlayerTeleportLocation().get(player) != null)
      {
        ((BukkitTask)TeleportManager.getPlayerTeleportLocation().remove(player)).cancel();
        Utils.sendMsg(player, "&cTeleportacja zostala anulowana!", true);
      }
    }
  }
}
