package com.fantasycraft.forgepermittor;

import com.fantasycraft.forgepermittor.info.ItemValidator;
import com.fantasycraft.forgepermittor.listeners.DeathMessageListener;
import com.fantasycraft.forgepermittor.listeners.ProtectionListener;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import com.fantasycraft.forgepermittor.protection.plugins.GriefProtectionPlugin;
import com.fantasycraft.forgepermittor.protection.plugins.LwcPlugin;
import com.fantasycraft.forgepermittor.protection.plugins.TownyPlugin;
import com.fantasycraft.forgepermittor.protection.plugins.WorldguardPlugin;
import com.fantasycraft.forgepermittor.protection.ProtectionManager;
import com.griefcraft.lwc.LWCPlugin;
import com.palmergames.bukkit.towny.Towny;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by thomas on 8/16/2014.
 */
public class ForgePermittor extends JavaPlugin {

    @Getter
    private static ForgePermittor instance;
    @Getter
    private NMSResolver nmsResolver;
    @Getter
    private ItemValidator itemValidator;
    @Getter
    private ProtectionManager protectionManager;


    private static boolean debug = true;

    public static void log(String string, boolean isdebug){
        if (debug && isdebug)
            getInstance().getLogger().info("[DEBUG] " + string);
        if (!isdebug)
            getInstance().getLogger().info(string);
    }

    public ForgePermittor(){
        instance = this;
    }

    @Override
    public void onEnable() {

        try {
            this.nmsResolver = new NMSResolver();
        } catch (Exception e) {
            log("Failed to get nms values this protection will now disable", false);
            e.printStackTrace();
            setEnabled(false);
            return;
        }
        log("Itemstackclass: " + getNmsResolver().getItemStack().getName(), true);
        log("BlockClass: " + getNmsResolver().getBlock().getName(), true);
        log("ItemClass: " + getNmsResolver().getItem().getName(), true);
        log("BlockContainer: " + getNmsResolver().getBlockContainer().getName(), true);
        log("ItemFood: "  + getNmsResolver().getItemFood().getName(), true);
        log("ItemSword: " + getNmsResolver().getItemSword().getName(), true);
        log("ItemBlock: " + getNmsResolver().getItemBlock().getName(), true);
        log("NBTTagCompound: " + getNmsResolver().getNBTTagCompound().getName(), true);
        log("IInventory: " + getNmsResolver().getIInventory().getName(), true);
        this.itemValidator = new ItemValidator(getNmsResolver());


        this.RegisterPlugins();

        this.getServer().getPluginManager().registerEvents( new ProtectionListener(getProtectionManager(), getItemValidator()), this );
        this.getServer().getPluginManager().registerEvents( new DeathMessageListener(), this );
    }


    private void RegisterPlugins(){

        this.protectionManager = new ProtectionManager();

        Towny towny = ((Towny)getServer().getPluginManager().getPlugin("Towny"));
        if (towny != null) {
            log("Towny Registered!", false);
            getProtectionManager().RegisterPlugin(new TownyPlugin(towny));
        }

        WorldGuardPlugin worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
        if (worldGuard != null) {
            log("WorldGuard Registered!", false);
            getProtectionManager().RegisterPlugin(new WorldguardPlugin(worldGuard));
        }

        GriefPrevention griefPrevention = (GriefPrevention) getServer().getPluginManager().getPlugin("GriefPrevention");
        if (griefPrevention != null) {
            log("GriefPrevention Registered!", false);
            getProtectionManager().RegisterPlugin(new GriefProtectionPlugin());
        }

        LWCPlugin lwcPlugin = (LWCPlugin) getServer().getPluginManager().getPlugin("LWC");
        if (griefPrevention != null) {
            log("GriefPrevention Registered!", false);
            getProtectionManager().RegisterPlugin(new LwcPlugin());
        }






    }
}
