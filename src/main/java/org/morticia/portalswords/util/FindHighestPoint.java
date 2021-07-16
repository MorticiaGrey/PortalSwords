package org.morticia.portalswords.util;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FindHighestPoint {
    public static int findHighestPoint(ServerWorld serverWorld, int x, int z) {
        serverWorld.setChunkForced(x, z, true);
        int y = 80;
        while (true) {
            if (serverWorld.getBlockState(new BlockPos(x, y, z)).isAir()) {
                y--;
            } else {
                break;
            }
        }
        return y;
    }
}
