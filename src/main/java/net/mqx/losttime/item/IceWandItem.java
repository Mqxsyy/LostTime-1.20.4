package net.mqx.losttime.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.mqx.losttime.entity.ModEntityType;
import net.mqx.losttime.entity.projectile.IceSpearProjectileEntity;

public class IceWandItem extends Item {
    public IceWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            IceSpearProjectileEntity projectile = new IceSpearProjectileEntity(ModEntityType.ICE_SPEAR, world);
            world.spawnEntity(projectile);
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
