package io.github.rudeyeti.serveractivity.commands.serveractivity;

import io.github.rudeyeti.serveractivity.ServerActivity;
import org.bukkit.command.CommandSender;

public class InfoSubcommand {
    public static void execute(CommandSender sender) {
        sender.sendMessage("General information:" + System.lineSeparator() +
                           "Author - " + ServerActivity.plugin.getDescription().getAuthors().get(0) + System.lineSeparator() +
                           "Version - " + ServerActivity.plugin.getDescription().getVersion());
    }
}
