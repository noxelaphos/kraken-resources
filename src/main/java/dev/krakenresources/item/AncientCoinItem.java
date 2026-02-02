package dev.krakenresources.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AncientCoinItem extends Item {
    public AncientCoinItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        
        if (!world.isClientSide) {
            // Play sound minecraft:item.spyglass.use
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SPYGLASS_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

            // Remove all status effects
            user.removeAllEffects();
            
            // Consume 1 item
            if (!user.getAbilities().instabuild) {
                stack.shrink(1);
            }

            // Apply 2 second (40 ticks) cooldown
            user.getCooldowns().addCooldown(this, 40);
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && player.getCooldowns().isOnCooldown(this)) {
            return false;
        }

        // Apply slowness 10 (amplifier 9) for 1 second (20 ticks)
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 9));
        
        // Play sound minecraft:ui.stonecutter.take_result
        attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.PLAYERS, 1.0F, 1.0F);

        // Consume 1 item from the stack and apply cooldown if attacker is a player
        if (attacker instanceof Player player) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            // Apply 2 second (40 ticks) cooldown
            player.getCooldowns().addCooldown(this, 40);
        } else {
            stack.shrink(1);
        }

        return super.hurtEnemy(stack, target, attacker);
    }
}
