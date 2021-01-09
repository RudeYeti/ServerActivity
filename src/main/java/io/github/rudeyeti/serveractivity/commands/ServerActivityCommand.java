package io.github.rudeyeti.serveractivity.commands;

import io.github.rudeyeti.serveractivity.commands.serveractivity.GenerateSubcommand;
import io.github.rudeyeti.serveractivity.commands.serveractivity.InfoSubcommand;
import io.github.rudeyeti.serveractivity.commands.serveractivity.LeaderboardSubcommand;
import io.github.rudeyeti.serveractivity.commands.serveractivity.RankSubcommand;
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
            sender.sendMessage("List of available subcommands:\n" +
                               "info - Shows details about the author and the version of this plugin.\n" +
                               "generate - Creates a log file with the current recorded activity.\n" +
                               "leaderboard - Lists the top five players based on activity.\n" +
                               "rank - Returns the current activity level of the player.");
        } else if (args[0].matches("i(nfo)?(rmation)?|authors?|ver(sion)?")) {
            InfoSubcommand.execute(sender);
        } else if (args[0].matches("g(en(erate)?)?|l(og|ist)|(creat|mak)e")) {
            GenerateSubcommand.execute(sender, label, args);
        } else if (args[0].matches("l(ead(er(board)?)?)?|high(est)?|top")) {
            LeaderboardSubcommand.execute(sender);
        } else if (args[0].matches("r(ank)?|levels?|me")) {
            RankSubcommand.execute(sender);
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <info | generate | leaderboard | rank>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Return an empty list instead of null, so the player name does not keep appearing in the command arguments.
        return (args.length <= 1) ? StringUtil.copyPartialMatches(args[0], Arrays.asList("info", "generate", "leaderboard", "rank"), new ArrayList<>()) : Collections.singletonList("");
    }
}