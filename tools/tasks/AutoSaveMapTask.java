package com.tobiasz.tools.tasks;

import com.tobiasz.tools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSaveMapTask
  extends BukkitRunnable
{
  public void run()
  {
    for (Player player  ) {
      Utils.sendMsg(player, "&fTrwa zapisywanie mapy...", true);
    }
    Bukkit.savePlayers();
    for (World world : Bukkit.getWorlds()) {
      world.save();
    }
    for (Player player : Bukkit.getOnlinePlayers()) {
      Utils.sendMsg(player, "&fZapisywanie mapy zostalo zakonczone!", true);
    }
  }
}
