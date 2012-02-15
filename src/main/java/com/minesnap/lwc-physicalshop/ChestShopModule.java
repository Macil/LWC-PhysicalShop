package com.minesnap.lwcphysicalshop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import com.griefcraft.scripting.JavaModule;
import com.griefcraft.scripting.event.LWCProtectionInteractEvent;
import com.griefcraft.scripting.event.LWCProtectionRegisterEvent;

import com.wolvereness.physicalshop.Shop;
import com.wolvereness.physicalshop.ShopHelpers;

public class ChestShopModule extends JavaModule {
    private LWCPhysicalShop plugin;

    public ChestShopModule(LWCPhysicalShop plugin) {
        this.plugin = plugin;
    }

    public boolean isShopBlock(Block block) {
        if (block.getType() == Material.CHEST) {
            block = block.getRelative(BlockFace.UP);
        }

        if (block.getType() == Material.SIGN_POST
            || block.getType() == Material.WALL_SIGN) {
            Shop shop = ShopHelpers.getShop(block, plugin.getPhysicalShop());
            return shop != null;
        }

        return false;
    }

    @Override
    public void onProtectionInteract(LWCProtectionInteractEvent event) {
        if (isShopBlock(event.getEvent().getClickedBlock())) {
            event.getProtection().remove();
        }
    }

    @Override
    public void onRegisterProtection(LWCProtectionRegisterEvent event) {
        if (isShopBlock(event.getBlock())) {
            Player player = event.getPlayer();
            if (player != null) {
                player.sendMessage(ChatColor.DARK_RED + "You can not lock a shop!");
            }
            event.setCancelled(true);
        }
    }
}
