package com.tobiasz.tools;

import com.tobiasz.tools.commands.BanCommand;
import com.tobiasz.tools.commands.BanIPCommand;
import com.tobiasz.tools.commands.BroadcastCommand;
import com.tobiasz.tools.commands.EnchantCommand;
import com.tobiasz.tools.commands.FlyCommand;
import com.tobiasz.tools.commands.GamemodeCommand;
import com.tobiasz.tools.commands.GodCommand;
import com.tobiasz.tools.commands.HatCommand;
import com.tobiasz.tools.commands.HeadCommand;
import com.tobiasz.tools.commands.HealCommand;
import com.tobiasz.tools.commands.HelpOpCommand;
import com.tobiasz.tools.commands.HomeCommand;
import com.tobiasz.tools.commands.KickAllCommand;
import com.tobiasz.tools.commands.KickCommand;
import com.tobiasz.tools.commands.ListCommand;
import com.tobiasz.tools.commands.MotdCommand;
import com.tobiasz.tools.commands.MuteCommand;
import com.tobiasz.tools.commands.RepairCommand;
import com.tobiasz.tools.commands.SetHomeCommand;
import com.tobiasz.tools.commands.SetSpawnCommand;
import com.tobiasz.tools.commands.SlotCommand;
import com.tobiasz.tools.commands.SpawnCommand;
import com.tobiasz.tools.commands.SpeedCommand;
import com.tobiasz.tools.commands.TempBanCommand;
import com.tobiasz.tools.commands.TempBanIPCommand;
import com.tobiasz.tools.commands.TpCommand;
import com.tobiasz.tools.commands.TpaCommand;
import com.tobiasz.tools.commands.TpacceptCommand;
import com.tobiasz.tools.commands.TpdenyCommand;
import com.tobiasz.tools.commands.UnBanCommand;
import com.tobiasz.tools.commands.UnBanIPCommand;
import com.tobiasz.tools.commands.UnMuteCommand;
import com.tobiasz.tools.commands.WhoIsCommand;
import com.tobiasz.tools.data.Ban;
import com.tobiasz.tools.data.BanIP;
import com.tobiasz.tools.data.Mute;
import com.tobiasz.tools.data.User;
import com.tobiasz.tools.database.MySQL;
import com.tobiasz.tools.listeners.BugListener;
import com.tobiasz.tools.listeners.GodListener;
import com.tobiasz.tools.listeners.JoinQuitListener;
import com.tobiasz.tools.listeners.LoginListener;
import com.tobiasz.tools.listeners.RespawnListener;
import com.tobiasz.tools.listeners.ServerListPingListener;
import com.tobiasz.tools.listeners.SignListener;
import com.tobiasz.tools.listeners.TeleportListener;
import com.tobiasz.tools.listeners.WitherListener;
import com.tobiasz.tools.managers.BanIPManager;
import com.tobiasz.tools.managers.BanManager;
import com.tobiasz.tools.managers.MuteManager;
import com.tobiasz.tools.managers.TeleportManager;
import com.tobiasz.tools.managers.UserManager;
import com.tobiasz.tools.tasks.AFKTask;
import com.tobiasz.tools.tasks.AutoMessageTask;
import com.tobiasz.tools.tasks.AutoSaveMapTask;
import com.tobiasz.tools.utils.Utils;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class ToolsPlugin
  extends JavaPlugin
{
  public String motd;
  public int slots;
  private BukkitTask afkTask;
  private BukkitTask autoMessageTask;
  private BukkitTask autoSaveMapTask;
  private BanCommand banCommand;
  private BanIPCommand banIPCommand;
  private BroadcastCommand broadcastCommand;
  private BugListener bugListener;
  private EnchantCommand enchantCommand;
  private FlyCommand flyCommand;
  private GamemodeCommand gamemodeCommand;
  private GodCommand godCommand;
  private GodListener godListener;
  private HatCommand hatCommand;
  private HeadCommand headCommand;
  private HealCommand healCommand;
  private HelpOpCommand helpOpCommand;
  private HomeCommand homeCommand;
  private TempBanIPCommand tempBanIPCommand;
  private JoinQuitListener joinQuitListener;
  private KickAllCommand kickAllCommand;
  private KickCommand kickCommand;
  private ListCommand listCommand;
  private MotdCommand motdCommand;
  private MuteCommand muteCommand;
  private LoginListener loginListener;
  private MySQL mysql;
  private RepairCommand repairCommand;
  private UnMuteCommand unMuteCommand;
  private UnBanCommand unBanCommand;
  private UnBanIPCommand unBanIPCommand;
  private RespawnListener respawnListener;
  private SetHomeCommand setHomeCommand;
  private SetSpawnCommand setSpawnCommand;
  private SlotCommand slotCommand;
  private ServerListPingListener serverListPingListener;
  private SignListener signListener;
  private SpawnCommand spawnCommand;
  private SpeedCommand speedCommand;
  private TeleportListener teleportListener;
  private TempBanCommand tempBanCommand;
  private TpacceptCommand tpacceptCommand;
  private TpaCommand tpaCommand;
  private TpCommand tpCommand;
  private TpdenyCommand tpdenyCommand;
  private WhoIsCommand whoIsCommand;
  private WitherListener witherListener;
  
  public String getMotd()
  {
    return this.motd;
  }
  
  public int getSlots()
  {
    return this.slots;
  }
  
  public BukkitTask getAfkTask()
  {
    return this.afkTask;
  }
  
  public BukkitTask getAutoMessageTask()
  {
    return this.autoMessageTask;
  }
  
  public BukkitTask getAutoSaveMapTask()
  {
    return this.autoSaveMapTask;
  }
  
  public BanCommand getBanCommand()
  {
    return this.banCommand;
  }
  
  public BanIPCommand getBanIPCommand()
  {
    return this.banIPCommand;
  }
  
  public BroadcastCommand getBroadcastCommand()
  {
    return this.broadcastCommand;
  }
  
  public BugListener getBugListener()
  {
    return this.bugListener;
  }
  
  public EnchantCommand getEnchantCommand()
  {
    return this.enchantCommand;
  }
  
  public FlyCommand getFlyCommand()
  {
    return this.flyCommand;
  }
  
  public GamemodeCommand getGamemodeCommand()
  {
    return this.gamemodeCommand;
  }
  
  public GodCommand getGodCommand()
  {
    return this.godCommand;
  }
  
  public GodListener getGodListener()
  {
    return this.godListener;
  }
  
  public HatCommand getHatCommand()
  {
    return this.hatCommand;
  }
  
  public HeadCommand getHeadCommand()
  {
    return this.headCommand;
  }
  
  public HealCommand getHealCommand()
  {
    return this.healCommand;
  }
  
  public HelpOpCommand getHelpOpCommand()
  {
    return this.helpOpCommand;
  }
  
  public HomeCommand getHomeCommand()
  {
    return this.homeCommand;
  }
  
  public TempBanIPCommand getTempBanIPCommand()
  {
    return this.tempBanIPCommand;
  }
  
  public JoinQuitListener getJoinQuitListener()
  {
    return this.joinQuitListener;
  }
  
  public KickAllCommand getKickAllCommand()
  {
    return this.kickAllCommand;
  }
  
  public KickCommand getKickCommand()
  {
    return this.kickCommand;
  }
  
  public ListCommand getListCommand()
  {
    return this.listCommand;
  }
  
  public MotdCommand getMotdCommand()
  {
    return this.motdCommand;
  }
  
  public MuteCommand getMuteCommand()
  {
    return this.muteCommand;
  }
  
  public LoginListener getLoginListener()
  {
    return this.loginListener;
  }
  
  public MySQL getMysql()
  {
    return this.mysql;
  }
  
  public RepairCommand getRepairCommand()
  {
    return this.repairCommand;
  }
  
  public UnMuteCommand getUnMuteCommand()
  {
    return this.unMuteCommand;
  }
  
  public UnBanCommand getUnBanCommand()
  {
    return this.unBanCommand;
  }
  
  public UnBanIPCommand getUnBanIPCommand()
  {
    return this.unBanIPCommand;
  }
  
  public RespawnListener getRespawnListener()
  {
    return this.respawnListener;
  }
  
  public SetHomeCommand getSetHomeCommand()
  {
    return this.setHomeCommand;
  }
  
  public SetSpawnCommand getSetSpawnCommand()
  {
    return this.setSpawnCommand;
  }
  
  public SlotCommand getSlotCommand()
  {
    return this.slotCommand;
  }
  
  public ServerListPingListener getServerListPingListener()
  {
    return this.serverListPingListener;
  }
  
  public SignListener getSignListener()
  {
    return this.signListener;
  }
  
  public SpawnCommand getSpawnCommand()
  {
    return this.spawnCommand;
  }
  
  public SpeedCommand getSpeedCommand()
  {
    return this.speedCommand;
  }
  
  public TeleportListener getTeleportListener()
  {
    return this.teleportListener;
  }
  
  public TempBanCommand getTempBanCommand()
  {
    return this.tempBanCommand;
  }
  
  public TpacceptCommand getTpacceptCommand()
  {
    return this.tpacceptCommand;
  }
  
  public TpaCommand getTpaCommand()
  {
    return this.tpaCommand;
  }
  
  public TpCommand getTpCommand()
  {
    return this.tpCommand;
  }
  
  public TpdenyCommand getTpdenyCommand()
  {
    return this.tpdenyCommand;
  }
  
  public WhoIsCommand getWhoIsCommand()
  {
    return this.whoIsCommand;
  }
  
  public WitherListener getWitherListener()
  {
    return this.witherListener;
  }
  
  public static boolean isFloat(String string)
  {
    String decimalPattern = "([0-9]*)\\.([0-9]*)";
    return Pattern.matches("([0-9]*)\\.([0-9]*)", string);
  }
  
  public static boolean isInteger(String string)
  {
    CharSequence sequence = string.subSequence(0, string.length());
    return Pattern.matches("-?[0-9]+", sequence);
  }
  
  public Location getSpawn()
  {
    return Utils.getLocation(getConfig().getString("spawn.world"), getConfig().getDouble("spawn.x"), getConfig().getDouble("spawn.y"), getConfig().getDouble("spawn.z"), (float)getConfig().getDouble("spawn.yaw"), (float)getConfig().getDouble("spawn.pitch"));
  }
  
  public void onDisable()
  
  {
    for (Ban ban : BanManager.getBans().values()) {
      ban.update(true);
    }
    for (BanIP banIP : BanIPManager.getBansIp().values()) {
      banIP.update(true);
    }
    for (User user : UserManager.getUsers().values()) {
      user.update(true);
    }
    Bukkit.getConsoleSender().sendMessage("Dziekujemy Za Uzywanie MCTools");
  }
  
  public void onEnable()
  {
	  Bukkit.getConsoleSender().sendMessage("##########");
	  Bukkit.getConsoleSender().sendMessage("MCTools By Tobiasz");
	  Bukkit.getConsoleSender().sendMessage("Pakiet Standart");
	  Bukkit.getConsoleSender().sendMessage("Wersja 1.0.0");
	  Bukkit.getConsoleSender().sendMessage("Baza Danych : Wczytana");
	  Bukkit.getConsoleSender().sendMessage("##########");
    saveDefaultConfig();
    this.motd = getConfig().get("config.slot-manager.motd").toString();
    this.slots = Integer.parseInt(getConfig().get("config.slot-manager.slots").toString());
    User.setTools(this);
    Mute.setTools(this);
    Ban.setTools(this);
    BanIP.setTools(this);
    UserManager.setTools(this);
    MuteManager.setTools(this);
    BanManager.setTools(this);
    BanIPManager.setTools(this);
    this.mysql = new MySQL(this);
    UserManager.setup();
    MuteManager.setup();
    BanManager.setup();
    BanIPManager.setup();
    this.banCommand = new BanCommand(this);
    this.banIPCommand = new BanIPCommand(this);
    this.broadcastCommand = new BroadcastCommand(this);
    this.enchantCommand = new EnchantCommand(this);
    this.flyCommand = new FlyCommand(this);
    this.gamemodeCommand = new GamemodeCommand(this);
    this.godCommand = new GodCommand(this);
    this.hatCommand = new HatCommand(this);
    this.headCommand = new HeadCommand(this);
    this.healCommand = new HealCommand(this);
    this.helpOpCommand = new HelpOpCommand(this);
    this.homeCommand = new HomeCommand(this);
    this.kickAllCommand = new KickAllCommand(this);
    this.kickCommand = new KickCommand(this);
    this.listCommand = new ListCommand(this);
    this.motdCommand = new MotdCommand(this);
    this.muteCommand = new MuteCommand(this);
    this.repairCommand = new RepairCommand(this);
    this.setHomeCommand = new SetHomeCommand(this);
    this.setSpawnCommand = new SetSpawnCommand(this);
    this.slotCommand = new SlotCommand(this);
    this.serverListPingListener = new ServerListPingListener(this);
    this.spawnCommand = new SpawnCommand(this);
    this.speedCommand = new SpeedCommand(this);
    this.tempBanCommand = new TempBanCommand(this);
    this.tempBanIPCommand = new TempBanIPCommand(this);
    this.tpacceptCommand = new TpacceptCommand(this);
    this.tpaCommand = new TpaCommand(this);
    this.tpCommand = new TpCommand(this);
    this.tpdenyCommand = new TpdenyCommand(this);
    this.unBanCommand = new UnBanCommand(this);
    this.unBanIPCommand = new UnBanIPCommand(this);
    this.unMuteCommand = new UnMuteCommand(this);
    this.whoIsCommand = new WhoIsCommand(this);
    this.bugListener = new BugListener(this);
    this.godListener = new GodListener(this);
    this.joinQuitListener = new JoinQuitListener(this);
    this.loginListener = new LoginListener(this);
    this.respawnListener = new RespawnListener(this);
    this.signListener = new SignListener(this);
    this.teleportListener = new TeleportListener(this);
    this.witherListener = new WitherListener(this);
    this.autoMessageTask = new AutoMessageTask(this).runTaskTimer(this, 60L, 1200L);
    this.autoSaveMapTask = new AutoSaveMapTask().runTaskTimer(this, 6000L, 18000L);
    this.afkTask = new AFKTask(this).runTaskTimer(this, 20L, 20L);
  }
  
  public void teleportPlayerWithDelay(final Player player, int delayTime, final Location location, final String messageAfterTp, final boolean prefix, final Runnable postTeleport)
  {
    if (TeleportManager.getPlayerTeleportLocation().get(player) != null) {
      TeleportManager.getPlayerTeleportLocation().remove(player);
    }
    BukkitTask task = Bukkit.getServer().getScheduler().runTaskLater(this, new Runnable()
    {
      public void run()
      {
        if (player.isOnline())
        {
          player.teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
          TeleportManager.getPlayerTeleportLocation().remove(player);
          if (messageAfterTp != null) {
            Utils.sendMsg(player, messageAfterTp, prefix);
          }
          if (postTeleport != null) {
            postTeleport.run();
          }
        }
      }
    }, delayTime * 20);
    



    TeleportManager.getPlayerTeleportLocation().put(player, task);
  }
}
