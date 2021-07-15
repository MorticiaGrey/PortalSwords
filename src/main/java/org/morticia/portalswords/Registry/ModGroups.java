package org.morticia.portalswords.Registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.morticia.portalswords.Morticia.portalswords;

public class ModGroups {
    public static final ItemGroup PORTAL_SWORDS_ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(portalswords.MOD_ID, "portal_swords_item_group"),
            // TODO: 7/14/21 Update this to mod item, probably ender sword
            () -> new ItemStack(Items.NETHERITE_SWORD)
    );
}
