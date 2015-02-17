package com.fantasycraft.forgepermittor.update;

import com.fantasycraft.forgepermittor.ForgePermittor;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by thomas on 9/2/2014.
 */
public class UpdateChecker extends BukkitRunnable {

    @Getter
    ForgePermittor forgePermittor;

    public UpdateChecker(ForgePermittor forgePermittor){
        this.forgePermittor = forgePermittor;
    }

    private static boolean ListenerRegistered = false;

    @Override
    public void run() {
        // update disabled
    	/*try {
            String newversion = new Scanner(new URL("http://vps28166.vps.ovh.ca/update/").openStream(), "UTF-8").useDelimiter("\\A").next().replace("\n", "");
            if (!newversion.equalsIgnoreCase(getForgePermittor().getDescription().getVersion())){
                getForgePermittor();
				ForgePermittor.log("New Update Available: " + newversion + " Current version: " + getForgePermittor().getDescription().getVersion(), false);
                getForgePermittor().setIsuptodate(false);
                if (!ListenerRegistered) {
                    getForgePermittor().getServer().getPluginManager().registerEvents(new UpdateNotifierListener(), getForgePermittor());
                    ListenerRegistered = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

	public ForgePermittor getForgePermittor() {
		return forgePermittor;
	}

	public static boolean isListenerRegistered() {
		return ListenerRegistered;
	}


}
