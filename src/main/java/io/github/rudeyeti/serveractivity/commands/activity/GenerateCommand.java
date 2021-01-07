package io.github.rudeyeti.serveractivity.commands.activity;

import io.github.rudeyeti.serveractivity.Plugins;
import io.github.rudeyeti.serveractivity.ServerActivity;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateCommand {
    public static void execute(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("serveractivity.generate") || sender.isOp()) {
            if (args.length > 1) {
                if (NumberUtils.isNumber(args[1])) {
                    if (Integer.parseInt(args[1]) > 0) {
                        try {
                            if (Plugins.getCoreProtect() != null) {
                                int time = (int) Duration.ofDays(1).getSeconds() * Integer.parseInt(args[1]);
                                File dataFolder = ServerActivity.plugin.getDataFolder();
                                File file = new File(dataFolder, "activity.txt");
                                int fileNumber = 0;
                                AtomicInteger pages = new AtomicInteger();
                                List<String> lines = new ArrayList<>();

                                while (file.exists()) {
                                    fileNumber++;
                                    file = new File(dataFolder, "activity." + fileNumber + ".txt");
                                }

                                file.createNewFile();
                                FileWriter fileWriter = new FileWriter(file);

                                ServerActivity.server.getWhitelistedPlayers().forEach((offlinePlayer) -> {
                                    List<String[]> activity = Plugins.getCoreProtect().performLookup(
                                            time, Collections.singletonList(offlinePlayer.getName()), null, null, null, null, 0, null
                                    );

                                    if (activity.size() > 0) {
                                        pages.set((int) Math.ceil((activity.size() + 1) / 4.0));
                                    } else {
                                        pages.set(0);
                                    }

                                    lines.add(offlinePlayer.getName() + " - " + pages + "\n");
                                });

                                lines.sort(new Comparator<String>() {
                                    public int compare(String line1, String line2) {
                                        return extractInt(line2) - extractInt(line1);
                                    }

                                    int extractInt(String line) {
                                        String number = line.replaceAll("[^\\d]", "");
                                        return Integer.parseInt(number);
                                    }
                                });

                                lines.forEach((line) -> {
                                    try {
                                        fileWriter.write(line);
                                    } catch (IOException error) {
                                        error.printStackTrace();
                                    }
                                });

                                fileWriter.close();
                                sender.sendMessage("The file " + file.getName() + " has been successfully generated.");
                            }
                        } catch (IOException error) {
                            error.printStackTrace();
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage: The specified time must be at least 1.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: The specified time must be a number.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + args[0] + " <time>");
            }
        } else {
        sender.sendMessage(ChatColor.RED + "Usage: You are missing the correct permission to perform this command.");
        }
    }
}
