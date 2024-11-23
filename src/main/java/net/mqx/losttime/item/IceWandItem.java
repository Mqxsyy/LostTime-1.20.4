package net.mqx.losttime.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.mqx.losttime.entity.projectile.IceSpearProjectileEntity;

import java.util.List;

public class IceWandItem extends Item {
    public IceWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            IceSpearProjectileEntity projectile = new IceSpearProjectileEntity(world, user);
            projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);

            if (Screen.hasShiftDown()) {
                Box box = new Box(user.getBlockPos()).expand(64, 64, 64);
                List<IceSpearProjectileEntity> entities = world.getEntitiesByClass(
                        IceSpearProjectileEntity.class, box, EntityPredicates.VALID_ENTITY);

                for (IceSpearProjectileEntity entity : entities) {
                    if (entity.getOwner() == user) {
                        entity.Explode();
                    }
                }
            } else {
                world.spawnEntity(projectile);
            }
        }

        return TypedActionResult.pass(user.getStackInHand(hand));

    }
}
