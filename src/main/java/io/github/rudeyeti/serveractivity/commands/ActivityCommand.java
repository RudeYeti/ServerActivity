package io.github.rudeyeti.serveractivity.commands;

import io.github.rudeyeti.serveractivity.ServerActivity;
import io.github.rudeyeti.serveractivity.commands.activity.GenerateCommand;
import io.github.rudeyeti.serveractivity.commands.activity.InfoSubcommand;
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

public class ActivityCommand  implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("List of available subcommands:\n" +
                               "info - Shows details about the author and the version of this plugin.\n" +
                               "generate - Creates a log file with the current recorded activity.");
        } else if (args[0].matches("i(nfo)?(rmation)?|authors?|ver(sion)?")) {
            InfoSubcommand.execute(sender);
        } else if (args[0].matches("g(en(erate)?)?|l(og|ist)|(creat|mak)e")) {
            GenerateCommand.execute(sender, label, args);
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <info | generate>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return (args.length <= 1) ? StringUtil.copyPartialMatches(args[0], Arrays.asList("info", "generate"), new ArrayList<>()) : Collections.singletonList("");
    }
}