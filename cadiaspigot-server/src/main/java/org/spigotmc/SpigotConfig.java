package org.spigotmc;

import net.cadiamc.spigot.CadiaMCThread;
import net.cadiamc.spigot.command.KnockbackCommand;
import net.cadiamc.spigot.json.JsonConfig;
import net.cadiamc.spigot.knockback.CraftKnockbackProfile;
import net.cadiamc.spigot.knockback.KnockbackProfile;
import co.aikar.timings.Timings;
import co.aikar.timings.TimingsManager;
import com.google.common.collect.Lists;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.server.AttributeRanged;
import net.minecraft.server.GenericAttributes;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.github.paperspigot.SharedConfig;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class SpigotConfig {

    public static YamlConfiguration config;
    static Map<String, Command> commands;
    public static CadiaMCThread thread;

    public static void init() {
        config = SharedConfig.config;
        commands = SharedConfig.commands;
        SharedConfig.readConfig(SpigotConfig.class, null);

        loadKnockbackProfiles();
        commands.put("knockback", new KnockbackCommand());

        (thread = new CadiaMCThread(Runtime.getRuntime().availableProcessors() * 2)).loadAsyncThreads(true);
        thread.requestTask(() -> {});
    }

    private static boolean getBoolean(String path, boolean def) {
        path = "spigot." + path;
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static int getInt(String path, int def) {
        path = "spigot." + path;
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    private static <T> List getList(String path, T def) {
        path = "spigot." + path;
        config.addDefault(path, def);
        return (List<T>) config.getList(path, config.getList(path));
    }

    private static String getString(String path, String def) {
        path = "spigot." + path;
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    private static double getDouble(String path, double def) {
        path = "spigot." + path;
        config.addDefault(path, def);
        return config.getDouble(path, config.getDouble(path));
    }

    public static boolean logCommands;

    private static void logCommands() {
        logCommands = getBoolean("commands.log", true);
    }

    public static int tabComplete;

    private static void tabComplete() {
        tabComplete = getInt("commands.tab-complete", 0);
    }

    private static JsonConfig knockbackConfig;
    public static KnockbackProfile globalKbProfile;
    public static final List<KnockbackProfile> kbProfiles = new ArrayList<>();

    public static KnockbackProfile getKbProfileByName(String name) {
        for (KnockbackProfile profile : kbProfiles) {
            if (profile.getName().equalsIgnoreCase(name)) {
                return profile;
            }
        }

        return null;
    }

    public static void loadKnockbackProfiles() {
        knockbackConfig = new JsonConfig(new File("./knockback.json")).load();

        Map<String, Object> profilesMap = (Map<String, Object>) knockbackConfig.get("profiles");

        for (String profileName : profilesMap.keySet()) {
            KnockbackProfile profile = getKbProfileByName(profileName);

            if (profile == null) {
                profile = new CraftKnockbackProfile(profileName);
            }

            profile.setFriction(Double.valueOf(knockbackConfig.getString("profiles." + profileName + ".friction")));
            profile.setHorizontal(Float.valueOf(knockbackConfig.getString("profiles." + profileName + ".horizontal")));
            profile.setVertical(Float.valueOf(knockbackConfig.getString("profiles." + profileName + ".vertical")));
            profile.setVerticalLimit(Double.valueOf(knockbackConfig.getString("profiles." + profileName + ".verticalLimit")));
            profile.setExtraHorizontal(Float.valueOf(knockbackConfig.getString("profiles." + profileName + ".extraHorizontal")));
            profile.setExtraVertical(Float.valueOf(knockbackConfig.getString("profiles." + profileName + ".extraVertical")));

            kbProfiles.add(profile);
        }

        if (kbProfiles.isEmpty()) {
            kbProfiles.add(new CraftKnockbackProfile("Default"));
        }

        globalKbProfile = getKbProfileByName(knockbackConfig.getString("global-profile", "Default"));

        if (globalKbProfile == null) {
            globalKbProfile = kbProfiles.get(0);
        }
    }

    public static void saveKnockbackProfiles() {
        knockbackConfig.clear();

        for (KnockbackProfile profile : kbProfiles) {
            knockbackConfig.set("profiles." + profile.getName() + ".friction", profile.getFriction());
            knockbackConfig.set("profiles." + profile.getName() + ".horizontal", profile.getHorizontal());
            knockbackConfig.set("profiles." + profile.getName() + ".vertical", profile.getVertical());
            knockbackConfig.set("profiles." + profile.getName() + ".verticalLimit", profile.getVerticalLimit());
            knockbackConfig.set("profiles." + profile.getName() + ".extraHorizontal", profile.getExtraHorizontal());
            knockbackConfig.set("profiles." + profile.getName() + ".extraVertical", profile.getExtraVertical());
        }

        knockbackConfig.save();
    }

    public static void sendKnockbackInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.BLUE + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 35));

        for (KnockbackProfile profile : kbProfiles) {
            boolean current = globalKbProfile.getName().equals(profile.getName());

            sender.sendMessage((current ? ChatColor.GREEN.toString() : ChatColor.RED.toString()) + ChatColor.BOLD + profile.getName());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Friction: " + ChatColor.RED + profile.getFriction());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Horizontal: " + ChatColor.RED + profile.getHorizontal());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Vertical: " + ChatColor.RED + profile.getVertical());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Vertical Limit: " + ChatColor.RED + profile.getVerticalLimit());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Extra Horizontal: " + ChatColor.RED + profile.getExtraHorizontal());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Extra Vertical: " + ChatColor.RED + profile.getExtraVertical());
        }

        sender.sendMessage(ChatColor.BLUE + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 35));
    }

    public static String whitelistMessage;
    public static String unknownCommandMessage;
    public static String serverFullMessage;
    public static String outdatedClientMessage = "Outdated client! Please use {0}";
    public static String outdatedServerMessage = "Outdated server! I\'m still on {0}";

    private static String transform(String s) {
        return ChatColor.translateAlternateColorCodes('&', s).replaceAll("\\n", "\n");
    }

    private static void messages() {
        whitelistMessage = transform(getString("messages.whitelist", "You are not whitelisted on this server!"));
        unknownCommandMessage = transform(getString("messages.unknown-command", "Unknown command. Type \"/help\" for help."));
        serverFullMessage = transform(getString("messages.server-full", "The server is full!"));
        outdatedClientMessage = transform(getString("messages.outdated-client", outdatedClientMessage));
        outdatedServerMessage = transform(getString("messages.outdated-server", outdatedServerMessage));
    }

    public static int timeoutTime = 60;
    public static boolean restartOnCrash = true;
    public static String restartScript = "./start.sh";
    public static String restartMessage;

    private static void watchdog() {
        timeoutTime = getInt("settings.timeout-time", timeoutTime);
        restartOnCrash = getBoolean("settings.restart-on-crash", restartOnCrash);
        restartScript = getString("settings.restart-script", restartScript);
        restartMessage = transform(getString("messages.restart", "Server is restarting"));
        commands.put("restart", new RestartCommand("restart"));
        WatchdogThread.doStart(timeoutTime, restartOnCrash);
    }

    public static boolean fetchSkulls = true;

    private static void sportPaper() {
        fetchSkulls = getBoolean("settings.fetch-skulls", fetchSkulls);
    }

    public static boolean bungee;

    private static void bungee() {
        bungee = getBoolean("settings.bungeecord", false);
    }

    private static void timings() {
        boolean timings = getBoolean("timings.enabled", true);
        boolean verboseTimings = getBoolean("timings.verbose", true);
        TimingsManager.privacy = getBoolean("timings.server-name-privacy", false);
        TimingsManager.hiddenConfigs = getList("timings.hidden-config-entries", Lists.newArrayList("database", "settings.bungeecord-addresses"));
        int timingHistoryInterval = getInt("timings.history-interval", 300);
        int timingHistoryLength = getInt("timings.history-length", 3600);


        Timings.setVerboseTimingsEnabled(verboseTimings);
        Timings.setTimingsEnabled(timings);
        Timings.setHistoryInterval(timingHistoryInterval * 20);
        Timings.setHistoryLength(timingHistoryLength * 20);

        Bukkit.getLogger().log(Level.INFO, "Spigot Timings: " + timings +
                " - Verbose: " + verboseTimings +
                " - Interval: " + timeSummary(Timings.getHistoryInterval() / 20) +
                " - Length: " + timeSummary(Timings.getHistoryLength() / 20));
    }

    protected static String timeSummary(int seconds) {
        String time = "";
        if (seconds > 60 * 60) {
            time += TimeUnit.SECONDS.toHours(seconds) + "h";
            seconds /= 60;
        }

        if (seconds > 0) {
            time += TimeUnit.SECONDS.toMinutes(seconds) + "m";
        }
        return time;
    }

    private static void nettyThreads() {
        int count = getInt("settings.netty-threads", 4);
        System.setProperty("io.netty.eventLoopThreads", Integer.toString(count));
        Bukkit.getLogger().log(Level.INFO, "Using {0} threads for Netty based IO", count);
    }

    public static boolean lateBind;

    private static void lateBind() {
        lateBind = getBoolean("settings.late-bind", false);
    }

    public static boolean disableStatSaving;
    public static TObjectIntHashMap<String> forcedStats = new TObjectIntHashMap<String>();

    private static void stats() {
        disableStatSaving = getBoolean("stats.disable-saving", false);

        if (!config.contains("spigot.stats.forced-stats")) {
            config.createSection("spigot.stats.forced-stats");
        }

        ConfigurationSection section = config.getConfigurationSection("spigot.stats.forced-stats");
        for (String name : section.getKeys(true)) {
            if (section.isInt(name)) {
                forcedStats.put(name, section.getInt(name));
            }
        }

        if (disableStatSaving && section.getInt("achievement.openInventory", 0) < 1) {
            Bukkit.getLogger().warning("*** WARNING *** stats.disable-saving is true but stats.forced-stats.achievement.openInventory" +
                    " isn't set to 1. Disabling stat saving without forcing the achievement may cause it to get stuck on the player's " +
                    "screen.");
        }
    }

    private static void tpsCommand() {
        commands.put("tps", new TicksPerSecondCommand("tps"));
    }

    public static int playerSample;

    private static void playerSample() {
        playerSample = getInt("settings.sample-count", 12);
        System.out.println("Server Ping Player Sample Count: " + playerSample);
    }

    public static int playerShuffle;

    private static void playerShuffle() {
        playerShuffle = getInt("settings.player-shuffle", 0);
    }

    public static List<String> spamExclusions;

    private static void spamExclusions() {
        spamExclusions = getList("commands.spam-exclusions", Arrays.asList(new String[]
                {
                        "/skill"
                }));
    }

    public static boolean silentCommandBlocks;

    private static void silentCommandBlocks() {
        silentCommandBlocks = getBoolean("commands.silent-commandblock-console", false);
    }

    public static boolean filterCreativeItems;

    private static void filterCreativeItems() {
        filterCreativeItems = getBoolean("settings.filter-creative-items", true);
    }

    public static Set<String> replaceCommands;

    private static void replaceCommands() {
        replaceCommands = new HashSet<String>((List<String>) getList("commands.replace-commands",
                Arrays.asList("setblock", "summon", "testforblock", "tellraw")));
    }

    public static int userCacheCap;

    private static void userCacheCap() {
        userCacheCap = getInt("settings.user-cache-size", 1000);
    }

    public static boolean saveUserCacheOnStopOnly;

    private static void saveUserCacheOnStopOnly() {
        saveUserCacheOnStopOnly = getBoolean("settings.save-user-cache-on-stop-only", false);
    }

    public static int intCacheLimit;

    private static void intCacheLimit() {
        intCacheLimit = getInt("settings.int-cache-limit", 1024);
    }

    public static double movedWronglyThreshold;

    private static void movedWronglyThreshold() {
        movedWronglyThreshold = getDouble("settings.moved-wrongly-threshold", 0.0625D);
    }

    public static double movedTooQuicklyThreshold;

    private static void movedTooQuicklyThreshold() {
        movedTooQuicklyThreshold = getDouble("settings.moved-too-quickly-threshold", 100.0D);
    }

    public static double maxHealth = 2048;
    public static double movementSpeed = 2048;
    public static double attackDamage = 2048;

    private static void attributeMaxes() {
        maxHealth = getDouble("settings.attribute.maxHealth.max", maxHealth);
        ((AttributeRanged) GenericAttributes.maxHealth).b = maxHealth;
        movementSpeed = getDouble("settings.attribute.movementSpeed.max", movementSpeed);
        ((AttributeRanged) GenericAttributes.MOVEMENT_SPEED).b = movementSpeed;
        attackDamage = getDouble("settings.attribute.attackDamage.max", attackDamage);
        ((AttributeRanged) GenericAttributes.ATTACK_DAMAGE).b = attackDamage;
    }

    public static boolean debug;

    private static void debug() {
        debug = getBoolean("settings.debug", false);

        if (debug && !LogManager.getRootLogger().isTraceEnabled()) {
            // Enable debug logging
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration conf = ctx.getConfiguration();
            conf.getLoggerConfig(LogManager.ROOT_LOGGER_NAME).setLevel(org.apache.logging.log4j.Level.ALL);
            ctx.updateLoggers(conf);
        }

        if (LogManager.getRootLogger().isTraceEnabled()) {
            Bukkit.getLogger().info("Debug logging is enabled");
        } else {
            Bukkit.getLogger().info("Debug logging is disabled");
        }
    }
}
