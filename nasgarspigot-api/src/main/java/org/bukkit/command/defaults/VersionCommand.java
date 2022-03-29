package org.bukkit.command.defaults;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class VersionCommand extends BukkitCommand {
    public VersionCommand(String name) {
        super(name);

        this.description = "Gets the version of this server including any plugins in use";
        this.usageMessage = "/version [plugin name]";
        this.setPermission("bukkit.command.version");
        this.setAliases(Arrays.asList("ver", "about"));
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7This server is running &aNasgarSpigot-1.8&7."));
        return true;
    }
}
