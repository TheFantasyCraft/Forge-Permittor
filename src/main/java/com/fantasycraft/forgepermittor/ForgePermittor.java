package com.fantasycraft.forgepermittor;

import com.fantasycraft.forgepermittor.listeners.ProtectionListener;
import com.fantasycraft.forgepermittor.nms.ItemValidator;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;
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

    private static boolean debug = true;

    public static void log(String string, boolean isdebug){
        if (debug && isdebug)
            getInstance().getLogger().info("[DEBUG] " + string);
        if (!debug)
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
            log("Failed to get nms values this plugin will now disable", false);
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
        this.itemValidator = new ItemValidator(getNmsResolver());

        this.getServer().getPluginManager().registerEvents( new ProtectionListener(), this );

    }
}
