package com.github.retrooper.packetevents.protocol.effect.type;

import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;

public interface EffectType extends MappedEntity {
    String getType();
    int getTypeId();
}
