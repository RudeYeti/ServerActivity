package io.github.rudeyeti.serveractivity;

import io.github.rudeyeti.serveractivity.commands.ActivityCommand;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ServerActivity extends JavaPlugin {

    public static Plugin plugin;
    public static Server server;
    public static Logger logger;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        plugin = getPlugin(this.getClass());
        server = plugin.getServer();
        logger = this.getLogger();

        Plugins.getCoreProtect();

        this.getCommand("serveractivity").setExecutor(new ActivityCommand());
    }
}
