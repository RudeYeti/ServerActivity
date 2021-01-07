package io.github.rudeyeti.serveractivity.commands.activity;

import io.github.rudeyeti.serveractivity.ServerActivity;
import org.bukkit.command.CommandSender;

public class InfoSubcommand {
    public static void execute(CommandSender sender) {
        sender.sendMessage("General information:\n" +
                           "Author - " + ServerActivity.plugin.getDescription().getAuthors().get(0) + "\n" +
                           "Version - " + ServerActivity.plugin.getDescription().getVersion());
    }
}
