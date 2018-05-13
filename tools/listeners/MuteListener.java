package com.tobiasz.tools.listeners;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.data.Mute;
import com.tobiasz.tools.managers.MuteManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;

public class MuteListener
  implements Listener
{
  ToolsPlugin plugin;
  
  public MuteListener(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler
  public void onPlayerChat(AsyncPlayerChatEvent event)
  {
    Player player = event.getPlayer();
    Mute mute = MuteManager.getMute(event.getPlayer().getUniqueId());
    if (mute != null)
    {
      if (mute.getExpireTime() == 0L)
      {
        Date date = new Date(MuteManager.getMute(player.getUniqueId()).getExpireTime());
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeShow = dt.format(date);
        player.sendMessage("Jestes Wyciszony Do " + timeShow + "Powod" + MuteManager.getMute(player.getUniqueId()).getReason());
        return;
      }
      if (mute.getExpireTime() != 0L)
      {
        if (mute.getExpireTime() <= System.currentTimeMillis())
        {
          MuteManager.deleteMute(mute);
          return;
        }
        Date date = new Date(MuteManager.getMute(player.getUniqueId()).getExpireTime());
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeShow = dt.format(date);
        player.sendMessage("Zostales wyciszony do " + timeShow + "owod" + MuteManager.getMute(player.getUniqueId()).getReason());
      }
    }
  }
}
