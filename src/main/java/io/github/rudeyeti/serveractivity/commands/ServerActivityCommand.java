package io.github.rudeyeti.serveractivity.commands;

import io.github.rudeyeti.serveractivity.commands.serveractivity.GenerateSubcommand;
import io.github.rudeyeti.serveractivity.commands.serveractivity.InfoSubcommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServerActivityCommand  implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("List of available subcommands:" + System.lineSeparator() +
                               "info - Shows details about the author and the version of this plugin." + System.lineSeparator() +
                               "generate - Creates a log file with the current recorded activity.");
        } else if (args[0].matches("i(nfo)?(rmation)?|authors?|ver(sion)?")) {
            InfoSubcommand.execute(sender);
        } else if (args[0].matches("g(en(erate)?)?|l(og|ist)|(creat|mak)e")) {
            GenerateSubcommand.execute(sender, label, args);
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <info | generate>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Return an empty list instead of null, so the player name does not keep appearing in the command arguments.
        return (args.length <= 1) ? StringUtil.copyPartialMatches(args[0], Arrays.asList("info", "generate"), new ArrayList<>()) : Collections.singletonList("");
    }
}