package com.tobiasz.tools.tasks;

import com.tobiasz.tools.ToolsPlugin;
import com.tobiasz.tools.utils.Utils;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoMessageTask
  extends BukkitRunnable
{
  private final List<String> messages;
  private final String prefix;
  private final String suffix;
  public ToolsPlugin plugin;
  private int index;
  
  public AutoMessageTask(ToolsPlugin plugin)
  {
    this.plugin = plugin;
    this.prefix = this.plugin.getConfig().getString("config.auto-msg.prefix");
    this.suffix = this.plugin.getConfig().getString("config.auto-msg.suffix");
    this.messages = this.plugin.getConfig().getStringList("config.auto-msg.messages");
    this.index = 0;
  }
  
  public void run()
  {
    if (this.index >= this.messages.size()) {
      this.index = 0;
    }
    for (Player player : Bukkit.getOnlinePlayers()) {
      Utils.sendMsg(player, String.valueOf(this.prefix) + this.suffix + (String)this.messages.get(this.index), false);
    }
    this.index += 1;
  }
}
