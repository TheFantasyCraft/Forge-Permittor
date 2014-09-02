package com.fantasycraft.forgepermittor;

import com.fantasycraft.forgepermittor.info.ItemValidator;
import com.fantasycraft.forgepermittor.listeners.DeathMessageListener;
import com.fantasycraft.forgepermittor.listeners.FakePlayerHandler;
import com.fantasycraft.forgepermittor.listeners.ProtectionListener;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import com.fantasycraft.forgepermittor.protection.ProtectionManager;
import com.fantasycraft.forgepermittor.protection.plugins.GriefProtectionPlugin;
import com.fantasycraft.forgepermittor.protection.plugins.TownyPlugin;
import com.fantasycraft.forgepermittor.protection.plugins.WorldguardPlugin;
import com.fantasycraft.forgepermittor.update.UpdateChecker;
import com.palmergames.bukkit.towny.Towny;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import lombok.Setter;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
    @Getter
    private ConfigInfo configInfo;

    @Getter@Setter
    private boolean isuptodate;

    @Getter
    DeathMessageListener deathMessageListener;
    @Getter
    FakePlayerHandler fakePlayerHandler;
    @Getter
    ProtectionListener protectionListener;


    public static void log(String string, boolean isdebug){
        if (getInstance().getConfigInfo().isDebug() && isdebug)
            getInstance().getLogger().info("[DEBUG] " + string);
        if (!isdebug)
            getInstance().getLogger().info(string);
    }

    public ForgePermittor(){
        instance = this;
    }

    @Override
    public void onEnable() {
        this.LoadConfiguration();

        try {
            this.nmsResolver = new NMSResolver();
        } catch (Exception e) {
            log("Failed to get nms values this protection will now disable", false);
            e.printStackTrace();
            setEnabled(false);
            return;
        }
        this.itemValidator = new ItemValidator(getNmsResolver());
        this.protectionManager = new ProtectionManager();

        Reload();

        new UpdateChecker(this).runTaskAsynchronously(this);

        this.PrintNMSdata();
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        configInfo = new ConfigInfo(getConfig());
    }

    private void Reload(){
        if (getConfigInfo().isProtection()) {
            log("Protection enabled!", false);
            RegisterPlugins();
            if (getProtectionListener() == null) {
                protectionListener = new ProtectionListener(getProtectionManager(), getItemValidator());
                this.getServer().getPluginManager().registerEvents(getProtectionListener(), this);
            } else
                getProtectionListener().setEnabled(true);
        }
        else {
            log("Protection disabled!", false);
            if (getProtectionListener() != null)
                getProtectionListener().setEnabled(false);
        }

        if (getConfigInfo().isDeathMessages()){
            if (getDeathMessageListener() == null){
                deathMessageListener = new DeathMessageListener();
                this.getServer().getPluginManager().registerEvents( getDeathMessageListener(), this );
            }
            else
                getDeathMessageListener().setEnabled(true);
        }
        else
            if (getProtectionListener() != null)
                getProtectionListener().setEnabled(false);

        if (getConfigInfo().isHandlefakeplayers()){
            if (getFakePlayerHandler() == null){
                fakePlayerHandler = new FakePlayerHandler(getProtectionManager());
                this.getServer().getPluginManager().registerEvents( getFakePlayerHandler(), this);
            }else
                getFakePlayerHandler().setEnabled(true);
        }
        else
            if (getFakePlayerHandler() != null)
                getFakePlayerHandler().setEnabled(false);


    }

    private void RegisterPlugins(){
        Towny towny = ((Towny)getServer().getPluginManager().getPlugin("Towny"));
        if (towny != null) {
            if (getConfigInfo().isTowny()) {
                log("Towny Registered!", false);
                getProtectionManager().RegisterPlugin(new TownyPlugin(towny));
            }else{
                log("Towny Found but disabled!", false);
                getProtectionManager().UnloadPlugin("Towny");
            }
        }

        WorldGuardPlugin worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
        if (worldGuard != null) {
            if (getConfigInfo().isWorldguard()) {
                log("WorldGuard Registered!", false);
                getProtectionManager().RegisterPlugin(new WorldguardPlugin(worldGuard));
            }
            else {
                log("WorldGuard Found but disabled!", false);
                getProtectionManager().UnloadPlugin("WorldGuard");
            }
        }

        GriefPrevention griefPrevention = (GriefPrevention) getServer().getPluginManager().getPlugin("GriefPrevention");
        if (griefPrevention != null) {
            if (getConfigInfo().isGriefpreventions()) {
                log("GriefPrevention Registered!", false);
                getProtectionManager().RegisterPlugin(new GriefProtectionPlugin());
            }
            else{
                log("GriefPrevention Found but Disabled!", false);
                getProtectionManager().UnloadPlugin("GriefPrevention");
            }
        }

        //Todo: Add Factions
        /*
        if (getServer().getPluginManager().getPlugin("Factions") != null) {
            log("Factions Registered!", false);
            getProtectionManager().RegisterPlugin(new FactionsPlugin());
        }*/
    }

    private void PrintNMSdata(){
        log("Itemstackclass: " + getNmsResolver().getItemStack().getName(), true);
        log("BlockClass: " + getNmsResolver().getBlock().getName(), true);
        log("ItemClass: " + getNmsResolver().getItem().getName(), true);
        log("BlockContainer: " + getNmsResolver().getBlockContainer().getName(), true);
        log("ItemFood: "  + getNmsResolver().getItemFood().getName(), true);
        log("ItemSword: " + getNmsResolver().getItemSword().getName(), true);
        log("ItemBlock: " + getNmsResolver().getItemBlock().getName(), true);
        log("NBTTagCompound: " + getNmsResolver().getNBTTagCompound().getName(), true);
        log("IInventory: " + getNmsResolver().getIInventory().getName(), true);
    }

    private void LoadConfiguration(){
        getConfig().addDefault("protection.enabled", true);
        getConfig().addDefault("protection.plugins.towny", true);
        getConfig().addDefault("protection.plugins.worldguard", true);
        getConfig().addDefault("protection.plugins.griefprevention", true);
        getConfig().addDefault("other.HandleFakePlayers", true);
        getConfig().addDefault("other.FixBrokenDeathMessages", true);
        getConfig().addDefault("other.debug", false);
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.configInfo = new ConfigInfo(getConfig());
        System.out.println(getConfigInfo());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0)
            return false;

        if (args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            Reload();
            System.out.println(getConfigInfo());
            sender.sendMessage(ChatColor.GREEN + "Plugin reloaded!");
        }
        if (args[0].equalsIgnoreCase("checkupdate")){
            new UpdateChecker(this).runTaskAsynchronously(this);
        }
        return true;

    }
}
