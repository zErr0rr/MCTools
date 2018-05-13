package com.tobiasz.tools.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils
{
  public static int calculate(String s)
  {
    int i = 1;
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("js");
    Number n = Integer.valueOf(1);
    try
    {
      n = (Number)engine.eval(s);
    }
    catch (ScriptException e)
    {
      e.printStackTrace();
    }
    i = n.intValue();
    return i;
  }
  
  public static boolean checkItem(ItemStack i1, ItemStack i2)
  {
    return (i1.getType().equals(i2.getType())) && (i1.hasItemMeta() == i2.hasItemMeta()) && (i1.getItemMeta().getDisplayName() != null) && (i2.getItemMeta().getDisplayName() != null) && (i1.getItemMeta().getDisplayName().equalsIgnoreCase(i2.getItemMeta().getDisplayName()));
  }
  
  public static boolean containsIgnoreCase(String[] array, String element)
  {
    for (String s : array) {
      if (s.equalsIgnoreCase(element)) {
        return true;
      }
    }
    return false;
  }
  
  public static void copy(InputStream in, File file)
  {
    try
    {
      OutputStream out = new FileOutputStream(file);
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      out.close();
      in.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static List<String> fixColor(List<String> strings)
  {
    List<String> colors = new ArrayList();
    for (String s : strings) {
      colors.add(fixColor(s));
    }
    return colors;
  }
  
  public static String fixColor(String string)
  {
    return ChatColor.translateAlternateColorCodes('&', string);
  }
  
  public static String[] fixColor(String[] strings)
  {
    for (int i = 0; i < strings.length; i++) {
      strings[i] = fixColor(strings[i]);
    }
    return strings;
  }
  
  public static boolean getBoolean(String s)
  {
    if ((!"true".equalsIgnoreCase(s)) && (!"tak".equalsIgnoreCase(s)) && (!"t".equalsIgnoreCase(s)) && (!"1".equalsIgnoreCase(s)) && (!"yes".equalsIgnoreCase(s)) && (!"y".equalsIgnoreCase(s))) {
      break label106;
    }
    label106:
    return (!"false".equalsIgnoreCase(s)) && (!"nie".equalsIgnoreCase(s)) && (!"n".equalsIgnoreCase(s)) && (!"0".equalsIgnoreCase(s)) && ("no".equalsIgnoreCase(s));
  }
  
  public static Player getDamager(EntityDamageByEntityEvent event)
  {
    Entity damager = event.getDamager();
    if ((damager instanceof Player)) {
      return (Player)damager;
    }
    if ((damager instanceof Projectile))
    {
      Projectile p = (Projectile)damager;
      if ((p.getShooter() instanceof Player)) {
        return (Player)p.getShooter();
      }
    }
    return null;
  }
  
  public static String getDate(long time)
  {
    return new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(time));
  }
  
  public static String getDurationBreakdown(long millis)
  {
    if (millis == 0L) {
      return "0";
    }
    long days = TimeUnit.MILLISECONDS.toDays(millis);
    if (days > 0L) {
      millis -= TimeUnit.DAYS.toMillis(days);
    }
    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    if (hours > 0L) {
      millis -= TimeUnit.HOURS.toMillis(hours);
    }
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    if (minutes > 0L) {
      millis -= TimeUnit.MINUTES.toMillis(minutes);
    }
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
    if (seconds > 0L) {
      millis -= TimeUnit.SECONDS.toMillis(seconds);
    }
    StringBuilder sb = new StringBuilder();
    if (days > 0L)
    {
      sb.append(days);
      long i = days % 10L;
      if (i == 1L) {
        sb.append(" dzien ");
      } else {
        sb.append(" dni ");
      }
    }
    if (hours > 0L)
    {
      sb.append(hours);
      long i = hours % 10L;
      if (i == 1L) {
        sb.append(" godzine ");
      } else if (i < 5L) {
        sb.append(" godziny ");
      } else {
        sb.append(" godzin ");
      }
    }
    if (minutes > 0L)
    {
      sb.append(minutes);
      long i = minutes % 10L;
      if (i == 1L) {
        sb.append(" minute ");
      } else if (i < 5L) {
        sb.append(" minuty ");
      } else {
        sb.append(" minut ");
      }
    }
    if (seconds > 0L)
    {
      sb.append(seconds);
      long i = seconds % 10L;
      if (i == 1L) {
        sb.append(" sekunde");
      } else if (i < 5L) {
        sb.append(" sekundy");
      } else {
        sb.append(" sekund");
      }
    }
    return sb.toString();
  }
  
  public static Location getLocation(String world, double x, double y, double z, float yaw, float pitch)
  {
    return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
  }
  
  public static Location getLocation(String world, int x, int y, int z)
  {
    return new Location(Bukkit.getWorld(world), x, y, z);
  }
  
  public static Collection<Player> getOnlinePlayers()
  {
    Collection<Player> players = new ArrayList();
    players.addAll(Arrays.asList(Bukkit.getOnlinePlayers()));
    return players;
  }
  
  public static String getTime(long time)
  {
    return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
  }
  
  public static void giveItems(Player p, ItemStack... items)
  {
    Inventory i = p.getInventory();
    HashMap<Integer, ItemStack> notStored = i.addItem(items);
    for (Map.Entry<Integer, ItemStack> e : notStored.entrySet()) {
      p.getWorld().dropItemNaturally(p.getLocation(), (ItemStack)e.getValue());
    }
  }
  
  public static boolean isAlphaNumeric(String s)
  {
    return s.matches("^[a-zA-Z0-9_]*$");
  }
  
  public static boolean isFloat(String string)
  {
    return Pattern.matches("([0-9]*)\\.([0-9]*)", string);
  }
  
  public static boolean isInteger(String string)
  {
    return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
  }
  
  public static long parseDateDiff(String time, boolean future)
  {
    try
    {
      Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
      Matcher m = timePattern.matcher(time);
      int years = 0;
      int months = 0;
      int weeks = 0;
      int days = 0;
      int hours = 0;
      int minutes = 0;
      int seconds = 0;
      boolean found = false;
      while (m.find()) {
        if ((m.group() != null) && (!m.group().isEmpty()))
        {
          for (int i = 0; i < m.groupCount(); i++) {
            if ((m.group(i) != null) && (!m.group(i).isEmpty()))
            {
              found = true;
              break;
            }
          }
          if (found)
          {
            if ((m.group(1) != null) && (!m.group(1).isEmpty())) {
              years = Integer.parseInt(m.group(1));
            }
            if ((m.group(2) != null) && (!m.group(2).isEmpty())) {
              months = Integer.parseInt(m.group(2));
            }
            if ((m.group(3) != null) && (!m.group(3).isEmpty())) {
              weeks = Integer.parseInt(m.group(3));
            }
            if ((m.group(4) != null) && (!m.group(4).isEmpty())) {
              days = Integer.parseInt(m.group(4));
            }
            if ((m.group(5) != null) && (!m.group(5).isEmpty())) {
              hours = Integer.parseInt(m.group(5));
            }
            if ((m.group(6) != null) && (!m.group(6).isEmpty())) {
              minutes = Integer.parseInt(m.group(6));
            }
            if (m.group(7) != null) {
              if (!m.group(7).isEmpty()) {
                seconds = Integer.parseInt(m.group(7));
              }
            }
          }
        }
      }
      if (!found) {
        return -1L;
      }
      Calendar c = new GregorianCalendar();
      if (years > 0) {
        c.add(1, years * (future ? 1 : -1));
      }
      if (months > 0) {
        c.add(2, months * (future ? 1 : -1));
      }
      if (weeks > 0) {
        c.add(3, weeks * (future ? 1 : -1));
      }
      if (days > 0) {
        c.add(5, days * (future ? 1 : -1));
      }
      if (hours > 0) {
        c.add(11, hours * (future ? 1 : -1));
      }
      if (minutes > 0) {
        c.add(12, minutes * (future ? 1 : -1));
      }
      if (seconds > 0) {
        c.add(13, seconds * (future ? 1 : -1));
      }
      Calendar max = new GregorianCalendar();
      max.add(1, 10);
      if (c.after(max)) {
        return max.getTimeInMillis();
      }
      return c.getTimeInMillis();
    }
    catch (Exception ex) {}
    return -1L;
  }
  
  public static String secondsToString(int seconds)
  {
    if (seconds == 0) {
      return "nigdy";
    }
    LinkedHashMap<Integer, String> values = new LinkedHashMap(6);
    values.put(Integer.valueOf(31104000), "y");
    values.put(Integer.valueOf(2592000), "msc");
    values.put(Integer.valueOf(86400), "d");
    values.put(Integer.valueOf(3600), "h");
    values.put(Integer.valueOf(60), "min");
    values.put(Integer.valueOf(1), "s");
    String[] v = new String[6];
    int i = 0;
    for (Map.Entry<Integer, String> e : values.entrySet())
    {
      int iDiv = seconds / ((Integer)e.getKey()).intValue();
      if (iDiv >= 1)
      {
        int x = (int)Math.floor(iDiv);
        v[i] = (String.valueOf(x) + (String)e.getValue());
        seconds -= x * ((Integer)e.getKey()).intValue();
      }
      i++;
    }
    return StringUtils.join(v, " ");
  }
  
  public static boolean sendMsg(Collection<? extends CommandSender> senders, String message, boolean prefix)
  {
    for (CommandSender sender : senders) {
      sendMsg(sender, message, null, prefix);
    }
    return true;
  }
  
  public static boolean sendMsg(Collection<? extends CommandSender> senders, String message, String permission, boolean prefix)
  {
    sendMsg(Bukkit.getConsoleSender(), message, permission, prefix);
    for (CommandSender sender : senders) {
      sendMsg(sender, message, permission, prefix);
    }
    return true;
  }
  
  public static boolean sendMsg(CommandSender sender, String message, boolean prefix)
  {
    return sendMsg(sender, message, null, prefix);
  }
  
  public static boolean sendMsg(CommandSender sender, String message, String permission, boolean prefix)
  {
    if ((permission != null) && (!sender.hasPermission(permission))) {
      sender.sendMessage(fixColor(String.valueOf(prefix ? " &9࿃ " : "") + message));
    }
    sender.sendMessage(fixColor(String.valueOf(prefix ? " &9࿃ " : "") + message));
    return true;
  }
}
