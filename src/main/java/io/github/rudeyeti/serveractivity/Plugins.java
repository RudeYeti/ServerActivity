package io.github.rudeyeti.serveractivity;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;

public class Plugins {

    public static CoreProtectAPI getCoreProtect() {
        Plugin plugin = ServerActivity.server.getPluginManager().getPlugin("CoreProtect");

        if (!(plugin instanceof CoreProtect)) {
            return null;
        }

        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();

        if (!CoreProtect.isEnabled()) {
            return null;
        }

        if (CoreProtect.APIVersion() < 4) {
            return null;
        }

        return CoreProtect;
    }
}
