package org.morticia.portalswords.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class Riftstalker extends SwordItem {
    public Riftstalker(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient()) {
            return TypedActionResult.consume(this.asItem().getDefaultStack());
        }
        if (world instanceof ServerWorld && !player.hasVehicle() && !player.hasPassengers() && player.canUsePortals()) {
            RegistryKey<World> registryKey = world.getRegistryKey() == World.END ? World.OVERWORLD : World.END;
            ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
            if (serverWorld == null) {
                return TypedActionResult.pass(this.asItem().getDefaultStack());
            }

            player.moveToWorld(serverWorld);
            return TypedActionResult.consume(this.asItem().getDefaultStack());
        }
        return TypedActionResult.pass(this.asItem().getDefaultStack());
    }
}
