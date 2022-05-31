package com.github.retrooper.packetevents.protocol.effect;

import com.github.retrooper.packetevents.protocol.effect.type.EffectType;
import org.jetbrains.annotations.NotNull;

public class Effect {
    private EffectType type;

    public Effect(@NotNull final EffectType type) {
        this.type = type;
    }

    public EffectType getType() {
        return type;
    }

    public void setType(EffectType type) {
        this.type = type;
    }
}
