package com.tobiasz.tools.tasks;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.managers.AFKManager;
import com.tobiasz.tools.utils.Utils;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AFKTask
  extends BukkitRunnable
{
  public ToolsPlugin plugin;
  
  public AFKTask(ToolsPlugin plugin)
  {
    this.plugin = plugin;
  }
  
  public void run()
  {
    for (Player player  ) {
      if (!player.hasPermission("tobiasz.tools.afk")) {
        if (AFKManager.getAfkLocation().get(player) == null)
        {
          AFKManager.setNewLocation(player);
        }
        else if ((AFKManager.getAfkLastMove().get(player) == null) && (((Location)AFKManager.getAfkLocation().get(player)).equals(player.getLocation())))
        {
          AFKManager.setNewTime(player);
        }
        else if ((AFKManager.getAfkLastMove().get(player) != null) && (AFKManager.getAfkLocation().get(player) != null) && (((Location)AFKManager.getAfkLocation().get(player)).equals(player.getLocation())))
        {
          if ((System.currentTimeMillis() - ((Long)AFKManager.getAfkLastMove().get(player)).longValue()) / 1000L >= 300L)
          {
            AFKManager.getAfkLastMove().remove(player);
            AFKManager.getAfkLocation().remove(player);
            player.kickPlayer(Utils.fixColor(" &c Zostales Wyrzucony za bycie afk wiecej niz 5 minut !"));
          }
        }
        else
        {
          AFKManager.getAfkLastMove().remove(player);
          AFKManager.getAfkLocation().remove(player);
        }
      }
    }
  }
}
