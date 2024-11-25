package net.mqx.losttime.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class LostTimeUtils {
    public static Random random = new Random();

    public static double GetDistance(BlockPos a, BlockPos b) {
        double x = Math.pow(a.getX() - b.getX(), 2);
        double y = Math.pow(a.getY() - b.getY(), 2);
        double z = Math.pow(a.getZ() - b.getZ(), 2);
        return Math.sqrt(x + y + z);
    }

    public static BlockPos ToBlockPos(Vec3d pos) {
        int x = (int) Math.floor(pos.x);
        int y = (int) Math.floor(pos.y);
        int z = (int) Math.floor(pos.z);

        return new BlockPos(x, y, z);
    }
}
