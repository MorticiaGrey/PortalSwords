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

public class Firestalker extends SwordItem {
    public Firestalker(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    // Keep in mind, code is run on server AND client
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient()) {
            return TypedActionResult.consume(this.asItem().getDefaultStack());
        }
        if (world instanceof ServerWorld && !player.hasVehicle() && !player.hasPassengers() && player.canUsePortals()) {
            /*RegistryKey<World> registryKey = world.getRegistryKey() == World.NETHER ? World.OVERWORLD : World.NETHER;
            ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
            if (serverWorld == null) {
                return TypedActionResult.pass(this.asItem().getDefaultStack());
            }*/

            ServerWorld serverWorld = (ServerWorld)world;
            MinecraftServer minecraftServer = serverWorld.getServer();
            RegistryKey<World> registryKey = world.getRegistryKey() == World.NETHER ? World.OVERWORLD : World.NETHER;
            ServerWorld serverWorld2 = minecraftServer.getWorld(registryKey);

            if (serverWorld2 != null && minecraftServer.isNetherAllowed() && !player.hasVehicle()) {
                world.getProfiler().push("portal");
                player.resetNetherPortalCooldown();
                player.moveToWorld(serverWorld2);
                world.getProfiler().pop();
            }

            //player.moveToWorld(serverWorld);
            return TypedActionResult.consume(this.asItem().getDefaultStack());
        }
        return TypedActionResult.pass(this.asItem().getDefaultStack());
    }
}
