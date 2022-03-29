package online.nasgar.spigot;

import com.google.common.base.Throwables;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

/**
 * Copyright 01/08/2020 Kevin Acaymo
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Kevin Acaymo
 */
@Getter
@Setter
public class CadiaMCSpigotConfig {

    private static final String HEADER = "This is the main configuration file for SpigotV.\n"
            + "Modify with caution, and make sure you know what you are doing.\n";

    private File configFile;
    private YamlConfiguration config;

    private boolean hidePlayersFromTab;

    private boolean firePlayerMoveEvent;
    private boolean fireLeftClickAir;
    private boolean fireLeftClickBlock;

    public static boolean proxyPipePingFix;

    public static boolean disableMoveEvent;

    private boolean entityActivation;

    private boolean invalidArmAnimationKick;

    private boolean mobAIEnabled;

    private boolean baseVersionEnabled;

    private boolean doChunkUnload;

    private boolean enabled1_8Stuff;
    private boolean blockOperations;

    private boolean disableJoinMessage;
    private boolean disableLeaveMessage;

    private boolean tickVillages;
    private boolean tickPortalForcer;
    private boolean tickMobSpawning;

    private boolean enderpearlCollision;

    //new spigot things (Almost all is of mSpigot (FrozenOrbSpigot)
    private boolean anticheatEnabled;

    private boolean mobStackingEnabled;
    private int mobStackingMultiplier;

    private boolean disableTileEntityTick;
    private boolean disableRecheckGaps;
    private boolean disableTickingWeather;
    private boolean disableBiomeCacheCleanup;
    private boolean disableTickingSleepCheck;
    private boolean disableTickingVillages;
    private boolean disableTickingChunks;
    private boolean disableTickingMaps;

    private boolean disableUnloadingChunks;

    private boolean disableLoadingNearbyChunks;

    private boolean enderpearlTaliban;
    private boolean enderpearlGates;
    private boolean enderpearlTripwire;

    private boolean disableWeather;

    public static float potionI = 0.05F;
    public static float potionE = 0.5F;
    public static float potionF = -20.0F;

    public static boolean practiceOptimizations;

    private CadiaMCThread thread;

    private boolean asyncHitDetection;
    private boolean asyncKnockbackDetection;
    private boolean asyncAffinityThreads;
    private boolean asyncCatcher;
    private int asyncThreads;

    public CadiaMCSpigotConfig() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("version.properties");
        Properties prop = new Properties();

        this.configFile = new File("settings.yml");
        this.config = new YamlConfiguration();

        try {
            config.load(this.configFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load settings.yml, please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }

        this.config.options().header(CadiaMCSpigotConfig.HEADER);
        this.config.options().copyDefaults(true);

        this.loadConfig();

        this.thread = new CadiaMCThread(asyncThreads);
        this.thread.loadAsyncThreads(asyncAffinityThreads);
        this.thread.requestTask(() -> {});
    }

    private void loadConfig()
    {
        this.hidePlayersFromTab = getBoolean("hide-players-from-tab", true);

        this.firePlayerMoveEvent = getBoolean("fire-player-move-event", false);
        this.fireLeftClickAir = getBoolean("fire-left-click-air", false);
        this.fireLeftClickBlock = getBoolean("fire-left-click-block", false);

        this.entityActivation = getBoolean("entity-activation", false);

        this.invalidArmAnimationKick = getBoolean("invalid-arm-animation-kick", false);

        this.mobAIEnabled = getBoolean("mob-ai", true);

        this.baseVersionEnabled = getBoolean("1-8-enabled", false);

        this.doChunkUnload = getBoolean("do-chunk-unload", true);

        this.blockOperations = getBoolean("block-operations", false);

        this.disableJoinMessage = getBoolean("disable-join-message", true);
        this.disableLeaveMessage = getBoolean("disable-leave-message", true);
        this.tickVillages = getBoolean("tick-villages", false);

        this.tickPortalForcer = getBoolean("tick-portal-forcer", false);
        this.tickMobSpawning = getBoolean("tick-mob-spawning", false);

        this.enderpearlCollision = getBoolean("enderpearl-collision", true);

        //new spigot things (Almost all is of mSpigot (FrozenOrbSpigot)
        this.disableTileEntityTick = getBoolean("disable.ticking.tile-entities", false);
        this.disableRecheckGaps = getBoolean("disable.ticking.recheck-gaps", false);
        this.disableTickingWeather = getBoolean("disable.ticking.weather", true);
        this.disableBiomeCacheCleanup = getBoolean("disable.ticking.biome-cache-cleanup", true);
        this.disableTickingSleepCheck = getBoolean("disable.ticking.sleep-check", true);
        this.disableTickingVillages = getBoolean("disable.ticking.villages", true);
        this.disableTickingChunks = getBoolean("disable.ticking.chunks", false);
        this.disableTickingMaps = getBoolean("disable.ticking.maps", true);

        this.disableWeather = getBoolean("disable.weather", true);

        this.disableUnloadingChunks = getBoolean("disable.unloading-chunks", true);
        this.disableLoadingNearbyChunks = getBoolean("disable.loading-nearby-chunks", true);

        this.anticheatEnabled = getBoolean("anticheat", true);

        this.mobStackingEnabled = this.getBoolean("mobStackingEnabled", false);
        this.mobStackingMultiplier = this.getInt("mobStackingMultiplier", 5);

        this.enderpearlTaliban = this.getBoolean("enderpearl.taliban", true);
        this.enderpearlGates = this.getBoolean("enderpearl.gates", true);
        this.enderpearlTripwire = this.getBoolean("enderpearl.tripwire", true);

        potionI = this.getFloat("potionI", 0.05F);
        potionE = this.getFloat("potionE", 0.5F);
        potionF = this.getFloat("potionF", -20.0F);

        practiceOptimizations = this.getBoolean("settings.practice-optimizations", false);
        proxyPipePingFix = getBoolean("settings.proxy-pipe-ping-fix", false);
        disableMoveEvent = getBoolean("settings.disable-move-event", false);

        this.asyncCatcher = false;
        this.asyncAffinityThreads = false;
        this.asyncHitDetection = true;
        this.asyncKnockbackDetection = true;
        this.asyncThreads = 4;

        try {
            this.config.save(this.configFile);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + this.configFile, ex);
        }
    }

    public void save() {
        try {
            this.config.save(this.configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object val) {
        this.config.set(path, val);

        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getKeys(String path) {
        if (!this.config.isConfigurationSection(path)) {
            this.config.createSection(path);
            return new HashSet<>();
        }

        return this.config.getConfigurationSection(path).getKeys(false);
    }

    public boolean getBoolean(String path, boolean def) {
        this.config.addDefault(path, def);
        return this.config.getBoolean(path, this.config.getBoolean(path));
    }

    public double getDouble(String path, double def) {
        this.config.addDefault(path, def);
        return this.config.getDouble(path, this.config.getDouble(path));
    }

    public float getFloat(String path, float def) {
        return (float) this.getDouble(path, (double) def);
    }

    public int getInt(String path, int def) {
        this.config.addDefault(path, def);
        return config.getInt(path, this.config.getInt(path));
    }

    public <T> List getList(String path, T def) {
        this.config.addDefault(path, def);
        return this.config.getList(path, this.config.getList(path));
    }

    public String getString(String path, String def) {
        this.config.addDefault(path, def);
        return this.config.getString(path, this.config.getString(path));
    }

    public boolean isBlockOperations() {
        return this.blockOperations;
    }

    public boolean isHidePlayersFromTab() {
        return this.hidePlayersFromTab;
    }

    public boolean isFirePlayerMoveEvent() {
        return this.firePlayerMoveEvent;
    }

    public boolean isFireLeftClickAir() {
        return this.fireLeftClickAir;
    }

    public boolean isFireLeftClickBlock() {
        return this.fireLeftClickBlock;
    }

    public boolean isEntityActivation() {
        return this.entityActivation;
    }

    public boolean isInvalidArmAnimationKick() {
        return this.invalidArmAnimationKick;
    }

    public boolean isMobAIEnabled() {
        return this.mobAIEnabled;
    }

    public boolean is18Enabled() {
        return this.enabled1_8Stuff;
    }

    public boolean isDoChunkUnload() {
        return this.doChunkUnload;
    }

    public void setDisableJoinMessage(boolean disableJoinMessage) {
        this.disableJoinMessage = disableJoinMessage;
    }

    public void setDisableLeaveMessage(boolean disableLeaveMessage) {
        this.disableLeaveMessage = disableLeaveMessage;
    }

    public boolean isAsyncHitDetection() {
        return this.asyncHitDetection;
    }

    public boolean isAsyncKnockbackDetection() {
        return this.asyncKnockbackDetection;
    }

    public CadiaMCThread getThread() {
        return thread;
    }
}