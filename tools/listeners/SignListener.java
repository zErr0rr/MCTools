package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.PluginManager;

public class SignListener
  implements Listener
{
  ToolsPlugin plugin;
  
  public SignListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler
  public void onSignColour(SignChangeEvent event)
  {
    Player p = event.getPlayer();
    if (!p.hasPermission("tobiasz.kolorowetabliczki")) {
      return;
    }
    for (int i = 0; i <= 3; i++) {
      event.setLine(i, Utils.fixColor(String.valueOf(event.getLine(i))));
    }
  }
}
