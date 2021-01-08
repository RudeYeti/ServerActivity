package io.github.rudeyeti.serveractivity.commands.serveractivity;

import io.github.rudeyeti.serveractivity.Plugins;
import io.github.rudeyeti.serveractivity.ServerActivity;
import io.github.rudeyeti.serveractivity.Time;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateSubcommand {
    public static void execute(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("serveractivity.generate") || sender.isOp()) {
            if (args.length > 1) {
                try {
                    if (Plugins.getCoreProtect() != null) {
                        String timeString = Time.get(args[1]);
                        int time;

                        // Making sure that the time string was valid.
                        if (NumberUtils.isDigits(timeString)) {
                            time = Integer.parseInt(timeString);
                        } else {
                            sender.sendMessage(ChatColor.RED + timeString);
                            return;
                        }

                        File dataFolder = ServerActivity.plugin.getDataFolder();
                        File file = new File(dataFolder, "activity.txt");
                        int fileNumber = 0;
                        AtomicInteger pages = new AtomicInteger();
                        List<String> lines = new ArrayList<>();

                        // Making sure that no files are being overwritten, so instead it makes a new one.
                        while (file.exists()) {
                            fileNumber++;
                            file = new File(dataFolder, "activity" + fileNumber + ".txt");
                        }

                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file);

                        // Message so you know the command was received properly while it's generating the file.
                        sender.sendMessage("The file " + file.getName() + " is being generated, please wait...");

                        ServerActivity.server.getWhitelistedPlayers().forEach((offlinePlayer) -> {
                            int activity = Plugins.getCoreProtect().performLookup(
                                    time, Collections.singletonList(offlinePlayer.getName()), null, null, null, null, 0, null
                            ).size();

                            // Formula to count the pages of CoreProtect entries, which is basically the total divided by four.
                            pages.set(activity > 0 ? (int) Math.ceil((activity + 1) / 4.0) : 0);
                            lines.add(offlinePlayer.getName() + " - " + pages + System.lineSeparator());
                        });

                        lines.sort(new Comparator<String>() {
                            public int compare(String line1, String line2) {
                                // Making sure that there are no repeats in the whitelist.
                                return line1.equals(line2) ? extractInt(line1) : extractInt(line2) - extractInt(line1) ;
                            }

                            int extractInt(String line) {
                                // Extracting the number after the Minecraft username.
                                String number = line.replaceAll(".+ - ", "").replace(System.lineSeparator(), "");
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
                sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + args[0] + " <time>");
            }
        } else {
        sender.sendMessage(ChatColor.RED + "Usage: You are missing the correct permission to perform this command.");
        }
    }
}
