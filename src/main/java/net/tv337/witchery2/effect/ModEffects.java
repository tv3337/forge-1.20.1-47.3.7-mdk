package net.tv337.witchery2.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tv337.witchery2.Witchery;

public class ModEffects {
    public static final DeferredRegister <MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Witchery.MOD_ID);

    public static final RegistryObject <MobEffect> GRUES_PREY = MOB_EFFECTS.register("gruesprey",
            () -> new gruesprey(MobEffectCategory.NEUTRAL, 0x333333).addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "7107DE5E-7CE8-4030-948E-514C1F160890", -.25F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register (IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
