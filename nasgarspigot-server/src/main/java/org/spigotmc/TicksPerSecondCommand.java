package org.spigotmc;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class TicksPerSecondCommand extends Command {

    public static final DecimalFormat df = new DecimalFormat("#.#");
    private static DecimalFormat readableFileSizeFormatter = new DecimalFormat("#,##0.#");
    long startTime;

    public TicksPerSecondCommand(String name) {
        super(name);
        this.startTime = System.currentTimeMillis();
        this.description = "Gets the current ticks per second for the server";
        this.usageMessage = "/tps";
        this.setPermission("bukkit.command.tps");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) {
            return true;
        }

        // PaperSpigot start - Further improve tick handling
        double[] tps = org.bukkit.Bukkit.spigot().getTPS();
        String[] tpsAvg = new String[tps.length];

        for (int i = 0; i < tps.length; i++) {
            tpsAvg[i] = format(tps[i]);
        }

        int entities = MinecraftServer.getServer().getWorld().entityList.size();
        final Runtime runtime = Runtime.getRuntime();
        final long used = runtime.totalMemory() - runtime.freeMemory();


        sender.sendMessage(ChatColor.GOLD + "Server TPS (5s, 1m, 5m, 15m): " + org.apache.commons.lang.StringUtils.join(tpsAvg, ", "));
        sender.sendMessage(ChatColor.GOLD + "Online players: " + ChatColor.GREEN + String.format("%s/%s", sender.getServer().getOnlinePlayers().size(), sender.getServer().getMaxPlayers()));
        sender.sendMessage(ChatColor.GOLD + "Full tick: " + formatTickTime(MinecraftServer.getServer().lastTickTime) + " ms" + " (" + "Threads: " + Thread.getAllStackTraces().keySet().parallelStream().filter(Thread::isAlive).count() + "/" + Thread.getAllStackTraces().keySet().parallelStream().count() + ", Daemon: " + Thread.getAllStackTraces().keySet().parallelStream().filter(Thread::isDaemon).count() + ")");
        sender.sendMessage(ChatColor.GOLD + "Entities: " + ChatColor.GREEN + entities);
        sender.sendMessage(ChatColor.GOLD + "Memory: " + ChatColor.GREEN + readableFileSize(used) + "/" + readableFileSize(runtime.totalMemory()) + " (Max: " + readableFileSize(runtime.maxMemory()) + ")");
        sender.sendMessage(ChatColor.GOLD + "Uptime: " + ChatColor.GREEN + formatFullMilis(Long.valueOf(System.currentTimeMillis() - this.startTime)));
        if (sender instanceof Player) {
            sender.sendMessage(ChatColor.GOLD + "Chunks: " + ChatColor.GREEN + Bukkit.getWorld(((Player) sender).getLocation().getWorld().getName()).getLoadedChunks().length + " ยง7(" + ((Player) sender).getLocation().getWorld().getName() + "ยง7)");
        }
        return true;
    }

    private static String format(double tps) // PaperSpigot - made static
    {
        return ((tps > 19.5) ? ChatColor.GREEN : ChatColor.RED).toString()
                + ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }
    private static String readableFileSize(final long size) {
        if (size <= 0L) {
            return "0";
        }
        final String[] units = { "B", "kB", "MB", "GB", "TB" };
        final int digitGroups = (int)(Math.log10(size) / Math.log10(1024.0));
        return String.valueOf(TicksPerSecondCommand.readableFileSizeFormatter.format(size / Math.pow(1024.0, digitGroups))) + ' ' + units[digitGroups];
    }

    public static String formatFullMilis(final Long milis) {
        final double seconds = Math.max(0L, milis) / 1000.0;
        final double minutes = seconds / 60.0;
        final double hours = minutes / 60.0;
        final double days = hours / 24.0;
        final double weeks = days / 7.0;
        final double months = days / 31.0;
        final double years = months / 12.0;
        if (years >= 1.0) {
            return String.valueOf(TicksPerSecondCommand.df.format(years)) + " year" + ((years != 1.0) ? "s" : "");
        }
        if (months >= 1.0) {
            return String.valueOf(TicksPerSecondCommand.df.format(months)) + " month" + ((months != 1.0) ? "s" : "");
        }
        if (weeks >= 1.0) {
            return String.valueOf(TicksPerSecondCommand.df.format(weeks)) + " week" + ((weeks != 1.0) ? "s" : "");
        }
        if (days >= 1.0) {
            return String.valueOf(TicksPerSecondCommand.df.format(days)) + " day" + ((days != 1.0) ? "s" : "");
        }
        if (hours >= 1.0) {
            return String.valueOf(TicksPerSecondCommand.df.format(hours)) + " hour" + ((hours != 1.0) ? "s" : "");
        }
        if (minutes >= 1.0) {
            return String.valueOf(TicksPerSecondCommand.df.format(minutes)) + " minute" + ((minutes != 1.0) ? "s" : "");
        }
        return String.valueOf(TicksPerSecondCommand.df.format(seconds)) + " second" + ((seconds != 1.0) ? "s" : "");
    }

    private static String formatTickTime(double time) {
        return (time < 40.0D ? ChatColor.GREEN : time < 60.0D ? ChatColor.YELLOW : ChatColor.RED).toString() + Math.round(time * 10.0D) / 10.0D;
    }
}
