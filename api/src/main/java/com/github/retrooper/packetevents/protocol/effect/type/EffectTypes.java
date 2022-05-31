package com.github.retrooper.packetevents.protocol.effect.type;

import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.TypesBuilder;
import com.github.retrooper.packetevents.util.TypesBuilderData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EffectTypes {
    private static final Map<String, EffectType> EFFECT_TYPE_MAPPINGS;
    private static final Map<Byte, Map<Integer, EffectType>> EFFECT_TYPE_ID_MAPPINGS;
    private static final TypesBuilder TYPES_BUILDER;

    static {
        EFFECT_TYPE_MAPPINGS = new HashMap<>();
        EFFECT_TYPE_ID_MAPPINGS = new HashMap<>();
        TYPES_BUILDER = new TypesBuilder("effect/effect_type_mappings",
                ClientVersion.V_1_16,
                ClientVersion.V_1_17,
                ClientVersion.V_1_18);
    }

    public static EffectType define(@NotNull final String key) {
        final TypesBuilderData data = TYPES_BUILDER.define(key);
        final EffectType effectType = new EffectType() {
            @Override
            public ResourceLocation getName() {
                return data.getName();
            }

            @Override
            public int getId(ClientVersion version) {
                final int index = TYPES_BUILDER.getDataIndex(version);
                return data.getData()[index];
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof EffectType) {
                    return getName() == ((EffectType) obj).getName();
                }
                return false;
            }
        };

        EFFECT_TYPE_MAPPINGS.put(effectType.getName().toString(), effectType);
        for (ClientVersion version : TYPES_BUILDER.getVersions()) {
            final int index = TYPES_BUILDER.getDataIndex(version);
            final Map<Integer, EffectType> typeIdMap = EFFECT_TYPE_ID_MAPPINGS.computeIfAbsent((byte) index, k -> new HashMap<>());
            typeIdMap.put(effectType.getId(version), effectType);
        }
        return effectType;
    }

    @Nullable
    public static EffectType getByName(@NotNull final String name) {
        return EFFECT_TYPE_MAPPINGS.get(name);
    }

    @Nullable
    public static EffectType getById(@NotNull final ClientVersion version, final int id) {
        final int index = TYPES_BUILDER.getDataIndex(version);
        final Map<Integer, EffectType> typeIdMap = EFFECT_TYPE_ID_MAPPINGS.get((byte) index);
        return typeIdMap.get(id);
    }

    public static final EffectType AMBIENT_BASALT_DELTAS_ADDITIONS = define("ambient_basalt_deltas_additions");

    public static Collection<EffectType> values = EFFECT_TYPE_MAPPINGS.values();
}
