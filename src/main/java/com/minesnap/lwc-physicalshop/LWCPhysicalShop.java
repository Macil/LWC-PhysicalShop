package com.minesnap.lwcphysicalshop;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginEnableEvent;

import com.griefcraft.lwc.LWCPlugin;
import com.griefcraft.scripting.ModuleLoader;

import com.wolvereness.physicalshop.PhysicalShop;

public class LWCPhysicalShop extends JavaPlugin implements Listener {
    private PhysicalShop physicalShop;
    private LWCPlugin lwc;

    private ChestShopModule chestShopModule = new ChestShopModule(this);

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        Plugin temp;

        temp = pm.getPlugin("PhysicalShop");
        if (temp != null && temp instanceof PhysicalShop) {
            physicalShop = (PhysicalShop) temp;
        }

        temp = pm.getPlugin("LWC");
        if (temp != null && temp instanceof LWCPlugin) {
            lwc = (LWCPlugin) temp;
        }

        if (!init()) {
            // Listen for plugin enable if we're still waiting on them
            pm.registerEvents(this, this);
            getLogger().info("Waiting for both PhysicalShop and LWC to be loaded.");
        }
    }

    private boolean init() {
        if (physicalShop == null || lwc == null)
            return false;

        ModuleLoader moduleLoader = lwc.getLWC().getModuleLoader();
        moduleLoader.registerModule(this, chestShopModule);

        getLogger().info("Enabled.");

        return true;
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        Plugin plugin = event.getPlugin();
        if (plugin instanceof PhysicalShop) {
            physicalShop = (PhysicalShop) plugin;
        } else if (plugin instanceof LWCPlugin) {
            lwc = (LWCPlugin) plugin;
        }

        init();
    }

    public PhysicalShop getPhysicalShop() {
        return physicalShop;
    }
}
