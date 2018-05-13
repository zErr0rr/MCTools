package com.tobiasz.tools.managers;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AFKManager
{
  private static HashMap<Player, Location> afkLocation = new HashMap();
  private static HashMap<Player, Long> afkLastMove = new HashMap();
  
  public static void setNewLocation(Player player)
  {
    afkLocation.put(player, player.getLocation());
  }
  
  public static void setNewTime(Player player)
  {
    afkLastMove.put(player, Long.valueOf(System.currentTimeMillis()));
  }
  
  public static HashMap<Player, Long> getAfkLastMove()
  {
    return afkLastMove;
  }
  
  public static HashMap<Player, Location> getAfkLocation()
  {
    return afkLocation;
  }
}
