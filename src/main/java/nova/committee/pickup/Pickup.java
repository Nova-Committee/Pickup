package nova.committee.pickup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Pickup.MOD_ID)
public class Pickup {
    public static final String MOD_ID = "pickup";

    private static final Logger LOGGER = LogManager.getLogger();

    public Pickup() {
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Pickup mod has set up!");
    }
}
