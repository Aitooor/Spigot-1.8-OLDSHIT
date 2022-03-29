package org.bukkit.event.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

/**
 * Called when an entity's active potion effect is extended or upgraded.
 *
 * Handlers of {@link PotionEffectAddEvent} will also receive this event.
 */
public class PotionEffectExtendEvent extends PotionEffectAddEvent {

    private final PotionEffect oldEffect;

    public PotionEffectExtendEvent(final LivingEntity entity, final PotionEffect effect, final PotionEffect oldEffect, final EffectAddReason cause) {
        super(entity, effect, cause);
        this.oldEffect = oldEffect;
    }

    /**
     * Get the state of the potion effect prior to the change
     */
    public PotionEffect getOldEffect() {
        return oldEffect;
    }
}
