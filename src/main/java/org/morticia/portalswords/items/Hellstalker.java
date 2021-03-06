package org.morticia.portalswords.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.morticia.portalswords.util.FindHighestPoint;

public class Hellstalker extends SwordItem {
    public Hellstalker(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setOnFireFor(7);
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient()) {
            return TypedActionResult.consume(this.asItem().getDefaultStack());
        }
        if (world instanceof ServerWorld && !player.hasVehicle() && !player.hasPassengers() && player.canUsePortals()) {
            ServerWorld serverWorld = (ServerWorld)world;
            MinecraftServer minecraftServer = serverWorld.getServer();
            RegistryKey<World> registryKey = world.getRegistryKey() == World.NETHER ? World.OVERWORLD : World.NETHER;
            ServerWorld serverWorld2 = minecraftServer.getWorld(registryKey);

            if (serverWorld2 == null) {
                return TypedActionResult.pass(this.asItem().getDefaultStack());
            }

            int y = FindHighestPoint.findHighestPoint(serverWorld2, 0, 0);

            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    serverWorld2.setBlockState(new BlockPos(x, y, z), Blocks.NETHER_BRICKS.getDefaultState());
                }
            }

            for (int i = y + 1; i < y + 3; i++) {
                for (int x = -2; x <= 2; x++) {
                    for (int z = -2; z <= 2; z++) {
                        serverWorld2.setBlockState(new BlockPos(x, i, z), Blocks.AIR.getDefaultState());
                    }
                }
            }

            TeleportTarget target = new TeleportTarget(new Vec3d(0, y + 2, 0), player.getVelocity(), player.prevYaw, player.prevPitch);
            if (player instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) player).teleport(serverWorld2, target.position.x, target.position.y, target.position.z, target.yaw, target.pitch);
            }

            return TypedActionResult.consume(this.asItem().getDefaultStack());
        }
        return TypedActionResult.pass(this.asItem().getDefaultStack());
    }
}
