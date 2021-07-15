package org.morticia.portalswords.Morticia;

import net.fabricmc.api.ModInitializer;
import org.morticia.portalswords.Registry.ModItems;

public class portalswords implements ModInitializer {
    public static String MOD_ID = "portalswords";

    @Override
    public void onInitialize() {
        ModItems.initItems();
    }
}
