package com.github.retrooper.packetevents.protocol.effect;

import com.github.retrooper.packetevents.protocol.effect.type.IEffect;
import org.jetbrains.annotations.NotNull;

public class EffectType {
    private IEffect effect;

    public EffectType(@NotNull final IEffect effect) {
        this.effect = effect;
    }

    public IEffect getEffect() {
        return effect;
    }

    public void setEffect(IEffect effect) {
        this.effect = effect;
    }

    public Type getType() {
        return Type.getType(effect.getName().getKey());
    }

    public enum Type {
        SOUND(),
        VISUAL();

        public static Type getType(String key) {
            if (key.equals("sound")) {
                return SOUND;
            } else if (key.equals("visual")) {
                return VISUAL;
            }
            return null;
        }
    }
}
